package me.edstorm17.battleroyale.listeners;

import me.edstorm17.battleroyale.Battle;
import me.edstorm17.battleroyale.BattleStage;
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
                    if (Battle.red.hasEntry(event.getPlayer().getName())) {
                        event.setRespawnLocation(active.getRedSpawnLocation());
                    } else if (Battle.blue.hasEntry(event.getPlayer().getName())) {
                        event.setRespawnLocation(active.getBlueSpawnLocation());
                    } else {
                        event.setRespawnLocation(active.getSpawnLocation());
                    }
                }
            } else {
                event.setRespawnLocation(Bukkit.getWorld("world").getSpawnLocation());
            }
        }
        for (AttributeModifier modifier : event.getPlayer().getAttribute(Attribute.MAX_HEALTH).getModifiers()) {
            event.getPlayer().getAttribute(Attribute.MAX_HEALTH).removeModifier(modifier);
        }
    }

}
