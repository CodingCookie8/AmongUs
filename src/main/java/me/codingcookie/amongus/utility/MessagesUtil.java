package me.codingcookie.amongus.utility;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.*;

public enum MessagesUtil {

    IN_GAME(GRAY + "> " + RED + "This command can only be run in game."),
    VISION_OFF(BLUE + "" + BOLD + "[AmongUs] " + GOLD + "Your vision is turned off!"),
    ERROR_1(RED + "[AmongUs] ERROR 1: Something went horribly wrong! Contact the developer as soon as possible.");

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

    public void broadcastMessage(){
        Bukkit.broadcastMessage(message);
    }
}
