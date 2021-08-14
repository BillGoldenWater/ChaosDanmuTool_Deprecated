package indi.goldenwater.chaosdanmutool.model.danmu;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class FansMedal {
    public long anchor_roomid;
    public int guard_level;
    public int icon_id;
    public int is_lighted;
    public int medal_color;
    public int medal_color_border;
    public int medal_color_end;
    public int medal_color_start;
    public int medal_level;
    public String medal_name;
    public int score;
    public String special;
    public long target_id;

    public static FansMedal parse(JsonObject fans_medal) {
        FansMedal fansMedal = new FansMedal();
        fansMedal.anchor_roomid = fans_medal.get("anchor_roomid").getAsInt();
        fansMedal.guard_level = fans_medal.get("guard_level").getAsInt();
        fansMedal.icon_id = fans_medal.get("icon_id").getAsInt();
        fansMedal.is_lighted = fans_medal.get("is_lighted").getAsInt();
        try {
            fansMedal.medal_color = fans_medal.get("medal_color").getAsInt();
        } catch (Exception e) {
            fansMedal.medal_color = Integer.parseInt(fans_medal.get("medal_color").getAsString().replace("#", ""), 16);
        }
        fansMedal.medal_color_border = fans_medal.get("medal_color_border").getAsInt();
        fansMedal.medal_color_end = fans_medal.get("medal_color_end").getAsInt();
        fansMedal.medal_color_start = fans_medal.get("medal_color_start").getAsInt();
        fansMedal.medal_level = fans_medal.get("medal_level").getAsInt();
        fansMedal.medal_name = fans_medal.get("medal_name").getAsString();

        JsonElement score = fans_medal.get("score");
        if (score == null || score.isJsonNull()) {
            fansMedal.score = 0;
        } else {
            fansMedal.score = fans_medal.get("score").getAsInt();
        }

        fansMedal.special = fans_medal.get("special").getAsString();
        fansMedal.target_id = fans_medal.get("target_id").getAsInt();
        return fansMedal;
    }
}
