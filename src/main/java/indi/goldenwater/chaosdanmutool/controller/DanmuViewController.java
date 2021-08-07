package indi.goldenwater.chaosdanmutool.controller;

import indi.goldenwater.chaosdanmutool.ChaosDanmuTool;
import indi.goldenwater.chaosdanmutool.config.Config;
import indi.goldenwater.chaosdanmutool.utils.ReadFileInJar;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.apache.logging.log4j.Logger;

public class DanmuViewController {
    final Logger logger = ChaosDanmuTool.getLogger();

    @FXML
    protected WebView danmuView;

    @FXML
    protected AnchorPane anchorPane;

    @FXML
    protected void initialize() throws Exception {
        logger.info("[Danmu View] Initializing.");
        final Config config = ChaosDanmuTool.getConfig();

        anchorPane.setStyle("-fx-background-color: transparent;");
        initDanmuView(config);

        logger.info("[Danmu View] Initialized.");
    }

    private void initDanmuView(Config config) throws Exception {
        danmuView.setMouseTransparent(true);
        danmuView.setStyle("-fx-background-color: transparent;");

        WebEngine webEngine = danmuView.getEngine();
        String html = ReadFileInJar.readAsString("/html/index.html");

        if (html == null) {
            webEngine.loadContent("Failed to load html.");
            logger.error("[DanmuView] Failed to load html.");
            return;
        }

        html = html.replace("{{port}}", String.valueOf(config.danmuView.webSocketServer.port))
                .replace("{{maxListNumber}}", String.valueOf(config.danmuView.maxDanmuNumber))
                .replace("{{outerMargin}}", config.danmuView.style.outerMargin + "px")
                .replace("{{backgroundColorRed}}", String.valueOf(config.danmuView.style.backgroundColor.red))
                .replace("{{backgroundColorGreen}}", String.valueOf(config.danmuView.style.backgroundColor.green))
                .replace("{{backgroundColorBlue}}", String.valueOf(config.danmuView.style.backgroundColor.blue))
                .replace("{{backgroundColorAlpha}}", String.valueOf(config.danmuView.style.backgroundColor.alpha));

        final com.sun.webkit.WebPage webPage = com.sun.javafx.webkit.Accessor.getPageFor(webEngine);
        webPage.setBackgroundColor(0);

        webEngine.loadContent(html);
    }
}
