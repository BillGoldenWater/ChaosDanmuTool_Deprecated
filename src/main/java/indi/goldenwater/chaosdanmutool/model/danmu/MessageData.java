package indi.goldenwater.chaosdanmutool.model.danmu;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MessageData {
    //STOP_LIVE_ROOM_LIST
    public List<Integer> room_id_list;

    //ONLINE_RANK_V2
    public List<Object> list;

    //ONLINE_RANK_COUNT
    public int count;

    //COMBO_SEND
    //public String action;
    //public String batch_combo_id;
    public int batch_combo_num;
    public String combo_id;
    public int combo_num;
    //public int combo_total_coin;
    //public int dmscore;
    public int gift_id;
    public String gift_name;
    public int gift_num;
    public int is_show;
    //public MedalInfo medal_info;
    //public String name_color;
    public String r_uname;
    public int ruid;
    //public Object send_master;
    public int total_num;
    //public long uid;
    //public String uname;
}
