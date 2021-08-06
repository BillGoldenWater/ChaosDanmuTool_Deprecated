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

    //SEND_GIFT
    public String action;
    public String batch_combo_id;
    public Object batch_combo_send;
    public String beatId;
    public String biz_source;
    public Object blind_gift;
    public int broadcast_id;
    public String coin_type; // silver, gold(?)
    public int combo_resources_id;
    public Object combo_send;
    public int combo_stay_time;
    public int combo_total_coin;
    public int crit_prob;
    public int demarcation;
    //public int dmscore;
    public int draw;
    public int effect;
    public int effect_block;
    public String face; // face icon url?
    public int giftId;
    public String giftName;
    public int giftType;
    public int gold;
    public int guard_level;
    public boolean is_first;
    public int is_special_batch;
    public double magnification;
    public MedalInfo medal_info;
    public String name_color;
    public int num; // gift num?
    public String original_gift_name;
    public int price;
    public int rcost;
    public int remain;
    public String rnd;
    public Object send_master;
    public int silver;
    @SerializedName("super")
    public int super_;
    public int super_batch_gift_num;
    public int super_gift_num;
    public int svga_block;
    public String tag_image;
    public long tid;
    //public long timestamp;
    public Object top_list;
    public int total_coin;
    //public long uid;
    //public String uname;

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
