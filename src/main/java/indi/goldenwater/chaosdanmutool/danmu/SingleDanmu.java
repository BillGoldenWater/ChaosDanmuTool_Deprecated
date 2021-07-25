package indi.goldenwater.chaosdanmutool.danmu;

import javafx.scene.layout.HBox;

public class SingleDanmu extends HBox {
    private String danmuContent;

    public SingleDanmu() {
        super();
    }

    public SingleDanmu(String content) {
        super();
        danmuContent = content;
    }

    public void setDanmuContent(String danmuContent) {
        this.danmuContent = danmuContent;
    }
}
