package me.edstorm17.battleroyale.entities;

import me.edstorm17.battleroyale.BukkitUtils;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.animal.EntitySheep;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_21_R3.util.CraftChatMessage;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public class Special extends EntitySheep implements Vehicle, Steerable {

    public Special(Location location) {
        super(EntityTypes.ba, BukkitUtils.getHandle(location.getWorld()));
        BukkitUtils.getHandle(location.getWorld()).b(this);
        this.f(true);
        this.b(CraftChatMessage.fromStringOrNull("jeb_"));
        this.p(false);
        this.a_(location.getX(), location.getY(), location.getZ());
    }

    @Override
    public void shoot(Player player) {
        player.spawnParticle(Particle.EXPLOSION, player.getLocation(), 1, 0, 0, 0, 10);
        player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 10, 1);
        for (Entity e : player.getNearbyEntities(8, 8, 8)) {
            if (e instanceof LivingEntity living) {
                living.damage(10, player);
            }
        }
    }

    @Nullable
    @Override
    public EntityAgeable a(WorldServer worldServer, EntityAgeable entityAgeable) {
        return null;
    }
}
