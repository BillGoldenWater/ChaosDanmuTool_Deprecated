package indi.goldenwater.chaosdanmutool.danmu;

import com.google.gson.*;
import indi.goldenwater.chaosdanmutool.ChaosDanmuTool;
import indi.goldenwater.chaosdanmutool.model.*;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;

public class DanmuProcessor {
    static final Logger logger = ChaosDanmuTool.getLogger();

    public static void processCommand(String jsonStr) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(MessageCommand.class, new DanmuDeserializer());
        Gson gson = gsonBuilder.create();
        MessageCommand command;
        try {
            command = gson.fromJson(jsonStr, MessageCommand.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug(jsonStr);
            return;
        }

        if (command instanceof DanmuMsg) {
            DanmuMsg danmuMsg = (DanmuMsg) command;
            logger.info(String.format("%s: %s", danmuMsg.uName, danmuMsg.content));
        } else if (command instanceof InteractWord) {
            InteractWord interactWord = (InteractWord) command;
            logger.info(String.format("%s 进入了直播间", interactWord.uname));
        }

//        switch (command.cmd) {
//            case "HOT_RANK_CHANGED":
//            case "ENTRY_EFFECT":
//
//            case "ONLINE_RANK_V2":
//            case "ONLINE_RANK_COUNT":
//            case "NOTICE_MSG":
//            case "STOP_LIVE_ROOM_LIST":
//                break;
//            case "SEND_GIFT": {
//                MessageData data = command.data;
//                if (!data.batch_combo_id.equals("")) break;
//
//                System.out.printf("%s ", formatTime(data.timestamp * 1000));
//                if (data.medal_info.is_lighted == 1) {
//                    System.out.printf("[%s %d] ", data.medal_info.medal_name, data.medal_info.medal_level);
//                }
//                System.out.printf("%s %s %dx%s\n", data.uname, data.action, data.num, data.giftName);
//                break;
//            }
//            case "COMBO_SEND": {
//                MessageData data = command.data;
//                if (data.medal_info.is_lighted == 1) {
//                    System.out.printf("[%s %d] ", data.medal_info.medal_name, data.medal_info.medal_level);
//                }
//                System.out.printf("%s %s %dx%s\n", data.uname, data.action, data.total_num, data.gift_name);
//                break;
//            }
//            case "ROOM_REAL_TIME_MESSAGE_UPDATE": {
//                MessageData data = command.data;
//                System.out.printf("直播间信息更新: 粉丝数 %d; 粉丝团 %d;\n", data.fans, data.fans_club);
//                break;
//            }
//            default: {
//                System.out.println(jsonStr);
//            }
//        }
    }

    public static void connectSuccess() {
        System.out.println("连接成功!");
    }

    public static void updateActivity(int activity) {
        System.out.println("当前人气: " + activity);
    }

    public static void decodeError(DanmuReceiver.Data data) {
        logger.error("处理失败:\n" + data.toString());
    }

