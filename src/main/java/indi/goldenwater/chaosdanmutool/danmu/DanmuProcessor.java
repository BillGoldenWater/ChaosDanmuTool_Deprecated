package indi.goldenwater.chaosdanmutool.danmu;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DanmuProcessor {
    public static void processCommand(String jsonStr) {
        Gson gson = new Gson();
        MessageCommand command;
        try {
            command = gson.fromJson(jsonStr, MessageCommand.class);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(jsonStr);
            return;
        }

        switch (command.cmd) {
            case "HOT_RANK_CHANGED":
            case "ENTRY_EFFECT":

            case "ONLINE_RANK_V2":
            case "ONLINE_RANK_COUNT":
            case "NOTICE_MSG":
            case "STOP_LIVE_ROOM_LIST":
                break;
            case "INTERACT_WORD": {
                MessageData data = command.data;
                System.out.printf("%s ", formatTime(data.timestamp * 1000));
                if (data.fans_medal.is_lighted == 1) {
                    System.out.printf("[%s %d] ", data.fans_medal.medal_name, data.fans_medal.medal_level);
                }
                System.out.printf("%s 进入了房间\n", data.uname);
                break;
            }
            case "SEND_GIFT": {
                MessageData data = command.data;
                if (!data.batch_combo_id.equals("")) break;

                System.out.printf("%s ", formatTime(data.timestamp * 1000));
                if (data.medal_info.is_lighted == 1) {
                    System.out.printf("[%s %d] ", data.medal_info.medal_name, data.medal_info.medal_level);
                }
                System.out.printf("%s %s %dx%s\n", data.uname, data.action, data.num, data.giftName);
                break;
            }
            case "COMBO_SEND": {
                MessageData data = command.data;
                if (data.medal_info.is_lighted == 1) {
                    System.out.printf("[%s %d] ", data.medal_info.medal_name, data.medal_info.medal_level);
                }
                System.out.printf("%s %s %dx%s\n", data.uname, data.action, data.total_num, data.gift_name);
                break;
            }
            case "ROOM_REAL_TIME_MESSAGE_UPDATE": {
                MessageData data = command.data;
                System.out.printf("直播间信息更新: 粉丝数 %d; 粉丝团 %d;\n", data.fans, data.fans_club);
                break;
            }
            case "DANMU_MSG": {
                DanmuInfo danmuInfo = DanmuInfo.parse(command.info);
                System.out.printf("%s ", formatTime(danmuInfo.timestamp));
                if (danmuInfo.medalInfo.is_lighted == 1) {
                    System.out.printf("[%s %d] ", danmuInfo.medalInfo.medal_name, danmuInfo.medalInfo.medal_level);
                }
                System.out.printf("%s: %s\n", danmuInfo.uName, danmuInfo.content);
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

    public static String formatTime(long timestampInMillis) {
        Date date = new Date(timestampInMillis);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return String.format("[%02d:%02d:%02d]", calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND));
    }

    public static class MessageCommand {
        public String cmd;
        public List<Object> info;
        public MessageData data;
    }

    public static class MessageData {
        //STOP_LIVE_ROOM_LIST
        public List<Integer> room_id_list;

        //ONLINE_RANK_V2
        public List<Object> list;

        //ONLINE_RANK_COUNT
        public int count;

        //ROOM_REAL_TIME_MESSAGE_UPDATE
        public long roomid;
        public int fans;
        public int red_notice;
        public int fans_club;

        //INTERACT_WORD
        public Object contribution;
        public int dmscore;
        public FansMedal fans_medal;
        public Object identities;
        public int is_spread;
        public int msg_type;
        //public long roomid;
        public long score;
        public String spread_desc;
        public String spread_info;
        public int tail_icon;
        public long timestamp;
        public long trigger_time;
        public long uid;
        public String uname;
        public String uname_color;

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

    public static class MedalInfo {
        public int anchor_roomid;
        public String anchor_uname;
        public int guard_level;
        public int icon_id;
        public int is_lighted;
        public int medal_color;
        public int medal_color_border;
        public int medal_color_end;
        public int medal_color_start;
        public int medal_level;
        public String medal_name;
        public String special;
        public long target_id;
    }

    public static class DanmuInfo {
        public List<Object> info;

        public long timestamp;

        public String content;

        public long uid;
        public String uName;
        public int isAdmin;

        public MedalInfo medalInfo;

        public int userUL;

        public String userTitle;

        public static DanmuInfo parse(List<Object> info) {
            DanmuInfo danmuInfo = new DanmuInfo();
            danmuInfo.info = info;

            List<?> danmuMeta = (List<?>) info.get(0);
            if (danmuMeta != null) {
                danmuInfo.timestamp = ((Double) danmuMeta.get(4)).longValue();
            }

            danmuInfo.content = (String) info.get(1);

            List<?> userData = (List<?>) info.get(2);
            if (userData != null) {
                danmuInfo.uid = ((Double) userData.get(0)).longValue();
                danmuInfo.uName = (String) userData.get(1);
                danmuInfo.isAdmin = ((Double) userData.get(2)).intValue();
            }

            List<?> medalInfo = (List<?>) info.get(3);
            if (medalInfo != null) {
                MedalInfo tempMedalInfo = new MedalInfo();

                if (medalInfo.isEmpty()) {
                    tempMedalInfo.is_lighted = 0;
                } else {
                    tempMedalInfo.medal_level = ((Double) medalInfo.get(0)).intValue();
                    tempMedalInfo.medal_name = (String) medalInfo.get(1);
                    tempMedalInfo.anchor_uname = (String) medalInfo.get(2);
                    tempMedalInfo.anchor_roomid = ((Double) medalInfo.get(3)).intValue();
                }

                danmuInfo.medalInfo = tempMedalInfo;
            }

            List<?> levelInfo = (List<?>) info.get(4);
            if (levelInfo != null) {
                danmuInfo.userUL = ((Double) levelInfo.get(0)).intValue();
            }

            List<?> titleInfo = (List<?>) info.get(5);
            if (titleInfo != null) {
                if (!titleInfo.isEmpty()) {
                    danmuInfo.userTitle = (String) titleInfo.get(0);
                }
            }

            return danmuInfo;
        }
    }
}