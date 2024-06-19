package me.edstorm17.battleroyale.commands;

import me.edstorm17.battleroyale.ui.GUIRouter;
import me.edstorm17.battleroyale.ui.guis.ItemGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ItemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            GUIRouter.openGUI(player, new ItemGUI());
        }
        return true;
    }

}
