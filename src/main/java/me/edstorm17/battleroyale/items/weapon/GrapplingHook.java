package me.edstorm17.battleroyale.items.weapon;

import me.edstorm17.battleroyale.SpecialAbilities;
import me.edstorm17.battleroyale.items.Ability;
import me.edstorm17.battleroyale.items.BaseItem;
import me.edstorm17.battleroyale.items.Item;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;

public class GrapplingHook extends BaseItem implements Ability {


    public GrapplingHook() {
        super(
                "GRAPPLING_HOOK",
                Material.FISHING_ROD,
                "Grappling Hook"
        );
    }

    private static final Map<Player, Long> timer = new HashMap<>();

    @Override
    public void onClick(PlayerInteractEvent event) {
        if (SpecialAbilities.affectedPlayers.contains(event.getPlayer())) return;
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            long currentTime = System.currentTimeMillis();
            long cooldown = 2000L;
            if (event.getPlayer().getInventory().getChestplate() != null && Item.get(event.getPlayer().getInventory().getChestplate()) == Item.POT) cooldown -= 500L;
            if (event.getPlayer().getInventory().getBoots() != null && Item.get(event.getPlayer().getInventory().getBoots()) == Item.NIKE) cooldown -= 500L;

            if (currentTime - timer.getOrDefault(event.getPlayer(), 0L) >= cooldown) {
                event.getPlayer().setVelocity(event.getPlayer().getEyeLocation().getDirection().multiply(3));
                timer.put(event.getPlayer(), currentTime);
            }
        }
    }

}
