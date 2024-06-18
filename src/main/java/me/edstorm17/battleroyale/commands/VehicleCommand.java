package me.edstorm17.battleroyale.commands;

import me.edstorm17.battleroyale.entities.Bomber;
import me.edstorm17.battleroyale.entities.Fighter;
import me.edstorm17.battleroyale.entities.FlyingBoat;
import me.edstorm17.battleroyale.entities.Special;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VehicleCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player && args.length == 1) {
            Location loc = player.getLocation();
            switch (args[0]) {
                case "fighter" -> new Fighter(loc);
                case "bomber" -> new Bomber(loc);
                case "special" -> new Special(loc).mount(player);
            }
        }

        return true;
    }
}
