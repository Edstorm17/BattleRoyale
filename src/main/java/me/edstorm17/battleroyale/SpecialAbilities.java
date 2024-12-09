package me.edstorm17.battleroyale;

import me.edstorm17.battleroyale.items.Item;
import me.edstorm17.battleroyale.items.weapon.Minigun.CustomProjectile;
import me.edstorm17.battleroyale.listeners.MiscListener;
import net.minecraft.network.protocol.game.ClientboundSetBorderWarningDistancePacket;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.projectile.EntityLargeFireball;
import net.minecraft.world.entity.projectile.IProjectile;
import net.minecraft.world.level.World;
import org.bukkit.*;
import org.bukkit.Particle.DustOptions;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.craftbukkit.v1_21_R3.CraftWorldBorder;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public final class SpecialAbilities {

    public static final Map<Player, Long> lastPress = new HashMap<>();
    public static final Map<Player, Boolean> available = new HashMap<>();
    public static final Map<Player, Long> dashCooldown = new HashMap<>();

    public static void dash(Player player) {
        if (!Item.isWearingAll(player, Item.POT, Item.SAMOURAI, Item.DRONES, Item.NIKE)) return;
        if (affectedPlayers.contains(player)) return;
        long time = System.currentTimeMillis();
        if (
                time - lastPress.getOrDefault(player, 0L) < 500 &&
                available.getOrDefault(player, true) &&
                time - dashCooldown.getOrDefault(player, 0L) >= 1500
        ) {
            available.put(player, false);
            player.getWorld().spawnParticle(Particle.DUST, player.getLocation(), 10, 0.5, 0.5, 0.5, new DustOptions(Color.WHITE, 3));
            player.setVelocity(player.getEyeLocation().getDirection().normalize().multiply(2));
            dashCooldown.put(player, time);
        } else {
            lastPress.put(player, time);
        }
    }

    private static final Map<Player, Long> furyFirstPress = new HashMap<>();
    private static final Map<Player, Long> furySecondPress = new HashMap<>();
    private static final Map<Player, Long> furyCooldown = new HashMap<>();

    public static void shadowFury(Player player) {
        if (!Item.isWearingAll(player, Item.POT, Item.SAMOURAI, Item.DRONES, Item.NIKE)) return;
        long time = System.currentTimeMillis();
        if (
                time - furyFirstPress.getOrDefault(player, 0L) < 1000 &&
                (furyFirstPress.getOrDefault(player, 0L) < furySecondPress.getOrDefault(player, 0L)) &&
                (furySecondPress.getOrDefault(player, 0L) < time) &&
                time - furyCooldown.getOrDefault(player, 0L) >= 120000
        ) {
            initFury(player);
            furyCooldown.put(player, time);
        } else if (
                time - furyFirstPress.getOrDefault(player, 0L) < 500 &&
                time - furySecondPress.getOrDefault(player, 0L) >= 500
        ) {
            furySecondPress.put(player, time);
        } else {
            furyFirstPress.put(player, time);
        }
    }

    private static final Map<Player, Iterator<Player>> targets = new ConcurrentHashMap<>();
    private static final Map<Player, Player> current = new ConcurrentHashMap<>();
    private static final Map<Player, Location> startLoc = new ConcurrentHashMap<>();

    public static void initFury(Player player) {
        Bukkit.getScheduler().runTask(BattleRoyale.getInstance(), () -> {
            Set<Player> playerTargets = player.getNearbyEntities(500, 500, 500).stream().filter(entity -> entity instanceof Player).map(e -> (Player) e).collect(Collectors.toSet());
            playerTargets.forEach(player1 -> player1.sendTitle("§l§5BEWARE", "§l§5SHADOW STEP", 5, 60, 5));
            targets.put(player, playerTargets.iterator());
            startLoc.put(player, player.getLocation());
            stepFury(player, null);
        });
    }

    public static void stepFury(Player player, @Nullable Player hit) {
        Player currentPlayer = current.get(player);
        if (currentPlayer != hit) return;
        Iterator<Player> iterator = targets.get(player);
        MiscListener.freeze(currentPlayer, 0L);
        if (iterator == null) return;
        if (iterator.hasNext()) {
            MiscListener.freeze(player, 5000L);
            player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 100, 9, false, false));
            Player target = iterator.next();
            current.put(player, target);
            MiscListener.freeze(target, 5000L);
            Location location = target.getLocation().subtract(target.getEyeLocation().getDirection().setY(0).normalize());
            location.setPitch(0);
            player.teleport(location);
            player.getWorld().playSound(location, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
        } else {
            targets.remove(player);
            current.remove(player);
            player.teleport(startLoc.get(player));
            MiscListener.freeze(player, 0L);
            player.removePotionEffect(PotionEffectType.STRENGTH);
        }
    }

    public static final Map<Player, Long> firstPress = new HashMap<>();
    public static final Map<Player, Long> secondPress = new HashMap<>();
    public static final Map<Player, Long> cooldown = new HashMap<>();

    public static void meteorStrike(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!Item.isWearingAll(player, Item.SHOOTER_BOOTS, Item.SHOOTER_HELMET, Item.SHOOTER_CHESTPLATE, Item.SHOOTER_LEGGING)) return;
        long time = System.currentTimeMillis();
        if (
                time - firstPress.getOrDefault(player, 0L) < 1000 &&
                (firstPress.getOrDefault(player, 0L) < secondPress.getOrDefault(player, 0L)) &&
                (secondPress.getOrDefault(player, 0L) < time) &&
                time - cooldown.getOrDefault(player, 0L) >= 120000 &&
                (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)
        ) {
            strike(player);
            cooldown.put(player, time);
        } else if (
                time - firstPress.getOrDefault(player, 0L) < 500 &&
                time - secondPress.getOrDefault(player, 0L) >= 500 &&
                (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)
        ) {
            secondPress.put(player, time);
        } else if (
                event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK
        ) {
            firstPress.put(player, time);
        }
    }

    public static final Set<Player> affectedPlayers = new HashSet<>();
    private static final Map<Player, AttributeModifier> attackSpeedModifiers = new HashMap<>();
    private static final Map<Player, Set<Player>> playersAffected = new HashMap<>();
    private static final Map<Player, Long> berserkerCooldown = new HashMap<>();

    public static void berserkerRage(Player player) {
        long time = System.currentTimeMillis();
        if (time - berserkerCooldown.getOrDefault(player, 0L) < 120000) return;
        berserkerCooldown.put(player, time);
        Set<Player> affected = player.getNearbyEntities(50, 50, 50).stream().filter(e -> e instanceof Player).map(e -> (Player) e).collect(Collectors.toSet());
        for (Player target : affected) {
            affectedPlayers.add(target);
            target.sendTitle( "§l§4BEWARE", "§l§4BERSERKER", 5, 60, 5);
            target.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 50, 1);
            target.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 600, 3, false, false));
        }
        player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 50, 1);
        player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 50, 1);
        player.playSound(player.getLocation(), Sound.ENTITY_DROWNED_AMBIENT, 50, 1);
        playersAffected.put(player, affected);

        player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 600, 1, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 600, 3, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600, 1, false, false));
        AttributeModifier modifier = new AttributeModifier(NamespacedKey.fromString(UUID.randomUUID().toString()), 2, Operation.MULTIPLY_SCALAR_1, EquipmentSlotGroup.ANY);
        attackSpeedModifiers.put(player, modifier);
        player.getAttribute(Attribute.ATTACK_SPEED).addModifier(modifier);

        WorldBorder border = player.getWorld().getWorldBorder();
        WorldBorder fakeBorder = Bukkit.createWorldBorder();

        fakeBorder.setCenter(border.getCenter());
        fakeBorder.setDamageAmount(border.getDamageAmount());
        fakeBorder.setDamageBuffer(border.getDamageBuffer());
        fakeBorder.setSize(border.getSize());
        fakeBorder.setWarningDistance(300000000);
        fakeBorder.setWarningTime(border.getWarningTime());

        player.setWorldBorder(fakeBorder);

        Bukkit.getScheduler().runTaskLater(BattleRoyale.getInstance(), () -> {
            player.getAttribute(Attribute.ATTACK_SPEED).removeModifier(attackSpeedModifiers.get(player));
            attackSpeedModifiers.remove(player);
            affectedPlayers.removeAll(playersAffected.get(player));
            player.setWorldBorder(border);
        }, 600);
    }

    public static void strike(Player player) {
        Location loc = player.getTargetBlock(Set.of(Material.AIR, Material.WATER, Material.LAVA), 100).getLocation();
        loc.getWorld().getNearbyEntities(loc, 50, 400, 50).forEach(entity -> {
            if (entity instanceof Player player1) {
                player1.sendTitle( "§l§cBEWARE", "§l§cMETEOR STRIKE", 5, 60, 5);
            }
        });

        new BukkitRunnable() {
            final Random random = new Random();
            final long start = System.currentTimeMillis();

            @Override
            public void run() {
                if (System.currentTimeMillis() - start > 10000) cancel();
                for (int i = 0; i < 10; i++) {
                    double x = loc.getX() + random.nextDouble(50) - 25;
                    double z = loc.getZ() + random.nextDouble(50) - 25;
                    WorldServer world = BukkitUtils.getHandle(loc.getWorld());
                    IProjectile fireball = new StrikeFireball(world, 50d);
                    world.b(fireball);
                    fireball.a_(x, 320, z);
                    fireball.a(BukkitUtils.getHandle(player), 90, 0, 0, 4, 0);
                    fireball.c(BukkitUtils.getHandle(player));
                }
            }
        }.runTaskTimer(BattleRoyale.getInstance(), 0L, 2);
    }

    public static class StrikeFireball extends EntityLargeFireball implements CustomProjectile {
        private final double damage;

        public StrikeFireball(World world, double damage) {
            super(EntityTypes.Y, world);
            this.damage = damage;
        }

        @Override
        public double getDamage() {
            return damage;
        }

        @Override
        public Sound getSound() {
            return Sound.ITEM_FIRECHARGE_USE;
        }
    }

}