    public static String formatTime(long timestampInMillis) {
        Date date = new Date(timestampInMillis);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return String.format("[%02d:%02d:%02d]", calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND));
    }

    public static class DanmuDeserializer implements JsonDeserializer<MessageCommand> {

        @Override
        public MessageCommand deserialize(JsonElement jsonElement,
                                          Type type,
                                          JsonDeserializationContext jsonDeserializationContext)
                throws JsonParseException {
            if (jsonElement.isJsonNull()) return null;
            if (!jsonElement.isJsonObject()) return null;

            JsonObject jsonObject = jsonElement.getAsJsonObject();
            switch (jsonObject.get("cmd").getAsString()) {
                case "DANMU_MSG": {
                    DanmuMsg danmuMsg = new DanmuMsg();
                    danmuMsg.cmd = jsonObject.get("cmd").getAsString();
                    JsonArray info = jsonObject.get("info").getAsJsonArray();

                    JsonArray danmuMeta = info.get(0).getAsJsonArray();
                    if (danmuMeta != null) {
                        danmuMsg.timestamp = danmuMeta.get(4).getAsLong();
                    }

                    danmuMsg.content = info.get(1).getAsString();

                    JsonArray userData = info.get(2).getAsJsonArray();
                    if (userData != null) {
                        danmuMsg.uid = userData.get(0).getAsLong();
                        danmuMsg.uName = userData.get(1).getAsString();
                        danmuMsg.isAdmin = userData.get(2).getAsInt();
                    }

                    JsonArray medalInfo = info.get(3).getAsJsonArray();
                    if (medalInfo != null) {
                        MedalInfo tempMedalInfo = new MedalInfo();

                        if (medalInfo.isEmpty()) {
                            tempMedalInfo.is_lighted = 0;
                        } else {
                            tempMedalInfo.medal_level = medalInfo.get(0).getAsInt();
                            tempMedalInfo.medal_name = medalInfo.get(1).getAsString();
                            tempMedalInfo.anchor_uname = medalInfo.get(2).getAsString();
                            tempMedalInfo.anchor_roomid = medalInfo.get(3).getAsInt();
                        }

                        danmuMsg.medalInfo = tempMedalInfo;
                    }

                    JsonArray levelInfo = info.get(4).getAsJsonArray();
                    if (levelInfo != null) {
                        danmuMsg.userUL = levelInfo.get(0).getAsInt();
                    }

                    JsonArray titleInfo = info.get(5).getAsJsonArray();
                    if (titleInfo != null) {
                        if (!titleInfo.isEmpty()) {
                            danmuMsg.userTitle = titleInfo.get(0).getAsString();
                        }
                    }
                    return danmuMsg;
                }
                case "INTERACT_WORD": {
                    InteractWord interactWord = new InteractWord();
                    interactWord.cmd = jsonObject.get("cmd").getAsString();
                    JsonObject data = jsonObject.get("data").getAsJsonObject();

                    interactWord.contribution = data.get("contribution");
                    interactWord.dmscore = data.get("dmscore").getAsInt();

                    FansMedal fansMedal = new FansMedal();
                    JsonObject fans_medal = data.get("fans_medal").getAsJsonObject();
                    fansMedal.anchor_roomid = fans_medal.get("anchor_roomid").getAsInt();
                    fansMedal.guard_level = fans_medal.get("guard_level").getAsInt();
                    fansMedal.icon_id = fans_medal.get("icon_id").getAsInt();
                    fansMedal.is_lighted = fans_medal.get("is_lighted").getAsInt();
                    fansMedal.medal_color = fans_medal.get("medal_color").getAsInt();
                    fansMedal.medal_color_border = fans_medal.get("medal_color_border").getAsInt();
                    fansMedal.medal_color_end = fans_medal.get("medal_color_end").getAsInt();
                    fansMedal.medal_color_start = fans_medal.get("medal_color_start").getAsInt();
                    fansMedal.medal_level = fans_medal.get("medal_level").getAsInt();
                    fansMedal.medal_name = fans_medal.get("medal_name").getAsString();
                    fansMedal.score = fans_medal.get("score").getAsInt();
                    fansMedal.special = fans_medal.get("special").getAsString();
                    fansMedal.target_id = fans_medal.get("target_id").getAsInt();

                    interactWord.fans_medal = fansMedal;
                    interactWord.identities = data.get("identities").getAsJsonArray();
                    interactWord.is_spread = data.get("is_spread").getAsInt();
                    interactWord.msg_type = data.get("msg_type").getAsInt();
                    interactWord.roomid = data.get("roomid").getAsLong();
                    interactWord.score = data.get("score").getAsLong();
                    interactWord.spread_desc = data.get("spread_desc").getAsString();
                    interactWord.spread_info = data.get("spread_info").getAsString();
                    interactWord.tail_icon = data.get("tail_icon").getAsInt();
                    interactWord.timestamp = data.get("timestamp").getAsLong();
                    interactWord.trigger_time = data.get("trigger_time").getAsLong();
                    interactWord.uid = data.get("uid").getAsLong();
                    interactWord.uname = data.get("uname").getAsString();
                    interactWord.uname_color = data.get("uname_color").getAsString();

                    return interactWord;
                }
                default: {
                    logger.debug(jsonObject.toString());
                    return null;
                }
            }
        }
    }
}