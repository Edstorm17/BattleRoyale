package me.edstorm17.battleroyale.commands;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class StuffCommand implements CommandExecutor  {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            ItemStack helmet = new ItemStack(Material.NETHERITE_HELMET);
            ItemMeta helmetMeta = helmet.getItemMeta();
            ItemStack body = new ItemStack(Material.NETHERITE_CHESTPLATE);
            ItemMeta bodyMeta = body.getItemMeta();
            ItemStack pants = new ItemStack(Material.NETHERITE_LEGGINGS);
            ItemMeta pantsMeta = pants.getItemMeta();
            ItemStack shoes = new ItemStack(Material.NETHERITE_BOOTS);
            ItemMeta shoesMeta = shoes.getItemMeta();
            ItemStack epee = new ItemStack(Material.NETHERITE_SWORD);
            ItemMeta epeeMeta = epee.getItemMeta();


            bodyMeta.addEnchant( Enchantment.PROTECTION, 6, true);
            helmetMeta.addEnchant( Enchantment.PROTECTION, 6, true);
            pantsMeta.addEnchant( Enchantment.PROTECTION, 6, true);
            shoesMeta.addEnchant( Enchantment.PROTECTION, 6, true );
            epeeMeta.addEnchant(Enchantment.SHARPNESS,6,true);



            bodyMeta.setUnbreakable(true);
            helmetMeta.setUnbreakable(true);
            pantsMeta.setUnbreakable(true);
            shoesMeta.setUnbreakable(true);


            helmet.setItemMeta(helmetMeta);
            body.setItemMeta(bodyMeta);
            pants.setItemMeta(bodyMeta);
            shoes.setItemMeta(bodyMeta);

            player.getInventory().addItem( epee, new ItemStack(Material.END_CRYSTAL, 64), new ItemStack(Material.OBSIDIAN, 64), new ItemStack(Material.GOLDEN_APPLE, 64));
            player.getEquipment().setItemInOffHand(new ItemStack(Material.TOTEM_OF_UNDYING, 10));
            player.getEquipment().setHelmet(helmet);
            player.getEquipment().setBoots(shoes);
            player.getEquipment().setLeggings(pants);
            player.getEquipment().setChestplate(body);

        }

        return true;
    }
}
