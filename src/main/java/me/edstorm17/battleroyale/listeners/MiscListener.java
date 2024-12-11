package me.edstorm17.battleroyale.listeners;

import me.edstorm17.battleroyale.BukkitUtils;
import me.edstorm17.battleroyale.SpecialAbilities;
import me.edstorm17.battleroyale.SpecialAbilities.StrikeFireball;
import me.edstorm17.battleroyale.items.Item;
import me.edstorm17.battleroyale.items.weapon.SmokeBomb;
import net.minecraft.server.level.EntityPlayer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class MiscListener implements Listener {

    public static final Map<Player, Long> frozen = new HashMap<>();

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (System.currentTimeMillis() - frozen.getOrDefault(e.getPlayer(), 0L) <= 0) {
            e.setCancelled(true);
        }
    }

    public static void freeze(Player player, long milliseconds) {
        frozen.put(player, System.currentTimeMillis() + milliseconds);
    }

    @EventHandler
    public void onDamageTank(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player player)) return;
        if (!Item.isWearingAll(player, Item.SWAT_BOOTS, Item.SWAT_LEGGING, Item.SWAT_ARMOR, Item.SWAT_HELMET)) return;
        if (player.getHealth() - e.getFinalDamage() <= 20 && player.getHealth() - e.getFinalDamage() > 0) {
            SpecialAbilities.berserkerRage(player);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onDamage(EntityDamageEvent e) {
        for (Entry<Location, Player> entry : SmokeBomb.locations.entrySet()) {
            if (entry.getValue() == e.getEntity()) continue;
            Location playerLoc = e.getEntity().getLocation();
            Location loc = entry.getKey();
            if (
                    loc.getX() - playerLoc.getX() >= - 7 && loc.getX() - playerLoc.getX() <= 7 &&
                    loc.getY() - playerLoc.getY() >= -7 && loc.getY() - playerLoc.getY() <= 7 &&
                    loc.getZ() - playerLoc.getZ() >= - 7 && loc.getZ() - playerLoc.getZ() <= 7
            ) {
                e.setDamage(e.getDamage() * 2);
            }
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        SpecialAbilities.meteorStrike(e);
        SpecialAbilities.naturalDisaster(e);
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if ((e.getEntity() instanceof Player target) && (e.getDamager() instanceof Player player)) {
            SpecialAbilities.stepFury(player, target);
        } else if (
                e.getEntity() instanceof Player target &&
                BukkitUtils.getHandle(e.getDamager()) instanceof StrikeFireball fireball &&
                fireball.p() instanceof EntityPlayer entityPlayer &&
                entityPlayer.cG().equals(target.getUniqueId())
        ) {
            e.setCancelled(true);
        }
    }

    public static boolean waterEnabled = true;

    @EventHandler
    public void onBlockFromTo(BlockFromToEvent event) {
        if (event.getBlock().getType() == Material.WATER && !waterEnabled)
            event.setCancelled(true);
    }

}
