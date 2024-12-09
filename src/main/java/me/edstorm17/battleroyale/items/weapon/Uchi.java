package me.edstorm17.battleroyale.items.weapon;

import me.edstorm17.battleroyale.items.Ability;
import me.edstorm17.battleroyale.items.BaseItem;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlotGroup;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Uchi extends BaseItem implements Ability {

    public Uchi() {
        super(
                "uchi",
                Material.BREEZE_ROD,
                "Uchigatana",
                List.of(
                        "A katana with a long single-edged curved blade.",
                        "",
                        "A unique weapon wielded by the samurai from the Land of FromSoftware.",
                        "",
                        "",
                        "The blade, with its undulating design, boasts extraordinary sharpness, and its slash attacks cause blood loss."
                ),
                Map.of(
                        Attribute.ATTACK_SPEED, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), -2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        Attribute.ATTACK_DAMAGE, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 8, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND)
                ),
                null,
                null,
                true
        );
    }

    @Override
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Location location = player.getEyeLocation().add(player.getLocation().getDirection().normalize().multiply(1.5));

        player.getWorld().spawnParticle(
                Particle.SWEEP_ATTACK,
                location,
                10,
                0.1, 0.1, 0.1
        );
    }

    @Override
    public void onHit(EntityDamageByEntityEvent event) {
        event.getEntity().setFreezeTicks(180);
    }

}
