package indi.goldenwater.chaosdanmutool.danmu;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class DanmuServer extends WebSocketServer {
    private final List<WebSocket> webSocketList = new ArrayList<>();

    public DanmuServer(int port) {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        webSocketList.add(webSocket);
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        webSocketList.remove(webSocket);
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
