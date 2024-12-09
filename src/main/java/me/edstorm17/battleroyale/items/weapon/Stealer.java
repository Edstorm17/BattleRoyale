package me.edstorm17.battleroyale.items.weapon;

import me.edstorm17.battleroyale.items.Ability;
import me.edstorm17.battleroyale.items.BaseItem;
import me.edstorm17.battleroyale.items.Item;
import me.edstorm17.battleroyale.listeners.MiscListener;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Stealer extends BaseItem implements Ability {

    public Stealer() {
        super(
                "stealer",
                Material.MACE,
                "Life Stealer",
                List.of("Made of the dirtiest player, this is something to take caution of"
                ),
                Map.of(
                        Attribute.ATTACK_DAMAGE, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 11, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.HAND),
                        Attribute.ATTACK_KNOCKBACK, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.HAND),
                        Attribute.ATTACK_SPEED, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), -3.5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.HAND)
                ),
                Map.of(
                        Enchantment.WIND_BURST,1
                ),
                null,
                true
        );
    }

    public static final Map<Player, AttributeModifier> modifiers = new HashMap<>();

    @Override
    public void onHit(EntityDamageByEntityEvent event) {
        if (((Player) event.getDamager()).getAttackCooldown() == 1) {
            Player target = (Player) event.getEntity();
            AttributeModifier modifier = modifiers.getOrDefault(target, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ANY));
            double value = modifier.getAmount();
            target.getAttribute(Attribute.MAX_HEALTH).removeModifier(modifier);
            modifier = new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), value -2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ANY);
            target.getAttribute(Attribute.MAX_HEALTH).addModifier(modifier);
            modifiers.put(target, modifier);
        }
    }

    public static final Map<Player, Long> cooldown = new HashMap<>();

    @Override
    public void onClick(PlayerInteractEvent event) {
        boolean swat = Item.isWearingAll(event.getPlayer(), Item.SWAT_ARMOR, Item.SWAT_BOOTS, Item.SWAT_HELMET, Item.SWAT_LEGGING);
        if (!swat) return;
        if (System.currentTimeMillis() - cooldown.getOrDefault(event.getPlayer(), 0L) < (swat ? 15000 : 30000)) return;
        for (Entity e : event.getPlayer().getNearbyEntities(10, 10, 10)) {
            if (e instanceof Player player) {
                MiscListener.frozen.put(player, System.currentTimeMillis() + 3000L);
                player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 60, 0, false, false));
            }
        }
        event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.BLOCK_ANVIL_PLACE, 10, 0.5f);
        cooldown.put(event.getPlayer(), System.currentTimeMillis());
    }
}

