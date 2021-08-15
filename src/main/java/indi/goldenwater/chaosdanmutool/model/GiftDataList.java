package indi.goldenwater.chaosdanmutool.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import indi.goldenwater.chaosdanmutool.utils.HTTPRequest;

import java.util.HashMap;
import java.util.Map;

public class GiftDataList {
    public static final Map<Integer, GiftData> giftList = new HashMap<>();

    public static void update() {
        String jsonStr = HTTPRequest.getAsString(
                "https://api.live.bilibili.com/xlive/web-room/v1/giftPanel/giftConfig?platform=pc");
        Gson gson = new Gson();
        GiftDataListResponse responseJsonObj = gson.fromJson(jsonStr, GiftDataListResponse.class);

        JsonObject data = responseJsonObj.data.getAsJsonObject();
        JsonArray giftList = data.getAsJsonArray("list");
        for (JsonElement jsonElement : giftList) {
            JsonObject giftDataJsonObj = jsonElement.getAsJsonObject();
            GiftData giftData = GiftData.parse(giftDataJsonObj);
            GiftDataList.giftList.put(giftData.id, giftData);
        }
    }

    public static class GiftData {
        public int id;
        public String img_basic;
        public String coin_type;
        public int price; // price / 1000 = 1RMB

        public static GiftData parse(JsonObject data) {
            GiftData giftData = new GiftData();
            giftData.id = data.get("id").getAsInt();
            giftData.img_basic = data.get("img_basic").getAsString();
            giftData.coin_type = data.get("coin_type").getAsString();
            giftData.price = data.get("price").getAsInt();
            return giftData;
        }
    }

    public static class GiftDataListResponse {
        public int code;
        public JsonObject data;
        public String message;
        public int ttl;
    }
}
