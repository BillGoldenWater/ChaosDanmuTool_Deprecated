package indi.goldenwater.chaosdanmutool.utils;

import com.google.gson.Gson;

import java.util.List;

public class DanmuProcessor {
    public static void processCommand(String jsonStr) {
        Gson gson = new Gson();
        DanmuCommand command = gson.fromJson(jsonStr, DanmuCommand.class);

        if (command.cmd.equals("INTERACT_WORD")) return;
        if (command.cmd.equals("STOP_LIVE_ROOM_LIST")) return;
        System.out.println(jsonStr);
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
        public Object data;
    }
    //STOP_LIVE_ROOM_LIST 未知
    //INTERACT_WORD 进入消息
    //DANMU_MSG 弹幕
    //SEND_GIFT 礼物
}
