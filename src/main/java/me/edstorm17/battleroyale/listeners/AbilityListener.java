package me.edstorm17.battleroyale.listeners;

import me.edstorm17.battleroyale.BukkitUtils;
import me.edstorm17.battleroyale.entities.Vehicle;
import me.edstorm17.battleroyale.items.Ability;
import me.edstorm17.battleroyale.items.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class AbilityListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if (p.getVehicle() != null && BukkitUtils.getHandle(p.getVehicle()) instanceof Vehicle vehicle) {
            vehicle.shoot(p);
        } else if (event.hasItem()) {
            Item item = Item.get(event.getItem());
            if (item != null && item.getItem() instanceof Ability ability) {
                event.setCancelled(true);
                ability.onClick(event);
            }
        }
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        Player player;
        if (event.getDamager() instanceof Player) player = (Player) event.getDamager();
        else if (((Projectile) event.getDamager()).getShooter() instanceof Player) player = (Player) ((Projectile) event.getDamager()).getShooter();
        else return;
        for (Item item : Item.getEquipmentAsCustomItems(player)) {
            if (item.getItem() instanceof Ability ability) {
                ability.onHit(event);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            for (Item item : Item.getEquipmentAsCustomItems(player)) {
                if (item.getItem() instanceof Ability ability) {
                    ability.onDamage(event);
                }
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        for (Item item : Item.getEquipmentAsCustomItems(event.getPlayer())) {
            if (item.getItem() instanceof Ability ability) {
                ability.onMove(event);
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        for (Item item : Item.getEquipmentAsCustomItems(event.getEntity())) {
            if (item.getItem() instanceof Ability ability) {
                ability.onDeath(event);
            }
        }
    }

}
