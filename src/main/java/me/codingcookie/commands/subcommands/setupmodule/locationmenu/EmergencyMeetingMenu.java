package me.codingcookie.commands.subcommands.setupmodule.locationmenu;

import me.codingcookie.AmongUs;
import me.codingcookie.commands.subcommands.setupmodule.CheckSetup;
import me.codingcookie.commands.subcommands.setupmodule.MainMenu;
import me.codingcookie.files.messages.GetMessagesFile;
import me.codingcookie.permissions.PermissionHandler;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.*;
import static org.bukkit.ChatColor.DARK_GRAY;

public class EmergencyMeetingMenu {

    private AmongUs plugin;
    private PermissionHandler pH;
    private GetMessagesFile messagesFile;
    private MainMenu mainMenu;
    private CheckSetup checkSetup;

    public EmergencyMeetingMenu(AmongUs plugin){
        this.plugin = plugin;
    }

    private String module = GOLD + "" + STRIKETHROUGH + "   ----------- " + BLUE + "" + BOLD + "[Setup Module]" + GOLD + "" + STRIKETHROUGH + " -----------";
    private String pre = BLUE + "" + BOLD + "[AmongUs] ";
    private String pre2 = DARK_GRAY + "> ";

    public void emerMeetingMenu(Player player){
        pH = new PermissionHandler();
        messagesFile = new GetMessagesFile();
        mainMenu = new MainMenu(plugin);
        checkSetup = new CheckSetup(plugin);

        player.sendMessage("");
        player.sendMessage("");
        player.sendMessage("");
        player.sendMessage("");
        player.sendMessage("");

        player.sendMessage(module);
        player.sendMessage(GOLD + "Click on one of the emergency meeting locations to change it.");
        player.sendMessage("");

        for (int i = 1; i <= 10; i++) {
            mainMenu.clickableLink(checkEmergencyMeeting(i), "LOCATION #" + i, "/amongus setup location emermeeting " + i, "Click me to setup/change Emergency Meeting location #" + i, player);
        }

        player.sendMessage("");
        mainMenu.clickableLink(true, "BACK", "/amongus setup locations", "Click me to go back!", player);
    }

    public void initiateEmerMeetingLocationChange(int location){

    }

    Boolean checkEmergencyMeeting(int i){
        for (i = 1; i <= 10; i++) {
            if(!plugin.getConfig().getBoolean("location.emergencymeeting." + i + ".setup")){
                return false;
            }
        }
        return true;
    }
}
