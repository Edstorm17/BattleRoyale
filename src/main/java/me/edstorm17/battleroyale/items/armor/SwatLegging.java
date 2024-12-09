package me.edstorm17.battleroyale.items.armor;

import me.edstorm17.battleroyale.items.BaseItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlotGroup;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SwatLegging extends BaseItem {

    public SwatLegging() {
        super(
                "swat4",
                Material.NETHERITE_LEGGINGS,
                "swat leggings",
                List.of(
                        "ultimate defense but no bacon",
                        "why so defensive you ask? for the puffer and some stranges players"
                ),
                Map.of(
                        Attribute.MAX_HEALTH, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 20, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.LEGS),
                        Attribute.ARMOR, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 7, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.LEGS),
                        Attribute.MOVEMENT_SPEED, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), -0.01, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.LEGS),
                        Attribute.KNOCKBACK_RESISTANCE, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), -1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.LEGS),
                        Attribute.ATTACK_SPEED, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), -0.01, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.LEGS)
                ),
                Map.of(
                        Enchantment.PROTECTION,3
                ),
                null,
                true
        );
    }
}
