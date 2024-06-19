package me.edstorm17.battleroyale.ui;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class GUIRouter {

    private static Map<Player, Stack<BaseGUI>> guiHistory = new HashMap<>();

    public static void openGUI(Player player, BaseGUI gui) {
        gui.open(player);
        String title = gui.getTitle();
        guiHistory.computeIfAbsent(player, k -> new Stack<>()).push(gui);
    }

    public static void closeCurrentGUI(Player player) {
        BaseGUI currentGUI = getCurrentGUI(player);
        if (currentGUI != null) {
            currentGUI.close(player);
        }
    }

    public static BaseGUI getCurrentGUI(Player player) {
        Stack<BaseGUI> history = guiHistory.get(player);
        return history == null || history.isEmpty() ? null : history.peek();
    }

    public static void goBack(Player player) {
        Stack<BaseGUI> history = guiHistory.get(player);
        if (history != null && !history.isEmpty()) {
            history.pop();
            BaseGUI previousGUI = history.isEmpty() ? null : history.peek();
            if (previousGUI != null) {
                previousGUI.open(player);
            }
        } else {
            player.closeInventory();
        }
    }

}
