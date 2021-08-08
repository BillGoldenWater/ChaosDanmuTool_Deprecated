package indi.goldenwater.chaosdanmutool.utils;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
        closeStage(id);
        setStage(id, stage);
    }

    public void closeAll() {
        stages.forEach((k, v) -> closeStage(k));
    }

    public void closeStage(String id) {
        Stage stage = stages.get(id);
        if (stage != null) {
            stage.getOnCloseRequest().handle(new WindowEvent(null, WindowEvent.WINDOW_CLOSE_REQUEST));
        }
//        stages.remove(id);
    }
}
