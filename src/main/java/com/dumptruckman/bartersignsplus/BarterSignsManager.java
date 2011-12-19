package com.dumptruckman.bartersignsplus;

import com.dumptruckman.bartersignsplus.locale.Language;
import com.dumptruckman.bartersignsplus.persistence.DB;
import com.dumptruckman.bartersignsplus.sign.BarterSign;
import com.dumptruckman.bartersignsplus.sign.SignRefreshTask;
import com.dumptruckman.bartersignsplus.util.Logging;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

/**
 * @author dumptruckman
 */
public class BarterSignsManager {

    private static final int SIGN_MENU_DURATION = 10;

    public static final int BARTER_SIGN = 0;
    public static final int REFRESH_TASK = 1;
    public static final int REFRESH_TASK_ID = 2;

    private static HashMap<String, BarterSign> signs = new HashMap<String, BarterSign>();

    /**
     * Obtain the sign object from memory
     * @param block Block where sign is physically
     * @return Sign object
     */
    public static BarterSign getBarterSign(Block block) {
        String id = getSignId(block);
        return signs.get(id);
    }

    /**
     * Creates a new barter sign at block's location
     * @param block Block where sign is physically
     * @return Sign object
     */
    public static BarterSign createBarterSign(Block block) {
        String id = getSignId(block);
        BarterSign barterSign = new BarterSign(block);
        signs.put(id, barterSign);
        return barterSign;
    }

    /**
     * Checks to see if a barter sign is registered for given block
     * @param block Block where sign is physically
     * @return true if sign is active at location
     */
    public static Boolean barterSignExists(Block block) {
        String id = getSignId(block);
        return signs.containsKey(id);
    }

    /**
     * Removes a sign from memory and wipes it from the database
     * @param block Block where sign is to remove
     */
    public static void removeBarterSign(Block block) {
        if (barterSignExists(block)) {
            BarterSign barterSign = getBarterSign(block);
            cancelSignRefresh(barterSign.getId());
            barterSign.destroy();
            signs.remove(barterSign.getId());
        }
    }

    /**
     * Loads signs from database into memory
     */
    public static void loadBarterSigns() {
        Logging.debug("Loading sign data");
        // TODO
    }

    public static void getVendorBench() {
        // TODO
    }

    /**
     * Helper method to get a string id for the sign at block's location.  This id is used for storage in database and memory.
     * @param block Block where sign is physically
     * @return Id of sign
     */
    public static String getSignId(Block block) {
        return block.getWorld().getName() + "." + block.getX() + "," + block.getY() + "," + block.getZ();
    }

    public static void cancelSignRefresh(String id) {
        int taskId = (Integer) signs.get(id).getRefreshTaskId();
        if (taskId != -1) {
            Bukkit.getServer().getScheduler().cancelTask(taskId);
        }
    }

    public static void scheduleSignRefresh(String id) {
        cancelSignRefresh(id);

        int taskId = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(BarterSignsPlus.getInstance(),
                (SignRefreshTask) signs.get(id).getRefreshTask(), (long) (SIGN_MENU_DURATION * 20));
        signs.get(id).setRefreshTaskId(taskId);
    }
}
