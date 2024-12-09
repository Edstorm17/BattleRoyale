package me.edstorm17.battleroyale.ui.guis;

import me.edstorm17.battleroyale.items.Item;
import me.edstorm17.battleroyale.items.ItemBuilder;
import me.edstorm17.battleroyale.ui.BaseGUI;
import me.edstorm17.battleroyale.ui.ClickableItem;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class KitGUI extends BaseGUI {

    private static final int SIZE = 6 * 9;

    public KitGUI() {
        super(SIZE, "Kits");
    }

    @Override
    public void initializeItems(Player player) {
        setNextItem(new ClickableItem(new ItemStack(Material.IRON_HELMET), (p) -> {
            p.getEquipment().setItem(EquipmentSlot.HEAD, new ItemStack(Material.IRON_HELMET));
            p.getEquipment().setItem(EquipmentSlot.CHEST, new ItemStack(Material.IRON_CHESTPLATE));
            p.getEquipment().setItem(EquipmentSlot.LEGS, new ItemStack(Material.IRON_LEGGINGS));
            p.getEquipment().setItem(EquipmentSlot.FEET, new ItemStack(Material.IRON_BOOTS));
            p.getInventory().addItem(new ItemStack(Material.STONE_AXE));
            p.getEquipment().setItem(EquipmentSlot.OFF_HAND, new ItemStack(Material.SHIELD));

            p.playSound(p, Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
        }));

        setNextItem(new ClickableItem(new ItemStack(Material.NETHERITE_BLOCK), (p) -> {
            p.getEquipment().setItem(EquipmentSlot.HEAD, ItemBuilder.builder().material(Material.NETHERITE_HELMET).enchantment(Enchantment.PROTECTION, 6).build());
            p.getEquipment().setItem(EquipmentSlot.CHEST, ItemBuilder.builder().material(Material.NETHERITE_CHESTPLATE).enchantment(Enchantment.PROTECTION, 6).build());
            p.getEquipment().setItem(EquipmentSlot.LEGS, ItemBuilder.builder().material(Material.NETHERITE_LEGGINGS).enchantment(Enchantment.PROTECTION, 6).build());
            p.getEquipment().setItem(EquipmentSlot.FEET, ItemBuilder.builder().material(Material.NETHERITE_BOOTS).enchantment(Enchantment.PROTECTION, 6).build());


        }));
        setNextItem(new ClickableItem(new ItemStack(Material.NETHERITE_CHESTPLATE), (p) -> {
            p.getInventory().setHelmet(Item.SWAT_HELMET.getItem().toItemStack());
            p.getInventory().setChestplate(Item.SWAT_ARMOR.getItem().toItemStack());
            p.getInventory().setLeggings(Item.SWAT_LEGGING.getItem().toItemStack());
            p.getInventory().setBoots(Item.SWAT_BOOTS.getItem().toItemStack());
            p.getInventory().addItem(new ItemStack(Item.STEALER.getItem().toItemStack()));
            p.getInventory().addItem(new ItemStack(Item.SERINGUE.getItem().toItemStack()));
            p.getInventory().setItemInOffHand((new ItemStack(Item.GRAPPLING_HOOK.getItem().toItemStack())));
//            p.getInventory().setItem(Item.SNAPFLASH, 1);

//           p.getInventory().add(new Itt

        }));
        setNextItem(new ClickableItem(new ItemStack(Material.SLIME_BALL), (p) -> {
            p.getInventory().setHelmet((Item.POT.getItem().toItemStack()));
            p.getInventory().setBoots((Item.NIKE.getItem().toItemStack()));
            p.getInventory().setLeggings((Item.DRONES.getItem().toItemStack()));
            p.getInventory().setChestplate((Item.SAMOURAI.getItem().toItemStack()));

        }));
        setNextItem(new ClickableItem(new ItemStack(Material.LAVA_BUCKET), (p) -> {
            p.getInventory().clear();

            p.playSound(p, Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
        }));
    }
}
