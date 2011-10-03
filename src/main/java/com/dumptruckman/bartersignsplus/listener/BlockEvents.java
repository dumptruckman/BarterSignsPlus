package com.dumptruckman.bartersignsplus.listener;

import com.dumptruckman.bartersignsplus.BarterSignsManager;
import com.dumptruckman.bartersignsplus.config.Config;
import com.dumptruckman.bartersignsplus.locale.Language;
import com.dumptruckman.bartersignsplus.sign.BarterSign;
import com.dumptruckman.bartersignsplus.permission.Perm;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.block.*;

/**
 * @author dumptruckman
 */
public class BlockEvents extends BlockListener {

    public void onSignChange(SignChangeEvent event) {
        // Throw out unimportant events immediately
        if (event.isCancelled()) return;

        // Check sign to see if it has barter sign setup phrase
        String line = event.getLine(0).toLowerCase();
        if (!line.equals("[barter]")) {
            boolean found = false;
            for (String phrase : Config.SIGN_SETUP_PHRASES.getStringList()) {
                if (line.equals(phrase.toLowerCase())) {
                    found = true;
                    break;
                }
            }
            if (!found) return;
        }

        // Check permissions to create sign
        if (!Perm.CREATE_SIGN.has(event.getPlayer())) {
            Language.NO_CREATE_PERM.bad(event.getPlayer());
            return;
        }

        // Create sign
        BarterSign barterSign = BarterSignsManager.createBarterSign(event.getBlock());
        barterSign.setOwner(event.getPlayer());
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
        //if (sign == null) {
        //    sign = new BarterSign(block);
        //}
        //if (sign == null) return;
        final BarterSign barterSign = sign;

        if (!event.isCancelled() && barterSign.isDroppingItemsOnBreak()) {
            barterSign.dropContents();
        }

        /*if (!event.isCancelled()) {
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
        }*/
    }

    @Override
    public void onBlockDamage(BlockDamageEvent event) {
        //if (event.isCancelled()) return;

        Block block = event.getBlock();
        if (!(block.getState() instanceof Sign)) return;

        if (!BarterSignsManager.barterSignExists(block)) return;

        BarterSign barterSign = BarterSignsManager.getBarterSign(event.getBlock());
        //if (barterSign == null) {
        //    barterSign = new BarterSign(block);
        //}
        /*if (!BarterSign.SignPhase.READY.equalTo(barterSign.getPhase())
                || barterSign.getMenuIndex() != barterSign.REMOVE) {
            event.setCancelled(true);
        }*/
    }

    @Override
    public void onBlockBurn(BlockBurnEvent event) {
        if (event.isCancelled()) return;

        Block block = event.getBlock();
        if (!(block.getState() instanceof Sign)) return;

        if (!BarterSignsManager.barterSignExists(block)) return;
        BarterSign barterSign = BarterSignsManager.getBarterSign(event.getBlock());

        event.setCancelled(barterSign.isIndestructible());
    }

    @Override
    public void onBlockFade(BlockFadeEvent event) {
        if (event.isCancelled()) return;

        Block block = event.getBlock();
        if (!(block.getState() instanceof Sign)) return;

        if (!BarterSignsManager.barterSignExists(block)) return;
        BarterSign barterSign = BarterSignsManager.getBarterSign(event.getBlock());

        event.setCancelled(barterSign.isIndestructible());
    }

    @Override
    public void onBlockPhysics(BlockPhysicsEvent event) {
        if (event.isCancelled()) return;

        Block block = event.getBlock();
        if (!(block.getState() instanceof Sign)) return;

        if (!BarterSignsManager.barterSignExists(block)) return;
        BarterSign barterSign = BarterSignsManager.getBarterSign(event.getBlock());

        event.setCancelled(barterSign.isIndestructible());
    }
}
