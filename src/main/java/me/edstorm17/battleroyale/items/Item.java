package me.edstorm17.battleroyale.items;

import me.edstorm17.battleroyale.items.armor.*;
import me.edstorm17.battleroyale.items.weapon.*;
import org.apache.commons.lang3.ArrayUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.Objects;

public enum Item {

    PUFFER(new Puffer()),
    SNIPER(new Sniper()),
    FOULARD(new Foulard()),
    RAYGUN(new Raygun()),
    STEALER(new Stealer()),
    SEED(new Seed()),
    SERINGUE(new Seringue()),
    GAY(new Gay()),
    GRAVITY(new GravityGlove()),
    NIKE(new RogueBoots()),
    DRONES(new RogueLeggings()),
    SAMOURAI(new RogueChestplate()),
    POT(new RogueHelmet()),
    GRAPPLING_HOOK(new GrapplingHook()),
    SWAT_ARMOR(new SwatArmor()),
    SWAT_LEGGING(new SwatLegging()),
    SWAT_BOOTS(new SwatBoots()),
    SWAT_HELMET(new SwatHelmet()),
    DEMOLISHER_WAND(new DemolisherWand()),
    BUILDER_WAND(new BuilderWand()),
    DRAGOON(new Dragoon()),
    SWORDCOOL(new Swordcool()),
    PHOENIX_CHESTPLATE(new PhoenixChestplate()),
    BUILDER_BOOTS(new BuilderBoots()),
    BOOM(new Boom()),
    UCHI(new Uchi()),
    RIVERS(new Rivers()),
    MINIGUN(new Minigun()),
    SHOOTER_HELMET(new ShooterHelmet()),
    SHOOTER_CHESTPLATE(new ShooterChestplate()),
    SHOOTER_LEGGING(new ShooterLeggings()),
    SHOOTER_BOOTS(new ShooterBoots()),
    GLUTTONY(new Gluttony()),
    SLOTH(new Sloth()),
    SMOKE_BOMB(new SmokeBomb()),
    GOD_BOOTS(new GodBoots()),

    FIGHTER(new BaseItem("fighter", Material.OAK_BOAT, ChatColor.RED + "Fighter")),
    BOMBER(new BaseItem("bomber", Material.ACACIA_BOAT, ChatColor.RED + "Bomber")),
    SPECIAL(new BaseItem("special", Material.BAMBOO_RAFT, ChatColor.DARK_RED + "Special")),
    ;

    public static final Menu MENU = new Menu();

    private final BaseItem item;

    Item(BaseItem item) {
        this.item = item;
    }

    public BaseItem getItem() {
        return item;
    }

    public static String getId(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return "";
        return meta.getPersistentDataContainer().get(BaseItem.idKey, PersistentDataType.STRING);
    }

    public static Item get(ItemStack itemStack) {
        if (itemStack == null) return null;
        String id = getId(itemStack);
        for (Item item : Item.values()) {
            if (item.getItem().getId().equals(id)) return item;
        }
        return null;
    }

    public static Item[] getArmorAsCustomItems(Player player) {
        return Arrays.stream(player.getInventory().getArmorContents())
                .map(Item::get).toArray(Item[]::new);
    }

    public static Item[] getEquipmentAsCustomItems(Player player) {
        return Arrays.stream(ArrayUtils.addAll(player.getInventory().getArmorContents(), player.getInventory().getItemInMainHand(), player.getInventory().getItemInOffHand()))
                .filter(Objects::nonNull).map(Item::get).filter(Objects::nonNull).toArray(Item[]::new);
    }

    public static boolean isWearingAll(Player player, Item... items) {
        return Arrays.stream(Item.getArmorAsCustomItems(player))
                .allMatch(item -> Arrays.asList(items).contains(item));
    }

    public static boolean isWearingAtLeast(Player player, Item... items) {
        return Arrays.stream(items)
                .allMatch(item -> Arrays.asList(getArmorAsCustomItems(player)).contains(item));
    }

}
