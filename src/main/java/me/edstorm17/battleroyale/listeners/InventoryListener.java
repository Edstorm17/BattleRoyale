package me.edstorm17.battleroyale.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getClickedInventory() != null && event.getClickedInventory().getType() == InventoryType.PLAYER && event.getSlot() == 39) {
            ItemStack clicked = event.getCurrentItem();
            event.getClickedInventory().setItem(39, event.getCursor());
            event.getWhoClicked().setItemOnCursor(clicked);
            event.setCancelled(true);
        }
    }
}
