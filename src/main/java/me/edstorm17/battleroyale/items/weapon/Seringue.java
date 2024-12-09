package me.edstorm17.battleroyale.items.weapon;

import me.edstorm17.battleroyale.items.Ability;
import me.edstorm17.battleroyale.items.BaseItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Seringue extends BaseItem implements Ability {

    public Seringue() {
        super(
                "seringue",
                Material.BOWL,
                "BOOST",
                List.of("la légende dit que meme ta mere peut pas aller aussi vite"),
                null,
                null,
                null,
                false
        );
    }

    private static Map<Player, Long> timeout = new HashMap<>();

    @SuppressWarnings("all")
    @Override
    public void onClick(PlayerInteractEvent event) {
        if ((event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) && System.currentTimeMillis() - timeout.getOrDefault(event.getPlayer(), 0L) >= 60 * 1000) {
            event.getPlayer().playSound(event.getPlayer(), Sound.ENTITY_GENERIC_DRINK, 1, 1);

            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 600, 2 , false, false));
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 6 , false, false));
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600, 2, false, false));
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 600, 10, false, false));
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 600, 5, false, false));
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 1200, 1, false, false));
            event.getPlayer().getAttribute(Attribute.MAX_HEALTH).addModifier(new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 4d, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ANY));

            timeout.put(event.getPlayer(), System.currentTimeMillis());
        }
    }
}
