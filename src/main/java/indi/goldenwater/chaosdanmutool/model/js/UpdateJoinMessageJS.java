package indi.goldenwater.chaosdanmutool.model.js;

public class UpdateJoinMessageJS {
    public static String getJs(String html) {
        return String.format("updateJoinMessage(\"%s\")", html.replace("\"", "\\\""));
    }
}
