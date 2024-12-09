package me.edstorm17.battleroyale.entities;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;

public class Bomber extends FlyingBoat {

    public Bomber(Location location) {
        super(location);
    }

    @Override
    public void shoot(Player player) {
        TNTPrimed tnt = player.getWorld().spawn(player.getLocation().subtract(0, 1, 0), TNTPrimed.class);
        tnt.setFuseTicks(100);
    }
}
