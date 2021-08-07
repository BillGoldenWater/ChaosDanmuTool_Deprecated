package indi.goldenwater.chaosdanmutool.config;

public class Config {
    public DanmuReceiverConfig danmuReceiver;
    public DanmuViewConfig danmuView;

    public static class DanmuReceiverConfig {
        public String serverUrl;
        public int roomid;
    }

    public static class WebSocketServerConfig {
        public int port;
    }

    public static class DanmuViewConfig {
        public WebSocketServerConfig webSocketServer;
        public int width;
        public int height;
        public int maxDanmuNumber;
    }
}
