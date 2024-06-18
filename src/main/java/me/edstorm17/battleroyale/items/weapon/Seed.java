package me.edstorm17.battleroyale.items.weapon;

import me.edstorm17.battleroyale.items.BaseItem;
import me.edstorm17.battleroyale.items.Hittable;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;

public class Seed extends BaseItem implements Hittable {

    public Seed() {
        super(
                "seed",
                Material.WHEAT_SEEDS,
                "GETME",
                null,
                null,
                Map.of(Enchantment.KNOCKBACK, 10)
        );
    }

    @Override
    public void hit(Player source, Player target) {
        target.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,10 , 6, false, false));
    }
}
