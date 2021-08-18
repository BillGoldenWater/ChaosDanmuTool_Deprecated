package indi.goldenwater.chaosdanmutool.model.danmu.useraction;

import com.google.gson.JsonArray;
import indi.goldenwater.chaosdanmutool.model.danmu.FansMedal;
import indi.goldenwater.chaosdanmutool.model.danmu.MessageCommand;

public class InteractWord extends MessageCommand {
    public Object contribution;
    public int dmscore;
    public FansMedal fans_medal;
    public JsonArray identities;
    public int is_spread;
    public int msg_type;
    public long roomid;
    public long score;
    public String spread_desc;
    public String spread_info;
    public int tail_icon;
    public long timestamp;
    public long trigger_time;
    public long uid;
    public String uname;
    public String uname_color;

    public static class MsgType {
        public static final int join = 1;
        public static final int follow = 2;
        public static final int share = 3;
    }
}
