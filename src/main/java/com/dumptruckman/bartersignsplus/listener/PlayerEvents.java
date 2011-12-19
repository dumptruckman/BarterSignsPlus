package com.dumptruckman.bartersignsplus.listener;

import com.dumptruckman.bartersignsplus.BarterSignsManager;
import com.dumptruckman.bartersignsplus.locale.Language;
import com.dumptruckman.bartersignsplus.permission.Perm;
import com.dumptruckman.bartersignsplus.sign.BarterSign;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.inventory.ItemStack;

/**
 * @author dumptruckman
 */
public class PlayerEvents extends PlayerListener {

    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;
        if (!(event.getClickedBlock().getState() instanceof Sign)) return;

        BarterSign barterSign = BarterSignsManager.getBarterSign(event.getClickedBlock());
        if (barterSign == null) return;

        /*if (Config.PLUGINS_OVERRIDE.getBoolean())
            if (barterSign.isActive())
                if (barterSign.getMenuIndex() != barterSign.REMOVE)
                    event.setCancelled(true);*/

        Player player = event.getPlayer();
        if (Perm.USE_SIGN.has(player)) {
            Language.NO_USE_PERM.bad(player);
            return;
        }

        event.setUseItemInHand(Event.Result.DENY);
        
        boolean sneaking = event.getPlayer().isSneaking();
        if (barterSign.isActive() && player.equals(barterSign.getOwner()) /*|| player.isAdmin())*/) { //@TODO
            if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) onManagerRightClickActiveSign(event, barterSign, sneaking);
            else if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) onManagerLeftClickActiveSign(event, barterSign, sneaking);
        } else if (barterSign.isActive()) {
            if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) onUserRightClickActiveSign(event, barterSign, sneaking);
            else if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) onUserLeftClickActiveSign(event, barterSign, sneaking);
        } else if (player.equals(barterSign.getOwner())) {  //@TODO
            if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) onManagerRightClickInactiveSign(event, barterSign, sneaking);
            else if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) onManagerLeftClickInactiveSign(event, barterSign, sneaking);
        } else {
            if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) onUserRightClickInactiveSign(event, barterSign, sneaking);
            else if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) onUserLeftClickInactiveSign(event, barterSign, sneaking);
        }
    }

    public void onManagerRightClickActiveSign(PlayerInteractEvent event, BarterSign barterSign, Boolean sneaking) {
        // Manager menu scroll
    }

    public void onManagerLeftClickActiveSign(PlayerInteractEvent event, BarterSign barterSign, Boolean sneaking) {
        // Manager menu interact
    }

    public void onManagerRightClickInactiveSign(PlayerInteractEvent event, BarterSign barterSign, Boolean sneaking) {
        // Remind manager to set up sign
        Language.SIGN_SETUP_MESSAGE.info(event.getPlayer());
    }

    public void onManagerLeftClickInactiveSign(PlayerInteractEvent event, BarterSign barterSign, Boolean sneaking) {
        // Manager is setting up sign
        ItemStack itemInHand = event.getItem();
        if (!validateHeldItem(event.getPlayer(), itemInHand)) return;


        ItemStack itemToSell = new ItemStack(itemInHand.getType(), 1, itemInHand.getDurability(), itemInHand.getData().getData());
        barterSign.finishSetup(itemToSell);
        
    }

    public void onUserRightClickActiveSign(PlayerInteractEvent event, BarterSign barterSign, Boolean sneaking) {
        
    }

    public void onUserLeftClickActiveSign(PlayerInteractEvent event, BarterSign barterSign, Boolean sneaking) {

    }

    public void onUserRightClickInactiveSign(PlayerInteractEvent event, BarterSign barterSign, Boolean sneaking) {

    }

    public void onUserLeftClickInactiveSign(PlayerInteractEvent event, BarterSign barterSign, Boolean sneaking) {

    }

    public boolean validateHeldItem(Player player, ItemStack item) {
        if (item == null || item.getType() == Material.AIR) {
            Language.NO_HELD_ITEM.bad(player);
            return false;
        }
        return true;
    }
}
