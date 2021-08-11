package indi.goldenwater.chaosdanmutool.model.js;

public class UpdateStatusBarDisplay {
    public static String getJs(String html) {
        return String.format("updateStatusBarDisplay(\"%s\")", html.replace("\"", "\\\""));
    }
}
