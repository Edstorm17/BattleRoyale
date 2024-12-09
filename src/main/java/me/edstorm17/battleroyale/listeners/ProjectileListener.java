package me.edstorm17.battleroyale.listeners;

import me.edstorm17.battleroyale.BukkitUtils;
import me.edstorm17.battleroyale.items.weapon.Minigun.CustomProjectile;
import me.edstorm17.battleroyale.items.weapon.Minigun.MinigunFireball;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProjectileListener implements Listener {

    public static final List<Arrow> arrows = new ArrayList<>();

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Arrow arrow && arrows.contains(arrow)) {
            arrows.remove(arrow);
            Location location;

            if (event.getHitBlock() != null) {
                location = event.getHitBlock().getLocation();
            } else if (event.getHitEntity() != null) {
                location = event.getHitEntity().getLocation();
            } else return;

            Collection<Entity> entities = location.getWorld().getNearbyEntities(location, 4, 4, 4, entity -> entity instanceof LivingEntity);
            for (Entity entity : entities) {
                ((LivingEntity) entity).damage(10d);
            }
        } else if (BukkitUtils.getHandle(event.getEntity()) instanceof CustomProjectile customProjectile && event.getHitEntity() instanceof LivingEntity livingEntity) {
            livingEntity.damage(customProjectile.getDamage(), (Entity) event.getEntity().getShooter());
        }
    }


}
