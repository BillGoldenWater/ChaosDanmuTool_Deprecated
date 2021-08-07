package indi.goldenwater.chaosdanmutool.danmu;

import indi.goldenwater.chaosdanmutool.ChaosDanmuTool;
import org.apache.logging.log4j.Logger;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class DanmuServer extends WebSocketServer {
    private static final Logger logger = ChaosDanmuTool.getLogger();

    private final List<WebSocket> webSocketList = new ArrayList<>();

    public DanmuServer(int port) {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        InetSocketAddress remoteAddress = webSocket.getRemoteSocketAddress();
        webSocketList.add(webSocket);
        logger.info(String.format("[DanmuServer] Client %s connected", remoteAddress.toString()));
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        InetSocketAddress remoteAddress = webSocket.getRemoteSocketAddress();
        webSocketList.remove(webSocket);
        logger.info(String.format("[DanmuServer] Client %s closed", remoteAddress.toString()));
    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {

    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {

    }

    @Override
    public void onStart() {

    }

    public void sendAll(String str) {
        for (WebSocket webSocket : webSocketList) {
            webSocket.send(str);
        }
    }
}
