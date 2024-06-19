package me.edstorm17.battleroyale.ui;

import org.bukkit.inventory.InventoryHolder;

public interface IInventory extends InventoryHolder {
    ClickableItem getItemAt(int slot);

}
