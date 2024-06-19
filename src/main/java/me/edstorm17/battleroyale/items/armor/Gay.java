package me.edstorm17.battleroyale.items.armor;

import me.edstorm17.battleroyale.items.BaseItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlotGroup;

import java.util.Map;
import java.util.UUID;

public class Gay extends BaseItem {

    public Gay() {
        super(
                "gay",
                Material.LEATHER_CHESTPLATE,
                "le gay",
                null,
                Map.of(Attribute.GENERIC_MAX_HEALTH, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 100d, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST),
                Attribute.GENERIC_GRAVITY, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST),
                Attribute.GENERIC_ARMOR, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 20, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST)),
                null
        );
    }
}
