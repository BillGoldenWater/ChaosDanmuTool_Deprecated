package indi.goldenwater.chaosdanmutool.controller;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class DanmuViewController {
    @FXML
    protected WebView danmuView;

    @FXML
    protected void initialize() {
        WebEngine engine = danmuView.getEngine();

        engine.load("https://www.bilibili.com");
    }
}
