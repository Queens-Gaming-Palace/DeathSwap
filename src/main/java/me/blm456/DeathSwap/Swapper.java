package me.blm456.DeathSwap;

import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Swapper {
    public static Game g;
    public Player playerOne;
    public Player playerTwo;
    public Random rand = new Random();
    public static int swapSwitch = 0;
    public static int startTimer = 0;

    public Swapper(Game game) {
        g = game;
    }

    public void start(Player p1, Player p2, Player sender) {
        if (!g.gameOn) {
            g.gameOn = true;
            this.playerOne = p1;
            this.playerTwo = p2;
            g.playing.put(p1, ((Object)null).toString());
            g.playing.put(p2, ((Object)null).toString());
            this.startCountDown(p1, p2);
        } else if (sender != null) {
            sender.sendMessage("There is already a game in progress");
        } else {
            g.log.info("There is already a game in progress");
        }

    }

    public void startSwap(final Player p1, final Player p2, int sawpness) {
        swapSwitch = Bukkit.getScheduler().scheduleSyncRepeatingTask(g, new Runnable() {
            public int swapTime;

            public void run() {
                if (this.swapTime > 0) {
                    --this.swapTime;
                } else {
                    p1.sendMessage("Swapping!");
                    p2.sendMessage("Swapping!");
                    Location loc1 = p1.getLocation();
                    Location loc2 = p2.getLocation();
                    p1.teleport(loc2);
                    p1.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 10));
                    p2.teleport(loc1);
                    p2.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 10));
                    int time = Swapper.this.rand.nextInt(100);
                    time += 22;
                    this.swapTime = time;
                }

            }
        }, 0L, 20L);
    }

    public void startCountDown(final Player p1, final Player p2) {
        startTimer = Bukkit.getScheduler().scheduleSyncRepeatingTask(g, new Runnable() {
            public int starttime = 5;

            public void run() {
                if (this.starttime > 0) {
                    p1.sendMessage("Game starting in " + this.starttime);
                    p2.sendMessage("Game starting in " + this.starttime);
                    --this.starttime;
                } else {
                    Bukkit.getScheduler().cancelTasks(g);
                    p1.sendMessage("Game Starting!");
                    p2.sendMessage("Game Starting!");
                    Swapper.this.finalStart(p1);
                    Swapper.this.finalStart(p2);
                    int time = Swapper.this.rand.nextInt(100);
                    time += 22;
                    Swapper.this.startSwap(p1, p2, time);
                }

            }
        }, 0L, 20L);
    }

    public void finalStart(Player p) {
        int locX = this.rand.nextInt(100000);
        int locZ = this.rand.nextInt(100000);
        Location location = new Location(p.getWorld(), (double)locX, 100.0D, (double)locZ);
        p.setHealth(20.0D);
        p.setFoodLevel(20);
        p.teleport(location);
        if (p.getGameMode() != GameMode.SURVIVAL) {
            p.setGameMode(GameMode.SURVIVAL);
        }

        p.getInventory().clear();
        p.updateInventory();
        p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 500, 10));
    }

    public void forceStop() {
        if (g.gameOn) {
            g.gameOn = false;
            g.playing.clear();
            Player p1 = this.playerOne;
            Player p2 = this.playerTwo;
            p1.teleport(p1.getWorld().getSpawnLocation());
            p2.teleport(p2.getWorld().getSpawnLocation());
            Bukkit.broadcastMessage("Game stopped by Admin!");
            Bukkit.getScheduler().cancelTasks(g);
        }

    }
}