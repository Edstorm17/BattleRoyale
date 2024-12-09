package me.edstorm17.battleroyale.items.weapon;

import me.edstorm17.battleroyale.items.Ability;
import me.edstorm17.battleroyale.items.BaseItem;
import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter.Red;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlotGroup;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Gluttony extends BaseItem implements Ability {

    public Gluttony() {
        super(
                "gluttony",
                Material.GOLDEN_SWORD,
                ChatColor.DARK_RED + "Gluttony",
                List.of(
                        "One of the Seven Deadly Sins",
                        ChatColor.ITALIC + "" + ChatColor.RED + "IT HUNGERS FOR BLOOD"
                ),
                Map.of(
                        Attribute.ATTACK_DAMAGE, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 11, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        Attribute.ATTACK_SPEED, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), -2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND)
                ),
                Map.of(
                        Enchantment.SHARPNESS, 3
                ),
                null,
                true
        );
    }

    @Override
    public void onHit(EntityDamageByEntityEvent event) {
        Player player = (Player) event.getDamager();
        player.setHealth(Math.min(player.getHealth() + 2, player.getAttribute(Attribute.MAX_HEALTH).getValue()));
    }
}