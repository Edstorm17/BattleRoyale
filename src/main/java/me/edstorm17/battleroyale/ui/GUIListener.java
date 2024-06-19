package me.edstorm17.battleroyale.ui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;
        if (!(event.getClickedInventory().getHolder() instanceof BaseGUI gui)) return;

        ClickableItem item = gui.getItemAt(event.getSlot());

        if (item == null && gui.allowPlace) return;

        event.setCancelled(true);

        if (item == null) return;

        item.click((Player) event.getWhoClicked());
    }
}
