package indi.goldenwater.chaosdanmutool;

import indi.goldenwater.chaosdanmutool.utils.StageManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class ChaosDanmuTool extends Application {
    private static ChaosDanmuTool instance;
    private StageManager stageManager;

    @Override
    public void start(Stage primaryStage) throws Exception {
        instance = this;

        URL fxml = getClass().getResource("scene/main.fxml");
        if (fxml == null) System.exit(-1);

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
    }

    public StageManager getStageManager() {
        return stageManager;
    }

    public static ChaosDanmuTool getInstance() {
        return instance;
    }
}
