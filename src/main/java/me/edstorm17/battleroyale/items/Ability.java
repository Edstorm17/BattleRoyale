package me.edstorm17.battleroyale.items;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public interface Ability {

    default void onClick(PlayerInteractEvent event) {}

    default void onDamage(EntityDamageEvent event) {}

    default void onHit(EntityDamageByEntityEvent event) {}

    default void onMove(PlayerMoveEvent event) {}

    default void onDeath(PlayerDeathEvent event) {}

}
