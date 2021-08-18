package indi.goldenwater.chaosdanmutool.model.html.roomstatuschange;

import indi.goldenwater.chaosdanmutool.model.danmu.roomstatuschange.Live;

public class LiveMsgHTML extends RoomStatusChangeHTML {
    public static String parse(Live live) {
        return parse("直播间", String.valueOf(live.roomid), "状态更改为直播中", "#F7B500", "#0ea3ff", "#F7B500");
    }
}
