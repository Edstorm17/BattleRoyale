package me.edstorm17.battleroyale.items;

import me.edstorm17.battleroyale.items.armor.Foulard;
import me.edstorm17.battleroyale.items.weapon.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public enum Item {

    PUFFER(new Puffer()),
    SNIPER(new Sniper()),
    FOULARD(new Foulard()),
    RAYGUN(new Raygun()),
    STEALER(new Stealer()),
    SEED(new Seed()),

    FIGHTER(new BaseItem("fighter", Material.OAK_BOAT, ChatColor.RED + "Fighter")),
    BOMBER(new BaseItem("bomber", Material.ACACIA_BOAT, ChatColor.RED + "Bomber")),
    SPECIAL(new BaseItem("special", Material.BAMBOO_RAFT, ChatColor.DARK_RED + "Special")),
    ;

    private final BaseItem item;

    Item(BaseItem item) {
        this.item = item;
    }

    public BaseItem getItem() {
        return item;
    }

    private static String getId(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return "";
        return meta.getPersistentDataContainer().get(BaseItem.idKey, PersistentDataType.STRING);
    }

    public static Item get(ItemStack itemStack) {
        String id = getId(itemStack);
        for (Item item : Item.values()) {
            if (item.getItem().getId().equals(id)) return item;
        }
        return null;
    }
}
