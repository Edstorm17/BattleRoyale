package me.edstorm17.battleroyale.entities;

import me.edstorm17.battleroyale.BukkitUtils;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.vehicle.EntityBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Info;
import org.bukkit.Location;

public abstract class FlyingBoat extends EntityBoat implements Vehicle {

    public FlyingBoat(Location location) {
        super(EntityTypes.aG, BukkitUtils.getHandle(location.getWorld()), () -> new Item(new Info()));
        BukkitUtils.getHandle(location.getWorld()).b(this);
        this.f(true);
        this.a_(location.getX(), location.getY(), location.getZ());
    }

}
