package indi.goldenwater.chaosdanmutool.controller;

import indi.goldenwater.chaosdanmutool.ChaosDanmuTool;
import indi.goldenwater.chaosdanmutool.utils.StageManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.net.URL;


public class MainController {
    @FXML
    protected Button btnOpenDanmuView;

    @FXML
    protected void onBtnOpenDanmuViewClicked() throws Exception {
        StageManager stageManager = ChaosDanmuTool.getInstance().getStageManager();

        Stage danmuView = new Stage();
        URL fxml = getClass().getResource("/scene/danmuView.fxml");
        if (fxml == null) {
            new Alert(Alert.AlertType.ERROR,"无法找到所需的文件 程序也许不完整", ButtonType.OK).show();
            return;
        }

        stageManager.setStage_AutoClose("danmuView", danmuView);

        Parent root = FXMLLoader.load(fxml);

//        danmuView.initStyle(StageStyle.UNDECORATED);
        danmuView.setScene(new Scene(root));
        danmuView.show();
    }
}
