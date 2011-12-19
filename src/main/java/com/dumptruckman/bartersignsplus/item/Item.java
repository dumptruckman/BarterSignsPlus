package com.dumptruckman.bartersignsplus.item;

import org.bukkit.inventory.ItemStack;

/**
 * @author dumptruckman
 */
public class Item {

    public String signName;
    public String signDescriptor;

    public Item(ItemStack itemStack) {
        itemStack.getData().
    }

    public String getSignName() {
        return signName;
    }

    public String getSignDescriptor() {
        return signDescriptor;
    }

    public static String itemToDataString(ItemStack item) {
        return itemToDataString(item, true);
    }

    public static String itemToDataString(ItemStack s, boolean withAmount) {
        String item = "";
        if (withAmount) {
            item += s.getAmount() + " ";
        }
        item += s.getTypeId() + "," + s.getDurability();
        return item;
    }

    public static ItemStack stringToItem(String item) {
        String[] sellInfo = item.split("\\s");
        String[] itemData = sellInfo[1].split(",");
        return new ItemStack(Integer.valueOf(itemData[0]), Integer.valueOf(sellInfo[0]), Short.valueOf(itemData[1]));
    }

    public static String itemToString(ItemStack item) {
        return itemToString(item, true);
    }

    public static String itemToString(ItemStack s, boolean withAmount) {
        String item = "";
        if (withAmount) {
            item += s.getAmount() + " ";
        }
        item += getShortItemName(s);
        return item;
    }

    public static String getShortItemName(ItemStack item) {
        String key = itemToDataString(item, false);
        String name = items.getString(key);
        if (name == null) {
            name = items.getString(item.getTypeId() + ",0");
        }
        if (name == null) {
            name = Integer.toString(item.getTypeId());
            if (item.getDurability() > 0) {
                name += "," + item.getDurability();
            }
            log.warning("Missing name for item: '" + name + "' in items.yml");
        }
        return name;
    }
}
