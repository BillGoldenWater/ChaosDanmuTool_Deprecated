package indi.goldenwater.chaosdanmutool.interfaces;

public class Config {
    public WebSocketServerConfig webSocketServer;
    public DanmuViewConfig danmuView;

    public static class WebSocketServerConfig {
        public int port;
    }

    public static class DanmuViewConfig {
        public int maxDanmuNumber;
    }
}
