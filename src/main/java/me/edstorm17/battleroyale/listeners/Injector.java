package me.edstorm17.battleroyale.listeners;

import io.netty.channel.*;
import me.edstorm17.battleroyale.BukkitUtils;
import me.edstorm17.battleroyale.entities.FlyingBoat;
import me.edstorm17.battleroyale.entities.Special;
import me.edstorm17.battleroyale.entities.Steerable;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.protocol.game.PacketPlayInBoatMove;
import net.minecraft.network.protocol.game.PacketPlayInSteerVehicle;
import net.minecraft.network.protocol.game.PacketPlayInVehicleMove;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_21_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_21_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.lang.reflect.Field;

public class Injector implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        injectPlayer(event.getPlayer());
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        unInjectPlayer(event.getPlayer());
    }

    public static void injectPlayer(Player player) {
        ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler() {

            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                if (!handlePacketWriting(ctx, msg)) return;
                super.write(ctx, msg, promise);
            }

            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                if (!handlePacketReading(ctx, msg)) return;
                super.channelRead(ctx, msg);
            }
        };

        ChannelPipeline pipeline = getChannel(((CraftPlayer) player).getHandle().c).pipeline();

        pipeline.addBefore("packet_handler", player.getName(), channelDuplexHandler);
    }

    public static void unInjectPlayer(Player player) {
        Channel channel = getChannel(((CraftPlayer) player).getHandle().c);
        channel.eventLoop().submit(() -> {
            channel.pipeline().remove(player.getName());
            return null;
        });
    }

    public static Channel getChannel(PlayerConnection playerConnection) {
        NetworkManager manager = null;
        try {
            Field network = playerConnection.getClass().getSuperclass().getDeclaredField("e");
            network.setAccessible(true);
            manager = (NetworkManager) network.get(playerConnection);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert manager != null;
        return manager.n;
    }

    public static boolean handlePacketReading(ChannelHandlerContext ctx, Object packet) {
        if (packet instanceof PacketPlayInSteerVehicle packetPlayInSteerVehicle) {
            Player player = Bukkit.getPlayer(ctx.name());
            assert player != null;
            if (player.getVehicle() != null && BukkitUtils.getHandle(player.getVehicle()) instanceof FlyingBoat) {
                VehicleListener.moveY.put(player, packetPlayInSteerVehicle.f());
            }
            if (player.getVehicle() != null && BukkitUtils.getHandle(player.getVehicle()) instanceof Steerable steerable) {
                steerable.steer(packetPlayInSteerVehicle.b(), packetPlayInSteerVehicle.e(), packetPlayInSteerVehicle.f(), packetPlayInSteerVehicle.g());
            }
        }

        return true;
    }

    public static boolean handlePacketWriting(ChannelHandlerContext ctx, Object packet) {
        return true;
    }

}
