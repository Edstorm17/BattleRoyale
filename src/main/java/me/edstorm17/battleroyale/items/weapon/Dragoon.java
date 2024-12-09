package me.edstorm17.battleroyale.items.weapon;

import me.edstorm17.battleroyale.items.Ability;
import me.edstorm17.battleroyale.items.BaseItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.player.PlayerInteractEvent;

public class Dragoon extends BaseItem implements Ability {


    public Dragoon() {
        super(
                "dragoon",
                Material.DRAGON_EGG,
                ChatColor.LIGHT_PURPLE + "Dragoon"
        );
    }

    @Override
    public void onClick(PlayerInteractEvent event) {
        event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation(), EntityType.ENDER_DRAGON);
    }
}
