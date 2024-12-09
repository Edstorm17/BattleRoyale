package me.edstorm17.battleroyale;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

public class BlockUtils {

    public static void fill(Map<Location, BlockData> blocks) {
        for (Entry<Location, BlockData> entry : blocks.entrySet()) {
            Block block = Objects.requireNonNull(entry.getKey().getWorld()).getBlockAt(entry.getKey());
            block.setBlockData(entry.getValue());
        }

        Bukkit.getLogger().info("Filled " + blocks.size() + " blocks.");
    }

    public static Map<Location, BlockData> replace(Location start, Location end, Material with) {
        if (start.getWorld() != end.getWorld() || start.getWorld() == null) throw new IllegalArgumentException("Invalid fill locations worlds");

        return replace(start.getWorld(), start.getBlockX(), start.getBlockY(), start.getBlockZ(), end.getBlockX(), end.getBlockY(), end.getBlockZ(), with);
    }

    public static Map<Location, BlockData> replace(World world, int startX, int startY, int startZ, int endX, int endY, int endZ, Material with) {
        Map<Location, BlockData> from = new HashMap<>();

        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                for (int z = startZ; z <= endZ; z++) {
                    Block block = world.getBlockAt(x, y, z);
                    from.put(new Location(world, x, y, z), block.getBlockData());
                    block.setType(with);
                }
            }
        }

		int volume = Math.abs(endX - startX + 1) * Math.abs(endY - startY + 1) * Math.abs(endZ - startZ + 1);
        Bukkit.getLogger().info("Replaced " + volume + " blocks.");
        return from;
    }

    public static void set(World world, int startX, int startY, int startZ, int endX, int endY, int endZ, Set<Material> filterOut, Material with) {
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                for (int z = startZ; z <= endZ; z++) {
                    Block block = world.getBlockAt(x, y, z);
                    if (filterOut.contains(block.getType())) continue;
                    block.setType(with);
                }
            }
        }

        int volume = Math.abs(endX - startX + 1) * Math.abs(endY - startY + 1) * Math.abs(endZ - startZ + 1);
        Bukkit.getLogger().info("Set " + volume + " blocks.");
    }

}
