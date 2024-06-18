package me.edstorm17.battleroyale.listeners;

import me.edstorm17.battleroyale.Battle;
import me.edstorm17.battleroyale.ScoreManager;
import me.edstorm17.battleroyale.commands.MatchCommand;
import me.edstorm17.battleroyale.items.weapon.Stealer;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnListener implements Listener {

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        if (event.getPlayer().getWorld().getName().equals(MatchCommand.worldName)) {
            Battle active = Battle.getActive();
            if (ScoreManager.red.hasEntry(event.getPlayer().getName())) {
                event.setRespawnLocation(active.getRedSpawnLocation());
            } else if (ScoreManager.blue.hasEntry(event.getPlayer().getName())) {
                event.setRespawnLocation(active.getBlueSpawnLocation());
            } else {
                event.setRespawnLocation(active.getSpawnLocation());
            }
        }
        for (AttributeModifier modifier : event.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getModifiers()) {
            event.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).removeModifier(modifier);
        }
    }

}
