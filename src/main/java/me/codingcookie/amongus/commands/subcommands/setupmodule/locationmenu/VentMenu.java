package me.codingcookie.amongus.commands.subcommands.setupmodule.locationmenu;

import me.codingcookie.amongus.AmongUs;
import me.codingcookie.amongus.commands.subcommands.setupmodule.MenuUtility;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.*;

public class VentMenu {

    private AmongUs plugin;
    private MenuUtility menuUtility;
    private LocationUtility lU;

    public VentMenu(AmongUs plugin){
        this.plugin = plugin;
    }

    public void ventMenu(Player player) {
        menuUtility = new MenuUtility(plugin);
        lU = new LocationUtility(plugin);

        for (int i = 1; i <= 10; i++) {
            player.sendMessage("");
        }

        player.sendMessage(lU.module);
        player.sendMessage(GOLD + "Click on one of the vent locations to change it.");
        player.sendMessage("");

        for (int i = 1; i <= 6; i++) {
            lU.clickableLocation(checkVent(i), "VENT LOCATION #" + i, "/amongus setup locations vent " + i,"vent." + i, player);
        }

        player.sendMessage("");
        menuUtility.clickableLink(true, "BACK", "/amongus setup locations", "Click me to go back!", player);
        menuUtility.clickableLink(false, "QUIT", "/amongus setup leave", "Click me to quit setup mode!", player);
    }

    Boolean checkVent(int i){
        if(!plugin.getConfig().getBoolean("location.vent." + i + ".setup")){
            return false;
        }
        return true;
    }
}
