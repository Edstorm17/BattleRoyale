package me.edstorm17.battleroyale.entities;

import me.edstorm17.battleroyale.BukkitUtils;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.vehicle.EntityBoat;
import org.bukkit.Location;

public abstract class FlyingBoat extends EntityBoat implements Vehicle {

    public FlyingBoat(Location location, EnumBoatType type) {
        super(EntityTypes.k, BukkitUtils.getHandle(location.getWorld()));
        BukkitUtils.getHandle(location.getWorld()).b(this);
        this.f(true);
        this.a(type);
        this.a_(location.getX(), location.getY(), location.getZ());
    }

}
