package indi.goldenwater.chaosdanmutool.utils;

import indi.goldenwater.chaosdanmutool.config.Config;

import java.io.IOException;

public class HTMLReplaceVar {
    public static String get(Config config) throws IOException {
        String html = ReadFileInJar.readAsString("/html/index.html");

        if (html == null) return null;

        html = html
                .replace("/*start-for-coding*/", "/*")
                .replace("/*end-for-coding*/", "*/")
                .replace("<!--start-for-coding-->", "<!--")
                .replace("<!--end-for-coding-->", "-->")
                .replace("/*start-actual-use*//*", "")
                .replace("/*end-actual-use*/", "");

        html = html.replace("{{port}}", String.valueOf(config.danmuView.webSocketServer.port))
                .replace("{{maxListNumber}}", String.valueOf(config.danmuView.maxDanmuNumber))
                .replace("{{outerMargin}}", config.danmuView.style.outerMargin + "px")
                .replace("{{backgroundColorRed}}", String.valueOf(config.danmuView.style.backgroundColor.red))
                .replace("{{backgroundColorGreen}}", String.valueOf(config.danmuView.style.backgroundColor.green))
                .replace("{{backgroundColorBlue}}", String.valueOf(config.danmuView.style.backgroundColor.blue))
                .replace("{{backgroundColorAlpha}}", String.valueOf(config.danmuView.style.backgroundColor.alpha));

        return html;
    }
}
