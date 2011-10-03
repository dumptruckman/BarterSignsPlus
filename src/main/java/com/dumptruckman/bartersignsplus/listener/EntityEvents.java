package com.dumptruckman.bartersignsplus.listener;

import com.dumptruckman.bartersignsplus.BarterSignsManager;
import com.dumptruckman.bartersignsplus.sign.BarterSign;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dumptruckman
 */
public class EntityEvents extends EntityListener {

    @Override
    public void onEntityExplode(EntityExplodeEvent event) {
        if (event.isCancelled()) return;

        List<Block> blocks = event.blockList();
        List<BarterSign> barterSigns = new ArrayList<BarterSign>();
        for (Block block : blocks) 
            if (block.getState() instanceof Sign)
                if (BarterSignsManager.barterSignExists(block)) {
                    BarterSign bSign = BarterSignsManager.getBarterSign(block);
                    barterSigns.add(bSign);
                    if (bSign.isIndestructible()) {
                        event.setCancelled(true);
                        return;
                    }
                }

        for (BarterSign bSign : barterSigns)
            if (bSign.isDroppingItemsOnBreak())
                bSign.dropContents();
    }
}