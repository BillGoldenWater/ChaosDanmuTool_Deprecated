package indi.goldenwater.chaosdanmutool.danmu;

import com.google.gson.*;
import indi.goldenwater.chaosdanmutool.model.danmu.*;
import indi.goldenwater.chaosdanmutool.model.danmu.gift.ComboSend;
import indi.goldenwater.chaosdanmutool.model.danmu.gift.SendGift;
import indi.goldenwater.chaosdanmutool.model.danmu.roomstatuschange.Live;
import indi.goldenwater.chaosdanmutool.model.danmu.roomstatuschange.Preparing;
import indi.goldenwater.chaosdanmutool.model.danmu.useraction.InteractWord;
import indi.goldenwater.chaosdanmutool.model.danmu.useraction.RoomBlockMsg;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Type;

public class DanmuDeserializer implements JsonDeserializer<MessageCommand> {
    private static final Logger logger = LogManager.getLogger(DanmuDeserializer.class);

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
                    danmuMsg.isVip = userData.get(3).getAsInt();
                    danmuMsg.isSVip = userData.get(4).getAsInt();
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
                        tempMedalInfo.medal_color = medalInfo.get(4).getAsInt();
                        tempMedalInfo.medal_color_border = medalInfo.get(7).getAsInt();
                        tempMedalInfo.medal_color_start = medalInfo.get(8).getAsInt();
                        tempMedalInfo.medal_color_end = medalInfo.get(9).getAsInt();
                        tempMedalInfo.is_lighted = medalInfo.get(11).getAsInt();
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
            case "COMBO_SEND": {
                ComboSend comboSend = new ComboSend();
                comboSend.cmd = jsonObject.get("cmd").getAsString();
                JsonObject data = jsonObject.get("data").getAsJsonObject();

                comboSend.action = data.get("action").getAsString();
                comboSend.batch_combo_id = data.get("batch_combo_id").getAsString();
                comboSend.batch_combo_num = data.get("batch_combo_num").getAsInt();
                comboSend.combo_id = data.get("combo_id").getAsString();
                comboSend.combo_num = data.get("combo_num").getAsInt();
                comboSend.combo_total_coin = data.get("combo_total_coin").getAsInt();
                comboSend.dmscore = data.get("dmscore").getAsInt();
                comboSend.gift_id = data.get("gift_id").getAsInt();
                comboSend.gift_name = data.get("gift_name").getAsString();
                comboSend.gift_num = data.get("gift_num").getAsInt();
                comboSend.is_show = data.get("is_show").getAsInt();
                comboSend.medal_info = MedalInfo.parse(data.get("medal_info").getAsJsonObject());
                comboSend.name_color = data.get("name_color").getAsString();
                comboSend.r_uname = data.get("r_uname").getAsString();
                comboSend.ruid = data.get("ruid").getAsInt();
                comboSend.send_master = data.get("send_master");
                comboSend.total_num = data.get("total_num").getAsInt();
                comboSend.uid = data.get("uid").getAsLong();
                comboSend.uname = data.get("uname").getAsString();

                return comboSend;
            }
            case "STOP_LIVE_ROOM_LIST": {
                StopLiveRoomList stopLiveRoomList = new StopLiveRoomList();
                stopLiveRoomList.cmd = jsonObject.get("cmd").getAsString();
                JsonObject data = jsonObject.get("data").getAsJsonObject();

                stopLiveRoomList.room_id_list = data.get("room_id_list").getAsJsonArray();

                return stopLiveRoomList;
            }
            case "ROOM_BLOCK_MSG": {
                RoomBlockMsg roomBlockMsg = new RoomBlockMsg();
                roomBlockMsg.cmd = jsonObject.get("cmd").getAsString();
                JsonObject data = jsonObject.get("data").getAsJsonObject();

                roomBlockMsg.dmscore = data.get("dmscore").getAsInt();
                roomBlockMsg.operator = data.get("operator").getAsInt();
                roomBlockMsg.uid = data.get("uid").getAsLong();
                roomBlockMsg.uname = data.get("uname").getAsString();

                return roomBlockMsg;
            }
            case "SUPER_CHAT_MESSAGE": {
                SuperChatMessage superChatMessage = new SuperChatMessage();
                superChatMessage.cmd = jsonObject.get("cmd").getAsString();
                superChatMessage.roomid = jsonObject.get("roomid").getAsString();

                JsonObject data = jsonObject.get("data").getAsJsonObject();

                superChatMessage.background_bottom_color = data.get("background_bottom_color").getAsString();
                superChatMessage.background_color = data.get("background_color").getAsString();
                superChatMessage.background_color_end = data.get("background_color_end").getAsString();
                superChatMessage.background_color_start = data.get("background_color_start").getAsString();
                superChatMessage.background_icon = data.get("background_icon").getAsString();
                superChatMessage.background_image = data.get("background_image").getAsString();
                superChatMessage.background_price_color = data.get("background_price_color").getAsString();
                superChatMessage.color_point = data.get("color_point").getAsDouble();
                superChatMessage.dmscore = data.get("dmscore").getAsInt();
                superChatMessage.end_time = data.get("end_time").getAsLong();
                superChatMessage.gift = data.get("gift").getAsJsonObject();
                superChatMessage.id = data.get("id").getAsLong();
                superChatMessage.is_ranked = data.get("is_ranked").getAsInt();
                superChatMessage.is_send_audit = data.get("is_send_audit").getAsString();

                JsonElement medal_info = data.get("medal_info");
                if (medal_info != null) {
                    superChatMessage.medal_info = MedalInfo.parse(medal_info.getAsJsonObject());
                } else {
                    superChatMessage.medal_info = null;
                }

                superChatMessage.message = data.get("message").getAsString();
                superChatMessage.message_font_color = data.get("message_font_color").getAsString();
                superChatMessage.message_trans = data.get("message_trans").getAsString();
                superChatMessage.price = data.get("price").getAsInt();
                superChatMessage.rate = data.get("rate").getAsInt();
                superChatMessage.start_time = data.get("start_time").getAsLong();
                superChatMessage.time = data.get("time").getAsInt();
                superChatMessage.token = data.get("token").getAsString();
                superChatMessage.trans_mark = data.get("trans_mark").getAsInt();
                superChatMessage.ts = data.get("ts").getAsLong();
                superChatMessage.uid = data.get("uid").getAsLong();
                superChatMessage.user_info = UserInfo.parse(data.get("user_info").getAsJsonObject());

                return superChatMessage;
            }
            case "GUARD_BUY": {
                GuardBuy guardBuy = new GuardBuy();
                guardBuy.cmd = jsonObject.get("cmd").getAsString();
                JsonObject data = jsonObject.get("data").getAsJsonObject();

                guardBuy.uid = data.get("uid").getAsLong();
                guardBuy.username = data.get("username").getAsString();
                guardBuy.guard_level = data.get("guard_level").getAsInt();
                guardBuy.num = data.get("num").getAsInt();
                guardBuy.price = data.get("price").getAsInt();
                guardBuy.gift_id = data.get("gift_id").getAsInt();
                guardBuy.gift_name = data.get("gift_name").getAsString();
                guardBuy.start_time = data.get("start_time").getAsLong();
                guardBuy.end_time = data.get("end_time").getAsLong();

                return guardBuy;
            }
            case "LIVE": {
                Live live = new Live();
                live.cmd = jsonObject.get("cmd").getAsString();

                live.live_key = jsonObject.get("live_key").getAsString();
                live.voice_background = jsonObject.get("voice_background").getAsString();
                live.sub_session_key = jsonObject.get("sub_session_key").getAsString();
                live.live_platform = jsonObject.get("live_platform").getAsString();
                live.live_model = jsonObject.get("live_model").getAsInt();
                live.roomid = jsonObject.get("roomid").getAsLong();

                return live;
            }
            case "PREPARING": {
                Preparing preparing = new Preparing();
                preparing.cmd = jsonObject.get("cmd").getAsString();

                preparing.roomid = jsonObject.get("roomid").getAsString();

                return preparing;
            }
            case "HOT_RANK_CHANGED": // plan to do
            case "HOT_RANK_SETTLEMENT":
            case "ENTRY_EFFECT":
            case "ANCHOR_LOT_START":
            case "ANCHOR_LOT_END":
            case "ANCHOR_LOT_AWARD":
            case "ANCHOR_LOT_CHECKSTATUS":
            case "WIDGET_BANNER": // ignore
            case "SUPER_CHAT_MESSAGE_JPN": // not need jpn
            case "ROOM_CHANGE":
            case "USER_TOAST_MSG":
            case "COMMON_NOTICE_DANMAKU":
            case "NOTICE_MSG":
            case "ONLINE_RANK_COUNT":
            case "ONLINE_RANK_TOP3":
            case "ONLINE_RANK_V2":
            case "PK_BATTLE_END":
            case "PK_BATTLE_FINAL_PROCESS":
            case "PK_BATTLE_PROCESS":
            case "PK_BATTLE_PROCESS_NEW":
            case "PK_BATTLE_SETTLE":
            case "PK_BATTLE_SETTLE_USER":
            case "PK_BATTLE_SETTLE_V2":
            case "PK_BATTLE_PRE":
            case "PK_BATTLE_PRE_NEW":
            case "PK_BATTLE_START":
            case "PK_BATTLE_START_NEW": {
                MessageCommand messageCommand = new MessageCommand();
                messageCommand.cmd = "IGNORE";
                return messageCommand;
            }
            default: {
                MessageCommand messageCommand = new MessageCommand();
                messageCommand.cmd = jsonObject.get("cmd").getAsString();
                logger.debug(jsonObject.toString());
                return messageCommand;
            }
        }
    }
}
