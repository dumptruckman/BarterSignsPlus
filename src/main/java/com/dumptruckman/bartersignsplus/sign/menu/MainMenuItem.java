package com.dumptruckman.bartersignsplus.sign.menu;

import com.dumptruckman.bartersignsplus.actionmenu.sign.SignActionMenuItem;
import com.dumptruckman.bartersignsplus.locale.Language;
import com.dumptruckman.bartersignsplus.sign.BarterSign;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * @author dumptruckman
 */
public class MainMenuItem extends SignActionMenuItem {

    protected BarterSign barterSign;

    public MainMenuItem(BarterSign barterSign, List<String> lines) {
        super(lines);
        this.barterSign = barterSign;
    }

    @Override
    public void onSelect() {
        this.setLines(Language.getStrings(Language.SIGN_MAIN_MENU, barterSign.getItemSold())
                Integer.toString(barterSign.getSellableItem().getAmount()),
                plugin.getShortItemName(barterSign.getSellableItem())));
        plugin.sendMessage(player, LanguagePath.OWNER_MESSAGE.getPath());
    }

    public void run() {
        if (!player.isSneaking()) {
            barterSign.showInfo(player);
        } else {
            barterSign.buy(player);
        }
    }
}
