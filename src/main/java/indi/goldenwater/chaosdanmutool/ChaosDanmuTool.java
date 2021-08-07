package indi.goldenwater.chaosdanmutool;

import indi.goldenwater.chaosdanmutool.config.Config;
import indi.goldenwater.chaosdanmutool.config.ConfigManager;
import indi.goldenwater.chaosdanmutool.danmu.DanmuReceiver;
import indi.goldenwater.chaosdanmutool.danmu.DanmuServer;
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

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class ChaosDanmuTool extends Application {
    private static ChaosDanmuTool instance;
    private static final Logger logger = LogManager.getLogger(ChaosDanmuTool.class);

    private static final File config = new File("./config.json");
    private static final ConfigManager<Config> configManager =
            new ConfigManager<>("/config.json", config, Config.class);

    private StageManager stageManager;
    public static DanmuServer server;

    public static void main(String[] args) {
        loadConfig();
//        launch(args);
        try {
//            new DanmuReceiver("wss://broadcastlv.chat.bilibili.com/sub", 30, 953650).connect();
            new DanmuReceiver("wss://broadcastlv.chat.bilibili.com/sub", 30, 953650).connect();//1455691
            server = new DanmuServer(25555);
            Platform.exit();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

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

    @Override
    public void stop() {
        saveConfig();
    }

    public static void loadConfig() {
        config.delete();
        try {
            configManager.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveConfig() {
        try {
            configManager.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public static Config getConfig() {
        return configManager.getConfig();
    }
}
