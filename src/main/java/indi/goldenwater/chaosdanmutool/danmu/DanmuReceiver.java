package indi.goldenwater.chaosdanmutool.danmu;

import com.nixxcode.jvmbrotli.common.BrotliLoader;
import com.nixxcode.jvmbrotli.dec.BrotliDecoderChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.zip.InflaterOutputStream;

public class DanmuReceiver extends WebSocketClient {
    private static final short headerLength = 4 + 2 + 2 + 4 + 4;

    private static DanmuReceiver instance;
    private static HeartBeat heartBeat;

    private final Logger logger = LogManager.getLogger(DanmuReceiver.class);
    private final int roomid;
    private final DanmuServer danmuServer;

    private int updatePeriod;

    public DanmuReceiver(String url, int updatePeriod, int roomid, DanmuServer danmuServer) throws URISyntaxException {
        super(new URI(url));
        this.updatePeriod = updatePeriod;
        this.roomid = roomid;
        this.danmuServer = danmuServer;

        if (instance != null) instance.close();
        instance = this;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        send(pack(Data.join(roomid, 3, "web")));
        startHeartBeat(this);
    }

    @Override
    public void onMessage(String s) {
    }

    @Override
    public void onMessage(ByteBuffer s) {
        List<Data> dataList = unpackCompressed(unpack(s));

        for (Data data : dataList) {
            if (data.protocolType != 0) {
                DanmuProcessor.decodeError(data);
                return;
            }
            switch (data.opCode) {
                case OpCode.heartBeatResponse: {
                    DanmuProcessor.updateActivity(danmuServer, ByteBuffer.wrap(data.body).getInt());
                    break;
                }
                case OpCode.joinSuccess: {
                    DanmuProcessor.connectSuccess(danmuServer);
                    break;
                }
                case OpCode.message: {
                    for (String jsonStr : data.getSplitJsonStr()) {
                        DanmuProcessor.processCommand(jsonStr, danmuServer);
                    }
                    break;
                }
                default: {
                    DanmuProcessor.decodeError(data);
                    break;
                }
            }
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        logger.warn(String.format(
                "[DanmuReceiver] closed with exit code %d, additional info: %s by remote: %b",
                code,
                reason,
                remote));
        stopHeartBeat();
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }

    public int getUpdatePeriod() {
        return updatePeriod;
    }

    public void setUpdatePeriod(int updatePeriod) {
        this.updatePeriod = updatePeriod;
    }

    public static ByteBuffer pack(Data originData) {
        ByteBuffer packagedData = ByteBuffer.allocate(headerLength + originData.bodyLength);
        packagedData.order(ByteOrder.BIG_ENDIAN);

        packagedData.putInt(originData.bodyLength + DataOffset.body)
                .putShort(headerLength)
                .putShort(originData.protocolType)
                .putInt(originData.opCode)
                .putInt(1)
                .put(originData.body);

        packagedData.flip();
        return packagedData;
    }

    public static Data unpack(ByteBuffer packedData) {
        return unpack(packedData, 0);
    }

    public static Data unpack(ByteBuffer packedData, int offset) {
        int packetLength = packedData.getInt(DataOffset.packetLength + offset);
        short protocolType = packedData.getShort(DataOffset.protocolType + offset);
        int opCode = packedData.getInt(DataOffset.opCode + offset);
        int bodyLength = packetLength - headerLength;

        byte[] body = new byte[bodyLength];
        packedData.position(DataOffset.body + offset);
        for (int i = 0; i < bodyLength; i++) {
            body[i] = packedData.get(DataOffset.body + i + offset);
        }

        return new Data(body, protocolType, opCode);
    }

    public static List<Data> unpackContinuous(ByteBuffer byteBuffer) {
        List<Data> dataList = new ArrayList<>();
        Data tempData = unpack(byteBuffer, 0);

        dataList.add(tempData);

        int dataLength = tempData.packageLength;
        while (dataLength < byteBuffer.limit()) {
            tempData = unpack(byteBuffer, dataLength);
            dataLength += tempData.packageLength;
            dataList.add(tempData);
        }

        return dataList;
    }

    public static List<Data> unpackCompressed(Data data) {
        int protocolType = data.getProtocolType();

        switch (protocolType) {
            case ProtocolType.compressed_1: {
                ByteBuffer inflatedByteBuffer = inflateData(ByteBuffer.wrap(data.body));
                return unpackContinuous(inflatedByteBuffer);
            }
            case ProtocolType.compressed_2: {
                try {
                    if (BrotliLoader.isBrotliAvailable()) {
                        ByteBuffer byteBuffer = ByteBuffer.wrap(
                                BrotliDecoderChannel.decompress(data.body));
                        return unpackContinuous(byteBuffer);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            default: {
                return new ArrayList<>(Collections.singleton(data));
            }
        }
    }

    public static ByteBuffer inflateData(ByteBuffer byteBuffer) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        InflaterOutputStream inflaterOutputStream = new InflaterOutputStream(byteArrayOutputStream);

        try {
            inflaterOutputStream.write(byteBuffer.array());
            return ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                byteArrayOutputStream.close();
                inflaterOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ByteBuffer.allocate(1);
    }

    public static void startHeartBeat(DanmuReceiver receiver) {
        stopHeartBeat();
        heartBeat = new HeartBeat(receiver);
        heartBeat.start();
    }

    public static void stopHeartBeat() {
        if (heartBeat == null) return;
        heartBeat.end();
    }

    public static class HeartBeat extends Thread {
        private boolean running = true;
        private final DanmuReceiver danmuReceiver;

        public HeartBeat(DanmuReceiver danmuReceiver) {
            this.danmuReceiver = danmuReceiver;
        }

        @Override
        @SuppressWarnings("BusyWait")
        public void run() {
            while (this.running) {
                danmuReceiver.send(DanmuReceiver.pack(Data.heartBeat()));
                try {
                    Thread.sleep(danmuReceiver.getUpdatePeriod() * 1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void end() {
            running = false;
        }
    }

    public static class Data {
        private final short protocolType;
        private final int opCode;
        private final byte[] body;
        private final int bodyLength;
        private final int packageLength;

        public Data(byte[] body, int opCode) {
            this(body, ProtocolType.json, opCode);
        }

        public Data(byte[] body, short protocolType, int opCode) {
            this.body = body;
            this.protocolType = protocolType;
            this.opCode = opCode;
            this.bodyLength = body.length;
            this.packageLength = bodyLength + headerLength;
        }

        public byte[] getBody() {
            return body;
        }

        public short getProtocolType() {
            return protocolType;
        }

        public int getOpCode() {
            return opCode;
        }

        public int getBodyLength() {
            return bodyLength;
        }

        public int getPackageLength() {
            return packageLength;
        }

        public List<String> getSplitJsonStr() {
            return Arrays.asList(new String(body).split("/[\\x00-\\x1f]+/"));
        }

        @Override
        public String toString() {
            String message = "Protocol Type: " + protocolType + " " +
                    "Operation Code: " + opCode + " " +
                    "Body: ";

            if (protocolType == 0) {
                switch (opCode) {
                    case OpCode.join:
                    case OpCode.joinSuccess:
                    case OpCode.message:
                        message += new String(body);
                        break;
                    case OpCode.heartBeatResponse:
                        message += ByteBuffer.wrap(body).getInt();
                        break;
                    default:
                        message += Arrays.toString(body);
                        break;
                }
            } else {
                message += Arrays.toString(body);
            }


            return message;
        }

        public static Data join(int roomId, int protocolVersion, String platform) {
            return new Data(
                    String.format("{\"roomid\": %d, " +
                            "\"protover\": %d, " +
                            "\"platform\": \"%s\"}", roomId, protocolVersion, platform).getBytes(StandardCharsets.UTF_8),
                    OpCode.join
            );
        }

        public static Data heartBeat() {
            return new Data(
                    "".getBytes(),
                    OpCode.heartBeat
            );
        }
    }

    public static class OpCode {
        public static final int heartBeat = 2;
        public static final int heartBeatResponse = 3;
        public static final int message = 5;
        public static final int join = 7;
        public static final int joinSuccess = 8;
    }

    public static class ProtocolType {
        public static final short json = 0;
        public static final short activity = 1;
        public static final short compressed_1 = 2;
        public static final short compressed_2 = 3;
    }

    public static class DataOffset {
        private static final int packetLength = 0;
        private static final int headerLength = packetLength + 4;
        private static final int protocolType = headerLength + 2;
        private static final int opCode = protocolType + 2;
        private static final int sequenceId = opCode + 4;
        private static final int body = sequenceId + 4;
    }
}
