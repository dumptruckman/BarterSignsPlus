package com.dumptruckman.bartersignsplus.listener;

import com.dumptruckman.bartersignsplus.BarterSignsManager;
import com.dumptruckman.bartersignsplus.object.BarterSign;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.block.*;

/**
 * @author dumptruckman
 */
public class BarterSignsBlockListener extends BlockListener {

    public void onSignChange(SignChangeEvent event) {
        // Throw out unimportant events immediately
        if (event.isCancelled()) return;
    }

    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.isCancelled()) return;
        Block block = event.getBlockAgainst();
        if (!(block.getState() instanceof Sign)) return;
        if (!BarterSignsManager.barterSignExists(block)) return;
        // Cancel block places when trying to place against a barter sign
        event.setCancelled(true);
    }

    @Override
    public void onBlockBreak(final BlockBreakEvent event) {
        //if (event.isCancelled()) return;

        Block block = event.getBlock();
        if (!(block.getState() instanceof Sign)) return;

        if (!BarterSignsManager.barterSignExists(block)) return;

        BarterSign sign = BarterSignsManager.getBarterSign(event.getBlock());
        // Why did I do this?:
        if (sign == null) {
            sign = new BarterSign(plugin, block);
        }
        final BarterSign barterSign = sign;

        if (barterSign.getMenuIndex() != barterSign.REMOVE) {
            event.setCancelled(true);
        }

        if (!event.isCancelled() && plugin.config.getBoolean(ConfigPath.SIGN_DROPS_ITEMS.getPath(),
                (Boolean) ConfigPath.SIGN_DROPS_ITEMS.getDefault())) {
            barterSign.drop();
            return;
        }

        if (!event.isCancelled()) {
            if (BarterSign.SignPhase.READY.equalTo(barterSign.getPhase())) {
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        event.getPlayer().sendBlockChange(barterSign.getLocation(), 0, (byte) 0);
                        barterSign.showMenu(null);
                    }
                }, 20L);
            } else {
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        event.getPlayer().sendBlockChange(barterSign.getLocation(), 0, (byte) 0);
                        plugin.signAndMessage(barterSign.getSign(), event.getPlayer(),
                                LanguagePath.SIGN_STOCK_SETUP.getPath(), barterSign.getOwner());
                        barterSign.getBlock().getState().update(true);
                    }
                }, 20L);
            }
        }
    }

    @Override
    public void onBlockDamage(BlockDamageEvent event) {
        //if (event.isCancelled()) return;

        Block block = event.getBlock();
        if (!(block.getState() instanceof Sign)) return;

        if (!BarterSign.exists(plugin, block)) return;

        BarterSign barterSign = BarterSignManager.getBarterSignFromBlock(event.getBlock());
        if (barterSign == null) {
            barterSign = new BarterSign(plugin, block);
        }
        if (!BarterSign.SignPhase.READY.equalTo(barterSign.getPhase())
                || barterSign.getMenuIndex() != barterSign.REMOVE) {
            event.setCancelled(true);
        }
    }

    @Override
    public void onBlockBurn(BlockBurnEvent event) {
        if (event.isCancelled()) return;

        Block block = event.getBlock();
        if (!(block.getState() instanceof Sign)) return;

        if (!BarterSign.exists(plugin, block)) return;

        event.setCancelled(plugin.config.getBoolean(ConfigPath.SIGN_INDESTRUCTIBLE.getPath(),
                (Boolean) ConfigPath.SIGN_INDESTRUCTIBLE.getDefault()));

        if (!event.isCancelled() && plugin.config.getBoolean(ConfigPath.SIGN_DROPS_ITEMS.getPath(),
                (Boolean) ConfigPath.SIGN_DROPS_ITEMS.getDefault())) {
            BarterSign barterSign = BarterSignManager.getBarterSignFromBlock(event.getBlock());
            if (barterSign == null) {
                return;
            }
        }
    }

    @Override
    public void onBlockFade(BlockFadeEvent event) {
        if (event.isCancelled()) return;

        Block block = event.getBlock();
        if (!(block.getState() instanceof Sign)) return;

        if (!BarterSign.exists(plugin, block)) return;

        event.setCancelled(plugin.config.getBoolean(ConfigPath.SIGN_INDESTRUCTIBLE.getPath(),
                (Boolean) ConfigPath.SIGN_INDESTRUCTIBLE.getDefault()));

        if (!event.isCancelled() && plugin.config.getBoolean(ConfigPath.SIGN_DROPS_ITEMS.getPath(),
                (Boolean) ConfigPath.SIGN_DROPS_ITEMS.getDefault())) {
            BarterSign barterSign = BarterSignManager.getBarterSignFromBlock(event.getBlock());
            if (barterSign == null) {
                return;
            }
        }
    }

    @Override
    public void onBlockPhysics(BlockPhysicsEvent event) {
        if (event.isCancelled()) return;

        Block block = event.getBlock();
        if (!(block.getState() instanceof Sign)) return;

        if (!BarterSign.exists(plugin, block)) return;

        event.setCancelled(true);

        if (!event.isCancelled() && plugin.config.getBoolean(ConfigPath.SIGN_DROPS_ITEMS.getPath(),
                (Boolean) ConfigPath.SIGN_DROPS_ITEMS.getDefault())) {
            BarterSign barterSign = BarterSignManager.getBarterSignFromBlock(event.getBlock());
            if (barterSign == null) {
                return;
            }
        }
    }
}
