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

public class SwatHelmet extends BaseItem {

    public SwatHelmet() {
        super(
                "swat3",
                Material.REINFORCED_DEEPSLATE,
                "swat helmet",
                List.of(
                        "The less viewable view of all, this why your slow, to avoid holes and taxes."
                ),
                Map.of(
                        Attribute.MAX_HEALTH, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 15, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.HEAD),
                        Attribute.ARMOR, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.HEAD),
                        Attribute.MOVEMENT_SPEED, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 0.01, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.HEAD),
                        Attribute.KNOCKBACK_RESISTANCE, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.HEAD),
                        Attribute.ATTACK_DAMAGE, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.HEAD)
                ),
                Map.of(
                        Enchantment.PROTECTION,3
                ),
                null,
                true
        );
    }
}
