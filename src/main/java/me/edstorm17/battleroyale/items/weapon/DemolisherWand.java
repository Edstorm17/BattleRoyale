package me.edstorm17.battleroyale.items.weapon;

import me.edstorm17.battleroyale.BlockUtils;
import me.edstorm17.battleroyale.items.Ability;
import me.edstorm17.battleroyale.items.BaseItem;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Set;

public class DemolisherWand extends BaseItem implements Ability {

    public DemolisherWand() {
        super(
                "demolisher_wand",
                Material.BLAZE_ROD,
                ChatColor.GOLD + "Demolisher's Wand",
                List.of(
                        ChatColor.GRAY + "" + ChatColor.ITALIC + "Capable of very powerful things.",
                        "",
                        ChatColor.DARK_GRAY + "Range: 500",
                        "",
                        ChatColor.RED + "" + ChatColor.BOLD + "WARNING - DANGER"
                ),
                null,
                null,
                null,
                false
        );
    }

    @Override
    public void onClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            Block b = event.getClickedBlock();
            if (b == null) b = event.getPlayer().getTargetBlock(Set.of(Material.AIR, Material.WATER, Material.LAVA), 500);

            if (!event.getPlayer().isSneaking()) {
                if (b.getType() == Material.BEDROCK) return;
                b.breakNaturally();
            } else {
                int x = b.getChunk().getX() << 4;
                int z = b.getChunk().getZ() << 4;
                BlockUtils.set(b.getWorld(), x, -64, z, x + 15, 320, z + 15, Set.of(Material.BEDROCK), Material.AIR);
            }
        } else if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (!event.getPlayer().isSneaking()) {
                Block block = event.getPlayer().getTargetBlock(Set.of(Material.AIR, Material.WATER, Material.LAVA), 150);
                BlockUtils.set(block.getWorld(), block.getX() - 2, block.getY() - 2, block.getZ() - 2, block.getX() + 2, block.getY() + 2, block.getZ() + 2, Set.of(Material.BEDROCK), Material.AIR);
            } else {
                Location loc = event.getPlayer().getEyeLocation();
                Vector vector = loc.getDirection().normalize();
                for (int i = 0; i < 100; i++) {
                    loc.add(vector);
                    BlockUtils.set(loc.getWorld(), loc.getBlockX() - 1, loc.getBlockY() - 1, loc.getBlockZ() - 1, loc.getBlockX() + 1, loc.getBlockY() + 1, loc.getBlockZ() + 1, Set.of(Material.BEDROCK), Material.AIR);
                }
            }
        }
    }
}
