package com.dumptruckman.bartersignsplus.sign;

import com.dumptruckman.bartersignsplus.BarterSignsManager;
import com.dumptruckman.bartersignsplus.actionmenu.sign.SignActionMenu;
import com.dumptruckman.bartersignsplus.util.Logging;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

/**
 * @author dumptruckman
 */
public class MenuSign {

    private static final int READY_MENU = 0;
    public static final int REMOVE = -1;

    private SignRefreshTask refreshTask = null;
    private int refreshTaskId = -1;
    
    private final String id;

    private SignActionMenu menu;

    private Location location;

    public MenuSign(final Block block) {
        menu = new SignActionMenu(block);
        this.id = BarterSignsManager.getSignId(block);
        this.location = block.getLocation();
    }

    public Block getBlock() {
        return menu.getBlock();
    }

    public Location getLocation() {
        return location;
    }
    
    /**
     * Returns the id of this sign
     * @return id of this sign
     */
    public String getId() {
        return this.id;
    }

    public Sign getSign() {
        BlockState sign = this.getBlock().getState();
        if (sign instanceof Sign) return (Sign) sign;
        Logging.severe("Tried to get a sign that is no longer a sign!");
        return null;
    }

    public void setRefreshTask(SignRefreshTask task) {
        this.refreshTask = task;
    }

    public SignRefreshTask getRefreshTask() {
        return refreshTask;
    }

    public void setRefreshTaskId(int taskId) {
        this.refreshTaskId = taskId;
    }

    public int getRefreshTaskId() {
        return this.refreshTaskId;
    }

    public void goMainMenu() {
        menu.setIndex(READY_MENU);
        showMenu(null);
    }

    public void setupMenu() {/*
        menu.addMenuItem(new MainMenuItem(plugin, this, plugin.lang.lang(SIGN_READY_SIGN.getPath(),
        Integer.toString(getSellableItem().getAmount()), plugin.getShortItemName(getSellableItem()))));
        menu.addMenuItem(new HelpMenuItem(plugin, this));
        menu.addMenuItem(new AlterStockMenuItem(plugin, this));
        menu.addMenuItem(new AlterPaymentMenuItem(plugin, this));
        menu.addMenuItem(new AlterSellableMenuItem(plugin, this));
        REMOVE = menu.addMenuItem(new RemoveSignMenuItem(plugin, this));
        menu.addMenuItem(new CollectRevenueMenuItem(plugin, this));
    */}

    public void cycleMenu(Player player, boolean reverse) {
        menu.cycleMenu(player, reverse);
        if (menu.getIndex() != 0) {
            BarterSignsManager.scheduleSignRefresh(this.getId());
        }
    }

    public void doSelectedMenuItem(Player player) {
        if (menu.getIndex() != 0) {
            BarterSignsManager.scheduleSignRefresh(this.getId());
        }
        menu.doSelectedMenuItem(player);
    }

    public void setMenuIndex(Player player, int index) {
        if (index >= menu.size()) {
            index = menu.size() - 1;
        }
        menu.setIndex(player, index);
        if (menu.getIndex() != 0) {
            BarterSignsManager.scheduleSignRefresh(this.getId());
        }
    }

    public void showMenu(Player player) {
        if (getBlock().getState() instanceof Sign) {
            menu.showMenu(player);
        } else {
            BarterSignsManager.removeBarterSign(getBlock());
        }
    }
}
