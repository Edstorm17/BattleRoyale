package me.edstorm17.battleroyale.items.armor;

import me.edstorm17.battleroyale.items.BaseItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlotGroup;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ShooterHelmet extends BaseItem {

    public ShooterHelmet() {
        super(
                "shooter_helmet",
                Material.BEACON,
                ChatColor.DARK_PURPLE + "Shooter Helmet",
                List.of(
                        "give good bonuses for long ranged weapons"
                ),
                Map.of(
                        Attribute.MAX_HEALTH, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 15, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.HEAD),
                        Attribute.ARMOR, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.HEAD),
                        Attribute.MOVEMENT_SPEED, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 0.05, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.HEAD),
                        Attribute.ATTACK_SPEED, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), -0.01, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.HEAD)
                ),
                Map.of(
                        Enchantment.PROTECTION,2
                ),
                null,
                true
        );
    }
}


