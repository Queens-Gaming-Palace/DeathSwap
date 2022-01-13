package me.blm456.DeathSwap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class deathThing implements Listener {
    public static Game g;

    public deathThing(Game game) {
        g = game;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        if (g.gameOn && g.playing.containsKey(p)) {
            Player p1 = g.swap.playerOne;
            Player p2 = g.swap.playerTwo;
            p1.teleport(p1.getWorld().getSpawnLocation());
            p2.teleport(p2.getWorld().getSpawnLocation());
            Bukkit.getScheduler().cancelTask(Swapper.swapSwitch);
            g.playing.clear();
            g.gameOn = false;
            if (p == p1) {
                Bukkit.broadcastMessage(p2.getName() + " beat " + p1.getName() + " in DeathSwap!");
            } else {
                Bukkit.broadcastMessage(p1.getName() + " beat " + p2.getName() + " in DeathSwap!");
            }
        }

    }
}
