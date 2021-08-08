package indi.goldenwater.chaosdanmutool;

import indi.goldenwater.chaosdanmutool.config.Config;
import indi.goldenwater.chaosdanmutool.config.ConfigManager;
import indi.goldenwater.chaosdanmutool.utils.FxmlNullAlert;
import indi.goldenwater.chaosdanmutool.utils.StageManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ChaosDanmuTool extends Application {
    private static ChaosDanmuTool instance;
    private static final Logger logger = LogManager.getLogger(ChaosDanmuTool.class);

    private static final File config = new File("./config.json");
    private static final ConfigManager<Config> configManager =
            new ConfigManager<>("/config.json", config, Config.class);

    private StageManager stageManager;

    public static void main(String[] args) {
        if (!config.exists()) saveDefaultConfig();
        loadConfig();
        launch(args);
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
        primaryStage.show();
        logger.info("Load main window success");
    }

    @Override
    public void stop() {
        logger.info("Stopping");
        stageManager.closeAll();
        saveConfig();
        logger.info("Stopped");
        System.exit(0);
    }

    public static void loadConfig() {
        logger.info("Loading config.");
        try {
            configManager.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Config loaded.");
    }

    public static void saveConfig() {
        logger.info("Saving config.");
        try {
            configManager.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Config saved.");
    }

    public static void saveDefaultConfig() {
        logger.info("Saving default config.");
        try {
            configManager.saveDefaultConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Default config saved.");
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
