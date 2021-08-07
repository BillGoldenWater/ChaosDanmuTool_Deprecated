package indi.goldenwater.chaosdanmutool.danmu;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import indi.goldenwater.chaosdanmutool.ChaosDanmuTool;
import indi.goldenwater.chaosdanmutool.model.danmu.*;
import org.apache.logging.log4j.Logger;

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

        if (command instanceof DanmuMsg) { // 普通弹幕
            DanmuMsg danmuMsg = (DanmuMsg) command;
            logger.info(String.format("%s: %s", danmuMsg.uName, danmuMsg.content));
        } else if (command instanceof InteractWord) { // 进入消息
            InteractWord interactWord = (InteractWord) command;
//            logger.info(String.format("%s 进入了直播间", interactWord.uname));
        } else if (command instanceof RoomRealTimeMessageUpdate) { // 直播间 信息更新
            RoomRealTimeMessageUpdate realTimeMessageUpdate = (RoomRealTimeMessageUpdate) command;
            logger.info(String.format("直播间信息更新: 粉丝数 %d; 粉丝团 %d;",
                    realTimeMessageUpdate.fans,
                    realTimeMessageUpdate.fans_club));
        } else if (command instanceof SendGift) { // 赠送礼物
            SendGift sendGift = (SendGift) command;
            logger.info(String.format("%s %s%sx%d", sendGift.uname, sendGift.action, sendGift.giftName, sendGift.num));
        } else if (command instanceof ComboSend) { // 连击特效
            ComboSend comboSend = (ComboSend) command;
            logger.info(String.format("%s %s %s 共%d个", comboSend.uname, comboSend.action, comboSend.gift_name, comboSend.total_num));
        } else if (command instanceof RoomBlockMsg) { // 禁言
            RoomBlockMsg roomBlockMsg = (RoomBlockMsg) command;
            logger.info(String.format("%s 已被管理员禁言", roomBlockMsg.uname));
        } else if (command instanceof StopLiveRoomList) { // 未知
            StopLiveRoomList stopLiveRoomList = (StopLiveRoomList) command;
        } else if (!command.cmd.equals("IGNORE")) {
            logger.debug(command.cmd);
            logger.debug(jsonStr);
        }
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
}