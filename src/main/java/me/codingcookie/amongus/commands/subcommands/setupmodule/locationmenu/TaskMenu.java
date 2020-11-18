package me.codingcookie.amongus.commands.subcommands.setupmodule.locationmenu;

import me.codingcookie.amongus.AmongUs;
import me.codingcookie.amongus.commands.subcommands.setupmodule.CheckSetup;
import me.codingcookie.amongus.commands.subcommands.setupmodule.MenuUtility;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.*;

public class TaskMenu {

    private AmongUs plugin;
    private MenuUtility menuUtility;
    private LocationUtility lU;
    private CheckSetup cS;

    public TaskMenu(AmongUs plugin){
        this.plugin = plugin;
    }

    public void taskMenu(Player player) {
        menuUtility = new MenuUtility(plugin);
        lU = new LocationUtility(plugin);
        cS = new CheckSetup(plugin);

        for (int i = 1; i <= 10; i++) {
            player.sendMessage("");
        }

        player.sendMessage(lU.module);
        player.sendMessage(GOLD + "Click on one of the vent locations to change it.");
        player.sendMessage("");

        lU.clickableLocation(checkTask("upload"), "UPLOAD", "/amongus setup locations task upload", "task.upload", player);
        lU.clickableLocation(checkTask("download"), "DOWNLOAD", "/amongus setup locations task download", "task.download", player);
        lU.clickableLocation(checkTask("navigation"), "NAVIGATION", "/amongus setup locations task navigation", "task.navigation", player);
        lU.clickableLocation(checkTask("clearasteroid"), "CLEAR ASTEROID", "/amongus setup locations task clearasteroid", "task.clearasteroid", player);
        lU.clickableLocation(checkTask("swipecard"), "SWIPE CARD", "/amongus setup locations task swipecard", "task.swipecard", player);
        lU.clickableLocation(checkTask("fuelengines"), "FUEL ENGINES", "/amongus setup locations task fuelengines", "task.fuelengines", player);
        lU.clickableLocation(checkTask("trashchute"), "TRASH CHUTE", "/amongus setup locations task trashchute", "task.trashchute", player);
        lU.clickableLocation(checkTask("inspect"), "INSPECT", "/amongus setup locations task inspect", "task.inspect", player);

        for (int i = 1; i <= 3; i++) {
            lU.clickableLocation(checkTask("fixwiring." + i), "FIX WIRING #" + i, "/amongus setup locations task fixwiring " + i, "task.fixwiring." + i, player);
        }

        player.sendMessage("");
        menuUtility.clickableLink(true, "BACK", "/amongus setup locations", "Click me to go back!", player);
        menuUtility.clickableLink(false, "QUIT", "/amongus setup leave", "Click me to quit setup mode!", player);
    }

    Boolean checkTask(String task){
        if(!plugin.getConfig().getBoolean("location.task." + task + ".setup")){
            return false;
        }
        return true;
    }

}
