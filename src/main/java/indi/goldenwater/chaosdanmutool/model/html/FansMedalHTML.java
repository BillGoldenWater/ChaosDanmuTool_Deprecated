package indi.goldenwater.chaosdanmutool.model.html;

import indi.goldenwater.chaosdanmutool.model.danmu.FansMedal;

import static indi.goldenwater.chaosdanmutool.utils.IntColorToHex.toHex;

public class FansMedalHTML {
    private static final String fansMedalTemplate = "<div class=\"fans-medal\" style=\"border-color: {{medal_color_border}}; " +
            "background: linear-gradient(45deg, {{medal_color_start}}, {{medal_color_end}});\">" +
            "<span class=\"fans-medal-name\">{{medal_name}}</span>" +
            "<span class=\"fans-medal-level\" style=\"color: {{medal_color}}\">{{medal_level}}</span>" +
            "</div>";

    //guard x

    public static String parse(FansMedal fansMedal) {
        return fansMedalTemplate
                .replace("{{medal_color_border}}", "#" + toHex(fansMedal.medal_color_border))
                .replace("{{medal_color_start}}", "#" + toHex(fansMedal.medal_color_start))
                .replace("{{medal_color_end}}", "#" + toHex(fansMedal.medal_color_end))
                .replace("{{medal_name}}", fansMedal.medal_name)
                .replace("{{medal_color}}", "#" + toHex(fansMedal.medal_color))
                .replace("{{medal_level}}", String.valueOf(fansMedal.medal_level));
    }
}
