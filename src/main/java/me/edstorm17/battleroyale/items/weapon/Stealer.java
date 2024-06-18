package me.edstorm17.battleroyale.items.weapon;

import me.edstorm17.battleroyale.BattleRoyale;
import me.edstorm17.battleroyale.items.Ability;
import me.edstorm17.battleroyale.items.BaseItem;
import me.edstorm17.battleroyale.items.Hittable;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlotGroup;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Stealer extends BaseItem implements Ability, Hittable {

    public Stealer() {
        super(
                "stealer",
                Material.WARPED_FENCE,
                "mouahha"
        );
    }

    @Override
    public void onClick(PlayerInteractEvent event) {

    }

    public static final Map<Player, AttributeModifier> modifiers = new HashMap<>();

    @Override
    public void hit(Player source, Player target) {
        if (!modifiers.containsKey(target)) {
            AttributeModifier modifier = new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), -1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ANY);
            target.getAttribute(Attribute.GENERIC_MAX_HEALTH).addModifier(modifier);
            modifiers.put(target, modifier);
        } else {
            AttributeModifier modifier = modifiers.get(target);
            double value = modifier.getAmount();
            target.getAttribute(Attribute.GENERIC_MAX_HEALTH).removeModifier(modifier);
            modifier = new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), value - 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ANY);
            target.getAttribute(Attribute.GENERIC_MAX_HEALTH).addModifier(modifier);
            modifiers.put(target, modifier);
        }
    }
}
