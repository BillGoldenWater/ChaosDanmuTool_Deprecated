package indi.goldenwater.chaosdanmutool.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import indi.goldenwater.chaosdanmutool.utils.ReadInputStreamAsStr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigManager<T> {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final T defaultConfig;
    private final File path;
    private final Class<T> type;

    private T config;

    public ConfigManager(T defaultConfig, File path, Class<T> type) {
        this.defaultConfig = defaultConfig;
        this.path = path;
        this.type = type;
    }

    public void load() throws IOException {
        String configJsonStr;
        if (path.exists()) {
            FileInputStream inputStream = new FileInputStream(path);
            configJsonStr = ReadInputStreamAsStr.read(inputStream);
            inputStream.close();
        } else {
            configJsonStr = gson.toJson(defaultConfig);
        }
        config = gson.fromJson(configJsonStr, type);
    }

    public void save() throws IOException {
        String configJsonStr = gson.toJson(config);

        FileWriter outputStream = new FileWriter(path);
        outputStream.write(configJsonStr);
        outputStream.flush();
        outputStream.close();
    }

    public void saveDefaultConfig() throws IOException {
        if (!path.exists()) {
            String configJsonStr = gson.toJson(defaultConfig);
            FileWriter outputStream = new FileWriter(path);
            outputStream.write(configJsonStr);
            outputStream.flush();
            outputStream.close();
        }
    }

    public T getConfig() {
        return config;
    }
}
