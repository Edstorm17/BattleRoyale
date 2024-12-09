package me.edstorm17.battleroyale.items.armor;

import me.edstorm17.battleroyale.items.Ability;
import me.edstorm17.battleroyale.items.BaseItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EquipmentSlotGroup;

import java.util.Map;
import java.util.UUID;

public class GodBoots extends BaseItem implements Ability {

    public GodBoots() {
        super(
                "god_boots",
                Material.GOLDEN_BOOTS,
                ChatColor.LIGHT_PURPLE + "God's Sandals",
                null,
                Map.of(
                        Attribute.MAX_HEALTH, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), -19, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.FEET)
                ),
                null,
                null,
                true
        );
    }

    @Override
    public void onDamage(EntityDamageEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onHit(EntityDamageByEntityEvent event) {
        event.setCancelled(true);
    }

}
