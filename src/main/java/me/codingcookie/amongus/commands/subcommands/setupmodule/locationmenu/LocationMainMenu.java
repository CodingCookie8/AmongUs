package me.codingcookie.amongus.commands.subcommands.setupmodule.locationmenu;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import me.codingcookie.amongus.AmongUs;
import me.codingcookie.amongus.commands.subcommands.setupmodule.CheckSetup;
import me.codingcookie.amongus.commands.subcommands.setupmodule.MenuUtility;
import me.codingcookie.amongus.utility.Singleton;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.*;

@CommandAlias("amongus")
@Subcommand("setup locations")
public class LocationMainMenu extends BaseCommand {

    private AmongUs plugin;

    private MenuUtility menuUtility;
    private CheckSetup checkSetup;
    private LocationUtility lU;
    private EmergencyMeetingMenu emergencyMeetingMenu;
    private VentMenu ventMenu;
    private TaskMenu taskMenu;
    private SabotageMenu sabotageMenu;

    public LocationMainMenu(AmongUs plugin){
        this.plugin = plugin;
    }

    private String pre2 = GRAY + ">";

    @Subcommand("lobby")
    public void onLobby(Player player, String[] args){
        if(!Singleton.getInstance().getSetup().contains(player.getUniqueId())){
            player.sendMessage(pre2 + RED + "You aren't in setup mode!");
            return;
        }
        if(args.length == 0) {
            lU = new LocationUtility(plugin);
            lU.waitingForLocation(player, "lobby");
        }
    }

    @Subcommand("emermeeting")
    public void onEmerMeeting(Player player, String[] args){
        if(!Singleton.getInstance().getSetup().contains(player.getUniqueId())){
            player.sendMessage(pre2 + RED + "You aren't in setup mode!");
            return;
        }
        if(args.length == 0){
            emergencyMeetingMenu = new EmergencyMeetingMenu(plugin);
            emergencyMeetingMenu.emerMeetingMenu(player);
        }
        if(args.length == 1){
            lU = new LocationUtility(plugin);
            int number = 0;
            if(args[0].equalsIgnoreCase("button")){
                lU.waitingForLocation(player, "emergencymeeting.button");
                return;
            }else {
                try {
                    number = Integer.parseInt(args[0]);
                } catch (NumberFormatException ex) {
                    return;
                }
                if ((number <= 0) || (number >= 11)) {
                    return;
                } else {
                    lU.waitingForLocation(player, "emergencymeeting." + number);
                    return;
                }
            }
        }
    }

    @Subcommand("vent")
    public void onVent(Player player, String[] args){
        if(!Singleton.getInstance().getSetup().contains(player.getUniqueId())){
            player.sendMessage(pre2 + RED + "You aren't in setup mode!");
            return;
        }
        if(args.length == 0){
            ventMenu = new VentMenu(plugin);
            ventMenu.ventMenu(player);
        }
        if(args.length == 1){
            lU = new LocationUtility(plugin);
            int number = 0;
            try {
                number = Integer.parseInt(args[0]);
            } catch (NumberFormatException ex) {
                return;
            }
            if((number <= 0) || (number >= 7)){
                return;
            }else {
                lU.waitingForLocation(player, "vent." + number);
                return;
            }
        }
    }

    @Subcommand("task")
    public void onTask(Player player, String[] args){
        if(!Singleton.getInstance().getSetup().contains(player.getUniqueId())){
            player.sendMessage(pre2 + RED + "You aren't in setup mode!");
            return;
        }
        if(args.length == 0){
            taskMenu = new TaskMenu(plugin);
            taskMenu.taskMenu(player);
        }
        if(args.length == 1){
            lU = new LocationUtility(plugin);
            try {
                lU.waitingForLocation(player, "task." + args[0]);
            }catch(NullPointerException e){
                player.sendMessage(RED + "Please do '/amongus setup' and use the clickable menu to setup the game.");
            }
        }
        if(args.length == 2){
            lU = new LocationUtility(plugin);
            int number = 0;
            try {
                number = Integer.parseInt(args[1]);
                lU.waitingForLocation(player, "task." + args[0] + "." + number);
            }catch(NullPointerException|NumberFormatException e){
                player.sendMessage(RED + "Please do '/amongus setup' and use the clickable menu to setup the game.");
            }
        }
    }

    @Subcommand("sabotage")
    public void onSabotage(Player player, String[] args){
        if(!Singleton.getInstance().getSetup().contains(player.getUniqueId())){
            player.sendMessage(pre2 + RED + "You aren't in setup mode!");
            return;
        }
        if(args.length == 0){
            sabotageMenu = new SabotageMenu(plugin);
            sabotageMenu.sabotageMenu(player);
        }
        if(args.length == 2){
            int number = 0;
            try {
                number = Integer.parseInt(args[1]);
                lU.waitingForLocation(player, "sabotage." + args[0] + "." + number);
            }catch(NullPointerException e){
                player.sendMessage(RED + "Please do '/amongus setup' and use the clickable menu to setup the game.");
            }
        }
    }

    public void locationMenu(Player player) {
        menuUtility = new MenuUtility(plugin);
        checkSetup = new CheckSetup(plugin);
        lU = new LocationUtility(plugin);

        for (int i = 1; i <= 10; i++) {
            player.sendMessage("");
        }

        player.sendMessage(lU.module);
        player.sendMessage(GOLD + "Below are all the locations you can change. Click on one to get started.");
        player.sendMessage("");

        lU.clickableLocation(checkSetup.checkLobby(), "LOBBY", "/amongus setup locations lobby", "lobby", player);
        menuUtility.clickableLink(checkSetup.checkEmergencyMeeting(), "EMERGENCY MEETING", "/amongus setup locations emermeeting", "Click me to setup/change Emergency Meeting locations", player);
        menuUtility.clickableLink(checkSetup.checkVents(), "VENTS", "/amongus setup locations vent", "Click me to setup/change vent locations", player);
        menuUtility.clickableLink(checkSetup.checkAllTasks(), "TASKS", "/amongus setup locations task", "Click me to setup/change task locations", player);
        menuUtility.clickableLink(checkSetup.checkAllSabotages(), "SABOTAGES", "/amongus setup locations sabotage", "Click me to setup/change sabotage locations", player);

        player.sendMessage("");
        menuUtility.clickableLink(true, "BACK", "/amongus setup", "Click me to go back!", player);
        menuUtility.clickableLink(false, "QUIT", "/amongus setup leave", "Click me to quit setup mode!", player);

    }


}
