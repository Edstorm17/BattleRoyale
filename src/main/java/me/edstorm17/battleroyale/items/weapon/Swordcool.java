package me.edstorm17.battleroyale.items.weapon;

import me.edstorm17.battleroyale.items.Ability;
import me.edstorm17.battleroyale.items.BaseItem;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Swordcool extends BaseItem implements Ability {

    public Swordcool() {
        super(
                "worthy",
                Material.NETHERITE_SHOVEL,
                "THE CYCLO BLADE",
                null,
                Map.of(
                        Attribute.ATTACK_SPEED, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        Attribute.ATTACK_DAMAGE, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND)
                ),
                null,
                null,
                true
        );
    }

    public static final Map<Player, AttributeModifier> modifiers = new HashMap<>();

    private static final long ATTACK_COOLDOWN_MS = 1000;
    private final Map<Player, Long> lastAttackTime = new HashMap<>();

    @Override
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Location location = player.getEyeLocation().add(player.getLocation().getDirection().normalize().multiply(1.5)); // Particules au bout de la lame

        player.getWorld().spawnParticle(
                Particle.DUST,
                location,
                10,
                0.1, 0.1, 0.1,
                new Particle.DustOptions(Color.RED, 1.0f)
        );
    }

    @Override
    public void onHit(EntityDamageByEntityEvent event) {
        Player source = (Player) event.getDamager();
        Player target = (Player) event.getEntity();

        long currentTime = System.currentTimeMillis();
        long lastTime = lastAttackTime.getOrDefault(source, 0L);

        if (currentTime - lastTime < ATTACK_COOLDOWN_MS) {
            return;
        }
        lastAttackTime.put(source, currentTime);

        double damage = 10;
        double lifestealAmount = damage * 0.25;

        target.damage(damage, source);
        double sourceHealth = source.getHealth();
        double maxHealth = source.getAttribute(Attribute.MAX_HEALTH).getValue();

        source.setHealth(Math.min(sourceHealth + lifestealAmount, maxHealth));

        target.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 60, 1));
        target.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 10, 2));

        AttributeModifier modifier = modifiers.getOrDefault(
                target,
                new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ANY)
        );
        if (!target.getAttribute(Attribute.MAX_HEALTH).getModifiers().contains(modifier)) target.getAttribute(Attribute.MAX_HEALTH).addModifier(modifier);
        modifiers.put(target, modifier);

        target.damage(target.getHealth() * 0.3 + target.getAttribute(Attribute.ARMOR).getValue() * 0.3, source);
    }
}
