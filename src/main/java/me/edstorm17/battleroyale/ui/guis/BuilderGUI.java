package me.edstorm17.battleroyale.ui.guis;

import me.edstorm17.battleroyale.items.weapon.BuilderWand;
import me.edstorm17.battleroyale.ui.BaseGUI;
import me.edstorm17.battleroyale.ui.ClickableItem;
import me.edstorm17.battleroyale.ui.GUIRouter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.EnumSet;

public class BuilderGUI extends BaseGUI {

    public static final int SIZE = 9;

    private static final EnumSet<Material> materials = EnumSet.of(
            Material.OAK_PLANKS,
            Material.STONE,
            Material.COBBLESTONE,
            Material.OBSIDIAN,
            Material.TNT,
            Material.GLASS,
            Material.GRASS_BLOCK,
            Material.DIRT,
            Material.SANDSTONE
    );

    public BuilderGUI() {
        super(SIZE, "Choose Block");
    }

    @Override
    public void initializeItems(Player player) {
        for (Material material : materials) {
            setNextItem(new ClickableItem(new ItemStack(material), p -> {
                BuilderWand.selectedMaterials.put(p, material);
                p.playSound(p, Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                p.sendMessage(ChatColor.GREEN + "Selected " + material + ".");
                GUIRouter.closeCurrentGUI(p);
            }));
        }
    }
}
