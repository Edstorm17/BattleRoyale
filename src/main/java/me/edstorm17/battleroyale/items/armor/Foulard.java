package me.edstorm17.battleroyale.items.armor;

import me.edstorm17.battleroyale.items.BaseItem;
import me.edstorm17.battleroyale.items.Passive;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlotGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Foulard extends BaseItem implements Passive {

    @SuppressWarnings("all")
    public Foulard() {
        super(
                "foulard",
                Material.RED_CARPET,
                ChatColor.LIGHT_PURPLE + "Le Foulard",
                List.of(ChatColor.DARK_GRAY + "À qui appartenait-il?"),
                Map.of(Attribute.GENERIC_MAX_HEALTH, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 10d, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.HEAD)),
                null
        );
    }

    private static final Map<Player, Integer> counter = new HashMap<>();

    @SuppressWarnings("all")
    @Override
    public void tick(Player player) {
        int count = counter.getOrDefault(player, 0);
        if (count >= 19) {
            for (Entity e : player.getNearbyEntities(4, 4, 4)) {
                if (e instanceof LivingEntity livingEntity) {
                    double healthSet = 1.5;
                    if (livingEntity.getHealth() < 3) healthSet = livingEntity.getHealth() / 2;
                    livingEntity.damage(3);
                    player.setHealth(Math.min(player.getHealth() + healthSet, player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()));
                }
            }
        }
        counter.put(player, count + 1);
    }
}
