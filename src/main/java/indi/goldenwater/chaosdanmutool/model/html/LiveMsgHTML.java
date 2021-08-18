package indi.goldenwater.chaosdanmutool.model.html;

import indi.goldenwater.chaosdanmutool.model.danmu.Live;

public class LiveMsgHTML extends RoomStatusChangeHTML {
    public static String parse(Live live) {
        return parse("直播间", live.roomid, "开播了", "#F7B500", "#0ea3ff", "#F7B500");
    }
}
