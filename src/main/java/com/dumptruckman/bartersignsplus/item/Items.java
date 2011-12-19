package com.dumptruckman.bartersignsplus.item;

import com.dumptruckman.bartersignsplus.BarterSignsPlus;
import com.dumptruckman.bartersignsplus.config.Config;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * @author dumptruckman
 */
public class Items {

    private static FileConfiguration items;

    /**
     * Loads the language data into memory and sets defaults
     *
     * @throws java.io.IOException
     */
    public static void load() throws IOException {

        // Make the data folders
        BarterSignsPlus.getInstance().getDataFolder().mkdirs();

        // Check if the language file exists.  If not, create it.
        File itemsFile = new File(BarterSignsPlus.getInstance().getDataFolder(), "items.yml");
        if (!itemsFile.exists()) {
            itemsFile.createNewFile();
        }

        // Load the language file into memory
        items = YamlConfiguration.loadConfiguration(itemsFile);
    }
}
