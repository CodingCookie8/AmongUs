package me.codingcookie.amongus.commands.subcommands.setupmodule.settingsmenu;

import me.codingcookie.amongus.AmongUs;
import me.codingcookie.amongus.commands.subcommands.setupmodule.MenuUtility;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.GOLD;

public class SettingsMenu {

    private AmongUs plugin;
    private MenuUtility menuUtility;
    private SettingsUtility sU;

    public SettingsMenu(AmongUs plugin){
        this.plugin = plugin;
    }

    public void settingsMenu(Player player) {
        menuUtility = new MenuUtility(plugin);
        sU = new SettingsUtility(plugin);

        for (int i = 1; i <= 10; i++) {
            player.sendMessage("");
        }

        player.sendMessage(sU.module);
        player.sendMessage(GOLD + "Below are all the settings you can change. Click on one to change it.");
        player.sendMessage("");

        sU.clickableInt(plugin.getConfig().getInt("settings.imposters"), "IMPOSTERS", "imposters", "/amongus setup settings imposters", "Click to change how many imposters are in the game", player);
        sU.clickableInt(plugin.getConfig().getInt("settings.maxemergencymeetings"), "EMERGENCY MEETINGS", "meetings", "/amongus setup settings maxemergencymeetings", "Click to change the maximum emergency meetings a player gets in a game", player);
        sU.clickableInt(plugin.getConfig().getInt("settings.emergencycooldown"), "EMERGENCY COOLDOWN", "seconds", "/amongus setup settings emergencycooldown", "Click to change the cooldown of hitting the emergency meeting button", player);
        sU.clickableInt(plugin.getConfig().getInt("settings.discussion"), "DISCUSSION", "seconds", "/amongus setup settings discussion", "Click to change the length of the discussion period", player);
        sU.clickableInt(plugin.getConfig().getInt("settings.voting"), "VOTING", "seconds", "/amongus setup settings voting", "Click to change the length of the voting period", player);
        sU.clickableBooleanSetting(plugin.getConfig().getBoolean("settings.crewmatevision"), "CREWMATE VISION", "/amongus setup settings crewmatevision", "Click to enable/disable crewmate vision", player);
        sU.clickableBooleanSetting(plugin.getConfig().getBoolean("settings.impostervision"), "IMPOSTER VISION", "/amongus setup settings impostervision", "Click to enable/disable imposter vision", player);
        sU.clickableBooleanSetting(plugin.getConfig().getBoolean("settings.anonymousejection"), "ANONYMOUS EJECTION", "/amongus setup settings anonymousejection", "Click to enable/disable anonymous ejection", player);
        sU.clickableInt(plugin.getConfig().getInt("settings.killcooldown"), "KILL COOLDOWN", "seconds", "/amongus setup settings killcooldown", "Click to change the cooldown of killing people", player);
        sU.clickableInt(plugin.getConfig().getInt("settings.sabotagelength"), "SABOTAGE LENGTH", "seconds", "/amongus setup settings sabotagelength", "Click me to change the length of the sabotages", player);

        player.sendMessage("");
        menuUtility.clickableLink(true, "BACK", "/amongus setup", "Click me to go back!", player);
        menuUtility.clickableLink(false, "QUIT", "/amongus setup leave", "Click me to quit setup mode!", player);

    }
}
