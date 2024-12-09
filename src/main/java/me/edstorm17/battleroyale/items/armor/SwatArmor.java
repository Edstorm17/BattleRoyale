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

public class SwatArmor extends BaseItem {

    public SwatArmor() {
        super(
                "swat",
                Material.NETHERITE_CHESTPLATE,
                "ultimate defense but no bacon",
                List.of(
                        "iImagine plates of steel on a plate of steel on a plate of stel on plate on a steel plate on the plate of the aristrocat",
                        "iIt is made only to use with gloves"
                ),
                Map.of(
                        Attribute.MAX_HEALTH, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 70, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST),
                        Attribute.ARMOR, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 9, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST),
                        Attribute.MOVEMENT_SPEED, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), -0.05, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST),
                        Attribute.KNOCKBACK_RESISTANCE, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), -1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST),
                        Attribute.ATTACK_SPEED, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), -0.01, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST)
                ),
                Map.of(
                        Enchantment.PROTECTION,3
                ),
                null,
                true
        );
    }
}
