package com.dumptruckman.bartersignsplus.persistence;

import com.dumptruckman.bartersignsplus.BarterSignsPlus;
import com.dumptruckman.bartersignsplus.config.Config;
import com.dumptruckman.bartersignsplus.util.Logging;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * @author dumptruckman
 */
public class DB {

    private static FileConfiguration data = null;
    private static File dataFile;
    private static int dataSaveTaskId = -1;

    public static void load() throws IOException {
        // Make the data folders
        BarterSignsPlus.getInstance().getDataFolder().mkdirs();

        // Check if the language file exists.  If not, create it.
        dataFile = new File(BarterSignsPlus.getInstance().getDataFolder(), "data.yml");
        if (!dataFile.exists()) {
            dataFile.createNewFile();
        }

        // Load the language file into memory
        data = YamlConfiguration.loadConfiguration(dataFile);
    }

    public static void save(boolean isReload) {
        if (isReload) {
            BarterSignsPlus.getInstance().getServer().getScheduler().cancelTask(dataSaveTaskId);
            dataSaveTaskId = -1;
        }
        try {
            data.save(dataFile);
        } catch (IOException e) {
            Logging.severe("Sign data was unable to be saved! " + e.getMessage());
        }
    }

    public static FileConfiguration getData() {
        return data;
    }
}
