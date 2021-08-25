package indi.goldenwater.chaosdanmutool.model.html.useraction;

import indi.goldenwater.chaosdanmutool.ChaosDanmuTool;
import indi.goldenwater.chaosdanmutool.config.Config;
import indi.goldenwater.chaosdanmutool.model.danmu.useraction.InteractWord;
import indi.goldenwater.chaosdanmutool.model.html.UserInfoHTML;

import static indi.goldenwater.chaosdanmutool.utils.IntColorToHex.toHex;

public class JoinMessageHTML {
    public static String joinMessageTemplate =
            "<span class=\"danmu-content\" style=\"color: {{textColor}}\">{{content}}</span>";

    public static String parse(InteractWord interactWord) {
        final Config config = ChaosDanmuTool.getConfig();

        String userInfoHTML = UserInfoHTML.parse(interactWord.fans_medal,
                false,
                false,
                false,
                interactWord.uname);
        String joinMessage = joinMessageTemplate
                .replace("{{textColor}}", "#" + toHex(config.internalViewConfig.style.danmuContent.textColor))
                .replace("{{content}}", "进入了直播间");
        return userInfoHTML + joinMessage;
    }
}
