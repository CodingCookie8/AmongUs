package me.codingcookie.listeners;

import me.codingcookie.AmongUs;
import me.codingcookie.files.messages.GetMessagesFile;
import me.codingcookie.permissions.PermissionHandler;
import me.codingcookie.utility.Singleton;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static org.bukkit.ChatColor.*;

public class QuitListener implements Listener {

    private AmongUs plugin;

    public QuitListener(AmongUs plugin){
        this.plugin = plugin;
    }

    private String pre = BLUE + "" + BOLD + "[AmongUs] ";

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();

        if(Singleton.getInstance().getAmongUsPlayers().contains(player.getUniqueId()) &&
                !Singleton.getInstance().getAmongUsAlive().contains(player.getUniqueId()) &&
                !Singleton.getInstance().getAmongUsDead().contains(player.getUniqueId())){
            Singleton.getInstance().getAmongUsPlayers().remove(player.getUniqueId());
            Bukkit.getWorld(player.getWorld().getName()).getPlayers().forEach(worldPlayer -> worldPlayer.sendMessage(
                    pre + RED + player.getName() + GRAY + " quit the game! (" + Singleton.getInstance().getAmongUsPlayers().size() + "/10)"));
        }
        if(Singleton.getInstance().getAmongUsPlayers().contains(player.getUniqueId()) &&
                Singleton.getInstance().getAmongUsAlive().contains(player.getUniqueId())){
            Singleton.getInstance().getAmongUsPlayers().remove(player.getUniqueId());
            Singleton.getInstance().getAmongUsAlive().remove(player.getUniqueId());
            Bukkit.getWorld(player.getWorld().getName()).getPlayers().forEach(worldPlayer -> worldPlayer.sendMessage(
                    pre + RED + player.getName() + GRAY + " has quit the game and therefore has died!"));
        }
        if(Singleton.getInstance().getAmongUsPlayers().contains(player.getUniqueId()) &&
                Singleton.getInstance().getAmongUsDead().contains(player.getUniqueId())){
            Singleton.getInstance().getAmongUsPlayers().remove(player.getUniqueId());
            Singleton.getInstance().getAmongUsDead().remove(player.getUniqueId());
            Bukkit.getWorld(player.getWorld().getName()).getPlayers().forEach(worldPlayer -> worldPlayer.sendMessage(
                    pre + GRAY + "Dead player " + RED + player.getName() + GRAY + " has quit the game!"));
        }

        if(Singleton.getInstance().getSetup().contains(player.getUniqueId())){
            player.setGameMode(Singleton.getInstance().getPreSetupGamemode().get(player.getUniqueId()));
            Singleton.getInstance().getPreSetupGamemode().remove(player.getUniqueId());
            Singleton.getInstance().getSetup().remove(player.getUniqueId());
        }
    }
}
