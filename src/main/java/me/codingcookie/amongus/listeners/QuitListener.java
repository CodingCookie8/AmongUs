package me.codingcookie.amongus.listeners;

import me.codingcookie.amongus.AmongUs;
import me.codingcookie.amongus.utility.Singleton;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;

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

        if(Singleton.getInstance().getAmongUsPlayers().contains(player.getName()) &&
                !Singleton.getInstance().getAmongUsAlive().contains(player.getName()) &&
                !Singleton.getInstance().getAmongUsDead().contains(player.getName())){

            Singleton.getInstance().getAmongUsPlayers().remove(player.getName());
            Bukkit.getWorld(player.getWorld().getName()).getPlayers().forEach(worldPlayer -> worldPlayer.sendMessage(
                    pre + RED + player.getName() + GRAY + " quit the game! (" + Singleton.getInstance().getAmongUsPlayers().size() + "/10)"));
        }
        if(Singleton.getInstance().getAmongUsPlayers().contains(player.getName()) &&
                Singleton.getInstance().getAmongUsAlive().contains(player.getName())){

            Singleton.getInstance().getAmongUsPlayers().remove(player.getName());
            Singleton.getInstance().getAmongUsAlive().remove(player.getName());
            Bukkit.getWorld(player.getWorld().getName()).getPlayers().forEach(worldPlayer -> worldPlayer.sendMessage(
                    pre + RED + player.getName() + GRAY + " has quit the game and therefore has died!"));
        }
        if(Singleton.getInstance().getAmongUsPlayers().contains(player.getName()) &&
                Singleton.getInstance().getAmongUsDead().contains(player.getName())){

            Singleton.getInstance().getAmongUsPlayers().remove(player.getName());
            Singleton.getInstance().getAmongUsDead().remove(player.getName());
            Bukkit.getWorld(player.getWorld().getName()).getPlayers().forEach(worldPlayer -> worldPlayer.sendMessage(
                    pre + GRAY + "Dead player " + RED + player.getName() + GRAY + " has quit the game!"));
        }

        if(Singleton.getInstance().getAmongUsCurrentlyPlaying().containsKey(player.getName())){
            Singleton.getInstance().getAmongUsCurrentlyPlaying().remove(player.getName());
        }

        if(Singleton.getInstance().getSetup().contains(player.getUniqueId())){
            player.setGameMode(Singleton.getInstance().getPreSetupGamemode().get(player.getUniqueId()));
            Singleton.getInstance().getPreSetupGamemode().remove(player.getUniqueId());
            Singleton.getInstance().getSetup().remove(player.getUniqueId());
        }

        if(Singleton.getInstance().getWaitingForSettingInt().containsKey(player.getUniqueId())){
            Singleton.getInstance().getWaitingForSettingInt().remove(player.getUniqueId());
        }

        if(Singleton.getInstance().getWaitingForLocation().containsKey(player.getUniqueId())){
            Singleton.getInstance().getWaitingForLocation().remove(player.getUniqueId());
        }

        player.removePotionEffect(PotionEffectType.BLINDNESS);
    }
}
