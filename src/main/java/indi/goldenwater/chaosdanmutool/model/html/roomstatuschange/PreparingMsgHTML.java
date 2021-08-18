package indi.goldenwater.chaosdanmutool.model.html.roomstatuschange;

import indi.goldenwater.chaosdanmutool.model.danmu.roomstatuschange.Preparing;

public class PreparingMsgHTML extends RoomStatusChangeHTML {
    public static String parse(Preparing preparing) {
        return parse("直播间", preparing.roomid, "状态更改为准备中", "#F7B500", "#0ea3ff", "#F7B500");
    }
}
