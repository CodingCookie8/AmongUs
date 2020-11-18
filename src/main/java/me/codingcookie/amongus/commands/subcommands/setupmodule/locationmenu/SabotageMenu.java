package me.codingcookie.amongus.commands.subcommands.setupmodule.locationmenu;

import me.codingcookie.amongus.AmongUs;
import me.codingcookie.amongus.commands.subcommands.setupmodule.MenuUtility;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.GOLD;

public class SabotageMenu {

    private AmongUs plugin;
    private MenuUtility menuUtility;
    private LocationUtility lU;

    public SabotageMenu(AmongUs plugin){
        this.plugin = plugin;
    }

    public void sabotageMenu(Player player) {
        menuUtility = new MenuUtility(plugin);
        lU = new LocationUtility(plugin);

        for (int i = 1; i <= 10; i++) {
            player.sendMessage("");
        }

        player.sendMessage(lU.module);
        player.sendMessage(GOLD + "Click on one of the sabotage locations to change it.");
        player.sendMessage("");

        lU.clickableLocation(checkSabotage("meltdown.1"), "MELTDOWN #1", "/amongus setup locations sabotage meltdown 1", "sabotage.meltdown.1", player);
        lU.clickableLocation(checkSabotage("meltdown.2"), "MELTDOWN #2", "/amongus setup locations sabotage meltdown 2", "sabotage.meltdown.2", player);

        lU.clickableLocation(checkSabotage("fixlights.1"), "FIX LIGHTS #1", "/amongus setup locations sabotage fixlights 1", "sabotage.fixlights.1", player);
        lU.clickableLocation(checkSabotage("fixlights.2"), "FIX LIGHTS #2", "/amongus setup locations sabotage fixlights 2", "sabotage.fixlights.2", player);

        lU.clickableLocation(checkSabotage("cleano2.1"), "CLEAN O2 #1", "/amongus setup locations sabotage cleano2 1", "sabotage.cleano2.1", player);
        lU.clickableLocation(checkSabotage("cleano2.2"), "CLEAN O2 #2", "/amongus setup locations sabotage cleano2 2", "sabotage.cleano2.2", player);

        player.sendMessage("");
        menuUtility.clickableLink(true, "BACK", "/amongus setup locations", "Click me to go back!", player);
        menuUtility.clickableLink(false, "QUIT", "/amongus setup leave", "Click me to quit setup mode!", player);

    }

    Boolean checkSabotage(String sabotage){
        if(!plugin.getConfig().getBoolean("location.sabotage." + sabotage + ".setup")){
            return false;
        }
        return true;
    }
}
