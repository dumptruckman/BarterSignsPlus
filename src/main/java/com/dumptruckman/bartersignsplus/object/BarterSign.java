package com.dumptruckman.bartersignsplus.object;

import org.bukkit.block.Block;

/**
 * @author dumptruckman
 */
public abstract class BarterSign {

    private final Block block;
    private final String id;

    public BarterSign(final Block block) {
        this.block = block;
        id = block.getWorld().getName() + "." + block.getX() + "," + block.getY() + "," + block.getZ();
    }

    public abstract boolean isIndestructible();
    public abstract boolean isDroppingItemsOnBreak();
    public abstract void dropContents();
}
