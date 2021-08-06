package indi.goldenwater.chaosdanmutool.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import indi.goldenwater.chaosdanmutool.utils.ReadFileInJar;
import indi.goldenwater.chaosdanmutool.utils.ReadInputStreamAsStr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigManager<T> {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final String pathInJar;
    private final File path;
    private final Class<T> type;

    private T config;

    public ConfigManager(String pathInJar, File path, Class<T> type) {
        this.pathInJar = pathInJar;
        this.path = path;
        this.type = type;
    }

    public void load() throws IOException {
        String configJsonStr;
        if (path.exists()) {
            configJsonStr = ReadInputStreamAsStr.read(new FileInputStream(path));
        } else {
            configJsonStr = ReadFileInJar.readAsString(pathInJar);
        }
        config = gson.fromJson(configJsonStr, type);
    }

    public void save() throws IOException {
        String configJsonStr = gson.toJson(config);

        FileWriter outputStream = new FileWriter(path);
        outputStream.write(configJsonStr);
        outputStream.flush();
    }

    public T getConfig() {
        return config;
    }
}
