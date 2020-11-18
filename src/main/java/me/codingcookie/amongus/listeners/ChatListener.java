package me.codingcookie.amongus.listeners;

import me.codingcookie.amongus.AmongUs;
import me.codingcookie.amongus.commands.subcommands.setupmodule.MenuUtility;
import me.codingcookie.amongus.commands.subcommands.setupmodule.settingsmenu.SettingsMenu;
import me.codingcookie.amongus.commands.subcommands.setupmodule.settingsmenu.SettingsUtility;
import me.codingcookie.amongus.utility.Singleton;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

import static org.bukkit.ChatColor.*;

public class ChatListener implements Listener {

    private AmongUs plugin;
    private SettingsUtility sU;
    private SettingsMenu sM;
    private MenuUtility menuUtility;

    public ChatListener(AmongUs plugin){
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        sU = new SettingsUtility(plugin);
        menuUtility = new MenuUtility(plugin);

        if(Singleton.getInstance().getSetup().contains(player.getUniqueId())){
           if(event.getMessage().equalsIgnoreCase("leave") || event.getMessage().equalsIgnoreCase("end")){
               menuUtility.leaveSetupMode(player);
               event.setCancelled(true);
           }
        }

        if(Singleton.getInstance().getWaitingForSettingInt().containsKey(player.getUniqueId())){
            int number = 0;
            try {
                number = Integer.parseInt(event.getMessage());
            } catch (NumberFormatException ex) {
                cancelEvent(player, "Can't recognize number submitted.");
                event.setCancelled(true);
                return;
            }
            if(number < 0){
                cancelEvent(player, "This setting can't be less than zero.");
            }
            if(((number <= 0) || (number >= 7)) && (Singleton.getInstance().getWaitingForSettingInt().get(player.getUniqueId()).equalsIgnoreCase("settings.imposters"))){
                cancelEvent(player, "This setting can't less than one or greater than seven!");
                event.setCancelled(true);
                return;
            }
            if((number <= 10) && (Singleton.getInstance().getWaitingForSettingInt().get(player.getUniqueId()).equalsIgnoreCase("settings.voting"))){
                cancelEvent(player, "This setting can't be below 10!");
                event.setCancelled(true);
                return;
            }else {
                sU.changeIntSetting(player, Singleton.getInstance().getWaitingForSettingInt().get(player.getUniqueId()), number);
                event.setCancelled(true);
                return;
            }
        }
    }

    void cancelEvent(Player player, String cancelMessage){
        sM = new SettingsMenu(plugin);
        player.sendMessage("");
        player.sendMessage(RED + cancelMessage);
        Singleton.getInstance().getWaitingForSettingInt().remove(player.getUniqueId());
        new BukkitRunnable() {
            public void run() {
                sM.settingsMenu(player);
            }
        }.runTaskLater(plugin, 20L);
    }
}
