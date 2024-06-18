package me.edstorm17.battleroyale.entities;

import net.minecraft.world.entity.Entity;
import org.bukkit.entity.Player;

public interface Vehicle {

    void shoot(Player player);

    default void mount(Player player) {
        if (this instanceof Entity entity) {
            entity.getBukkitEntity().addPassenger(player);
        }
    }

}
