package me.edstorm17.battleroyale.commands;

import me.edstorm17.battleroyale.*;
import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;

import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

public class MatchCommand implements CommandExecutor {

    public static final String worldName = "game_world";
    public static World gameWorld;
    private static final Random random = new Random();

    public static Location spawnLocation;
    public static Location blueSpawnLocation;
    public static Location redSpawnLocation;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) return true;

        Battle battle = new Battle(BattleType.valueOf(args[0].toUpperCase()), 30*1000);
        battle.start(Bukkit.getOnlinePlayers());

        return true;
    }

}
