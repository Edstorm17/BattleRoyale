package me.edstorm17.battleroyale;

import me.edstorm17.battleroyale.commands.ItemsCommand;
import me.edstorm17.battleroyale.commands.MatchCommand;
import me.edstorm17.battleroyale.commands.StuffCommand;
import me.edstorm17.battleroyale.commands.VehicleCommand;
import me.edstorm17.battleroyale.items.Item;
import me.edstorm17.battleroyale.items.Passive;
import me.edstorm17.battleroyale.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class BattleRoyale extends JavaPlugin {

    private static Plugin instance;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        getCommand("items").setExecutor(new ItemsCommand());
        getCommand("vehicle").setExecutor(new VehicleCommand());
        getCommand("match").setExecutor(new MatchCommand());
        getCommand("stuff").setExecutor(new StuffCommand());
        getServer().getPluginManager().registerEvents(new ClickListener(), this);
        getServer().getPluginManager().registerEvents(new HitListener(), this);
        getServer().getPluginManager().registerEvents(new VehicleListener(), this);
        getServer().getPluginManager().registerEvents(new Injector(), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        getServer().getPluginManager().registerEvents(new RespawnListener(), this);

        Bukkit.getScheduler().runTaskTimer(this, this::second, 0, 20);
        Bukkit.getScheduler().runTaskTimer(this, this::tick, 0, 1);

        for (Player player : Bukkit.getOnlinePlayers()) {
            Injector.injectPlayer(player);
        }

        ScoreManager.init();
    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Injector.unInjectPlayer(player);
        }
    }

    public static BattleRoyale getInstance() {
        return (BattleRoyale) instance;
    }

    private void second() {
        for (Entity entity : Bukkit.getWorld("world").getEntities()) {
            if (entity instanceof Arrow arrow && arrow.isInBlock()) entity.remove();
        }
    }

    private void tick() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getEquipment() != null) {
                for (ItemStack itemStack : player.getEquipment().getArmorContents()) {
                    if (itemStack != null) {
                        Item item = Item.get(itemStack);
                        if (item != null && item.getItem() instanceof Passive passive) {
                            passive.tick(player);
                        }
                    }
                }
            }
        }
    }
}
