package me.blm456.DeathSwap;

import java.util.HashMap;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Game extends JavaPlugin {
    public final Logger log = Logger.getLogger("Minecraft");
    public static Game g;
    public boolean gameOn = false;
    public final Permissions perms = new Permissions(this);
    public final Swapper swap = new Swapper(this);
    public final deathThing death = new deathThing(this);
    public HashMap<Player, String> playing = new HashMap();

    public Game() {
    }

    public void onDisable() {
        PluginDescriptionFile pdf = this.getDescription();
        this.log.info("[" + pdf.getName() + "] has been disabled!");
    }

    public void onEnable() {
        PluginDescriptionFile pdf = this.getDescription();
        this.log.info("[" + pdf.getName() + "] has been enabled!");
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(this.death, this);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p;
        if (label.equalsIgnoreCase("ds")) {
            if (args.length == 0) {
                if (sender instanceof Player) {
                    p = (Player)sender;
                    p.sendMessage("Usage /ds <player> <player>");
                }
            } else {
                Player p1;
                if (args.length == 1) {
                    if (sender instanceof Player) {
                        p = (Player)sender;
                        if (p.hasPermission(this.perms.start)) {
                            p1 = this.getServer().getPlayer(args[0]);
                            this.swap.start(p, p1, (Player)null);
                        } else {
                            p.sendMessage(ChatColor.DARK_RED + "You do not have permission for this!");
                        }
                    } else {
                        this.log.info("You must be a player to use this command!");
                    }
                } else if (args.length == 2) {
                    if (sender instanceof Player) {
                        p = (Player)sender;
                        if (p.hasPermission(this.perms.start)) {
                            p1 = this.getServer().getPlayer(args[0]);
                            Player p2 = this.getServer().getPlayer(args[1]);
                            this.swap.start(p1, p2, p);
                        } else {
                            p.sendMessage(ChatColor.DARK_RED + "You do not have permission for this!");
                        }
                    } else {
                        p = this.getServer().getPlayer(args[0]);
                        p1 = this.getServer().getPlayer(args[1]);
                        this.swap.start(p, p1, (Player)null);
                    }
                }
            }
        } else if (label.equalsIgnoreCase("dsstop")) {
            if (sender instanceof Player) {
                p = (Player)sender;
                if (p.hasPermission(this.perms.stop)) {
                    this.swap.forceStop();
                } else {
                    p.sendMessage("You do not have permission for this!");
                }
            } else {
                this.swap.forceStop();
            }
        }

        return false;
    }
}
