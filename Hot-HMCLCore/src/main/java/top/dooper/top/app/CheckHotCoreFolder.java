package top.dooper.top.app;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CheckHotCoreFolder {
    public static String getCurrentDirectory() {
        return System.getProperty("user.dir");
    }
    public static boolean existHotFolder() {
        String hotFolderPath = getCurrentDirectory() + "/Hot";
        return Paths.get(hotFolderPath).toFile().exists();
    }

    public static String getHotFolderPath() {
        return getCurrentDirectory() + "/Hot";
    }

    public static boolean existConfigFile() {
        Path configFile = Paths.get(getHotFolderPath(), "hot-config.json");
        return configFile.toFile().exists();
    }

    public static String getConfigFilePath() {
        return getHotFolderPath() + "/hot-config.json";
    }
}
