package me.edstorm17.battleroyale.items.weapon;

import me.edstorm17.battleroyale.items.Ability;
import me.edstorm17.battleroyale.items.BaseItem;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class Puffer extends BaseItem implements Ability {
    public Puffer() {
        super(
                "puffer",
                Material.PUFFERFISH,
                "Le Puffer",
                null,
                null,
                null
        );
    }

    @Override
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Vector vector = player.getEyeLocation().getDirection().multiply(10);

        for (int i = 0; i < 12; i++) {
            player.launchProjectile(Arrow.class, vector.rotateAroundY(30));
        }
    }
}
