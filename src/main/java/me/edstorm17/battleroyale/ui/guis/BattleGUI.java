package me.edstorm17.battleroyale.ui.guis;

import me.edstorm17.battleroyale.Battle;
import me.edstorm17.battleroyale.BattleType;
import me.edstorm17.battleroyale.items.ItemBuilder;
import me.edstorm17.battleroyale.ui.BaseGUI;
import me.edstorm17.battleroyale.ui.ClickableItem;
import me.edstorm17.battleroyale.ui.GUIRouter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;

import java.time.Duration;

public class BattleGUI extends BaseGUI {

    private static final int SIZE = 5 * 9;

    private BattleType type = BattleType.SOLO;
    private WorldType worldType = WorldType.NORMAL;
    private Long wall = 30 * 1000L;

    public BattleGUI() {
        super(SIZE, "New Game");
    }

    @Override
    public void initializeItems(Player player) {
        setItemAt(2, 2, new ClickableItem(ItemBuilder.builder().material(Material.PLAYER_HEAD).displayName(ChatColor.AQUA + "Mode").build(), p -> {}));
        setItemAt(3, 2, GRAY_PANE());
        setItemAt(3, 2, MODE_SOLO());

        setItemAt(2, 3, new ClickableItem(ItemBuilder.builder().material(Material.GRASS_BLOCK).displayName(ChatColor.GREEN + "World Type").build(), p -> {}));
        setItemAt(3, 3, GRAY_PANE());
        setItemAt(3, 3, WORLD_NORMAL());

        setItemAt(2, 4, new ClickableItem(ItemBuilder.builder().material(Material.CLOCK).displayName(ChatColor.YELLOW + "Wall Drop Time").build(), p -> {}));
        setItemAt(3, 4, GRAY_PANE());
        setItemAt(3, 4, WALL_TIME());

        setItemAt(8, 3, new ClickableItem(ItemBuilder.builder().material(Material.LIME_CONCRETE).displayName(ChatColor.GREEN + "CONFIRM").build(), p -> {
            GUIRouter.closeCurrentGUI(p);
            new Battle(this.type, this.worldType, this.wall).start();
        }));

        fillRestWithEmpty();
    }

    private ClickableItem MODE_SOLO() {
        return new ClickableItem(ItemBuilder.builder().material(Material.LIGHT_BLUE_STAINED_GLASS_PANE).displayName(ChatColor.AQUA + "SOLO").build(), p -> {
            setItemAt(3, 2, MODE_TEAMS());
            this.type = BattleType.TEAMS;
        });
    }

    private ClickableItem MODE_TEAMS() {
        return new ClickableItem(ItemBuilder.builder().material(Material.RED_STAINED_GLASS_PANE).displayName(ChatColor.RED + "TEAMS").build(), p -> {
            setItemAt(3, 2, MODE_SOLO());
            this.type = BattleType.SOLO;
        });
    }

    private ClickableItem WORLD_NORMAL() {
        return new ClickableItem(ItemBuilder.builder().material(Material.LIME_STAINED_GLASS_PANE).displayName(ChatColor.GREEN + "NORMAL").build(), p -> {
            setItemAt(3, 3, WORLD_FLAT());
            this.worldType = WorldType.FLAT;
        });
    }

    private ClickableItem WORLD_FLAT() {
        return new ClickableItem(ItemBuilder.builder().material(Material.YELLOW_STAINED_GLASS_PANE).displayName(ChatColor.YELLOW + "SUPERFLAT").build(), p -> {
            setItemAt(3, 3, WORLD_NORMAL());
            this.worldType = WorldType.NORMAL;
        });
    }

    private ClickableItem WALL_TIME() {
        Duration duration = Duration.ofMillis(this.wall);
        return new ClickableItem(ItemBuilder.builder().material(Material.HOPPER).displayName(ChatColor.LIGHT_PURPLE + String.valueOf(duration.toMinutesPart()) + "min " + duration.toSecondsPart() + "sec").build(), p -> {
            long newTime = this.wall + (30 * 1000L);
            if (wall == 5 * 60 * 1000L) newTime = 30 * 1000L;
            this.wall = newTime;
            setItemAt(3, 4, WALL_TIME());
        });
    }

    public static ClickableItem GRAY_PANE() {
        return new ClickableItem(ItemBuilder.builder().material(Material.GRAY_STAINED_GLASS_PANE).displayName(" ").build(), p -> {});
    }
}