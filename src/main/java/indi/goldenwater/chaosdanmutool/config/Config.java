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
        public DanmuViewStyleConfig style;
    }

    public static class DanmuViewStyleConfig {
        public VipIconStyle vipIcon;
        public AdminIconStyle adminIcon;
        public UserNameStyle userName;
        public DanmuContentStyle danmuContent;
    }

    public static class VipIconStyle {
        public String text;
        public int textColor;
        public int backgroundColor;
        public int borderColor;
    }

    public static class AdminIconStyle {
        public String text;
        public int textColor;
        public int backgroundColor;
        public int borderColor;
    }

    public static class UserNameStyle {
        public int textColor;
    }

    public static class DanmuContentStyle {
        public int textColor;
    }
}
