package me.blm456.DeathSwap;

import org.bukkit.permissions.Permission;

public class Permissions {
    public static Game g;
    public final Permission start = new Permission("DeathSwap.start");
    public final Permission stop = new Permission("DeathSwap.stop");

    public Permissions(Game game) {
        g = game;
    }
}