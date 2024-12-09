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

public class RogueChestplate extends BaseItem {

    public RogueChestplate() {
        super(
                "rogue_chestplate",
                Material.LEATHER_CHESTPLATE,
                ChatColor.DARK_GREEN + "Ghilie Suit",
                List.of(
                        "Green, foresty, and zesty",
                        "",
                        "Everything a Rogue could want!",
                        "Just don't forget to shower after use..."
                ),
                Map.of(
                        Attribute.MAX_HEALTH, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 10, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST),
                        Attribute.ARMOR, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 7, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST),
                        Attribute.MOVEMENT_SPEED, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST),
                        Attribute.KNOCKBACK_RESISTANCE, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 0.2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST),
                        Attribute.ATTACK_SPEED, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 0.01, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST)
                ),
                Map.of(
                        Enchantment.PROTECTION,2
                ),
                Color.fromRGB(2, 48, 32),
                true
        );
    }
}
