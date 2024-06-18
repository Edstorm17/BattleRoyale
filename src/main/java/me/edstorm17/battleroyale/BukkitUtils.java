package me.edstorm17.battleroyale;

import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_21_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_21_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_21_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class BukkitUtils {

    public static EntityPlayer getHandle(Player player) {
        return ((CraftPlayer) player).getHandle();
    }

    public static net.minecraft.world.entity.Entity getHandle(Entity entity) {
        return ((CraftEntity) entity).getHandle();
    }

    public static WorldServer getHandle(World world) {
        return ((CraftWorld) world).getHandle();
    }

}
