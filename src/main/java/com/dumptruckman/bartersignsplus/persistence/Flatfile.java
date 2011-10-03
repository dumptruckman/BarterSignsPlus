package com.dumptruckman.bartersignsplus.persistence;

import com.dumptruckman.bartersignsplus.BarterSignsPlus;
import com.dumptruckman.bartersignsplus.config.Config;
import com.dumptruckman.bartersignsplus.sign.BarterSign;
import com.dumptruckman.bartersignsplus.util.MinecraftTools;
import org.bukkit.util.config.Configuration;

import java.io.File;
import java.io.IOException;

/**
 * @author dumptruckman
 */
public class Flatfile implements Database {

    private static Configuration data;
    private static int dataSaveTaskId = -1;

    /**
     * Loads the language data into memory and sets defaults
     * @throws java.io.IOException
     */
    public void load() throws IOException {
        // Make the data folders
        BarterSignsPlus.getInstance().getDataFolder().mkdirs();

        // Check if the language file exists.  If not, create it.
        File dataFile = new File(BarterSignsPlus.getInstance().getDataFolder(), "data.yml");
        if (!dataFile.exists()) {
            dataFile.createNewFile();
        }

        // Load the language file into memory
        data = new Configuration(dataFile);
        data.load();

        // Start the data save timer
        dataSaveTaskId = BarterSignsPlus.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(
                BarterSignsPlus.getInstance(),
                new FlatfileSaveTimer(BarterSignsPlus.getInstance()),
                MinecraftTools.convertSecondsToTicks(Config.DATA_SAVE_PERIOD.getInt()),
                MinecraftTools.convertSecondsToTicks(Config.DATA_SAVE_PERIOD.getInt()));
    }

    public void save(boolean isReload) {
        if (isReload) {
            BarterSignsPlus.getInstance().getServer().getScheduler().cancelTask(dataSaveTaskId);
            dataSaveTaskId = -1;
        }
        data.save();
    }

    public void initializeSign(BarterSign sign) {
        
    }

    public void removeSign(BarterSign sign) {
        data.removeProperty("signs." + sign.getId());
    }
}
