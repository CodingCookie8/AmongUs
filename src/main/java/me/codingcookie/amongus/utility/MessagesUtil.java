package me.codingcookie.amongus.utility;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.*;

public enum MessagesUtil {

    IN_GAME(GRAY + "> " + RED + "This command can only be run in game."),
    VISION_OFF(BLUE + "" + BOLD + "[AmongUs] " + GOLD + "Your vision is turned off!"),
    NO_TASK(BLUE + "" + BOLD + "[AmongUs] " + RED + "You've already completed this task!"),
    LIGHTS_OFF(BLUE + "" + BOLD + "[AmongUs] " + GOLD + "The imposter " + RED + "%player%" + GOLD + " has turned off the lights!"),
    ERROR_1(RED + "[AmongUs] ERROR 1: Something went wrong! World is null. Check the wiki for more information."),
    ERROR_2(RED + "[AmongUs] ERROR 2: Something went wrong! Player is null. (Did someone log off? You can probably ignore this)");

    private String message;

    MessagesUtil(String message){
        this.message = message;
    }

    public void sendMessage(Player p){
        p.sendMessage(message);
    }

    public void sendMessage(CommandSender sender){
        sender.sendMessage(message);
    }

    public void sendMessage(Player p, Player p2){
        p.sendMessage(message.replace("%player%", p2.getName()));
    }

    public void broadcastMessage(){
        Bukkit.broadcastMessage(message);
    }
}
