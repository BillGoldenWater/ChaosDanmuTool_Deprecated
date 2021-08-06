package indi.goldenwater.chaosdanmutool.controller;

import indi.goldenwater.chaosdanmutool.ChaosDanmuTool;
import indi.goldenwater.chaosdanmutool.config.Config;
import indi.goldenwater.chaosdanmutool.utils.FxmlNullAlert;
import indi.goldenwater.chaosdanmutool.utils.StageManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.apache.logging.log4j.Logger;

import java.net.URL;


public class MainController {
    final Logger logger = ChaosDanmuTool.getLogger();

    @FXML
    protected Button btnOpenDanmuView;

    @FXML
    protected void onBtnOpenDanmuViewClicked() throws Exception {
        logger.info("Load danmu view");
        ChaosDanmuTool.loadConfig();
        final Config config = ChaosDanmuTool.getConfig();
        StageManager stageManager = ChaosDanmuTool.getInstance().getStageManager();

        Stage danmuView = new Stage();
        URL fxml = getClass().getResource("/scene/danmuView.fxml");
        if (fxml == null) {
            FxmlNullAlert.alert("danmuView", false);
            return;
        }

        stageManager.setStage_AutoClose("danmuView", danmuView);

        Parent root = FXMLLoader.load(fxml);

//        danmuView.initStyle(StageStyle.UNDECORATED);
        danmuView.setScene(new Scene(root, config.danmuView.width, config.danmuView.height));
        danmuView.show();
        logger.info("Load danmu view success");
    }
}
