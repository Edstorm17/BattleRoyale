package me.edstorm17.battleroyale.items.weapon;

import me.edstorm17.battleroyale.BattleRoyale;
import me.edstorm17.battleroyale.items.Ability;
import me.edstorm17.battleroyale.items.BaseItem;
import me.edstorm17.battleroyale.items.Item;
import org.bukkit.*;
import org.bukkit.Particle.DustOptions;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class SmokeBomb extends BaseItem implements Ability {

    public SmokeBomb() {
        super(
                "smoke_bomb",
                Material.BLACK_BUNDLE,
                "Smoke Bomb"
        );
    }

    private static final Map<Player, Long> cooldown = new HashMap<>();
    public static final Map<Location, Player> locations = new HashMap<>();

    @Override
    public void onClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getBlockFace() == BlockFace.UP) {
            if (System.currentTimeMillis() - cooldown.getOrDefault(event.getPlayer(), 0L) < 40000) return;
            boolean rogue = Item.isWearingAll(event.getPlayer(), Item.POT, Item.SAMOURAI, Item.DRONES, Item.NIKE);
            for (Entity e : event.getPlayer().getNearbyEntities(7, 7, 7)) {
                if (e instanceof Player player) {
                    if (rogue) player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 180, 0, false, false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 0, false, false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 230, 0, false, false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 180, 2, false, false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 180, 2, false, false));
                }

            }
            Location loc = event.getClickedBlock().getLocation().add(event.getClickedPosition());
            new BukkitRunnable() {
                final long start = System.currentTimeMillis();

                @Override
                public void run() {
                    if (System.currentTimeMillis() - start >= 9000) {
                        locations.remove(loc);
                        cancel();
                    }
                    event.getPlayer().getWorld().spawnParticle(Particle.SQUID_INK, loc, 1000, 5, 5, 5, 0);
                    event.getPlayer().getWorld().spawnParticle(Particle.DUST, loc, 1000, 5, 5, 5, 0, new DustOptions(Color.BLACK, 5));
                }
            }.runTaskTimer(BattleRoyale.getInstance(), 0, 1);
            event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.ENTITY_SQUID_HURT, 10, 0.5f);
            event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 10, 2f);
            if (rogue) locations.put(loc, event.getPlayer());
            cooldown.put(event.getPlayer(), System.currentTimeMillis());
        }
    }
}
