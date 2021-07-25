package indi.goldenwater.chaosdanmutool.utils;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class DanmuReceiver extends WebSocketClient {
    private static final short headerLength = 4 + 2 + 2 + 4 + 4;

    private static DanmuReceiver instance;
    private static HeartBeat heartBeat;
    private int updatePeriod;

    public DanmuReceiver(String url, int updatePeriod) throws URISyntaxException {
        super(new URI(url));
        this.updatePeriod = updatePeriod;
        if (instance != null) instance.close();
        instance = this;
//        new WebSocket.Builder().buildAsync()
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        send(pack(Data.join(953650, 3, "web")));

        startHeartBeat(this);
        System.out.println("joined.");
    }

    @Override
    public void onMessage(String s) {
        System.out.println("1");
        System.out.println(s);
    }

    @Override
    public void onMessage(ByteBuffer s) {
        System.out.println("receive 1");
        System.out.println(unpack(s));
        System.out.println(Arrays.toString(s.array()));
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
//        System.out.println("closed with exit code " + code + " additional info: " + reason + " by remote:" + remote);
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

        packagedData.putInt(originData.bodyLength)
                .putShort(headerLength)
                .putShort(originData.protocolType)
                .putInt(originData.opCode)
                .putInt(1)
                .put(originData.body);

        packagedData.flip();
        return packagedData;
    }

    public static Data unpack(ByteBuffer packedData) {
        int packetLength = packedData.getInt(DataOffset.packetLength);
        short protocolType = packedData.getShort(DataOffset.protocolType);
        int opCode = packedData.getInt(DataOffset.opCode);
        int bodyLength = packetLength - DataOffset.body;

        byte[] body = new byte[bodyLength];
        packedData.position(DataOffset.body);
        for (int i = 0; i < bodyLength; i++) {
            body[i] = packedData.get(DataOffset.body + i);
        }

        return new Data(body, protocolType, opCode);
    }

    public static void startHeartBeat(DanmuReceiver receiver) {
        stopHeartBeat();
        heartBeat = new HeartBeat(receiver);
        heartBeat.start();
    }

    public static void stopHeartBeat() {
        if (heartBeat == null) return;
        heartBeat.end();
        heartBeat.stop();
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

        public Data(byte[] body, int opCode) {
            this(body, ProtocolType.json, opCode);
        }

        public Data(byte[] body, short protocolType, int opCode) {
            this.body = body;
            this.protocolType = protocolType;
            this.opCode = opCode;
            this.bodyLength = body.length;
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

        public static Data join(int roomId, int protocolVersion, String platform) {
            return new Data(
                    String.format("\"roomid\":%d," +
                            "\"protover\":%d," +
                            "\"platform\":\"%s\"", roomId, protocolVersion, platform).getBytes(),
                    OpCode.join
            );
        }

        public static Data heartBeat() {
            return new Data(
                    "".getBytes(),
                    OpCode.heartBeat
            );
        }

        @Override
        public String toString() {
            String message = "Protocol Type: " + protocolType + " " +
                    "Operation Code: " + opCode + " " +
                    "Body: ";

            if (protocolType == 0) {
                switch (opCode) {
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
