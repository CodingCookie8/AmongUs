package me.codingcookie.amongus.commands.subcommands.setupmodule;

import me.codingcookie.amongus.AmongUs;
import me.codingcookie.amongus.utility.Singleton;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.*;

public class MenuUtility {

    private AmongUs plugin;

    public MenuUtility(AmongUs plugin){
        this.plugin = plugin;
    }

    private String module = GOLD + "" + STRIKETHROUGH + "   ----------- " + BLUE + "" + BOLD + "[Setup Module]" + GOLD + "" + STRIKETHROUGH + " -----------";
    private String pre = BLUE + "" + BOLD + "[AmongUs] ";

    private CheckSetup checkSetup;

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

    public void leaveSetupMode(Player player){
        if(!Singleton.getInstance().getSetup().contains(player.getUniqueId())){
            player.sendMessage(RED + "You aren't in setup mode! (How did you get here...)");
            return;
        }
        if(Singleton.getInstance().getWaitingForLocation().containsKey(player.getUniqueId())){
            Singleton.getInstance().getWaitingForLocation().remove(player.getUniqueId());
        }
        Singleton.getInstance().getSetup().remove(player.getUniqueId());
        player.setGameMode(Singleton.getInstance().getPreSetupGamemode().get(player.getUniqueId()));
        Singleton.getInstance().getPreSetupGamemode().remove(player.getUniqueId());
        for (int i = 1; i <= 30; i++) {
            player.sendMessage("");
        }

        player.sendMessage(pre + GOLD + "You have left setup mode!");
    }
}
