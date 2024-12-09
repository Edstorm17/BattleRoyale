package me.edstorm17.battleroyale.items.weapon;

import me.edstorm17.battleroyale.BattleRoyale;
import me.edstorm17.battleroyale.BukkitUtils;
import me.edstorm17.battleroyale.items.Ability;
import me.edstorm17.battleroyale.items.BaseItem;
import me.edstorm17.battleroyale.items.Item;
import me.edstorm17.battleroyale.ui.GUIRouter;
import me.edstorm17.battleroyale.ui.guis.MinigunGUI;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.entity.projectile.windcharge.WindCharge;
import net.minecraft.world.level.World;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Minigun extends BaseItem implements Ability {

    public Minigun() {
        super(
                "Minigun",
                Material.DISPENSER,
                ChatColor.GOLD + "Minigun",
                List.of(
                        "Mini, AND a gun, what more could you ask for?"
                ),
                null,
                null,
                null,
                false
        );
    }

    private static final Map<Player, Long> timer = new HashMap<>();

    @Override
    public void onClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            if (!timer.containsKey(player)) {
                new BukkitRunnable() {
                    BukkitTask task;

                    @Override
                    public void run() {
                        if (System.currentTimeMillis() - timer.get(player) > 1000) {
                            this.cancel();
                            if (task != null && !task.isCancelled()) {
                                task.cancel();
                            }
                            timer.remove(player);
                            return;
                        }

                        if (task == null || task.isCancelled()) {
                            boolean enhanced = Item.isWearingAll(player, Item.SHOOTER_HELMET, Item.SHOOTER_CHESTPLATE, Item.SHOOTER_LEGGING, Item.SHOOTER_BOOTS);

                            task = new BukkitRunnable() {
                                @Override
                                public void run() {
                                    shoot(player, enhanced);
                                }
                            }.runTaskTimer(BattleRoyale.getInstance(), 0, enhanced ? 2 : 4);
                        }
                    }
                }.runTaskTimer(BattleRoyale.getInstance(), 0, 10);
            }
            timer.put(player, System.currentTimeMillis());
        } else if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            GUIRouter.openGUI(event.getPlayer(), new MinigunGUI());
        }
    }

    public static final Map<Player, Class<? extends CustomProjectile>> selectedProjectiles = new HashMap<>();

    @SuppressWarnings("unchecked")
    public void shoot(Player player, boolean enhanced) {
        Class<? extends IProjectile> projectileClass = (Class<? extends IProjectile>) selectedProjectiles.getOrDefault(player, MinigunEgg.class);
        WorldServer worldServer = BukkitUtils.getHandle(player.getWorld());
        double damage = enhanced ? 5d : 10d;
        IProjectile projectile = newProjectile(projectileClass, worldServer, damage);
        Location location = player.getEyeLocation();
        worldServer.b(projectile);
        projectile.a_(location.getX(), location.getY(), location.getZ());
        projectile.a(BukkitUtils.getHandle(player), location.getPitch(), location.getYaw(), 0, 2, 0);
        projectile.c(BukkitUtils.getHandle(player));
        if (enhanced) {
            IProjectile proj1 = newProjectile(projectileClass, worldServer, damage);
            IProjectile proj2 = newProjectile(projectileClass, worldServer, damage);
            worldServer.b(proj1);
            worldServer.b(proj2);
            proj1.a_(location.getX(), location.getY(), location.getZ());
            proj2.a_(location.getX(), location.getY(), location.getZ());
            proj1.a(BukkitUtils.getHandle(player), location.getPitch(), location.getYaw() - 5, 0, 2, 0);
            proj2.a(BukkitUtils.getHandle(player), location.getPitch(), location.getYaw() + 5, 0, 2, 0);
            proj1.c(BukkitUtils.getHandle(player));
            proj2.c(BukkitUtils.getHandle(player));
        }
        player.getWorld().playSound(player.getLocation(), ((CustomProjectile) projectile).getSound(), 1, 1);
    }

    public IProjectile newProjectile(Class<? extends IProjectile> projectileClass, World world, double damage) {
        try {
            return projectileClass
                    .getDeclaredConstructor(World.class, double.class)
                    .newInstance(world, damage);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return null;
        }
    }

    public interface CustomProjectile {
        double getDamage();
        Sound getSound();
    }

    public static class MinigunArrow extends EntityTippedArrow implements CustomProjectile {
        private final double damage;

        public MinigunArrow(World world, double damage) {
            super(EntityTypes.g, world);
            this.damage = damage;
        }

        @Override
        public double getDamage() {
            return damage;
        }

        @Override
        public Sound getSound() {
            return Sound.ENTITY_ARROW_SHOOT;
        }
    }

    public static class MinigunEgg extends EntityEgg implements CustomProjectile {
        private final double damage;

        public MinigunEgg(World world, double damage) {
            super(EntityTypes.L, world);
            this.damage = damage;
        }

        @Override
        public double getDamage() {
            return damage;
        }

        @Override
        public Sound getSound() {
            return Sound.ENTITY_EGG_THROW;
        }
    }

    public static class MinigunFireball extends EntitySmallFireball implements CustomProjectile {
        private final double damage;

        public MinigunFireball(World world, double damage) {
            super(EntityTypes.bh, world);
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

    public static class MinigunSnowball extends EntitySnowball implements CustomProjectile {
        private final double damage;

        public MinigunSnowball(World world, double damage) {
            super(EntityTypes.bj, world);
            this.damage = damage;
        }

        @Override
        public double getDamage() {
            return damage;
        }

        @Override
        public Sound getSound() {
            return Sound.ENTITY_SNOWBALL_THROW;
        }
    }

    public static class MinigunWindCharge extends WindCharge implements CustomProjectile {
        private final double damage;

        public MinigunWindCharge(World world, double damage) {
            super(EntityTypes.bG, world);
            this.damage = damage;
        }

        @Override
        public double getDamage() {
            return damage;
        }

        @Override
        public Sound getSound() {
            return Sound.ENTITY_WIND_CHARGE_THROW;
        }
    }

}
