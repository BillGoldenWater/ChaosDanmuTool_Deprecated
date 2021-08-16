package indi.goldenwater.chaosdanmutool.model.html;

import indi.goldenwater.chaosdanmutool.ChaosDanmuTool;
import indi.goldenwater.chaosdanmutool.config.Config;
import indi.goldenwater.chaosdanmutool.model.GiftDataList;
import indi.goldenwater.chaosdanmutool.model.danmu.ComboSend;

import static indi.goldenwater.chaosdanmutool.utils.IntColorToHex.toHex;

public class ComboSendHTML extends DanmuItemHTML {
    private static final String comboSendTemplate = "" +
            "<span style=\"color: {{textColor}}\"> {{action}}<span style=\"color: {{giftNameColor}};\"> {{gift_name}} </span>" +
            "<img class=\"gift-icon\" src=\"{{gift_icon_url}}\" alt=\"\">" +
            " 共 {{gift_num}} 个 {{price}}" +
            "</span>";

    public static String parse(ComboSend comboSend) {
        final Config config = ChaosDanmuTool.getConfig();
        final GiftDataList.GiftData giftData = GiftDataList.giftList.get(comboSend.gift_id);
        String giftNameColor = "#ffffff";
        String price = "";
        if (giftData.coin_type.equals("gold")) {
            giftNameColor = "#ffff00";
            price = "￥" + (giftData.price / 1000.0) * comboSend.total_num;
        }

        String userInfoHTML = UserInfoHTML.parse(comboSend.medal_info,
                false,
                false,
                false,
                comboSend.uname);

        String comboSendHTML = comboSendTemplate
                .replace("{{textColor}}", "#" + toHex(config.danmuView.style.danmuContent.textColor))
                .replace("{{giftNameColor}}", giftNameColor)
                .replace("{{action}}", comboSend.action)
                .replace("{{gift_name}}", comboSend.gift_name)
                .replace("{{gift_icon_url}}", giftData.img_basic)
                .replace("{{gift_num}}", String.valueOf(comboSend.total_num))
                .replace("{{price}}", price);
        return start.replace("class=\"danmu-item\"", "class=\"danmu-item danmu-gift\"")
                + userInfoHTML
                + comboSendHTML
                + end;
    }
}
