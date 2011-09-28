package com.dumptruckman.bartersignsplus.data;

import com.dumptruckman.bartersignsplus.BarterSignsPlus;
import com.dumptruckman.bartersignsplus.config.Config;
import com.dumptruckman.bartersignsplus.util.MinecraftTools;
import org.bukkit.util.config.Configuration;

import java.io.File;
import java.io.IOException;

/**
 * @author dumptruckman
 */
public enum Data {

    ;

    private String path;

    Data(String path) {
        this.path = path;
    }

    /**
     * Retrieves the path for a config option
     * @return The path for a config option
     */
    public String getPath() {
        return path;
    }

    private static Configuration data;
    private static int dataSaveTaskId = -1;

    /**
     * Loads the language data into memory and sets defaults
     * @throws java.io.IOException
     */
    public static void load() throws IOException {
        // Make the data folders
        BarterSignsPlus.getInstance().getDataFolder().mkdirs();

        // Check if the language file exists.  If not, create it.
        File languageFile = new File(BarterSignsPlus.getInstance().getDataFolder(), "data.yml");
        if (!languageFile.exists()) {
            languageFile.createNewFile();
        }

        // Load the language file into memory
        data = new Configuration(new File(BarterSignsPlus.getInstance().getDataFolder(), "config.yml"));
        data.load();

        // Start the data save timer
        dataSaveTaskId = BarterSignsPlus.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(
                BarterSignsPlus.getInstance(),
                new DataSaveTimer(BarterSignsPlus.getInstance()),
                MinecraftTools.convertSecondsToTicks(Config.DATA_SAVE_PERIOD.getInt()),
                MinecraftTools.convertSecondsToTicks(Config.DATA_SAVE_PERIOD.getInt()));
    }

    public static void save(boolean isReload) {
        if (isReload) {
            BarterSignsPlus.getInstance().getServer().getScheduler().cancelTask(dataSaveTaskId);
            dataSaveTaskId = -1;
        }
        data.save();
    }
}
