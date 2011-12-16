package com.dumptruckman.bartersignsplus;

import com.dumptruckman.bartersignsplus.config.Config;
import com.dumptruckman.bartersignsplus.persistence.DB;
import com.dumptruckman.bartersignsplus.listener.BlockEvents;
import com.dumptruckman.bartersignsplus.listener.EntityEvents;
import com.dumptruckman.bartersignsplus.listener.PlayerEvents;
import com.dumptruckman.bartersignsplus.locale.Language;
import com.dumptruckman.bartersignsplus.sign.BarterSign;
import com.dumptruckman.bartersignsplus.util.Logging;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

/**
 * @author dumptruckman
 */
public class BarterSignsPlus extends JavaPlugin {

    private static BarterSignsPlus instance = null;

    private final BlockEvents blockListener = new BlockEvents();
    private final PlayerEvents playerListener = new PlayerEvents();
    private final EntityEvents entityListener = new EntityEvents();

    {
        ConfigurationSerialization.registerClass(BarterSign.class);
    }

    final public void onDisable() {
        // Save the plugin data
        DB.save(true);

        // Display disable message/version info
        Logging.info("disabled.", true);
    }

    final public void onEnable() {
        // Store the instance of this plugin
        instance = this;

        // Grab the PluginManager
        final PluginManager pm = getServer().getPluginManager();

        // Loads the configuration
        try {
            Config.load();
        } catch (IOException e) {  // Catch errors loading the config file and exit out if found.
            Logging.severe("Encountered an error while loading the configuration file.  Disabling...");
            pm.disablePlugin(this);
            return;
        }

        // Loads the language
        try {
            Language.load();
        } catch (IOException e) {  // Catch errors loading the language file and exit out if found.
            Logging.severe("Encountered an error while loading the language file.  Disabling...");
            pm.disablePlugin(this);
            return;
        }

        // Loads the data
        try {
            DB.load();
        } catch (IOException e) {  // Catch errors loading the language file and exit out if found.
            Logging.severe("Encountered an error while loading the data file.  Disabling...");
            pm.disablePlugin(this);
            return;
        }

        // Register Events
        registerEvents();

        //Call Home (usage stats)
        //if (!this.getDescription().getVersion().contains("${"))
        //    CallHome.load(this);

        // Display enable message/version info
        Logging.info("enabled.", true);
    }

    final public void registerEvents() {
        final PluginManager pm = getServer().getPluginManager();
        // Event registering goes here
        pm.registerEvent(Event.Type.SIGN_CHANGE, blockListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_PLACE, blockListener, Event.Priority.Highest, this);
        pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Event.Priority.Lowest, this);
        pm.registerEvent(Event.Type.PLAYER_TOGGLE_SNEAK, playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_DAMAGE, blockListener, Event.Priority.Highest, this);
        pm.registerEvent(Event.Type.BLOCK_BREAK, blockListener, Event.Priority.Highest, this);
        pm.registerEvent(Event.Type.BLOCK_PHYSICS, blockListener, Event.Priority.Highest, this);
        pm.registerEvent(Event.Type.BLOCK_FADE, blockListener, Event.Priority.Highest, this);
        pm.registerEvent(Event.Type.BLOCK_BURN, blockListener, Event.Priority.Highest, this);
        pm.registerEvent(Event.Type.ENTITY_EXPLODE, entityListener, Event.Priority.Highest, this);
    }

    public static BarterSignsPlus getInstance() {
        return instance;
    }
}
