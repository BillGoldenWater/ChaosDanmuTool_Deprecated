package indi.goldenwater.chaosdanmutool.utils;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigManager<T> {
    private final Gson gson = new Gson();
    private T config;

    public void load(String pathInJar, File path, Class<T> type) throws IOException {
        String configJsonStr;
        if (path.exists()) {
            configJsonStr = ReadInputStreamAsStr.read(new FileInputStream(path));
        } else {
            configJsonStr = ReadFileInJar.readAsString(pathInJar);
        }
        config = gson.fromJson(configJsonStr, type);
    }

    public void save(File path) throws IOException {
        String configJsonStr = gson.toJson(config);

        if (!path.createNewFile()) return;

        FileWriter outputStream = new FileWriter(path);
        outputStream.write(configJsonStr);
    }

    public T getConfig() {
        return config;
    }
}
