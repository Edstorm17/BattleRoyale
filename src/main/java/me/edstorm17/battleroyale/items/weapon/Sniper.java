package me.edstorm17.battleroyale.items.weapon;

import me.edstorm17.battleroyale.items.Ability;
import me.edstorm17.battleroyale.items.BaseItem;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class Sniper extends BaseItem implements Ability {

    public Sniper() {
        super(
                "sniper",
                Material.SPYGLASS,
                "Le Sniper",
                null,
                null,
                null
        );
    }

    @Override
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        player.launchProjectile(Arrow.class, player.getEyeLocation().getDirection().multiply(20));
    }
}
