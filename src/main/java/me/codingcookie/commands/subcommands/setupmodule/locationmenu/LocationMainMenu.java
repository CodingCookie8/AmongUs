package me.codingcookie.commands.subcommands.setupmodule.locationmenu;

import me.codingcookie.AmongUs;
import me.codingcookie.commands.subcommands.setupmodule.CheckSetup;
import me.codingcookie.commands.subcommands.setupmodule.MainMenu;
import me.codingcookie.files.messages.GetMessagesFile;
import me.codingcookie.permissions.PermissionHandler;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.*;

public class LocationMainMenu {

    private AmongUs plugin;
    private PermissionHandler pH;
    private GetMessagesFile messagesFile;
    private MainMenu mainMenu;
    private CheckSetup checkSetup;

    public LocationMainMenu(AmongUs plugin){
        this.plugin = plugin;
    }

    private String module = GOLD + "" + STRIKETHROUGH + "   ----------- " + BLUE + "" + BOLD + "[Setup Module]" + GOLD + "" + STRIKETHROUGH + " -----------";
    private String pre = BLUE + "" + BOLD + "[AmongUs] ";
    private String pre2 = DARK_GRAY + "> ";

    public void locationMenu(Player player) {
        pH = new PermissionHandler();
        messagesFile = new GetMessagesFile();
        mainMenu = new MainMenu(plugin);
        checkSetup = new CheckSetup(plugin);

        player.sendMessage("");
        player.sendMessage("");
        player.sendMessage("");
        player.sendMessage("");
        player.sendMessage("");
        player.sendMessage("");

        player.sendMessage(module);
        player.sendMessage(GOLD + "Below are all the locations you can change. Click on one to get started.");
        player.sendMessage("");

        mainMenu.clickableLink(checkSetup.checkEmergencyMeeting(), "EMERGENCY MEETING", "/amongus setup locations emermeeting", "Click me to setup/change Emergency Meeting locations", player);
        mainMenu.clickableLink(checkSetup.checkVents(), "VENTS", "/amongus setup locations vents", "Click me to setup/change vent locations", player);
        mainMenu.clickableLink(checkSetup.checkAllTasks(), "TASKS", "/amongus setup locations task", "Click me to setup/change task locations", player);
        mainMenu.clickableLink(checkSetup.checkAllSabotages(), "SABOTAGES", "/amongus setup locations sabotage", "Click me to setup/change sabotage locations", player);

        player.sendMessage("");
        mainMenu.clickableLink(true, "BACK", "/amongus setup", "Click me to go back!", player);
    }

}
