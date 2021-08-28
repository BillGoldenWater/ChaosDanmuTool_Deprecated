package indi.goldenwater.chaosdanmutool.config;

import com.google.gson.Gson;

public class Config {
    public DanmuReceiverConfig danmuReceiver;
    public InternalBrowserConfig internalBrowser;
    public DanmuViewConfig internalViewConfig;
    public DanmuViewConfig otherViewConfig;

    public static Config getDefault() {
        Gson gson = new Gson();
        Config config = new Config();

        config.danmuReceiver = new DanmuReceiverConfig();
        config.danmuReceiver.serverUrl = "wss://broadcastlv.chat.bilibili.com/sub";
        config.danmuReceiver.roomid = 0;
        config.danmuReceiver.heartBeatPeriod = 30;

        config.internalBrowser = new InternalBrowserConfig();
        config.internalBrowser.webSocketServer = new WebSocketServerConfig();
        config.internalBrowser.webSocketServer.port = 25555;
        config.internalBrowser.width = 400;
        config.internalBrowser.height = 600;
        config.internalBrowser.posX = 0;
        config.internalBrowser.posY = 0;

        config.internalViewConfig = new DanmuViewConfig();
        config.internalViewConfig.statusBarDisplay = true;
        config.internalViewConfig.maxDanmuNumber = 100;
        config.internalViewConfig.numberFormat = new NumberFormatConfig();
        config.internalViewConfig.numberFormat.formatActivity = true;
        config.internalViewConfig.numberFormat.formatFansNum = true;
        config.internalViewConfig.style = new DanmuViewStyleConfig();
        config.internalViewConfig.style.bodyMargin = 0;
        config.internalViewConfig.style.listMargin = 5;
        config.internalViewConfig.style.backgroundColor = "#3B3B3B44";
        config.internalViewConfig.style.zoom = 1.0;
        config.internalViewConfig.style.font = "";
        config.internalViewConfig.style.fontWeight = 400;
        config.internalViewConfig.style.lineSpacing = 5;
        config.internalViewConfig.style.giftIconMaxHeight = 30;
        config.internalViewConfig.style.vipIcon = new DanmuViewStyleConfig.TextIconStyle();
        config.internalViewConfig.style.vipIcon.text = "爷";
        config.internalViewConfig.style.vipIcon.textColor = "#FFFFFF";
        config.internalViewConfig.style.vipIcon.backgroundColor = "#BF9C00";
        config.internalViewConfig.style.vipIcon.borderColor = "#A98AC7";
        config.internalViewConfig.style.sVipIcon = new DanmuViewStyleConfig.TextIconStyle();
        config.internalViewConfig.style.sVipIcon.text = "爷";
        config.internalViewConfig.style.sVipIcon.textColor = "#FFFFFF";
        config.internalViewConfig.style.sVipIcon.backgroundColor = "#BF9C00";
        config.internalViewConfig.style.sVipIcon.borderColor = "#A98AC7";
        config.internalViewConfig.style.adminIcon = new DanmuViewStyleConfig.TextIconStyle();
        config.internalViewConfig.style.adminIcon.text = "房";
        config.internalViewConfig.style.adminIcon.textColor = "#FFFFFF";
        config.internalViewConfig.style.adminIcon.backgroundColor = "#D2A25B";
        config.internalViewConfig.style.adminIcon.borderColor = "#DE8402";
        config.internalViewConfig.style.userName = new DanmuViewStyleConfig.TextStyle();
        config.internalViewConfig.style.userName.textColor = "#00AAFF";
        config.internalViewConfig.style.danmuContent = new DanmuViewStyleConfig.TextStyle();
        config.internalViewConfig.style.danmuContent.textColor = "#FFFFFF";

        config.otherViewConfig = gson.fromJson(gson.toJson(config.internalViewConfig), DanmuViewConfig.class);
        config.otherViewConfig.style.backgroundColor = "#000000AA";
        config.otherViewConfig.numberFormat.formatActivity = false;
        config.otherViewConfig.numberFormat.formatFansNum = false;

        return config;
    }

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
        public NumberFormatConfig numberFormat;
        public DanmuViewStyleConfig style;
    }

    public static class NumberFormatConfig {
        public boolean formatActivity;
        public boolean formatFansNum;
    }

    public static class DanmuViewStyleConfig {
        public int bodyMargin;
        public int listMargin;
        public String backgroundColor;
        public double zoom;
        public String font;
        public int fontWeight;
        public int lineSpacing;
        public int giftIconMaxHeight;
        public TextIconStyle vipIcon;
        public TextIconStyle sVipIcon;
        public TextIconStyle adminIcon;
        public TextStyle userName;
        public TextStyle danmuContent;

        public static class TextStyle {
            public String textColor;
        }

        public static class TextIconStyle extends TextStyle {
            public String text;
            public String backgroundColor;
            public String borderColor;
        }
    }
}
