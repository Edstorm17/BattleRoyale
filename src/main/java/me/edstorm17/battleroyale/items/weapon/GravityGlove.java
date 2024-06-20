package me.edstorm17.battleroyale.items.weapon;

import me.edstorm17.battleroyale.BattleRoyale;
import me.edstorm17.battleroyale.items.Ability;
import me.edstorm17.battleroyale.items.BaseItem;
import me.edstorm17.battleroyale.items.Passive;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GravityGlove extends BaseItem implements Ability {

    public GravityGlove() {
        super(
                "gravityglove",
                Material.SPRUCE_SAPLING,
                ChatColor.DARK_RED + "Gravity" + ChatColor.DARK_PURPLE + "Gun"
        );
    }

    private Map<Player, Long> timer = new HashMap<>();

    @Override
    public void onClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            if (!timer.containsKey(player)) {
                new BukkitRunnable() {
                    BukkitTask task;

                    @Override
                    public void run() {
                        Location location = player.getEyeLocation().add(player.getEyeLocation().getDirection().multiply(10));
                        if (System.currentTimeMillis() - timer.get(player) > 1000) {
                            this.cancel();
                            task.cancel();
                            timer.remove(player);
                            return;
                        }
                        task = new BukkitRunnable() {
                            @Override
                            public void run() {
                                gravity(player, location);
                            }
                        }.runTaskTimer(BattleRoyale.getInstance(), 0, 1);
                    }
                }.runTaskTimer(BattleRoyale.getInstance(), 0, 10);
            }
            timer.put(player, System.currentTimeMillis());
        }
        event.setCancelled(true);
    }

    private final Map<Player, Entity> grabbed = new HashMap<>();

    public void gravity(Player player, Location location) {
        Iterator<Entity> entities = location.getWorld().getNearbyEntities(location, 1, 1, 1).iterator();
        if (!this.grabbed.containsKey(player) && entities.hasNext()) this.grabbed.put(player, entities.next());
        if (!this.grabbed.containsKey(player)) return;

        Location entityLoc = this.grabbed.get(player).getLocation();
        double x = location.getX() - entityLoc.getX();
        double y = location.getY() - entityLoc.getY();
        double z = location.getZ() - entityLoc.getZ();
        double length = 10d;
        x /= length;
        y /= length;
        z /= length;

        this.grabbed.get(player).setVelocity(new Vector(x, y, z));
    }
}
