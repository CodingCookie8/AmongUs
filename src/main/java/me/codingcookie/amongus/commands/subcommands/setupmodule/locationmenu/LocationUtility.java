package me.codingcookie.amongus.commands.subcommands.setupmodule.locationmenu;

import me.codingcookie.amongus.AmongUs;
import me.codingcookie.amongus.utility.Singleton;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static org.bukkit.ChatColor.*;

public class LocationUtility {

    private AmongUs plugin;

    private LocationMainMenu locationMainMenu;
    private EmergencyMeetingMenu emergencyMeetingMenu;
    private VentMenu ventMenu;
    private TaskMenu taskMenu;
    private SabotageMenu sabotageMenu;

    public LocationUtility(AmongUs plugin){
        this.plugin = plugin;
    }

    public String module = GOLD + "" + STRIKETHROUGH + "   ----------- " + BLUE + "" + BOLD + "[Setup Module]" + GOLD + "" + STRIKETHROUGH + " -----------";

    public void waitingForLocation(Player player, String location){
        player.sendMessage("");
        player.sendMessage(GOLD + "" + STRIKETHROUGH + "------------------------------------------ ");
        player.sendMessage(GOLD + "Use the shovel and right click on a block to change the location.");
        Singleton.getInstance().getWaitingForLocation().clear();
        Singleton.getInstance().getWaitingForLocation().put(player.getUniqueId(), location);
    }

    public void changedLocation(Player player) {
        locationMainMenu = new LocationMainMenu(plugin);
        player.sendMessage("");
        player.sendMessage(GOLD + "" + STRIKETHROUGH + "------------------------------------------ ");
        player.sendMessage(GOLD + "Successfully changed location.");
        new BukkitRunnable() {
            public void run() {
                returnToLastMenu(player);
                Singleton.getInstance().getWaitingForLocation().remove(player.getUniqueId());
                Singleton.getInstance().getWaitingForLocation().clear();
            }
        }.runTaskLater(plugin, 20L);
    }

    void returnToLastMenu(Player player){
        if(Singleton.getInstance().getWaitingForLocation().containsKey(player.getUniqueId())){
            locationMainMenu = new LocationMainMenu(plugin);
            emergencyMeetingMenu = new EmergencyMeetingMenu(plugin);
            ventMenu = new VentMenu(plugin);
            taskMenu = new TaskMenu(plugin);
            sabotageMenu = new SabotageMenu(plugin);
            if(Singleton.getInstance().getWaitingForLocation().get(player.getUniqueId()).contains("lobby"))
                locationMainMenu.locationMenu(player);
            if(Singleton.getInstance().getWaitingForLocation().get(player.getUniqueId()).contains("emergencymeeting"))
                emergencyMeetingMenu.emerMeetingMenu(player);
            if(Singleton.getInstance().getWaitingForLocation().get(player.getUniqueId()).contains("vent"))
                ventMenu.ventMenu(player);
            if(Singleton.getInstance().getWaitingForLocation().get(player.getUniqueId()).contains("task"))
                taskMenu.taskMenu(player);
            if(Singleton.getInstance().getWaitingForLocation().get(player.getUniqueId()).contains("sabotage"))
                sabotageMenu.sabotageMenu(player);
        }
    }

    public void clickableLocation(Boolean setup, String text, String runCommand, String location, Player player){
        if(!setup) {
            TextComponent setting = new TextComponent(RED + "              " + BOLD + text);
            setting.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, runCommand));
            setting.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(GOLD + "Click me to setup the " + RED + text + GOLD + " location.")));
            player.spigot().sendMessage(setting);
        }else if(setup){
            int locationX = plugin.getConfig().getInt("location." + location + ".x");
            int locationY = plugin.getConfig().getInt("location." + location + ".y");
            int locationZ = plugin.getConfig().getInt("location." + location + ".z");

            TextComponent setting = new TextComponent(GREEN + "              " + BOLD + text + " " + DARK_GRAY + "x: " + locationX + "  y: " + locationY + " z: " + locationZ);
            setting.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, runCommand));
            setting.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(GOLD + "Click me to change the " + GREEN + text + GOLD + " location.")));
            player.spigot().sendMessage(setting);
        }
    }
}
