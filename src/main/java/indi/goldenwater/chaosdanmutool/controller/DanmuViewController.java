package indi.goldenwater.chaosdanmutool.controller;

import com.sun.javafx.webkit.Accessor;
import com.sun.webkit.WebPage;
import indi.goldenwater.chaosdanmutool.ChaosDanmuTool;
import indi.goldenwater.chaosdanmutool.config.Config;
import indi.goldenwater.chaosdanmutool.utils.HTMLReplaceVar;
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
//        danmuView.setMouseTransparent(true);
        danmuView.setStyle("-fx-background-color: transparent;");

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
        });

        webEngine.loadContent(html);
    }
}
