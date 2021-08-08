package indi.goldenwater.chaosdanmutool.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadInputStreamAsStr {
    public static String read(InputStream inputStream) throws IOException {
        if (inputStream == null) return null;
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        StringBuilder stringBuilder = new StringBuilder();

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append("\n");
        }

        inputStreamReader.close();
        bufferedReader.close();

        return stringBuilder.toString();
    }
}
