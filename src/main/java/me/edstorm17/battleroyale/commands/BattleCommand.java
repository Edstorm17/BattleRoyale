package me.edstorm17.battleroyale.commands;

import me.edstorm17.battleroyale.*;
import me.edstorm17.battleroyale.ui.GUIRouter;
import me.edstorm17.battleroyale.ui.guis.BattleGUI;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BattleCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            if (sender instanceof Player player) {
                GUIRouter.openGUI(player, new BattleGUI());
            }

            return true;
        }

        if (args[0].equals("end") && Battle.getActive() != null) {
            Battle.getActive().finish();
        } else if (args[0].equals("exit") && Battle.getActive() != null) {
            if (Battle.getActive().getCurrentStage() != BattleStage.DONE)
                Battle.getActive().finish();
            Battle.getActive().exit();
        } else {
            Battle battle = new Battle(BattleType.valueOf(args[0].toUpperCase()), WorldType.NORMAL, 30*1000L);
            battle.start();
        }

        return true;
    }

}
