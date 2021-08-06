package indi.goldenwater.chaosdanmutool.utils;

import indi.goldenwater.chaosdanmutool.ChaosDanmuTool;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.apache.logging.log4j.Logger;

public class FxmlNullAlert {
    public static void alert(String sceneName, boolean autoClose) {
        Logger logger = ChaosDanmuTool.getLogger();
        String logMessage = String.format("Failed to load scene \"%s\".", sceneName);

        if (autoClose) {
            logger.fatal(logMessage);
        } else {
            logger.error(logMessage);
        }
        Alert alert = new Alert(Alert.AlertType.ERROR, "Cannot find needed file, program maybe incomplete.", ButtonType.OK);
        alert.show();
        if (autoClose) {
            alert.setOnCloseRequest(event -> System.exit(0));
        }
    }
}
