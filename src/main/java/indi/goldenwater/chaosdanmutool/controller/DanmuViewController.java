package indi.goldenwater.chaosdanmutool.controller;

import com.sun.javafx.webkit.Accessor;
import com.sun.webkit.WebPage;
import indi.goldenwater.chaosdanmutool.ChaosDanmuTool;
import indi.goldenwater.chaosdanmutool.config.Config;
import indi.goldenwater.chaosdanmutool.utils.HTMLReplaceVar;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class DanmuViewController {
    final Logger logger = ChaosDanmuTool.getLogger();

    private double lastPressedX;
    private double lastPressedY;
    private Stage thisStage;

    @FXML
    protected WebView danmuView;
    @FXML
    protected AnchorPane anchorPane;
    @FXML
    protected Button btnClose;
    @FXML
    protected Button btnReload;

    @FXML
    protected void initialize() throws Exception {
        logger.debug("[DanmuView] Initializing.");
        thisStage = ChaosDanmuTool.getInstance().getStageManager().getStage("danmuView");
        final Config config = ChaosDanmuTool.getConfig();

        loadPosition(config);
        thisStage.setOnCloseRequest(event -> onClose(config));
        anchorPane.setStyle("-fx-background-color: transparent;");
        btnClose.setStyle("-fx-background-color: transparent; -fx-text-fill: white");
        btnReload.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: white");
        initDanmuView(config);

        logger.debug("[DanmuView] Initialized.");
    }

    private void initDanmuView(Config config) throws Exception {
        danmuView.setStyle("-fx-background-color: transparent;");
        danmuView.setMouseTransparent(true);

        WebEngine webEngine = danmuView.getEngine();
        String html = HTMLReplaceVar.get(config);

        if (html == null) {
            webEngine.loadContent("Failed to load html.");
            logger.error("[DanmuView] Failed to load html.");
            return;
        }

        webEngine.documentProperty().addListener((observable, oldValue, newValue) -> {
            final WebPage webPage = Accessor.getPageFor(webEngine);
            webPage.setBackgroundColor(11111111);
            webPage.setBackgroundColor(0);
            System.out.println("test");
            thisStage.setWidth(thisStage.getWidth() - 1);
            thisStage.setWidth(thisStage.getWidth() + 1);
        });

        webEngine.loadContent(html);
    }

    @FXML
    protected void onAnchorPaneMousePressed(MouseEvent event) {
        lastPressedX = event.getSceneX();
        lastPressedY = event.getSceneY();
    }

    @FXML
    protected void onAnchorPaneMouseDragged(MouseEvent event) {
        thisStage.setX(event.getScreenX() - lastPressedX);
        thisStage.setY(event.getScreenY() - lastPressedY);
    }

    @FXML
    protected void onBtnCloseClicked(MouseEvent event) {
        final Config config = ChaosDanmuTool.getConfig();
        savePosition(config);
        thisStage.close();
    }

    @FXML
    protected void onBtnReloadClicked(MouseEvent event) throws IOException {
        final Config config = ChaosDanmuTool.getConfig();

        WebEngine webEngine = danmuView.getEngine();
        String html = HTMLReplaceVar.get(config);

        if (html == null) {
            webEngine.loadContent("Failed to load html.");
            logger.error("[DanmuView] Failed to load html.");
            return;
        }

        webEngine.loadContent(html);
    }

    private void loadPosition(Config config) {
        logger.debug("[DanmuView] Loading position.");
        if (config.danmuView.posX != 0) thisStage.setX(config.danmuView.posX);
        if (config.danmuView.posY != 0) thisStage.setY(config.danmuView.posY);
        logger.debug("[DanmuView] Position loaded.");
    }

    private void savePosition(Config config) {
        logger.debug("[DanmuView] Saving position.");
        config.danmuView.posX = thisStage.getX();
        config.danmuView.posY = thisStage.getY();
        ChaosDanmuTool.saveConfig();
        logger.debug("[DanmuView] Position saved.");
    }

    private void onClose(Config config) {
        savePosition(config);
    }
}
