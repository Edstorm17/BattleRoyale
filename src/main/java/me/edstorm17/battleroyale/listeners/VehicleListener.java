package me.edstorm17.battleroyale.listeners;

import me.edstorm17.battleroyale.BukkitUtils;
import me.edstorm17.battleroyale.entities.*;
import me.edstorm17.battleroyale.items.Item;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class VehicleListener implements Listener {

    public static final Map<Player, Boolean> moveY = new HashMap<>();

    @EventHandler
    public void onVehicleMove(VehicleMoveEvent event) {
        if (BukkitUtils.getHandle(event.getVehicle()) instanceof FlyingBoat) {
            if (!event.getVehicle().getPassengers().isEmpty() && event.getVehicle().getPassengers().get(0) instanceof Player player && moveY.containsKey(player) && moveY.get(player)) {
                Vector vector = event.getTo().toVector().subtract(event.getFrom().toVector()).setY(player.getEyeLocation().getDirection().getY() * 0.5).multiply(1.1);
                event.getVehicle().setVelocity(vector);
            }
        }
    }

    @EventHandler
    public void onBreak(VehicleDestroyEvent event) {
        if (BukkitUtils.getHandle(event.getVehicle()) instanceof FlyingBoat fb) {
            event.setCancelled(true);
            event.getVehicle().getWorld().dropItem(event.getVehicle().getLocation(), Item.valueOf(fb.getClass().getSimpleName().toUpperCase()).getItem().toItemStack());
            event.getVehicle().remove();
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlace(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.hasItem() && Item.get(event.getItem()) != null) {
            if (event.getBlockFace() != BlockFace.UP) return;
            event.setCancelled(true);
            String id = Item.get(event.getItem()).getItem().getId();
            Location loc = event.getClickedBlock().getLocation().add(0.5, 1, 0.5);
            float yaw = event.getPlayer().getLocation().getYaw();
            boolean isVehicle = true;
            switch (id) {
                case "fighter" -> new Fighter(loc).a(yaw, 0);
                case "bomber" -> new Bomber(loc).a(yaw, 0);
                case "special" -> new Special(loc).mount(event.getPlayer());
                default -> isVehicle = false;
            }
            if (isVehicle) event.getPlayer().getInventory().setItemInMainHand(null);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (BukkitUtils.getHandle(event.getEntity()) instanceof Steerable) {
            event.setCancelled(true);
        } else if (event.getEntity() instanceof Player player && player.getVehicle() != null && BukkitUtils.getHandle(player.getVehicle()) instanceof Steerable) {
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        if (BukkitUtils.getHandle(event.getRightClicked()) instanceof Vehicle vehicle) {
            vehicle.mount(event.getPlayer());
            event.setCancelled(true);
        }
    }

}
