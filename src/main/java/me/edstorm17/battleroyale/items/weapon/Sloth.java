package me.edstorm17.battleroyale.items.weapon;

import me.edstorm17.battleroyale.items.Ability;
import me.edstorm17.battleroyale.items.BaseItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Sloth extends BaseItem implements Ability {

    public Sloth() {
        super(
                "sloth",
                Material.IRON_SWORD,
                ChatColor.DARK_PURPLE + "Sloth",
                List.of(
                        "One of the Seven Deadly Sins",
                        ChatColor.ITALIC + "" + ChatColor.DARK_GRAY + "HUNGERS FOR THE LIFEFORCE OF YOUR ENEMIES"
                ),
                Map.of(
                        Attribute.ATTACK_DAMAGE, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 12, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        Attribute.ATTACK_SPEED, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), -2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND)
                ),
                Map.of(
                        Enchantment.SHARPNESS, 3
                ),
                null,
                true
        );
    }

    @Override
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof LivingEntity entity) {
            entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 40, 1, false, false));
            entity.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 30, 1, false, false));
        }
    }

}