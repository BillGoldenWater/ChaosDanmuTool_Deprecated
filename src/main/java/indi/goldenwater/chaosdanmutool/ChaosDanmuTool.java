package indi.goldenwater.chaosdanmutool;

import indi.goldenwater.chaosdanmutool.utils.DanmuReceiver;
import indi.goldenwater.chaosdanmutool.utils.StageManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.net.URISyntaxException;
import java.net.URL;

public class ChaosDanmuTool extends Application {
    private static ChaosDanmuTool instance;
    private StageManager stageManager;

    @Override
    public void start(Stage primaryStage) throws Exception {
        instance = this;

        URL fxml = getClass().getResource("/scene/main.fxml");
        if (fxml == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "无法找到所需的文件 程序也许不完整", ButtonType.OK);
            alert.show();
            alert.setOnCloseRequest(event -> System.exit(0));
            return;
        }

        stageManager = new StageManager();
        stageManager.setStage_AutoClose("main", primaryStage);

        Parent root = FXMLLoader.load(fxml);

        primaryStage.setTitle("Chaos Danmu Tool");
        primaryStage.setScene(new Scene(root));
        primaryStage.setOnCloseRequest(event -> stageManager.closeAll());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
//        try {
////            new DanmuReceiver("wss://broadcastlv.chat.bilibili.com/sub", 30).connect();
//            new DanmuReceiver("wss://broadcastlv.chat.bilibili.com/sub", 30, 953650).connect();
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
    }

    public StageManager getStageManager() {
        return stageManager;
    }

    public static ChaosDanmuTool getInstance() {
        return instance;
    }
}
