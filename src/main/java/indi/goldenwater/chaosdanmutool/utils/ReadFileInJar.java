package indi.goldenwater.chaosdanmutool.utils;

import java.io.IOException;
import java.io.InputStream;

public class ReadFileInJar {
    public static String readAsString(String name) throws IOException {
        InputStream inputStream = ReadFileInJar.class.getResourceAsStream(name);

        String result = ReadInputStreamAsStr.read(inputStream);
        if (inputStream != null) inputStream.close();

        return result;
    }
}
