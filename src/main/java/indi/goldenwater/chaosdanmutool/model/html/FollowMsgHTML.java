package indi.goldenwater.chaosdanmutool.model.html;

import indi.goldenwater.chaosdanmutool.model.danmu.InteractWord;

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
