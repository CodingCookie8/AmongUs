package me.codingcookie.commands.subcommands.setupmodule;

import me.codingcookie.AmongUs;
import me.codingcookie.files.messages.GetMessagesFile;
import me.codingcookie.permissions.PermissionHandler;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.*;
import static org.bukkit.ChatColor.GOLD;

public class SettingsMenu {

    private AmongUs plugin;
    private PermissionHandler pH;
    private GetMessagesFile messagesFile;
    private MainMenu mainMenu;
    private CheckSetup checkSetup;

    public SettingsMenu(AmongUs plugin){
        this.plugin = plugin;
    }

    private String module = GOLD + "" + STRIKETHROUGH + "   ----------- " + BLUE + "" + BOLD + "[Setup Module]" + GOLD + "" + STRIKETHROUGH + " -----------";
    private String pre = BLUE + "" + BOLD + "[AmongUs] ";
    private String pre2 = DARK_GRAY + "> ";

    public void settingsMenu(Player player) {
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
        player.sendMessage(GOLD + "Below are all the settings you can change. Click on one to change it.");
        player.sendMessage("");

        clickableBooleanSetting(plugin.getConfig().getBoolean("settings.crewmatevision"), "CREWMATE VISION", "/amongus setup settings crewmatevision", "Click to enable/disable crewmate vision", player);
        clickableBooleanSetting(plugin.getConfig().getBoolean("settings.impostervision"), "IMPOSTER VISION", "/amongus setup settings impostervision", "Click to enable/disable imposter vision", player);

        player.sendMessage("");
        mainMenu.clickableLink(true, "BACK", "/amongus setup", "Click me to go back!", player);
    }

    public void changeBooleanSetting(String setting){
        if(plugin.getConfig().getBoolean(setting)){
            plugin.getConfig().set(setting, false);
        }else if(!plugin.getConfig().getBoolean(setting)){
            plugin.getConfig().set(setting, true);
        }
        plugin.saveConfig();
    }

    void clickableInt(int seconds, String text, String runCommand, String hover, Player player){
        TextComponent setting = new TextComponent(GREEN + "              " + BOLD + text + WHITE + ": " + GOLD + seconds + " seconds");
        setting.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, runCommand));
        setting.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(GOLD + hover)));
        player.spigot().sendMessage(setting);
    }

    void clickableBooleanSetting(Boolean enabled, String text, String runCommand, String hover, Player player){
        if(!enabled) {
            TextComponent setting = new TextComponent(RED + "              " + BOLD + text + WHITE + ": " + RED + "FALSE");
            setting.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, runCommand));
            setting.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(RED + hover)));
            player.spigot().sendMessage(setting);
        }else if(enabled){
            TextComponent setting = new TextComponent(GREEN + "              " + BOLD + text + WHITE + ": " + GREEN + "TRUE");
            setting.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, runCommand));
            setting.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(GREEN + hover)));
            player.spigot().sendMessage(setting);
        }
    }
}
