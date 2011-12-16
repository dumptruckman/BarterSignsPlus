package com.dumptruckman.bartersignsplus.listener;

import com.dumptruckman.bartersignsplus.BarterSignsManager;
import com.dumptruckman.bartersignsplus.locale.Language;
import com.dumptruckman.bartersignsplus.permission.Perm;
import com.dumptruckman.bartersignsplus.sign.BarterSign;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;

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
        
        boolean sneaking = event.getPlayer().isSneaking();
        if (player.equals(barterSign.getOwner()) /*|| player.isAdmin())*/) {
            if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) onManagerRightClickSign(event, barterSign, sneaking);
            else if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) onManagerLeftClickSign(event, barterSign, sneaking);
        } else {
            if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) onManagerRightClickSign(event, barterSign, sneaking);
            else if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) onManagerLeftClickSign(event, barterSign, sneaking);
        }
    }

    public void onManagerRightClickSign(PlayerInteractEvent event, BarterSign barterSign, Boolean sneaking) {

    }

    public void onManagerLeftClickSign(PlayerInteractEvent event, BarterSign barterSign, Boolean sneaking) {
        if (!barterSign.isActive()) {
            
        } else {
            
        }
    }
}
