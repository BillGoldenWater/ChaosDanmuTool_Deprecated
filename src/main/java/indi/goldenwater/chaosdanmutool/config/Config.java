package indi.goldenwater.chaosdanmutool.config;

public class Config {
    public DanmuReceiverConfig danmuReceiver;
    public InternalBrowserConfig internalBrowser;
    public DanmuViewConfig internalViewConfig;
    public DanmuViewConfig otherViewConfig;

    public static class DanmuReceiverConfig {
        public String serverUrl;
        public int roomid;
        public int heartBeatPeriod;
    }

    public static class InternalBrowserConfig {
        public WebSocketServerConfig webSocketServer;
        public int width;
        public int height;
        public double posX;
        public double posY;
    }

    public static class WebSocketServerConfig {
        public int port;
    }

    public static class DanmuViewConfig {
        public boolean statusBarDisplay;
        public int maxDanmuNumber;
        public DanmuViewStyleConfig style;
    }

    public static class DanmuViewStyleConfig {
        public int outerMargin;
        public Color backgroundColor;
        public VipIconStyle vipIcon;
        public SVipIconStyle sVipIcon;
        public AdminIconStyle adminIcon;
        public UserNameStyle userName;
        public DanmuContentStyle danmuContent;
    }

    public static class Color {
        public int red;
        public int green;
        public int blue;
        public double alpha;
    }

    public static class VipIconStyle {
        public String text;
        public int textColor;
        public int backgroundColor;
        public int borderColor;
    }

    public static class SVipIconStyle {
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
