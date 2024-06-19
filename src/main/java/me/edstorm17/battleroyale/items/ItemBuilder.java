package me.edstorm17.battleroyale.items;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ItemBuilder {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Builder() {
        }

        private Material material;
        private int amount = 1;
        private String displayName;
        private List<String> lore;
        private final Map<Enchantment, Integer> enchantments = new HashMap<>();
        private final Map<Attribute, AttributeModifier> modifiers = new HashMap<>();
        private boolean unbreakable;

        public Builder material(Material material) {
            this.material = material;
            return this;
        }

        public Builder amount(int amount) {
            this.amount = amount;
            return this;
        }

        public Builder displayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder lore(List<String> lore) {
            this.lore = lore;
            return this;
        }

        public Builder enchantment(Enchantment enchantment, int level) {
            this.enchantments.put(enchantment, level);
            return this;
        }

        public Builder attributeModifier(Attribute attribute, AttributeModifier attributeModifier) {
            this.modifiers.put(attribute, attributeModifier);
            return this;
        }

        public Builder unbreakable(boolean unbreakable) {
            this.unbreakable = unbreakable;
            return this;
        }

        public ItemStack build() {
            ItemStack stack = new ItemStack(this.material, this.amount);
            ItemMeta meta = stack.getItemMeta();
            assert meta != null;

            meta.setDisplayName(this.displayName);
            meta.setLore(this.lore);
            for (Entry<Enchantment, Integer> entry : this.enchantments.entrySet()) {
                meta.addEnchant(entry.getKey(), entry.getValue(), true);
            }
            for (Entry<Attribute, AttributeModifier> entry : this.modifiers.entrySet()) {
                meta.addAttributeModifier(entry.getKey(), entry.getValue());
            }
            meta.setUnbreakable(unbreakable);

            stack.setItemMeta(meta);
            return stack;
        }

    }

}
