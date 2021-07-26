package indi.goldenwater.chaosdanmutool.utils;

import com.google.gson.Gson;

import java.util.List;

public class DanmuProcessor {
    public static void processCommand(String jsonStr) {
        Gson gson = new Gson();
        DanmuCommand command = gson.fromJson(jsonStr, DanmuCommand.class);

        switch (command.cmd) {
            case "NOTICE_MSG":
                System.out.println("NOTICE_MSG");
                break;
            case "STOP_LIVE_ROOM_LIST":
                System.out.println("STOP_LIVE_ROOM_LIST");
                break;
            case "INTERACT_WORD": {
                DanmuData data = command.data;
                System.out.printf("ts: %d ",data.timestamp);
                if (data.fans_medal.is_lighted == 1) {
                    System.out.printf("%s%d ",data.fans_medal.medal_name,data.fans_medal.medal_level);
                }
                System.out.printf("%s进入了房间\n", data.uname);
                break;
            }
            default: {
                System.out.println(jsonStr);
            }
        }
    }

    public static void connectSuccess() {
        System.out.println("连接成功!");
    }

    public static void updateActivity(int activity) {
        System.out.println("当前人气: " + activity);
    }

    public static void decodeError(DanmuReceiver.Data data) {
        System.out.println("处理失败:\n" + data);
    }

    public static class DanmuCommand {
        public String cmd;
        public List<Object> info;
        public DanmuData data;
    }

    public static class DanmuData {
        //STOP_LIVE_ROOM_LIST
        public List<Integer> room_id_list;
        //INTERACT_WORD
        public Object contribution;
        public int dmscore;
        public FansMedal fans_medal;
        public List<Object> identities;
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
    }

    public static class FansMedal {
        public long anchor_roomid;
        public int guard_level;
        public int icon_id;
        public int is_lighted;
        public int medal_color;
        public int medal_color_border;
        public int medal_color_end;
        public int medal_color_start;
        public int medal_level;
        public String medal_name;
        public int score;
        public String special;
        public long target_id;
    }
    //STOP_LIVE_ROOM_LIST 未知
    //INTERACT_WORD 进入消息
    //DANMU_MSG 弹幕
    //SEND_GIFT 礼物
}