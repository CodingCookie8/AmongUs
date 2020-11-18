package me.codingcookie.amongus.commands.subcommands.setupmodule.settingsmenu;

import me.codingcookie.amongus.AmongUs;
import me.codingcookie.amongus.utility.Singleton;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static org.bukkit.ChatColor.*;
import static org.bukkit.ChatColor.GREEN;

public class SettingsUtility {

    private AmongUs plugin;
    private SettingsMenu settingsMenu;

    public SettingsUtility(AmongUs plugin){
        this.plugin = plugin;
    }

    public String module = GOLD + "" + STRIKETHROUGH + "   ----------- " + BLUE + "" + BOLD + "[Setup Module]" + GOLD + "" + STRIKETHROUGH + " -----------";

    public void waitingForInt(Player player, String setting){
        player.sendMessage("");
        player.sendMessage(GOLD + "" + STRIKETHROUGH + "------------------------------------------ ");
        player.sendMessage(GOLD + "Enter a digit in the chat box below to change the amount.");
        Singleton.getInstance().getWaitingForSettingInt().put(player.getUniqueId(), setting);
    }

    public void changeIntSetting(Player player, String setting, int number){
        settingsMenu = new SettingsMenu(plugin);
        plugin.getConfig().set(setting, number);
        plugin.saveConfig();
        Singleton.getInstance().getWaitingForSettingInt().remove(player.getUniqueId(), setting);
        player.sendMessage("");
        player.sendMessage(GOLD + "" + STRIKETHROUGH + "------------------------------------------ ");
        player.sendMessage(GOLD + "Successfully changed setting.");
        player.sendMessage("");
        new BukkitRunnable() {
            public void run() {
                settingsMenu.settingsMenu(player);
            }
        }.runTaskLater(plugin, 20L);
    }

    public void clickableInt(int seconds, String text, String unit, String runCommand, String hover, Player player){
        TextComponent setting = new TextComponent(GREEN + "              " + BOLD + text + WHITE + ": " + GOLD + seconds + " " + unit);
        setting.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, runCommand));
        setting.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(GOLD + hover)));
        player.spigot().sendMessage(setting);
    }

    public void changeBooleanSetting(String setting){
        if(plugin.getConfig().getBoolean(setting)){
            plugin.getConfig().set(setting, false);
        }else if(!plugin.getConfig().getBoolean(setting)){
            plugin.getConfig().set(setting, true);
        }
        plugin.saveConfig();
    }

    public void clickableBooleanSetting(Boolean enabled, String text, String runCommand, String hover, Player player){
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
