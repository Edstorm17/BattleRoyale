package me.edstorm17.battleroyale.items.armor;

import me.edstorm17.battleroyale.items.Ability;
import me.edstorm17.battleroyale.items.BaseItem;
import me.edstorm17.battleroyale.items.Item;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PhoenixChestplate extends BaseItem implements Ability {

    public PhoenixChestplate() {
        super(
                "phoenix_chestplate",
                Material.GOLDEN_CHESTPLATE,
                ChatColor.GOLD + "Phoenix Chestplate",
                List.of("Power of the phoenix."),
                Map.of(
                        Attribute.MAX_HEALTH, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 40, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST)
                ),
                null,
                null,
                true
        );
    }

    private static final Map<Player, Long> cooldown = new HashMap<>();

    @Override
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player && Item.get(player.getInventory().getChestplate()) == Item.PHOENIX_CHESTPLATE) {
            if (System.currentTimeMillis() - cooldown.getOrDefault(player, 0L) < 60000) return;
            if (player.getHealth() - event.getFinalDamage() <= 0) {
                event.setDamage(0);
                player.setHealth(player.getAttribute(Attribute.MAX_HEALTH).getValue());
                player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 20, 0, false, false));
                player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 100, 2, false, false));
                player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 100, 2, false, false));
                player.getWorld().playSound(player.getLocation(), Sound.ITEM_TOTEM_USE, 10, 1f);
                player.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING, player.getLocation().add(0, 1, 0), 100, 0.5, 0.5, 0.5);
            }
            cooldown.put(player, System.currentTimeMillis());
        }
    }

}
