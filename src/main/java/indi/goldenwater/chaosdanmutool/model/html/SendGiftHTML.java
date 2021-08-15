package indi.goldenwater.chaosdanmutool.model.html;

import indi.goldenwater.chaosdanmutool.ChaosDanmuTool;
import indi.goldenwater.chaosdanmutool.config.Config;
import indi.goldenwater.chaosdanmutool.model.danmu.SendGift;

import static indi.goldenwater.chaosdanmutool.utils.IntColorToHex.toHex;

public class SendGiftHTML extends DanmuItemHTML {
    private static final String sendGiftTemplate = "<span style=\"color: #ffffff\"> {{action}} {{gift_name}} x {{gift_num}}</span>";

    public static String parse(SendGift sendGift) {
        final Config config = ChaosDanmuTool.getConfig();

        String userInfoHTML = UserInfoHTML.parse(sendGift.medal_info,
                false,
                false,
                false,
                sendGift.uname);

        String sendGiftHTML = sendGiftTemplate
                .replace("{{textColor}}", "#" + toHex(config.danmuView.style.danmuContent.textColor))
                .replace("{{action}}", sendGift.action)
                .replace("{{gift_name}}", sendGift.giftName)
                .replace("{{gift_num}}", String.valueOf(sendGift.num));
        return userInfoHTML + sendGiftHTML;
    }

    public static String parseForDanmu(SendGift sendGift) {
        return start + parse(sendGift) + end;
    }
}
