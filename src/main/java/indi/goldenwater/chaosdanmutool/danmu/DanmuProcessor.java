package indi.goldenwater.chaosdanmutool.danmu;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import indi.goldenwater.chaosdanmutool.model.danmu.*;
import indi.goldenwater.chaosdanmutool.model.html.*;
import indi.goldenwater.chaosdanmutool.model.js.DanmuItemJS;
import indi.goldenwater.chaosdanmutool.model.js.UpdateActivityJS;
import indi.goldenwater.chaosdanmutool.model.js.UpdateFansNumJS;
import indi.goldenwater.chaosdanmutool.model.js.UpdateStatusBarDisplay;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Calendar;
import java.util.Date;

public class DanmuProcessor {
    static final Logger logger = LogManager.getLogger(DanmuProcessor.class);

    public static void processCommand(String jsonStr) {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(MessageCommand.class, new DanmuDeserializer());
        final Gson gson = gsonBuilder.create();
        final DanmuServer danmuServer = DanmuServer.getInstance();

        MessageCommand command;
        try {
            command = gson.fromJson(jsonStr, MessageCommand.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(jsonStr);
            return;
        }

        if (command instanceof DanmuMsg) { // 普通弹幕
//            logger.trace(jsonStr);
            DanmuMsg danmuMsg = (DanmuMsg) command;
            if (danmuServer != null)
                danmuServer.broadcast(DanmuItemJS.getJsDanmuList(DanmuMsgHTML.parse(danmuMsg)));
            logger.info(String.format("%s: %s", danmuMsg.uName, danmuMsg.content));
        } else if (command instanceof InteractWord) { // 进入消息
            InteractWord interactWord = (InteractWord) command;
            switch (interactWord.msg_type) {
                case InteractWord.MsgType.join: {
                    if (danmuServer != null)
                        danmuServer.broadcast(UpdateStatusBarDisplay.getJs(JoinMessageHTML.parse(interactWord)));
                    logger.info(String.format("%s 进入了直播间", interactWord.uname));
                    break;
                }
                case InteractWord.MsgType.follow: {
                    if (danmuServer != null)
                        danmuServer.broadcast(DanmuItemJS.getJsDanmuList(FollowMsgHTML.parse(interactWord)));
                    logger.info(String.format("%s 关注了直播间", interactWord.uname));
                    break;
                }
                case InteractWord.MsgType.share: {
                    if (danmuServer != null)
                        danmuServer.broadcast(DanmuItemJS.getJsDanmuList(ShareMsgHTML.parse(interactWord)));
                    logger.info(String.format("%s 分享了直播间", interactWord.uname));
                    break;
                }
                default: {
                    logger.warn(jsonStr + "\n" + interactWord.msg_type);
                    break;
                }
            }
        } else if (command instanceof RoomRealTimeMessageUpdate) { // 直播间 信息更新
            RoomRealTimeMessageUpdate realTimeMessageUpdate = (RoomRealTimeMessageUpdate) command;
            if (danmuServer != null)
                danmuServer.broadcast(UpdateFansNumJS.getJs(realTimeMessageUpdate.fans));
            logger.info(String.format("直播间信息更新: 粉丝数 %d; 粉丝团 %d;",
                    realTimeMessageUpdate.fans,
                    realTimeMessageUpdate.fans_club));
        } else if (command instanceof SendGift) { // 赠送礼物
            SendGift sendGift = (SendGift) command;
            if (danmuServer != null) {
                if (sendGift.coin_type.equals("silver")) {
                    if (sendGift.batch_combo_id.equals("") && (sendGift.giftId != 1 || sendGift.num > 10)) {
                        danmuServer.broadcast(DanmuItemJS.getJsDanmuList(SendGiftHTML.parseForDanmu(sendGift)));
                    } else {
                        danmuServer.broadcast(UpdateStatusBarDisplay.getJs(SendGiftHTML.parse(sendGift)));
                    }
                } else {
                    danmuServer.broadcast(DanmuItemJS.getJsDanmuList(SendGiftHTML.parseForDanmu(sendGift)));
                }
            }
            logger.info(String.format("%s %s%sx%d", sendGift.uname, sendGift.action, sendGift.giftName, sendGift.num));
//            logger.trace(jsonStr);
        } else if (command instanceof ComboSend) { // 连击特效
            ComboSend comboSend = (ComboSend) command;
            if (danmuServer != null)
                danmuServer.broadcast(DanmuItemJS.getJsDanmuList(ComboSendHTML.parse(comboSend)));
            logger.info(String.format("%s %s %s 共%d个", comboSend.uname, comboSend.action, comboSend.gift_name, comboSend.total_num));
        } else if (command instanceof GuardBuy) {
            GuardBuy guardBuy = (GuardBuy) command;
            if (danmuServer != null)
                danmuServer.broadcast(DanmuItemJS.getJsDanmuList(GuardBuyHTML.parse(guardBuy)));
            logger.info(String.format("%s 购买了 %s x %d ￥%d", guardBuy.username, guardBuy.gift_name, guardBuy.num, guardBuy.price / 1000));
        } else if (command instanceof RoomBlockMsg) { // 禁言
            RoomBlockMsg roomBlockMsg = (RoomBlockMsg) command;
            if (danmuServer != null)
                danmuServer.broadcast(DanmuItemJS.getJsDanmuList(RoomBlockMsgHTML.parse(roomBlockMsg)));
            logger.info(String.format("%s 已被管理员禁言", roomBlockMsg.uname));
        } else if (command instanceof SuperChatMessage) {
            SuperChatMessage superChatMessage = (SuperChatMessage) command;
            if (danmuServer != null)
                danmuServer.broadcast(DanmuItemJS.getJsDanmuList(SuperChatHTML.parse(superChatMessage)));
            logger.info(String.format("醒目留言(%s): %s: %s", "￥" + superChatMessage.price, superChatMessage.user_info.uname, superChatMessage.message));
        } else if (command instanceof StopLiveRoomList) { // 未知
            StopLiveRoomList stopLiveRoomList = (StopLiveRoomList) command;
        } else if (!command.cmd.equals("IGNORE")) {
            logger.debug(command.cmd);
        }
//        logger.trace(jsonStr);
    }

    public static void connectSuccess() {
        logger.info("连接成功!");
    }

    public static void updateActivity(int activity) {
        DanmuServer danmuServer = DanmuServer.getInstance();
        if (danmuServer != null) {
            danmuServer.broadcast(UpdateActivityJS.getJs(activity));
        }
        logger.info("当前人气: " + activity);
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
}