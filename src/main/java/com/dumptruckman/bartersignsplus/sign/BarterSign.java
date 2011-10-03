package com.dumptruckman.bartersignsplus.sign;

import com.dumptruckman.bartersignsplus.BarterSignsManager;
import com.dumptruckman.bartersignsplus.config.Config;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 * @author dumptruckman
 */
public class BarterSign {

    private final Block block;
    private final String id;

    private Boolean indestructible;
    private Boolean dropsContents;
    private Player owner;

    public BarterSign(final Block block) {
        this.block = block;
        this.id = BarterSignsManager.getSignId(block);
        this.indestructible = Config.SIGN_INDESTRUCTIBLE.getBoolean();
        this.dropsContents = Config.SIGN_DROPS_ITEMS.getBoolean();
    }

    /**
     * Tells whether this sign should be indestructible
     * @return true if sign should be indestructible
     */
    public Boolean isIndestructible() {
        return this.indestructible;
    }

    /**
     * Tells whether the items contained within this sign should drop when the sign breaks
     * @return true if items should drop on sign break
     */
    public Boolean isDroppingItemsOnBreak() {
        return this.dropsContents;
    }

    /**
     * Causes the contents of this sign to drop to the ground
     */
    public void dropContents() {
        
    }

    /**
     * Sets the owner of this sign
     * @param player owner of sign
     */
    public void setOwner(Player player) {
        this.owner = player;
    }

    /**
     * Returns the id of this sign
     * @return id of this sign
     */
    public String getId() {
        return this.id;
    }

    /**
     * Does everything necessary to remove this sign
     */
    public void destroy() {
        if (this.isDroppingItemsOnBreak()) this.dropContents();
    }
}