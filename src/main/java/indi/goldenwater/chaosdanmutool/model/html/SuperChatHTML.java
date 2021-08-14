package indi.goldenwater.chaosdanmutool.model.html;

import indi.goldenwater.chaosdanmutool.model.danmu.SuperChatMessage;

public class SuperChatHTML {
    private static final String superChatTemplate = "<div class=\"danmu-item danmu-superChat\">" +
            "        <div class=\"superChat-top\"" +
            "             style=\"border-color: {{bg_bottom_color}};" +
            "             background-color: {{bg_color}};" +
            "             background-image: url({{bgImgURL}});\">" +

            "            {{userInfo}}" +

            "            <div class=\"superChat-price\" style=\"color:#000000;\">￥{{price}}</div>" +
            "        </div>" +
            "        <div class=\"superChat-bottom\"" +
            "             style=\"color:{{msg_font_color}}; border-color: {{bg_bottom_color}}; background-color: {{bg_bottom_color}};\">" +
            "            {{message}}" +
            "        </div>" +
            "    </div>";

    public static String parse(SuperChatMessage superChatMessage) {
        String userInfoHTML = UserInfoHTML.parse(superChatMessage.medal_info,
                superChatMessage.user_info.is_vip != 0,
                superChatMessage.user_info.is_svip != 0,
                superChatMessage.user_info.manager != 0,
                superChatMessage.user_info.uname);
        return superChatTemplate
                .replace("{{userInfo}}", userInfoHTML)
                .replace("{{bg_bottom_color}}", superChatMessage.background_bottom_color)
                .replace("{{bg_color}}", superChatMessage.background_color)
                .replace("{{msg_font_color}}", superChatMessage.message_font_color)
                .replace("{{bgImgURL}}", superChatMessage.background_image)
                .replace("{{price}}", String.valueOf(superChatMessage.price))
                .replace("{{message}}", superChatMessage.message);
    }
}
