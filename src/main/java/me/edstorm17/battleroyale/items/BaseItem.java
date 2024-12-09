package me.edstorm17.battleroyale.items;

import me.edstorm17.battleroyale.BattleRoyale;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class BaseItem {

    public static final NamespacedKey idKey = new NamespacedKey(BattleRoyale.getInstance(), "id");
    private final ItemStack itemStack;
    private final String ID;

    public BaseItem(
            @Nonnull String ID,
            @Nonnull Material material,
            @Nonnull String name
    ) {
        this(ID, material, name, null, null, null, null, false);
    }

    public BaseItem(
            @Nonnull String ID,
            @Nonnull Material material,
            @Nonnull String name,
            @Nullable List<String> lore,
            @Nullable Map<Attribute, AttributeModifier> attributes,
            @Nullable Map<Enchantment, Integer> enchants,
            @Nullable Color color,
            boolean unbreakable
            ) {

        itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        meta.setLore(lore);
        meta.setUnbreakable(unbreakable);

        if (attributes != null) {
            for (Entry<Attribute, AttributeModifier> attribute : attributes.entrySet()) {
                meta.addAttributeModifier(attribute.getKey(), attribute.getValue());
            }
        }

        if (enchants != null) {
            for (Entry<Enchantment, Integer> enchant : enchants.entrySet()) {
                meta.addEnchant(enchant.getKey(), enchant.getValue(), true);
            }
        }

        if (color != null) {
            LeatherArmorMeta meta1 = (LeatherArmorMeta) meta;
            meta1.setColor(color);
        }

        meta.getPersistentDataContainer().set(idKey, PersistentDataType.STRING, ID);
        this.ID = ID;

        itemStack.setItemMeta(meta);
    }

    public String getId() {
        return ID;
    }

    public ItemStack toItemStack() {
        return itemStack.clone();
    }

}
