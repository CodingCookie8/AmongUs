package me.codingcookie.amongus.commands.subcommands.setupmodule.locationmenu;

import me.codingcookie.amongus.AmongUs;
import me.codingcookie.amongus.commands.subcommands.setupmodule.MenuUtility;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.*;

public class EmergencyMeetingMenu {

    private AmongUs plugin;
    private MenuUtility menuUtility;
    private LocationUtility lU;

    public EmergencyMeetingMenu(AmongUs plugin){
        this.plugin = plugin;
    }

    public void emerMeetingMenu(Player player){
        menuUtility = new MenuUtility(plugin);
        lU = new LocationUtility(plugin);

        for (int i = 1; i <= 10; i++) {
            player.sendMessage("");
        }

        player.sendMessage(lU.module);
        player.sendMessage(GOLD + "Click on one of the emergency meeting locations to change it.");
        player.sendMessage("");

        lU.clickableLocation(checkEmergencyMeetingButton(), "BUTTON", "/amongus setup locations emermeeting button","emergencymeeting.button", player);
        for (int i = 1; i <= 10; i++) {
            lU.clickableLocation(checkEmergencyMeeting(i), "LOCATION #" + i, "/amongus setup locations emermeeting " + i,"emergencymeeting." + i, player);
        }

        player.sendMessage("");
        menuUtility.clickableLink(true, "BACK", "/amongus setup locations", "Click me to go back!", player);
        menuUtility.clickableLink(false, "QUIT", "/amongus setup leave", "Click me to quit setup mode!", player);
    }

    Boolean checkEmergencyMeeting(int i){
        if(!plugin.getConfig().getBoolean("location.emergencymeeting." + i + ".setup")){
            return false;
        }
        return true;
    }

    Boolean checkEmergencyMeetingButton(){
        if(!plugin.getConfig().getBoolean("location.emergencymeeting.button.setup")){
            return false;
        }
        return true;
    }
}
