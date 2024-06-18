package me.edstorm17.battleroyale.items;

import org.bukkit.entity.Player;

public interface Hittable {

    void hit(Player source, Player target);

}
