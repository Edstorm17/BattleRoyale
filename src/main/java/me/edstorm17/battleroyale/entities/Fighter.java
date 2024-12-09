package me.edstorm17.battleroyale.entities;

import me.edstorm17.battleroyale.listeners.ProjectileListener;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;

public class Fighter extends FlyingBoat {

    public Fighter(Location location) {
        super(location);
    }

    @Override
    public void shoot(Player player) {
        Arrow arrow = player.launchProjectile(Arrow.class, player.getEyeLocation().getDirection().multiply(15));
        arrow.setFireTicks(1200);
        ProjectileListener.arrows.add(arrow);
    }
}
