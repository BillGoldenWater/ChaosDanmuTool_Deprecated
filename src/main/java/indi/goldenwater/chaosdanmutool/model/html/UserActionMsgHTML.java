package indi.goldenwater.chaosdanmutool.model.html;

public class UserActionMsgHTML extends DanmuItemHTML {
    private static final String userActionMessageTemplate = "<div class=\"danmu-item\">" +
            "{{userInfo}}" +
            "<span style=\"color: {{actionColor}}\"> {{action}}</span>" +
            "</div>";

    public static String parse(String userInfoHTML, String action, String actionColor) {
        return start
                + userActionMessageTemplate
                .replace("{{userInfo}}", userInfoHTML)
                .replace("{{action}}", action)
                .replace("{{actionColor}}", actionColor)
                + end;
    }
}
