package me.edstorm17.battleroyale.items.armor;

import me.edstorm17.battleroyale.items.Ability;
import me.edstorm17.battleroyale.items.BaseItem;
import me.edstorm17.battleroyale.items.Passive;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlotGroup;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BuilderBoots extends BaseItem implements Ability {

    public BuilderBoots() {
        super(
                "builder_boots",
                Material.CHAINMAIL_BOOTS,
                ChatColor.DARK_BLUE + "Builder's Boots",
                List.of(
                        ChatColor.GRAY + "Previously owned by a powerful creator."
                ),
                Map.of(
                        Attribute.MAX_HEALTH, new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.FEET)
                ),
                Map.of(
                        Enchantment.FEATHER_FALLING, 10
                ),
                null,
                true
        );
    }

    @Override
    public void onMove(PlayerMoveEvent event) {
        Location loc = event.getPlayer().getLocation();
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                Block b = loc.getWorld().getBlockAt(loc.getBlockX() + x, loc.getBlockY() - 1, loc.getBlockZ() + z);
                if (b.isPassable()) {
                    b.setType(Material.COBBLESTONE);
                }
            }
        }
    }
}
