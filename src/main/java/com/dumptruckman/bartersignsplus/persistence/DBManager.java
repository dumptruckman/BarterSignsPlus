package com.dumptruckman.bartersignsplus.persistence;

import com.dumptruckman.bartersignsplus.config.Config;

import java.io.IOException;

/**
 * @author dumptruckman
 */
public class DBManager {

    private static Database database = null;

    public static void load() throws IOException {
        if (Config.DB_TYPE.getString().equalsIgnoreCase("flatfile")) {
            loadFlatfileDB();
        } else {
            throw new IOException("");
        }
    }

    public static void save(boolean isReload) {
        database.save(isReload);
    }

    private static void loadFlatfileDB() {
        database = new Flatfile();
    }

    private static Database getDB() {
        return database;
    }
}
