package me.codingcookie.commands.subcommands.setupmodule;

import me.codingcookie.AmongUs;
import me.codingcookie.files.messages.GetMessagesFile;
import me.codingcookie.permissions.PermissionHandler;
import me.codingcookie.utility.Singleton;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.*;

public class MainMenu {

    private AmongUs plugin;
    private PermissionHandler pH;
    private GetMessagesFile messagesFile;

    public MainMenu(AmongUs plugin){
        this.plugin = plugin;
    }

    private String module = GOLD + "" + STRIKETHROUGH + "   ----------- " + BLUE + "" + BOLD + "[Setup Module]" + GOLD + "" + STRIKETHROUGH + " -----------";
    private String pre = BLUE + "" + BOLD + "[AmongUs] ";
    private String pre2 = DARK_GRAY + "> ";

    private CheckSetup checkSetup;

    public void sendMainMenu(Player player){
        checkSetup = new CheckSetup(plugin);

        player.sendMessage("");
        player.sendMessage("");
        player.sendMessage("");
        player.sendMessage("");
        player.sendMessage("");

        player.sendMessage(module);
        player.sendMessage(GOLD + "Below are all the settings you can change. Click one of them to get started.");

        player.sendMessage(GOLD + "");
        player.sendMessage(GOLD + "To undo anything, type the word " + RED + "undo" + GOLD + " into chat. You can also type " + RED + "reset" + GOLD + " to start from the beginning.");
        player.sendMessage(GOLD + "Everything you do is saved, so feel free to leave or close Minecraft if need be. You can also type " + RED + "end" + GOLD + " to leave Setup mode.");

        player.sendMessage("");
        clickableLink(checkSetup.checkLocations(),"LOCATIONS", "/amongus setup locations", "Some locations haven't been set, click to setup", player);
        clickableLink(true,"SETTINGS", "/amongus setup settings", "Click me to change settings", player);

        player.sendMessage("");

    }

    public void clickableLink(Boolean setup, String text, String runCommand, String hover, Player player){
        if(!setup) {
            TextComponent location = new TextComponent(RED + "              " + BOLD + text);
            location.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, runCommand));
            location.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(RED + hover)));
            player.spigot().sendMessage(location);
        }if(setup){
            TextComponent location = new TextComponent(GREEN + "              " + BOLD + text);
            location.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, runCommand));
            location.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(GREEN + hover)));
            player.spigot().sendMessage(location);
        }
    }
}
