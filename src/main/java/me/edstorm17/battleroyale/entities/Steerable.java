package me.edstorm17.battleroyale.entities;

import net.minecraft.world.entity.EntityLiving;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Random;

public interface Steerable {

    Random random = new Random();

    default void steer(float sideways, float forward, boolean jumping, boolean sneaking) {
        if (!(this instanceof EntityLiving entity)) return;
        Entity e = entity.getBukkitEntity();
        if (e.getPassengers().get(0) instanceof Player player) {
            Vector vec = player.getEyeLocation().getDirection();
            vec.multiply(forward);
            e.setVelocity(vec);
            Location loc = player.getEyeLocation();
            e.setRotation(loc.getYaw(), loc.getPitch());

            e.getWorld().spawnParticle(Particle.DUST, e.getLocation(), 10, 0.5, 0.5, 0.5, new Particle.DustOptions(Color.fromRGB(random.nextInt(16777216)), 1.5f));
        }
    }

}
