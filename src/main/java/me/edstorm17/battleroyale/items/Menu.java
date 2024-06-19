package me.edstorm17.battleroyale.items;

import me.edstorm17.battleroyale.ui.GUIRouter;
import me.edstorm17.battleroyale.ui.guis.KitGUI;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

public class Menu extends BaseItem implements Ability {

    public Menu() {
        super(
                "menu",
                Material.NETHER_STAR,
                "Menu"
        );
    }

    @Override
    public void onClick(PlayerInteractEvent event) {
        GUIRouter.openGUI(event.getPlayer(), new KitGUI());
    }
}
