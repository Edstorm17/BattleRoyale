package me.edstorm17.battleroyale.items.weapon;

import me.edstorm17.battleroyale.items.Ability;
import me.edstorm17.battleroyale.items.BaseItem;
import me.edstorm17.battleroyale.items.Item;
import me.edstorm17.battleroyale.listeners.MiscListener;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

public class Snapflash extends BaseItem implements Ability {

    public Snapflash() {
        super(
                "snapflash",
                Material.WARPED_BUTTON,
                "CHEEZE"
        );
    }

    private static final Map<Player, Long> cooldown = new HashMap<>();

    @Override
    public void onClick(PlayerInteractEvent event) {
        boolean swat = Item.isWearingAll(event.getPlayer(), Item.SWAT_ARMOR, Item.SWAT_BOOTS, Item.SWAT_HELMET, Item.SWAT_LEGGING);
        if (System.currentTimeMillis() - cooldown.getOrDefault(event.getPlayer(), 0L) < (swat ? 5000 : 10000)) return;
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_AIR) {
            for (Entity e : event.getPlayer().getNearbyEntities(10, 10, 10)) {
                if (e instanceof Player player) {
                    MiscListener.frozen.put(player, System.currentTimeMillis() + 3000L);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 60, 0, false, false));
                }
            }
            event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.BLOCK_ANVIL_PLACE, 10, 0.5f);
        }
        cooldown.put(event.getPlayer(), System.currentTimeMillis());
    }
}
