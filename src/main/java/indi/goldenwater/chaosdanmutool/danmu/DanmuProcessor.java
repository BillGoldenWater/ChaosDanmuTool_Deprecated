package indi.goldenwater.chaosdanmutool.danmu;

import com.google.gson.*;
import indi.goldenwater.chaosdanmutool.ChaosDanmuTool;
import indi.goldenwater.chaosdanmutool.model.danmu.*;
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
//            logger.info(String.format("%s: %s", danmuMsg.uName, danmuMsg.content));
        } else if (command instanceof InteractWord) {
            InteractWord interactWord = (InteractWord) command;
//            logger.info(String.format("%s 进入了直播间", interactWord.uname));
        } else if (command instanceof RoomRealTimeMessageUpdate) {
            RoomRealTimeMessageUpdate realTimeMessageUpdate = (RoomRealTimeMessageUpdate) command;
            logger.info(String.format("直播间信息更新: 粉丝数 %d; 粉丝团 %d;",
                    realTimeMessageUpdate.fans,
                    realTimeMessageUpdate.fans_club));
        } else if (command instanceof SendGift) {
            SendGift sendGift = (SendGift) command;
            logger.info(String.format("%s %s %dx%s", sendGift.uname, sendGift.action, sendGift.num, sendGift.giftName));
        } else {
            logger.debug(command.cmd);
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
//            case "COMBO_SEND": {
//                MessageData data = command.data;
//                if (data.medal_info.is_lighted == 1) {
//                    System.out.printf("[%s %d] ", data.medal_info.medal_name, data.medal_info.medal_level);
//                }
//                System.out.printf("%s %s %dx%s\n", data.uname, data.action, data.total_num, data.gift_name);
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
                    interactWord.fans_medal = FansMedal.parse(data.get("fans_medal").getAsJsonObject());
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
                case "ROOM_REAL_TIME_MESSAGE_UPDATE": {
                    RoomRealTimeMessageUpdate realTimeMessageUpdate = new RoomRealTimeMessageUpdate();
                    realTimeMessageUpdate.cmd = jsonObject.get("cmd").getAsString();
                    JsonObject data = jsonObject.get("data").getAsJsonObject();

                    realTimeMessageUpdate.roomid = data.get("roomid").getAsLong();
                    realTimeMessageUpdate.fans = data.get("fans").getAsInt();
                    realTimeMessageUpdate.red_notice = data.get("red_notice").getAsInt();
                    realTimeMessageUpdate.fans_club = data.get("fans_club").getAsInt();

                    return realTimeMessageUpdate;
                }
                case "SEND_GIFT": {
                    SendGift sendGift = new SendGift();
                    sendGift.cmd = jsonObject.get("cmd").getAsString();
                    JsonObject data = jsonObject.get("data").getAsJsonObject();

                    sendGift.action = data.get("action").getAsString();
                    sendGift.batch_combo_id = data.get("batch_combo_id").getAsString();
                    sendGift.batch_combo_send = data.get("batch_combo_send");
                    sendGift.beatId = data.get("beatId").getAsString();
                    sendGift.biz_source = data.get("biz_source").getAsString();
                    sendGift.blind_gift = data.get("blind_gift");
                    sendGift.broadcast_id = data.get("broadcast_id").getAsInt();
                    sendGift.coin_type = data.get("coin_type").getAsString();
                    sendGift.combo_resources_id = data.get("combo_resources_id").getAsInt();
                    sendGift.combo_send = data.get("combo_send");
                    sendGift.combo_stay_time = data.get("combo_stay_time").getAsInt();
                    sendGift.combo_total_coin = data.get("combo_total_coin").getAsInt();
                    sendGift.crit_prob = data.get("crit_prob").getAsInt();
                    sendGift.demarcation = data.get("demarcation").getAsInt();
                    sendGift.dmscore = data.get("dmscore").getAsInt();
                    sendGift.draw = data.get("draw").getAsInt();
                    sendGift.effect = data.get("effect").getAsInt();
                    sendGift.effect_block = data.get("effect_block").getAsInt();
                    sendGift.face = data.get("face").getAsString();
                    sendGift.giftId = data.get("giftId").getAsInt();
                    sendGift.giftName = data.get("giftName").getAsString();
                    sendGift.giftType = data.get("giftType").getAsInt();
                    sendGift.gold = data.get("gold").getAsInt();
                    sendGift.guard_level = data.get("guard_level").getAsInt();
                    sendGift.is_first = data.get("is_first").getAsBoolean();
                    sendGift.is_special_batch = data.get("is_special_batch").getAsInt();
                    sendGift.magnification = data.get("magnification").getAsDouble();

                    sendGift.medal_info = MedalInfo.parse(data.get("medal_info").getAsJsonObject());

                    sendGift.name_color = data.get("name_color").getAsString();
                    sendGift.num = data.get("num").getAsInt();
                    sendGift.original_gift_name = data.get("original_gift_name").getAsString();
                    sendGift.price = data.get("price").getAsInt();
                    sendGift.rcost = data.get("rcost").getAsInt();
                    sendGift.remain = data.get("remain").getAsInt();
                    sendGift.rnd = data.get("rnd").getAsString();

                    JsonElement send_master = data.get("send_master");
                    if (send_master == null || send_master.isJsonNull()) {
                        sendGift.send_master = 0;
                    } else {
                        sendGift.send_master = send_master.getAsInt();
                    }

                    sendGift.silver = data.get("silver").getAsInt();

                    JsonElement super_ = data.get("super_");
                    if (super_ == null || super_.isJsonNull()) {
                        sendGift.super_ = 0;
                    } else {
                        sendGift.super_ = super_.getAsInt();
                    }

                    sendGift.super_batch_gift_num = data.get("super_batch_gift_num").getAsInt();
                    sendGift.super_gift_num = data.get("super_gift_num").getAsInt();
                    sendGift.svga_block = data.get("svga_block").getAsInt();
                    sendGift.tag_image = data.get("tag_image").getAsString();
                    sendGift.tid = data.get("tid").getAsLong();
                    sendGift.timestamp = data.get("timestamp").getAsLong();
                    sendGift.top_list = data.get("top_list");
                    sendGift.total_coin = data.get("total_coin").getAsInt();
                    sendGift.uid = data.get("uid").getAsLong();
                    sendGift.uname = data.get("uname").getAsString();

                    return sendGift;
                }
                default: {
                    logger.debug(jsonObject.toString());
                    return null;
                }
            }
        }
    }
}