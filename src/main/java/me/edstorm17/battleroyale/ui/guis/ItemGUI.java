package me.edstorm17.battleroyale.ui.guis;

import me.edstorm17.battleroyale.items.Item;
import me.edstorm17.battleroyale.ui.BaseGUI;
import me.edstorm17.battleroyale.ui.ClickableItem;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class ItemGUI extends BaseGUI {

    private static final int SIZE = 6 * 9;

    public ItemGUI() {
        super(SIZE, "Items");
    }

    @Override
    public void initializeItems(Player player) {
        for (Item item : Item.values()) {
            setNextItem(new ClickableItem(item.getItem().toItemStack(), (p) -> {
                p.getInventory().addItem(item.getItem().toItemStack());
                p.playSound(p, Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
            }));
        }
    }

}
