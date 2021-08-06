package indi.goldenwater.chaosdanmutool;

import indi.goldenwater.chaosdanmutool.utils.FxmlNullAlert;
import indi.goldenwater.chaosdanmutool.utils.StageManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;

public class ChaosDanmuTool extends Application {
    private static ChaosDanmuTool instance;
    private static final Logger logger = LogManager.getLogger(ChaosDanmuTool.class);
    private StageManager stageManager;

    @Override
    public void start(Stage primaryStage) throws Exception {
        logger.info("Load main window");
        instance = this;

        URL fxml = getClass().getResource("/scene/main.fxml");
        if (fxml == null) {
            FxmlNullAlert.alert("main", true);
            return;
        }

        stageManager = new StageManager();
        stageManager.setStage_AutoClose("main", primaryStage);

        Parent root = FXMLLoader.load(fxml);

        primaryStage.setTitle("Chaos Danmu Tool");
        primaryStage.setScene(new Scene(root));
        primaryStage.setOnCloseRequest(event -> Platform.exit());
        primaryStage.show();
        logger.info("Load main window success");
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

    public static Logger getLogger() {
        return logger;
    }
}
