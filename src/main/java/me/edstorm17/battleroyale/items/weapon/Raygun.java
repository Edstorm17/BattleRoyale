package me.edstorm17.battleroyale.items.weapon;

import me.edstorm17.battleroyale.BattleRoyale;
import me.edstorm17.battleroyale.items.Ability;
import me.edstorm17.battleroyale.items.BaseItem;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class Raygun extends BaseItem implements Ability {

    public Raygun() {
        super(
                "raygun",
                Material.WHEAT,
                ChatColor.DARK_RED + "Le Raygun"
        );
    }


    private static Map<Player, Long> raygunTimer = new HashMap<>();

    @Override
    public void onClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            if (!raygunTimer.containsKey(player)) {
                new BukkitRunnable() {
                    Location location;
                    int count;

                    @Override
                    public void run() {
                        location = player.getEyeLocation();
                        if (System.currentTimeMillis() - raygunTimer.get(player) > 1000) {
                            this.cancel();
                            raygunTimer.remove(player);
                            return;
                        }
                        ray(player, this.location, count);
                        if (count < 10) count++;
                    }
                }.runTaskTimer(BattleRoyale.getInstance(), 0, 10);
            }
            raygunTimer.put(player, System.currentTimeMillis());
        }
        event.setCancelled(true);
    }

    public void ray(Player player, Location location, int count) {
        for (int i = 0; i < 50; i++) {
            location.add(location.getDirection());
            for (Entity e : player.getWorld().getNearbyEntities(location, 1, 1, 1, entity -> !entity.equals(player) && entity instanceof LivingEntity)) {
                ((LivingEntity) e).damage(count);
            }
            player.getWorld().spawnParticle(Particle.DUST, location, 2, 0, 0, 0, new Particle.DustOptions(Color.RED, 1));
        }
    }
}
