package indi.goldenwater.chaosdanmutool.config;

public class Config {
    public WebSocketServerConfig webSocketServer;
    public DanmuViewConfig danmuView;

    public static class WebSocketServerConfig {
        public int port;
    }

    public static class DanmuViewConfig {
        public int width;
        public int height;
        public int maxDanmuNumber;
    }
}
