package me.edstorm17.battleroyale.items.weapon;

import me.edstorm17.battleroyale.items.Ability;
import me.edstorm17.battleroyale.items.BaseItem;
import me.edstorm17.battleroyale.items.Passive;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlotGroup;

import java.util.*;

public class Rivers extends BaseItem implements Ability, Passive {

    public Rivers() {
        super(
                "rivers",
                Material.BLAZE_ROD,
                "Rivers Of Blood",
                List.of(
                        "When Mohg, the Lord of Blood, first felt Okina's sword, and madFromSoftware",
                        "upon his flesh, he had a proposal, to offer Okina the life of a demon, whose thirst would never go unsated.",
                        "",
                        "upon his flesh, he had a proposal, to offer Okina the",
                        "life of a demon, whose thirst would never go unsated."
                ),
                Map.of(
                        Attribute.ATTACK_SPEED, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 0.8, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND),
                        Attribute.ATTACK_DAMAGE, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 7, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND)
                ),
                null,
                null,
                true
        );
    }

    private static final Random random = new Random();
    private static final Map<Location, Long> locations = new HashMap<>();

    @Override
    public void onClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            Location location = player.getEyeLocation().add(player.getLocation().getDirection().normalize().multiply(1.5)).add(random.nextDouble(4.0) - 2.0, random.nextDouble(4.0) - 2.0, random.nextDouble(4.0) - 2.0);
            locations.put(location, System.currentTimeMillis());
            event.getPlayer().setHealth(Math.max(event.getPlayer().getHealth() - 6.0, 0));
            player.getWorld().spawnParticle(Particle.RAID_OMEN, location, 5, 0.0, 0.0, 0.0, 10.0);
        }
    }

    long timer;

    @Override
    public void tick(Player player) {
        if (this.timer % 20L == 0L) {

            for (Location location : locations.keySet()) {
                location.getWorld().getNearbyEntities(location, 1.0, 1.0, 1.0).forEach((entity) -> {
                    if (entity instanceof Player p) {
                        p.damage(10d);
                    }

                });
                if (System.currentTimeMillis() - locations.get(location) >= 5000L) {
                    locations.remove(location);
                }
            }
        }

        ++this.timer;
    }
}