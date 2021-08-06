package indi.goldenwater.chaosdanmutool.controller;

import indi.goldenwater.chaosdanmutool.ChaosDanmuTool;
import indi.goldenwater.chaosdanmutool.config.Config;
import indi.goldenwater.chaosdanmutool.utils.ReadFileInJar;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;

public class DanmuViewController {
    @FXML
    protected WebView danmuView;

    @FXML
    protected void initialize() throws IOException {
        initDanmuView();
    }

    private void initDanmuView() throws IOException {
        final Config config = ChaosDanmuTool.getConfig();

        WebEngine webEngine = danmuView.getEngine();
        String html = ReadFileInJar.readAsString("/html/index.html");

        if (html == null) {
            webEngine.loadContent("Failed to load html.");
            return;
        }

        html = html.replace("{{port}}", String.valueOf(config.webSocketServer.port))
                .replace("{{maxListNumber}}", String.valueOf(config.danmuView.maxDanmuNumber));

        webEngine.loadContent(html);
    }
}
