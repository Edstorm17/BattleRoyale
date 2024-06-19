package me.edstorm17.battleroyale.ui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class ClickableItem {
    private ItemStack item;
    private Consumer<Player> onClick;

    public ClickableItem(ItemStack item, Consumer<Player> onClick) {
        this.item = item;
        this.onClick = onClick;
    }

    public void click(Player player) {
        this.onClick.accept(player);
    }

    public ItemStack getItem() {
        return item;
    }
}
