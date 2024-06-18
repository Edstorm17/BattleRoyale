package me.edstorm17.battleroyale;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.*;

public class ScoreManager {

    public static Scoreboard scoreboard;
    public static Objective objective;

    public static Team blue;
    public static Team red;

    public static void init() {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        scoreboard = manager.getNewScoreboard();
        objective = scoreboard.registerNewObjective("playerkills", Criteria.PLAYER_KILL_COUNT, "Kills");
        objective.setDisplayName(ChatColor.BOLD + "" + ChatColor.RED + "KILLS");

        blue = scoreboard.registerNewTeam("blue");
        red = scoreboard.registerNewTeam("red");
        blue.setColor(ChatColor.BLUE);
        red.setColor(ChatColor.RED);
        blue.setDisplayName(ChatColor.BLUE + "Blue");
        red.setDisplayName(ChatColor.RED + "Red");
        blue.setAllowFriendlyFire(false);
        red.setAllowFriendlyFire(false);
    }

}
