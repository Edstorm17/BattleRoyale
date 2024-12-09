package me.edstorm17.battleroyale.items.weapon;

import me.edstorm17.battleroyale.BattleRoyale;
import me.edstorm17.battleroyale.items.Ability;
import me.edstorm17.battleroyale.items.BaseItem;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class Boom extends BaseItem implements Ability {

    public Boom() {
        super(
                "boom",
                Material.TNT,
                ChatColor.RED + "" + ChatColor.BOLD + "BOOM"
        ); 
    }

    private static final Map<Player, Long> timer = new HashMap<>();

    @Override
    public void onClick(PlayerInteractEvent event) {
        if (System.currentTimeMillis() - timer.getOrDefault(event.getPlayer(), 0L) < 100000) return;

        ItemStack stack = event.getPlayer().getInventory().getItemInMainHand();
        stack.setAmount(stack.getAmount() - 1);

        new BukkitRunnable() {
            int countdown = 8;
            final Location loc = event.getPlayer().getLocation();
            @Override
            public void run() {
                if (countdown == 0) {
                    loc.getWorld().createExplosion(loc, 60, false, false, event.getPlayer());
                    this.cancel();
                }

                event.getPlayer().getNearbyEntities(50, 50, 50).forEach(entity -> {
                    if (entity instanceof Player player) {
                        player.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + countdown, null, 0, 20, 0);
                    }
                });
                event.getPlayer().sendTitle(ChatColor.RED + "" + ChatColor.BOLD + countdown, null, 0, 20, 0);

                loc.getWorld().spawnParticle(Particle.EXPLOSION, loc, 25, 10, 10, 10);
                loc.getWorld().spawnParticle(Particle.DUST, loc, 25, 2, 2, 2, new Particle.DustOptions(Color.RED, 10));

                countdown--;
            }
        }.runTaskTimer(BattleRoyale.getInstance(), 0, 20);

        timer.put(event.getPlayer(), System.currentTimeMillis());
    }

}