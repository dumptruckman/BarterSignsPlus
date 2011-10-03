package com.dumptruckman.bartersignsplus;

import com.dumptruckman.bartersignsplus.sign.BarterSign;
import com.dumptruckman.bartersignsplus.util.Logging;
import org.bukkit.block.Block;

import java.util.HashMap;

/**
 * @author dumptruckman
 */
public class BarterSignsManager {

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
     * @param barterSign sign to remove
     */
    public static void destroyBarterSign(BarterSign barterSign) {
        signs.remove(barterSign.getId());
        barterSign.destroy();
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
}
