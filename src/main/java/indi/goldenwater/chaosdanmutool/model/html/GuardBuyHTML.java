package indi.goldenwater.chaosdanmutool.model.html;

import indi.goldenwater.chaosdanmutool.ChaosDanmuTool;
import indi.goldenwater.chaosdanmutool.config.Config;
import indi.goldenwater.chaosdanmutool.model.GiftDataList;
import indi.goldenwater.chaosdanmutool.model.danmu.GuardBuy;

import static indi.goldenwater.chaosdanmutool.utils.IntColorToHex.toHex;

public class GuardBuyHTML extends DanmuItemHTML {
    private static final String guardBuyTemplate = "" +
            "<span style=\"color: {{textColor}}\"> 购买了<span style=\"color: {{giftNameColor}};\"> {{gift_name}} </span>" +
            "<img class=\"gift-icon\" src=\"{{gift_icon_url}}\" alt=\"\">" +
            " x {{gift_num}} {{price}}" +
            "</span>";

    public static String parse(GuardBuy guardBuy) {
        final Config config = ChaosDanmuTool.getConfig();
        final GiftDataList.GiftData giftData = GiftDataList.giftList.get(guardBuy.gift_id);

        String userInfoHTML = UserInfoHTML.parse(null,
                false,
                false,
                false,
                guardBuy.username);

        String guardBuyHTML = guardBuyTemplate
                .replace("{{textColor}}", "#" + toHex(config.danmuView.style.danmuContent.textColor))
                .replace("{{giftNameColor}}", "#ffff00")
                .replace("{{gift_name}}", guardBuy.gift_name != null ? guardBuy.gift_name : "未知")
                .replace("{{gift_icon_url}}", giftData.img_basic)
                .replace("{{gift_num}}", String.valueOf(guardBuy.num))
                .replace("{{price}}", "￥" + (guardBuy.price / 1000));
        return start.replace("class=\"danmu-item\"", "class=\"danmu-item danmu-gift\"")
                + userInfoHTML
                + guardBuyHTML
                + end;
    }
}
