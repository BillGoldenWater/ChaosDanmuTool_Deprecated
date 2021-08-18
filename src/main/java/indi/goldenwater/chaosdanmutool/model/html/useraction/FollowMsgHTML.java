package indi.goldenwater.chaosdanmutool.model.html.useraction;

import indi.goldenwater.chaosdanmutool.model.danmu.useraction.InteractWord;
import indi.goldenwater.chaosdanmutool.model.html.UserInfoHTML;

public class FollowMsgHTML extends UserActionMsgHTML {
    public static String parse(InteractWord interactWord) {
        String userInfoHTML = UserInfoHTML.parse(interactWord.fans_medal,
                false,
                false,
                false,
                interactWord.uname);
        return parse(userInfoHTML, "关注了直播间", "#F7B500");
    }
}
