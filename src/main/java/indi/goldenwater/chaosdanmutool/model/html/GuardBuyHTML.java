package indi.goldenwater.chaosdanmutool.model.html;

import indi.goldenwater.chaosdanmutool.ChaosDanmuTool;
import indi.goldenwater.chaosdanmutool.config.Config;
import indi.goldenwater.chaosdanmutool.model.danmu.GuardBuy;

public class GuardBuyHTML extends DanmuItemHTML {
    private static final String guardBuyTemplate = "" +
            "<span style=\"color: {{textColor}}\"> 购买了<span style=\"color: {{giftNameColor}};\"> {{gift_name}} </span>" +
            "<img class=\"gift-icon\" src=\"{{gift_icon_url}}\" alt=\"\">" +
            " x {{gift_num}} {{price}}" +
            "</span>";

    private static final String guardIconUrl = "https://i0.hdslb.com/bfs/activity-plat/static/20200716/1d0c5a1b042efb59f46d4ba1286c6727/icon-guard{{lvl}}.png";

    public static String parse(GuardBuy guardBuy) {
        final Config config = ChaosDanmuTool.getConfig();

        String userInfoHTML = UserInfoHTML.parse(null,
                false,
                false,
                false,
                guardBuy.username);

        String guardBuyHTML = guardBuyTemplate
                .replace("{{textColor}}", config.internalViewConfig.style.danmuContent.textColor)
                .replace("{{giftNameColor}}", "#ffff00")
                .replace("{{gift_name}}", guardBuy.gift_name != null ? guardBuy.gift_name : "未知")
                .replace("{{gift_icon_url}}", guardIconUrl
                        .replace("{{lvl}}", String.valueOf(guardBuy.guard_level)))
                .replace("{{gift_num}}", String.valueOf(guardBuy.num))
                .replace("{{price}}", "￥" + (guardBuy.price / 1000));
        return start.replace("class=\"danmu-item\"", "class=\"danmu-item danmu-gift\"")
                + userInfoHTML
                + guardBuyHTML
                + end;
    }
}
