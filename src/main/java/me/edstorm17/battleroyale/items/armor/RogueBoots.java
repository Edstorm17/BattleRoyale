package me.edstorm17.battleroyale.items.armor;

import me.edstorm17.battleroyale.items.Ability;
import me.edstorm17.battleroyale.items.BaseItem;
import me.edstorm17.battleroyale.items.Passive;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class RogueBoots extends BaseItem implements Passive, Ability {

    public RogueBoots() {
        super(
                "rogue_boots",
                Material.LEATHER_BOOTS,
                "Ninja Boots",
                List.of(
                        "Ultimate style and some sneakyness to boot!",
                        "I hate to say it but you got some style!"
                ),
                Map.of(
                        Attribute.MAX_HEALTH, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 10, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST),
                        Attribute.GRAVITY, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), -0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST),
                        Attribute.ARMOR, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 7, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST),
                        Attribute.MOVEMENT_SPEED, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 0.09, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST)

                ),
                null,
                Color.BLACK,
                true
        );
    }

    @Override
    public void tick(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 10, 0, true));
    }

    @Override
    public void onDamage(EntityDamageEvent event) {
        if (event.getCause() == DamageCause.FALL) {
            event.setCancelled(true);
        }
    }

}
