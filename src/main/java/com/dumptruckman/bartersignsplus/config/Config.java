package com.dumptruckman.bartersignsplus.config;

import com.dumptruckman.bartersignsplus.BarterSignsPlus;

import java.io.File;
import java.io.IOException;

/**
 * @author dumptruckman, SwearWord
 */
public enum Config {
    LANGUAGE_FILE_NAME("settings.language_file", "english.yml", "# This is the language file you wish to use."),
    DEBUG_MODE("settings.debug_mode.enable", false, "# Enables debug mode."),
    DATA_SAVE_PERIOD("settings.data.save_every", 300, "# This is often plugin data is written to the disk."),

    SIGN_INDESTRUCTIBLE("signs.default.indestructible", true,
            "# Whether or not signs are indestructible by default.",
            "# Note: You may still remove signs through the sign's menu."),
    SIGN_DROPS_ITEMS("signs.default.drops_items", true, "# Whether or not signs drop items on break by default."),
    SIGN_UNLIMITED_STOCK("signs.default.unlimited_stock", false, "# Signs will have unlimited stock by default."),
    ;

    private String path;
    private Object def;
    private String[] comments;

    Config(String path, Object def, String...comments) {
        this.path = path;
        this.def = def;
        this.comments = comments;
    }

    public final Boolean getBoolean() {
        return config.getBoolean(path, (Boolean)def);
    }

    public final Integer getInt() {
        return config.getInt(path, (Integer)def);
    }

    public final String getString() {
        return config.getString(path, (String)def);
    }

    /**
     * Retrieves the path for a config option
     * @return The path for a config option
     */
    private String getPath() {
        return path;
    }

    /**
     * Retrieves the default value for a config path
     * @return The default value for a config path
     */
    private Object getDefault() {
        return def;
    }

    /**
     * Retrieves the comment for a config path
     * @return The comments for a config path
     */
    private String[] getComments() {
        if (comments != null) {
            return comments;
        }

        String[] comments = new String[1];
        comments[0] = "";
        return comments;
    }

    private static CommentedConfiguration config;

    /**
     * Loads the configuration data into memory and sets defaults
     * @throws IOException
     */
    public static void load() throws IOException {
        // Make the data folders
        BarterSignsPlus.getInstance().getDataFolder().mkdirs();

        // Check if the config file exists.  If not, create it.
        File configFile = new File(BarterSignsPlus.getInstance().getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            configFile.createNewFile();
        }

        // Load the configuration file into memory
        config = new CommentedConfiguration(new File(BarterSignsPlus.getInstance().getDataFolder(), "config.yml"));
        config.load();

        // Sets defaults config values
        setDefaults();

        // Saves the configuration from memory to file
        config.save();
    }

    /**
     * Loads default settings for any missing config values
     */
    private static void setDefaults() {
        for (Config path : Config.values()) {
            config.addComment(path.getPath(), path.getComments());
            if (config.getString(path.getPath()) == null) {
                config.setProperty(path.getPath(), path.getDefault());
            }
        }
    }
}
