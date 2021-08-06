package indi.goldenwater.chaosdanmutool.utils;

import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class StageManager {
    private final Map<String, Stage> stages = new HashMap<>();

    public Stage getStage(String id) {
        return stages.get(id);
    }

    public void setStage(String id, Stage stage) {
        stages.put(id, stage);
    }

    public void setStage_AutoClose(String id, Stage stage) {
        Stage originStage = stages.get(id);
        if (originStage != null) {
            originStage.close();
        }
        setStage(id, stage);
    }

    public void closeAll() {
        stages.forEach((l, v) -> v.close());
    }

    public void close(String id) {
        Stage stage = stages.get(id);
        if (stage != null) {
            stage.close();
        }
        stages.remove(id);
    }
}
