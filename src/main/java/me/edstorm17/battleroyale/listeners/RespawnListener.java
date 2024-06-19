package me.edstorm17.battleroyale.listeners;

import me.edstorm17.battleroyale.Battle;
import me.edstorm17.battleroyale.BattleStage;
import me.edstorm17.battleroyale.ScoreManager;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnListener implements Listener {

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        if (event.getPlayer().getWorld().getName().equals(Battle.worldName)) {
            Battle active = Battle.getActive();
            if (active != null) {
                if (active.getCurrentStage() == BattleStage.WAR) {
                    event.setRespawnLocation(active.getSpawnLocation());
                } else {
                    if (ScoreManager.red.hasEntry(event.getPlayer().getName())) {
                        event.setRespawnLocation(active.getRedSpawnLocation());
                    } else if (ScoreManager.blue.hasEntry(event.getPlayer().getName())) {
                        event.setRespawnLocation(active.getBlueSpawnLocation());
                    } else {
                        event.setRespawnLocation(active.getSpawnLocation());
                    }
                }
            } else {
                event.setRespawnLocation(Bukkit.getWorld("world").getSpawnLocation());
            }
        }
        for (AttributeModifier modifier : event.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getModifiers()) {
            event.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).removeModifier(modifier);
        }
    }

}
