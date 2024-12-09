package me.edstorm17.battleroyale.ui.guis;

import me.edstorm17.battleroyale.items.weapon.Minigun;
import me.edstorm17.battleroyale.items.weapon.Minigun.*;
import me.edstorm17.battleroyale.ui.BaseGUI;
import me.edstorm17.battleroyale.ui.ClickableItem;
import me.edstorm17.battleroyale.ui.GUIRouter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Map.Entry;

public class MinigunGUI extends BaseGUI {

    public static final int SIZE = 9;

    private static final Map<Class<? extends CustomProjectile>, Material> projectiles = Map.of(
            MinigunArrow.class, Material.ARROW,
            MinigunEgg.class, Material.EGG,
            MinigunFireball.class, Material.FIRE_CHARGE,
            MinigunSnowball.class, Material.SNOWBALL,
            MinigunWindCharge.class, Material.WIND_CHARGE
    );

    public MinigunGUI() {
        super(SIZE, "Pick Projectile");
    }

    @Override
    public void initializeItems(Player player) {
        for (Entry<Class<? extends CustomProjectile>, Material> entry : projectiles.entrySet()) {
            setNextItem(new ClickableItem(new ItemStack(entry.getValue()), p -> {
                Minigun.selectedProjectiles.put(p, entry.getKey());
                p.playSound(p, Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                p.sendMessage(ChatColor.GREEN + "Selected " + entry.getValue() + ".");
                GUIRouter.closeCurrentGUI(p);
            }));
        }
    }

}
