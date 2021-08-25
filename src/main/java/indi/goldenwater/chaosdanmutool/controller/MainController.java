package indi.goldenwater.chaosdanmutool.controller;

import indi.goldenwater.chaosdanmutool.ChaosDanmuTool;
import indi.goldenwater.chaosdanmutool.config.Config;
import indi.goldenwater.chaosdanmutool.danmu.DanmuReceiver;
import indi.goldenwater.chaosdanmutool.danmu.DanmuServer;
import indi.goldenwater.chaosdanmutool.utils.FxmlNullAlert;
import indi.goldenwater.chaosdanmutool.utils.HTMLReplaceVar;
import indi.goldenwater.chaosdanmutool.utils.StageManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;


public class MainController {
    private final Logger logger = LogManager.getLogger(MainController.class);

    private Stage thisStage;

    @FXML
    protected Button btnOpenServer;
    @FXML
    protected Button btnOpenBrowser;
    @FXML
    protected Button btnReleaseHTML;

    @FXML
    protected TextField txtFieldRoomId;

    @FXML
    protected void initialize() {
        final StageManager stageManager = ChaosDanmuTool.getInstance().getStageManager();
        thisStage = stageManager.getStage("main");

        final Config config = ChaosDanmuTool.getConfig();

        DecimalFormat format = new DecimalFormat("#");

        final TextFormatter<Object> decimalTextFormatter = new TextFormatter<>(change -> {
            if (change.getControlNewText().isEmpty()) {
                return change;
            }
            ParsePosition parsePosition = new ParsePosition(0);
            Object object = format.parse(change.getControlNewText(), parsePosition);

            if (object == null || parsePosition.getIndex() < change.getControlNewText().length()) {
                return null;
            } else {
                return change;
            }
        });
        txtFieldRoomId.setTextFormatter(decimalTextFormatter);
        txtFieldRoomId.setText(String.valueOf(config.danmuReceiver.roomid));
        setOnClose();
    }

    @FXML
    protected void onBtnOpenServerClicked(MouseEvent event) throws Exception {
        if (event.getButton().compareTo(MouseButton.PRIMARY) == 0) {
            final Config config = ChaosDanmuTool.getConfig();
            config.danmuReceiver.roomid = Integer.parseInt(txtFieldRoomId.getText());
            startServer(config);
        }
    }

    @FXML
    protected void onBtnOpenBrowserClicked(MouseEvent event) throws Exception {
        if (event.getButton().compareTo(MouseButton.PRIMARY) == 0) {
            final Config config = ChaosDanmuTool.getConfig();
            showDanmuView(config);
        }
    }

    @FXML
    protected void onBtnReleaseHTMLClicked(MouseEvent event) throws Exception {
        if (event.getButton().compareTo(MouseButton.PRIMARY) == 0) {
            FileWriter fileWriter = new FileWriter("./index.html");

            String html = HTMLReplaceVar.get(ChaosDanmuTool.getConfig());
            if (html != null) {
                fileWriter.write(html);
                fileWriter.flush();
                fileWriter.close();
            }
        }
    }

    public void showDanmuView(Config config) throws Exception {
        logger.debug("Load danmu view");

        final StageManager stageManager = ChaosDanmuTool.getInstance().getStageManager();

        Stage danmuView = new Stage();
        URL fxml = getClass().getResource("/scene/danmuView.fxml");
        if (fxml == null) {
            FxmlNullAlert.alert("danmuView", false);
            return;
        }

        stageManager.setStage_AutoClose("danmuView", danmuView);

        Parent root = FXMLLoader.load(fxml);

        danmuView.setScene(new Scene(root, config.internalBrowser.width, config.internalBrowser.height, Color.TRANSPARENT));
//        danmuView.initStyle(StageStyle.UNDECORATED);
        danmuView.initStyle(StageStyle.TRANSPARENT);
        danmuView.setAlwaysOnTop(true);
        danmuView.show();

        logger.debug("Load danmu view success");
    }

    private void startServer(Config config) throws Exception {
        logger.debug("Starting server.");
        new DanmuServer(config.internalBrowser.webSocketServer.port).start();
        new DanmuReceiver(config.danmuReceiver.serverUrl,
                config.danmuReceiver.heartBeatPeriod,
                config.danmuReceiver.roomid).connect();
        logger.debug("Server started.");
    }

    private void closeServer() throws Exception {
        logger.debug("Closing server.");
        if (DanmuReceiver.getInstance() != null) DanmuReceiver.getInstance().close();
        if (DanmuServer.getInstance() != null) DanmuServer.getInstance().stop(0);
        logger.debug("Server closed.");
    }

    private void setOnClose() {
        thisStage.setOnCloseRequest(event -> {
            try {
                onClose();
                Platform.exit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void onClose() throws Exception {
        closeServer();
        thisStage.close();
    }
}
