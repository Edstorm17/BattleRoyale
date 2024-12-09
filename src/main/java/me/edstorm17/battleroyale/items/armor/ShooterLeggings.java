package me.edstorm17.battleroyale.items.armor;

import me.edstorm17.battleroyale.items.BaseItem;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlotGroup;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ShooterLeggings extends BaseItem {

    public ShooterLeggings() {
        super(
                "shooter_leggings",
                Material.LEATHER_LEGGINGS,
                ChatColor.DARK_PURPLE + "Shooter Leggings",
                List.of(
                        "give good bonuses for long ranged weapons"
                ),
                Map.of(
                        Attribute.MAX_HEALTH, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 15, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.LEGS),
                        Attribute.ARMOR, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.LEGS),
                        Attribute.MOVEMENT_SPEED, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 0.05, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.LEGS),
                        Attribute.ATTACK_SPEED, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), -0.01, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.LEGS)
                ),
                Map.of(
                        Enchantment.PROTECTION,2
                ),
                Color.PURPLE,
                true
        );
    }
}
