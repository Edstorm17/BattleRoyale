package me.edstorm17.battleroyale.commands;

import me.edstorm17.battleroyale.items.Item;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ItemsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            for (Item item : Item.values()) {
                player.getInventory().addItem(item.getItem().toItemStack());
            }
        }

        return true;
    }
}
