package indi.goldenwater.chaosdanmutool.model.js;

public class UpdateActivityJS {
    public static String getJs(int activity) {
        return String.format("updateActivity(%d)", activity);
    }
}
