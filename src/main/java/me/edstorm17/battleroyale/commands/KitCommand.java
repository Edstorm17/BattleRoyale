package me.edstorm17.battleroyale.commands;

import me.edstorm17.battleroyale.ui.GUIRouter;
import me.edstorm17.battleroyale.ui.guis.KitGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player player) {
            GUIRouter.openGUI(player, new KitGUI());
        }

        return true;
    }
}
