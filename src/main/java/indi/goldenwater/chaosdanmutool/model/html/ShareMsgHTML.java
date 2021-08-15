package indi.goldenwater.chaosdanmutool.model.html;

import indi.goldenwater.chaosdanmutool.model.danmu.InteractWord;

public class ShareMsgHTML extends UserActionMsgHTML {
    public static String parse(InteractWord interactWord) {
        String userInfoHTML = UserInfoHTML.parse(interactWord.fans_medal,
                false,
                false,
                false,
                interactWord.uname);
        return parse(userInfoHTML, "分享了直播间", "#F7B500");
    }
}
