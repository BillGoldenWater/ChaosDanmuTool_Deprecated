package indi.goldenwater.chaosdanmutool.model.html;

import indi.goldenwater.chaosdanmutool.ChaosDanmuTool;
import indi.goldenwater.chaosdanmutool.config.Config;
import indi.goldenwater.chaosdanmutool.model.danmu.DanmuMsg;

import static indi.goldenwater.chaosdanmutool.utils.IntColorToHex.toHex;

public class DanmuMsgHTML extends DanmuItemHTML {
    public static String danmuContentTemplate =
            "<span class=\"danmu-content\" style=\"color: {{textColor}}\">{{content}}</span>";

    public static String parse(DanmuMsg danmuMsg) {
        final Config config = ChaosDanmuTool.getConfig();

        String userInfoHTML = UserInfoHTML.parse(danmuMsg.medalInfo, false, danmuMsg.isAdmin != 0, danmuMsg.uName + ": ");
        String danmuContentHTML = danmuContentTemplate
                .replace("{{textColor}}", "#" + toHex(config.danmuView.style.danmuContent.textColor))
                .replace("{{content}}", danmuMsg.content);
        return start + userInfoHTML + danmuContentHTML + end;
    }
}
