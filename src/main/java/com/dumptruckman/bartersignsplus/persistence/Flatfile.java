package com.dumptruckman.bartersignsplus.persistence;

import com.dumptruckman.bartersignsplus.BarterSignsPlus;
import com.dumptruckman.bartersignsplus.config.Config;
import com.dumptruckman.bartersignsplus.sign.BarterSign;
import com.dumptruckman.bartersignsplus.util.Logging;
import com.dumptruckman.bartersignsplus.util.MinecraftTools;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.util.config.Configuration;

import java.io.File;
import java.io.IOException;

/**
 * @author dumptruckman
 */
public class Flatfile implements Database {

    private static FileConfiguration data;
    private static File dataFile;
    private static int dataSaveTaskId = -1;

    /**
     * Loads the language data into memory and sets defaults
     * @throws java.io.IOException
     */
    public void load() throws IOException {
        // Make the data folders
        BarterSignsPlus.getInstance().getDataFolder().mkdirs();

        // Check if the language file exists.  If not, create it.
        dataFile = new File(BarterSignsPlus.getInstance().getDataFolder(), "data.yml");
        if (!dataFile.exists()) {
            dataFile.createNewFile();
        }

        // Load the language file into memory
        data = YamlConfiguration.loadConfiguration(dataFile);

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
        try {
            data.save(dataFile);
        } catch (IOException e) {
            Logging.severe("Sign data was unable to be saved! " + e.getMessage());
        }
    }

    public void initializeSign(BarterSign sign) {
        
    }

    public void removeSign(BarterSign sign) {
        
    }
}
