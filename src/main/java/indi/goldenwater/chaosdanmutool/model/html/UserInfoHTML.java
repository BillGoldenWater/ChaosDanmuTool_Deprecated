package indi.goldenwater.chaosdanmutool.model.html;

import indi.goldenwater.chaosdanmutool.ChaosDanmuTool;
import indi.goldenwater.chaosdanmutool.config.Config;
import indi.goldenwater.chaosdanmutool.model.danmu.FansMedal;

import static indi.goldenwater.chaosdanmutool.utils.IntColorToHex.toHex;

public class UserInfoHTML extends FansMedalHTML {
    private static final String vipIconTemplate =
            "<div class=\"vip-icon\" style=\"color: {{textColor}}; " +
                    "background-color: {{backgroundColor}}; " +
                    "border-color: {{borderColor}}\">{{text}}</div>";
    private static final String sVipIconTemplate =
            "<div class=\"vip-icon\" style=\"color: {{textColor}}; " +
                    "background-color: {{backgroundColor}}; " +
                    "border-color: {{borderColor}}\">{{text}}</div>";
    private static final String adminIconTemplate =
            "<div class=\"admin-icon\" style=\"color: {{textColor}}; " +
                    "background-color: {{backgroundColor}}; " +
                    "border-color: {{borderColor}}\">{{text}}</div>";
    private static final String userNameTemplate =
            "<span class=\"user-name\" style=\"color: {{textColor}};\">{{userName}}</span>";

    public static String parse(FansMedal fansMedal, boolean isVip, boolean isSVip, boolean isAdmin, String userName) {
        final Config config = ChaosDanmuTool.getConfig();

        String fansMedalHTML = (fansMedal != null && fansMedal.is_lighted == 1) ? parse(fansMedal) : "";
        String vipIconHTML = vipIconTemplate
                .replace("{{textColor}}", "#" + toHex(config.internalViewConfig.style.vipIcon.textColor))
                .replace("{{backgroundColor}}", "#" + toHex(config.internalViewConfig.style.vipIcon.backgroundColor))
                .replace("{{borderColor}}", "#" + toHex(config.internalViewConfig.style.vipIcon.borderColor))
                .replace("{{text}}", config.internalViewConfig.style.vipIcon.text);
        String sVipIconHTML = sVipIconTemplate
                .replace("{{textColor}}", "#" + toHex(config.internalViewConfig.style.sVipIcon.textColor))
                .replace("{{backgroundColor}}", "#" + toHex(config.internalViewConfig.style.sVipIcon.backgroundColor))
                .replace("{{borderColor}}", "#" + toHex(config.internalViewConfig.style.sVipIcon.borderColor))
                .replace("{{text}}", config.internalViewConfig.style.sVipIcon.text);
        String adminIconHTML = adminIconTemplate
                .replace("{{textColor}}", "#" + toHex(config.internalViewConfig.style.adminIcon.textColor))
                .replace("{{backgroundColor}}", "#" + toHex(config.internalViewConfig.style.adminIcon.backgroundColor))
                .replace("{{borderColor}}", "#" + toHex(config.internalViewConfig.style.adminIcon.borderColor))
                .replace("{{text}}", config.internalViewConfig.style.adminIcon.text);
        String userNameHTML = userNameTemplate
                .replace("{{textColor}}", "#" + toHex(config.internalViewConfig.style.userName.textColor))
                .replace("{{userName}}", userName);

        return fansMedalHTML +
                (isVip ? (isSVip ? sVipIconHTML : vipIconHTML) : "") +
                (isAdmin ? adminIconHTML : "") +
                userNameHTML;
    }
}
