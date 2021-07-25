package indi.goldenwater.chaosdanmutool.utils;

import com.google.gson.Gson;

import java.util.List;

public class DanmuProcessor {
    public static void processCommand(String jsonStr) {
        Gson gson = new Gson();
        DanmuCommand command = gson.fromJson(jsonStr, DanmuCommand.class);
    }

    public static void connectSuccess() {
        
    }

    public static void updateActivity(int activity) {

    }

    public static void decodeError(DanmuReceiver.Data data) {

    }

    public static class DanmuCommand {
        public String cmd;
        public List<Object> info;
        public Object data;
    }
}
