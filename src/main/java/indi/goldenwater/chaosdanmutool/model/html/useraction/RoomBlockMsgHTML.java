package indi.goldenwater.chaosdanmutool.model.html.useraction;

import indi.goldenwater.chaosdanmutool.model.danmu.useraction.RoomBlockMsg;
import indi.goldenwater.chaosdanmutool.model.html.UserInfoHTML;

public class RoomBlockMsgHTML extends UserActionMsgHTML {
    public static String parse(RoomBlockMsg roomBlockMsg) {
        String userInfoHTML = UserInfoHTML.parse(null,
                false,
                false,
                false,
                roomBlockMsg.uname);
        return parse(userInfoHTML, "已被管理员禁言", "#ff0000");
    }
}
