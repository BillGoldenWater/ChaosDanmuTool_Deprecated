package indi.goldenwater.chaosdanmutool.model.danmu;

import com.google.gson.JsonObject;

public class MedalInfo extends FansMedal {
    public String anchor_uname;

    public MedalInfo() {
    }

    public MedalInfo(FansMedal fansMedal) {
        anchor_roomid = fansMedal.anchor_roomid;
        guard_level = fansMedal.guard_level;
        icon_id = fansMedal.icon_id;
        is_lighted = fansMedal.is_lighted;
        medal_color = fansMedal.medal_color;
        medal_color_border = fansMedal.medal_color_border;
        medal_color_start = fansMedal.medal_color_start;
        medal_color_end = fansMedal.medal_color_end;
        medal_level = fansMedal.medal_level;
        medal_name = fansMedal.medal_name;
        score = fansMedal.score;
        special = fansMedal.special;
        target_id = fansMedal.target_id;
    }

    public static MedalInfo parse(JsonObject medal_info) {
        FansMedal fansMedal = FansMedal.parse(medal_info);
        MedalInfo medalInfo = new MedalInfo(fansMedal);
        medalInfo.anchor_uname = medal_info.get("anchor_uname").getAsString();
        return medalInfo;
    }
}
