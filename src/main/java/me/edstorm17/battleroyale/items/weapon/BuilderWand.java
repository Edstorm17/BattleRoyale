package me.edstorm17.battleroyale.items.weapon;

import me.edstorm17.battleroyale.BlockUtils;
import me.edstorm17.battleroyale.items.Ability;
import me.edstorm17.battleroyale.items.BaseItem;
import me.edstorm17.battleroyale.ui.GUIRouter;
import me.edstorm17.battleroyale.ui.guis.BuilderGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BuilderWand extends BaseItem implements Ability {

    public BuilderWand() {
        super(
            "builder_wand",
                Material.BREEZE_ROD,
                ChatColor.AQUA + "Builder's Wand"
        );
    }

    public static final Map<Player, Material> selectedMaterials = new HashMap<>();
    public static final Map<Player, BuildMode> selectedBuildTypes = new HashMap<>();

    @Override
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            GUIRouter.openGUI(player, new BuilderGUI());
        } else if (player.isSneaking() && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            int i = selectedBuildTypes.getOrDefault(player, BuildMode.HORIZONTAL).ordinal() + 1;
            if (i >= BuildMode.values().length) i = 0;
            BuildMode buildType = BuildMode.values()[i];
            selectedBuildTypes.put(player, buildType);
            player.sendMessage(ChatColor.GREEN + "Selected build mode " + buildType.toString() + ".");
        } else if (event.getAction() == Action.RIGHT_CLICK_AIR) {
            Block block = player.getTargetBlock(Set.of(Material.AIR, Material.WATER, Material.LAVA), 5);
            Material material = selectedMaterials.getOrDefault(player, Material.COBBLESTONE);
            BuildMode buildMode = selectedBuildTypes.getOrDefault(player, BuildMode.HORIZONTAL);

            BlockUtils.set(
                    block.getWorld(),
                    block.getX() - buildMode.getX(),
                    block.getY() - buildMode.getY(),
                    block.getZ() - buildMode.getZ(),
                    block.getX() + buildMode.getX(),
                    block.getY() + buildMode.getY(),
                    block.getZ() + buildMode.getZ(),
                    Set.of(Material.BEDROCK),
                    material
            );
        } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();
            Material material = selectedMaterials.getOrDefault(player, Material.COBBLESTONE);
            BuildMode buildMode = selectedBuildTypes.getOrDefault(player, BuildMode.HORIZONTAL);

            BlockUtils.set(
                    block.getWorld(),
                    block.getX() - buildMode.getX() * 15,
                    block.getY() - buildMode.getY() * 15,
                    block.getZ() - buildMode.getZ() * 15,
                    block.getX() + buildMode.getX() * 15,
                    block.getY() + buildMode.getY() * 15,
                    block.getZ() + buildMode.getZ() * 15,
                    Set.of(Material.BEDROCK),
                    material
            );
        }
    }

    public enum BuildMode {
        HORIZONTAL(1, 0, 1),
        VERTICAL_X(1, 1, 0),
        VERTICAL_Z(0, 1, 1);

        private final int x, y, z;

        BuildMode(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }
    }

}
