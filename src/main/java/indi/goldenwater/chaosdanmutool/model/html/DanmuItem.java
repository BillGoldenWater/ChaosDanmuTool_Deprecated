package indi.goldenwater.chaosdanmutool.model.html;

public class DanmuItem {
    public String start = "<span class=\"danmu-item\">";
    public String end = "</span>";

    @Override
    public String toString() {
        return String.format("%s%s", start, end);
    }
}
