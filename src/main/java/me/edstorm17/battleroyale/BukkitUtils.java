package me.edstorm17.battleroyale;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketPlayOutNamedSoundEffect;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_21_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_21_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_21_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.Random;

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

    public static void sendPacket(Player player, Packet<?> packet) {
        getHandle(player).f.sendPacket(packet);
    }

    private static final Random random = new Random();

    public static void playSpecificSound(Player player, String sound, Location location, float volume, float pitch) {
        Holder<SoundEffect> effectHolder = BuiltInRegistries.b.e(new SoundEffect(MinecraftKey.b(sound), Optional.empty()));
        getHandle(player).f.a(new PacketPlayOutNamedSoundEffect(effectHolder, SoundCategory.a,  location.getX(), location.getY(), location.getZ(), volume, pitch, random.nextLong()));
    }

}
