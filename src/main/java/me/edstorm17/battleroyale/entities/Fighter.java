package me.edstorm17.battleroyale.entities;

import me.edstorm17.battleroyale.listeners.HitListener;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;

public class Fighter extends FlyingBoat {

    public Fighter(Location location) {
        super(location, EnumBoatType.a);
    }

    @Override
    public void shoot(Player player) {
        Arrow arrow = player.launchProjectile(Arrow.class, player.getEyeLocation().getDirection().multiply(15));
        arrow.setFireTicks(1200);
        HitListener.arrows.add(arrow);
    }

    @Override
    public EnumBoatType d() {
        return EnumBoatType.a;
    }
}
