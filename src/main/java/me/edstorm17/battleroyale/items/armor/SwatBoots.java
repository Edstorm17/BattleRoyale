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

public class SwatBoots extends BaseItem {

    public SwatBoots() {
        super(
                "swat2",
                Material.NETHERITE_BOOTS,
                "swat boots",
                List.of(

                        "Probably some old tanks shells repurpose for this.",
                        "No one know why it is coated with sheep fur , maybe to make it fluffiyier?"
                ),
                Map.of(
                        Attribute.MAX_HEALTH, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 20, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.FEET),
                        Attribute.ARMOR, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.FEET),
                        Attribute.MOVEMENT_SPEED, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), -0.01, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.FEET),
                        Attribute.KNOCKBACK_RESISTANCE, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.FEET),
                        Attribute.ATTACK_SPEED, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), -0.01, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.FEET)
                ),
                Map.of(
                        Enchantment.PROTECTION,3 ,
                        Enchantment.FEATHER_FALLING, 2

                ),

                null,
                true
        );
    }
}
