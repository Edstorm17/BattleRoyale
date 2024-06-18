package me.edstorm17.battleroyale.listeners;

import me.edstorm17.battleroyale.BukkitUtils;
import me.edstorm17.battleroyale.entities.FlyingBoat;
import me.edstorm17.battleroyale.entities.Vehicle;
import me.edstorm17.battleroyale.items.Ability;
import me.edstorm17.battleroyale.items.Hittable;
import me.edstorm17.battleroyale.items.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ClickListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if (p.getVehicle() != null && BukkitUtils.getHandle(p.getVehicle()) instanceof Vehicle vehicle) {
            vehicle.shoot(p);
        } else if (event.hasItem()) {
            Item item = Item.get(event.getItem());
            if (item != null && item.getItem() instanceof Ability ability) {
                ability.onClick(event);
            }
        }
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player target && event.getDamager() instanceof Player source) {
            Item item = Item.get(source.getInventory().getItemInMainHand());
            if (item != null && item.getItem() instanceof Hittable hittable) {
                hittable.hit(source, target);
            }
        }
    }

}
