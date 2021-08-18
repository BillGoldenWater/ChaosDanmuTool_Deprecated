package indi.goldenwater.chaosdanmutool.model.html.useraction;

import indi.goldenwater.chaosdanmutool.model.html.DanmuItemHTML;

public class UserActionMsgHTML extends DanmuItemHTML {
    private static final String userActionMessageTemplate = "{{userInfo}}" +
            "<span style=\"color: {{actionColor}}\"> {{action}}</span>";

    public static String parse(String userInfoHTML, String action, String actionColor) {
        return start
                + userActionMessageTemplate
                .replace("{{userInfo}}", userInfoHTML)
                .replace("{{action}}", action)
                .replace("{{actionColor}}", actionColor)
                + end;
    }
}
