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

public class RogueHelmet extends BaseItem {

    public RogueHelmet() {
        super(
                "rogue_helmet",
                Material.SCULK_SHRIEKER,
                "Sculking Maw",
                List.of(
                        "With this, you'll be SCULKING in the shadows!"
                ),
                Map.of(
                        Attribute.MAX_HEALTH, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 20, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.HEAD),
                        Attribute.ARMOR, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 7, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.HEAD),
                        Attribute.ATTACK_SPEED, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 0.3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.HEAD),
                        Attribute.MOVEMENT_SPEED, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 0.013, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.HEAD),
                        Attribute.JUMP_STRENGTH, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 0.4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.HEAD)
                ),
                Map.of(
                        Enchantment.PROTECTION, 2
                ),
                null,
                false
        );
    }
}
