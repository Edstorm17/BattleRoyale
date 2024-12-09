package me.edstorm17.battleroyale.items.weapon;

import me.edstorm17.battleroyale.items.Ability;
import me.edstorm17.battleroyale.items.BaseItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;

public class Seed extends BaseItem implements Ability {

    public Seed() {
        super(
                "seed",
                Material.WHEAT_SEEDS,
                "GETME",
                null,
                null,
                Map.of(Enchantment.KNOCKBACK, 10),
                null,
                false
        );
    }

    @Override
    public void onHit(EntityDamageByEntityEvent event) {
        ((LivingEntity) event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,10 , 6, false, false));
    }
}
