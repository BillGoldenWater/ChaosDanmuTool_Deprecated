package indi.goldenwater.chaosdanmutool.model.html;

import indi.goldenwater.chaosdanmutool.ChaosDanmuTool;
import indi.goldenwater.chaosdanmutool.config.Config;
import indi.goldenwater.chaosdanmutool.model.danmu.ComboSend;

import static indi.goldenwater.chaosdanmutool.utils.IntColorToHex.toHex;

public class ComboSendHTML extends DanmuItemHTML {
    private static final String comboSendTemplate = "<span style=\"color: {{textColor}}\"> {{action}} {{gift_name}} 共 {{gift_num}} 个</span>";

    public static String parse(ComboSend comboSend) {
        final Config config = ChaosDanmuTool.getConfig();

        String userInfoHTML = UserInfoHTML.parse(comboSend.medal_info,
                false,
                false,
                false,
                comboSend.uname);

        String comboSendHTML = comboSendTemplate
                .replace("{{textColor}}", "#" + toHex(config.danmuView.style.danmuContent.textColor))
                .replace("{{action}}", comboSend.action)
                .replace("{{gift_name}}", comboSend.gift_name)
                .replace("{{gift_num}}", String.valueOf(comboSend.total_num));
        return start + userInfoHTML + comboSendHTML + end;
    }
}
