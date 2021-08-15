package indi.goldenwater.chaosdanmutool.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPRequest {
    public static byte[] get(String urlStr) throws IOException {
        URL url = new URL(urlStr);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        InputStream responseStream = connection.getInputStream();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int data;
        while ((data = responseStream.read()) != -1) {
            byteArrayOutputStream.write(data);
        }

        return byteArrayOutputStream.toByteArray();
    }

    public static String getAsString(String url) {
        try {
            return new String(get(url));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
