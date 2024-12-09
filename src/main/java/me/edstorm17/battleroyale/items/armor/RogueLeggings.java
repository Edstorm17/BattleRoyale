package me.edstorm17.battleroyale.items.armor;

import me.edstorm17.battleroyale.items.BaseItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlotGroup;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class RogueLeggings extends BaseItem {

    public RogueLeggings() {
        super(
                "RogueLeggings",
                Material.LEATHER_LEGGINGS,
                "Black Jogging",
                List.of(
                        "Streetchy and... leathery?",
                        "Guess you wanna be comfy while kicking ass'?"
                ),
                Map.of(
                        Attribute.MAX_HEALTH, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 20, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.LEGS),
                        Attribute.JUMP_STRENGTH, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 0.1, Operation.ADD_NUMBER, EquipmentSlotGroup.LEGS),
                        Attribute.ARMOR, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.LEGS),
                        Attribute.ATTACK_SPEED, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 0.4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.LEGS),
                        Attribute.MOVEMENT_SPEED, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.LEGS)
                ),
                Map.of(
                        Enchantment.PROTECTION, 2
                ),
                Color.BLACK,
                true
//               ajoute un delay , de 5 sec et aussi rend le moin puissant

        );
    }
}
