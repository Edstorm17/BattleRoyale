package me.edstorm17.battleroyale.ui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_21_R3.inventory.CraftInventoryCustom;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class BaseGUI implements IInventory {


    public static ClickableItem EMPTY_PANE() {
        ItemStack emptyPane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta emptyPaneMeta = emptyPane.getItemMeta();
        assert emptyPaneMeta != null;
        emptyPaneMeta.setDisplayName(" ");
        emptyPane.setItemMeta(emptyPaneMeta);
        return new ClickableItem(emptyPane, player -> {});
    }

    public static ClickableItem BACK_BUTTON() {
        ItemStack backButton = new ItemStack(Material.ARROW);
        ItemMeta backButtonMeta = backButton.getItemMeta();
        assert backButtonMeta != null;
        backButtonMeta.setDisplayName(ChatColor.GREEN + "Back");
        backButton.setItemMeta(backButtonMeta);
        return new ClickableItem(backButton, GUIRouter::goBack);
    }

    public static ClickableItem NEXT_PAGE(Consumer<Player> onClick) {
        ItemStack nextPage = new ItemStack(Material.ARROW);
        ItemMeta nextPageMeta = nextPage.getItemMeta();
        assert nextPageMeta != null;
        nextPageMeta.setDisplayName(ChatColor.GREEN + "Next Page");
        nextPage.setItemMeta(nextPageMeta);
        return new ClickableItem(nextPage, onClick);
    }

    public static ClickableItem CLOSE_GUI() {
        ItemStack closeButton = new ItemStack(Material.BARRIER);
        ItemMeta closeButtonMeta = closeButton.getItemMeta();
        assert closeButtonMeta != null;
        closeButtonMeta.setDisplayName(ChatColor.RED + "Close");
        closeButton.setItemMeta(closeButtonMeta);
        return new ClickableItem(closeButton, GUIRouter::closeCurrentGUI);
    }



    protected Inventory inventory;

    protected int SIZE;

    protected String title;

    protected boolean allowPlace;

    protected Map<Integer, ClickableItem> clickableItems = new HashMap<>();

    public BaseGUI(int size, String title) {
        this.SIZE = size;
        this.title = title;
        this.inventory = Bukkit.createInventory(this, size, title);
    }

    public BaseGUI(int size, String title, boolean allowPlace) {
        this.SIZE = size;
        this.title = title;
        this.allowPlace = allowPlace;
        this.inventory = Bukkit.createInventory(this, size, title);
    }

    public void open(Player player) {

        this.clickableItems.clear();
        this.incrementalSlot = 0;

        this.initializeItems(player);
        player.openInventory(this.inventory);
        playGUISound(player);
    }

    public void playGUISound(Player player) {
        player.playSound(player, Sound.BLOCK_LEVER_CLICK, 1, 1);
    }

    public void close(Player player) {
        player.closeInventory();
    }

    public abstract void initializeItems(Player player);

    public void setItemAt(int slot, ClickableItem item) {
        this.clickableItems.put(slot, item);
        this.inventory.setItem(slot, item.getItem());
    }

    public void setItemAt(int x, int y, ClickableItem item) {
        this.setItemAt((x - 1) + ((y - 1) * 9), item);
    }

    int incrementalSlot = 0;

    public void setNextItem(ClickableItem item) {
        if (incrementalSlot >= this.inventory.getSize()) {
            throw new IndexOutOfBoundsException("Inventory is full!");
        }

        while (this.clickableItems.containsKey(incrementalSlot)) {
            incrementalSlot++;
            if (incrementalSlot >= this.inventory.getSize()) {
                throw new IndexOutOfBoundsException("Inventory is full!");
            }
        }

        this.clickableItems.put(incrementalSlot, item);
        this.inventory.setItem(incrementalSlot, item.getItem());
        incrementalSlot++;
    }

    @Override
    public ClickableItem getItemAt(int slot) {
        return this.clickableItems.getOrDefault(slot, null);
    }

    public void fillRestWithEmpty() {
        for (int i = 0; i < this.inventory.getSize(); i++) {
            if (this.clickableItems.containsKey(i)) continue;
            ClickableItem item = EMPTY_PANE();
            this.clickableItems.put(i, item);
            this.inventory.setItem(i, item.getItem());
        }
    }

    public enum BorderType {TOP, BOTTOM, LEFT, RIGHT, ALL}

    public enum Direction {HORIZONTAL, VERTICAL}

    public void fillBorder(BorderType borderType) {
        ClickableItem item = EMPTY_PANE();
        switch (borderType) {
            case TOP -> {
                for (int i = 0; i < 9; i++) {
                    setItemAt(i, item);
                }
            }
            case BOTTOM -> {
                for (int i = this.inventory.getSize() - 9; i < this.inventory.getSize(); i++) {
                    setItemAt(i, item);
                }
            }
            case LEFT -> {
                for (int i = 0; i < this.inventory.getSize(); i += 9) {
                    setItemAt(i, item);
                }
            }
            case RIGHT -> {
                for (int i = 8; i < this.inventory.getSize(); i += 9) {
                    setItemAt(i, item);
                }
            }
            case ALL -> {
                this.fillBorder(BorderType.TOP);
                this.fillBorder(BorderType.BOTTOM);
                this.fillBorder(BorderType.LEFT);
                this.fillBorder(BorderType.RIGHT);
            }
        }
    }

    public void fillBorder(BorderType borderType, ClickableItem item) {
        switch (borderType) {
            case TOP -> {
                for (int i = 0; i < 9; i++) {
                    setItemAt(i, item);
                }
            }
            case BOTTOM -> {
                for (int i = this.inventory.getSize() - 9; i < this.inventory.getSize(); i++) {
                    setItemAt(i, item);
                }
            }
            case LEFT -> {
                for (int i = 0; i < this.inventory.getSize(); i += 9) {
                    setItemAt(i, item);
                }
            }
            case RIGHT -> {
                for (int i = 8; i < this.inventory.getSize(); i += 9) {
                    setItemAt(i, item);
                }
            }
            case ALL -> {
                this.fillBorder(BorderType.TOP);
                this.fillBorder(BorderType.BOTTOM);
                this.fillBorder(BorderType.LEFT);
                this.fillBorder(BorderType.RIGHT);
            }
        }
    }

    public void fillBorder(int location, Direction direction) {
        ClickableItem item = EMPTY_PANE();
        location--;
        switch (direction) {
            case HORIZONTAL -> {
                for (int i = location * 9; i < (location * 9) + 9; i++) {
                    setItemAt(i, item);
                }
            }
            case VERTICAL -> {
                for (int i = location % 9; i < this.inventory.getSize(); i += 9) {
                    setItemAt(i, item);
                }
            }
        }
    }

    public static String getTitle(Inventory bukkitInventory) {
        CraftInventoryCustom inventory = (CraftInventoryCustom) bukkitInventory;
        Field titleField = null;
        try {
            titleField = inventory.getInventory().getClass().getDeclaredField("title");
            titleField.setAccessible(true);
            return (String) titleField.get(inventory.getInventory());
        } catch (NoSuchFieldException | IllegalAccessException ignored) {
        }
        return null;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public int getSIZE() {
        return SIZE;
    }

    public String getTitle() {
        return title;
    }
}
