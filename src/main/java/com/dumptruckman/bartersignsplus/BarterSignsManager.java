package com.dumptruckman.bartersignsplus;

import com.dumptruckman.bartersignsplus.object.BarterSign;
import com.dumptruckman.bartersignsplus.util.Logging;
import org.bukkit.Location;

/**
 * @author dumptruckman
 */
public class BarterSignsManager {

    public static BarterSign getBarterSign(Location location) {
        return getBarterSign(location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public static BarterSign getBarterSign(String world, int x, int y, int z) {
        // TODO
        return null;
    }

    public static void createBarterSign(Location location) {
        createBarterSign(location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public static void createBarterSign(String world, int x, int y, int z) {
        // TODO
    }

    public static void loadBarterSigns() {
        Logging.debug("Loading sign data");
        // TODO
    }

    public static void getVendorBench() {
        // TODO
    }
}
