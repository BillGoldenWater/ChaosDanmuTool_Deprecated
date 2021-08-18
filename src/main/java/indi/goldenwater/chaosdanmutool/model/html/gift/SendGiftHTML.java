package indi.goldenwater.chaosdanmutool.model.html.gift;

import indi.goldenwater.chaosdanmutool.ChaosDanmuTool;
import indi.goldenwater.chaosdanmutool.config.Config;
import indi.goldenwater.chaosdanmutool.model.GiftDataList;
import indi.goldenwater.chaosdanmutool.model.danmu.gift.SendGift;
import indi.goldenwater.chaosdanmutool.model.html.DanmuItemHTML;
import indi.goldenwater.chaosdanmutool.model.html.UserInfoHTML;

import static indi.goldenwater.chaosdanmutool.utils.IntColorToHex.toHex;

public class SendGiftHTML extends DanmuItemHTML {
    private static final String sendGiftTemplate = "<span style=\"color: {{textColor}}\"> {{action}} {{gift_name}} x {{gift_num}}</span>";
    private static final String comboSendTemplate = "" +
            "<span style=\"color: {{textColor}}\"> {{action}}<span style=\"color: {{giftNameColor}};\"> {{gift_name}} </span>" +
            "<img class=\"gift-icon\" src=\"{{gift_icon_url}}\" alt=\"\">" +
            " x {{gift_num}} {{price}}" +
            "</span>";

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
        final Config config = ChaosDanmuTool.getConfig();
        final GiftDataList.GiftData giftData = GiftDataList.giftList.get(sendGift.giftId);
        String giftNameColor = "#ffffff";
        String price = "";
        if (giftData.coin_type.equals("gold")) {
            price = "￥" + (giftData.price / 1000.0) * sendGift.num;
            giftNameColor = "#F7B500";
        }

        String userInfoHTML = UserInfoHTML.parse(sendGift.medal_info,
                false,
                false,
                false,
                sendGift.uname);

        String comboSendHTML = comboSendTemplate
                .replace("{{textColor}}", "#" + toHex(config.danmuView.style.danmuContent.textColor))
                .replace("{{giftNameColor}}", giftNameColor)
                .replace("{{action}}", sendGift.action)
                .replace("{{gift_name}}", sendGift.giftName)
                .replace("{{gift_icon_url}}", giftData.img_basic)
                .replace("{{gift_num}}", String.valueOf(sendGift.num))
                .replace("{{price}}", price);
        return start.replace("class=\"danmu-item\"", "class=\"danmu-item danmu-gift\"")
                + userInfoHTML
                + comboSendHTML
                + end;
    }
}
