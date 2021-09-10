package indi.goldenwater.chaosdanmutool.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import indi.goldenwater.chaosdanmutool.utils.HTTPRequest;

import java.util.HashMap;
import java.util.Map;

public class TitlesData {
    public static final Map<String, TitleData> titles = new HashMap<>();

    public static void update() {
        String jsonStr = HTTPRequest.getAsString(
                "https://api.live.bilibili.com/rc/v1/Title/webTitles");
        Gson gson = new Gson();
        TitlesDataResponse response = gson.fromJson(jsonStr, TitlesDataResponse.class);

        for (JsonElement element : response.data) {
            JsonObject titleDataJsonObj = element.getAsJsonObject();
            TitleData titleData = TitleData.parse(titleDataJsonObj);
            TitlesData.titles.put(titleData.identification, titleData);
        }
    }

    public static class TitleData {
        public String identification;
        public String name;
        public int title_id;
        public String web_pic_url;

        public static TitleData parse(JsonObject data) {
            TitleData titleData = new TitleData();

            titleData.identification = data.get("identification").getAsString();
            titleData.name = data.get("name").getAsString();
            titleData.title_id = data.get("title_id").getAsInt();
            titleData.web_pic_url = data.get("web_pic_url").getAsString();

            return titleData;
        }
    }

    public static class TitlesDataResponse {
        public int code;
        public JsonArray data;
        public String message;
        public String msg;
    }
}
