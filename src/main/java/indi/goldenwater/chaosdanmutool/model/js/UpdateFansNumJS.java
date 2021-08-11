package indi.goldenwater.chaosdanmutool.model.js;

public class UpdateFansNumJS {
    public static String getJs(int fansNum) {
        return String.format("updateFansNum(%d)", fansNum);
    }
}
