package indi.goldenwater.chaosdanmutool.model.danmu;

import com.google.gson.JsonObject;

public class UserInfo {
    public String face;
    public String face_frame;
    public int guard_level;
    public int is_main_vip;
    public int is_svip;
    public int is_vip;
    public String level_color;
    public int manager;
    public String name_color;
    public String title;
    public String uname;
    public int user_level;

    public static UserInfo parse(JsonObject user_info) {
        UserInfo userInfo = new UserInfo();

        userInfo.face = user_info.get("face").getAsString();
        userInfo.face_frame = user_info.get("face_frame").getAsString();
        userInfo.guard_level = user_info.get("guard_level").getAsInt();
        userInfo.is_main_vip = user_info.get("is_main_vip").getAsInt();
        userInfo.is_svip = user_info.get("is_svip").getAsInt();
        userInfo.is_vip = user_info.get("is_vip").getAsInt();
        userInfo.level_color = user_info.get("level_color").getAsString();
        userInfo.manager = user_info.get("manager").getAsInt();
        userInfo.name_color = user_info.get("name_color").getAsString();
        userInfo.title = user_info.get("title").getAsString();
        userInfo.uname = user_info.get("uname").getAsString();
        userInfo.user_level = user_info.get("user_level").getAsInt();

        return userInfo;
    }
}
