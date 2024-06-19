package me.edstorm17.battleroyale.items.weapon;

import me.edstorm17.battleroyale.items.Ability;
import me.edstorm17.battleroyale.items.BaseItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;

import java.lang.annotation.Target;
import java.util.List;

public class Snapflash extends BaseItem implements Ability {

    public Snapflash() {
        super(
                "snapflash",
                Material.WARPED_BUTTON,
                "CHEEZE"
        );
}

    @Override
    public void onClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
            for (Entity e : event.getPlayer().getNearbyEntities(10, 5, 10)) {
                if (e instanceof Player player) {
                    double x = player.getLocation().getX();
                    double y = player.getLocation().getY();

                    double angle = Math.toDegrees(Math.atan2(y, x));
                    Bukkit.broadcastMessage(String.valueOf(angle));

//                    player.addPotionEffect(PotionEffect)
                }
            }
        }
    }
}
