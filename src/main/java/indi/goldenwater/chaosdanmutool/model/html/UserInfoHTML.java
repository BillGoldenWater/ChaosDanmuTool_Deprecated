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
    private static final String adminIconTemplate =
            "<div class=\"admin-icon\" style=\"color: {{textColor}}; " +
                    "background-color: {{backgroundColor}}; " +
                    "border-color: {{borderColor}}\">{{text}}</div>";
    private static final String userNameTemplate =
            "<span class=\"user-name\" style=\"color: {{textColor}};\">{{userName}}</span>";

    public static String parse(FansMedal fansMedal, boolean isVip, boolean isAdmin, String userName) {
        Config config = ChaosDanmuTool.getConfig();
        
        String fansMedalHTML = (fansMedal != null && fansMedal.is_lighted == 1) ? parse(fansMedal) : "";
        String vipIconHTML = vipIconTemplate
                .replace("{{textColor}}", "#" + toHex(config.danmuView.style.vipIcon.textColor))
                .replace("{{backgroundColor}}", "#" + toHex(config.danmuView.style.vipIcon.backgroundColor))
                .replace("{{borderColor}}", "#" + toHex(config.danmuView.style.vipIcon.borderColor))
                .replace("{{text}}", config.danmuView.style.vipIcon.text);
        String adminIconHTML = adminIconTemplate
                .replace("{{textColor}}", "#" + toHex(config.danmuView.style.adminIcon.textColor))
                .replace("{{backgroundColor}}", "#" + toHex(config.danmuView.style.adminIcon.backgroundColor))
                .replace("{{borderColor}}", "#" + toHex(config.danmuView.style.adminIcon.borderColor))
                .replace("{{text}}", config.danmuView.style.adminIcon.text);
        String userNameHTML = userNameTemplate
                .replace("{{textColor}}", "#" + toHex(config.danmuView.style.userName.textColor))
                .replace("{{userName}}", userName);

        return fansMedalHTML +
                (isVip ? vipIconHTML : "") +
                (isAdmin ? adminIconHTML : "") +
                userNameHTML;
    }
}
