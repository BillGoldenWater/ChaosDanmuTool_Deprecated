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
}
