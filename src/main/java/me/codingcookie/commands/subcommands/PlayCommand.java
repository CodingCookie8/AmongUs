package me.codingcookie.commands.subcommands;

import me.codingcookie.AmongUs;
import me.codingcookie.files.messages.GetMessagesFile;
import me.codingcookie.permissions.PermissionHandler;
import me.codingcookie.utility.Singleton;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

import static org.bukkit.ChatColor.*;

public class PlayCommand {

    private AmongUs plugin;
    private PermissionHandler pH;
    private GetMessagesFile messagesFile;

    public PlayCommand(AmongUs plugin){
        this.plugin = plugin;
    }

    private String pre = BLUE + "" + BOLD + "[AmongUs] ";
    private String pre2 = DARK_GRAY + "> ";

    public void playCommand(Player player){
        pH = new PermissionHandler();
        messagesFile = new GetMessagesFile();

        Boolean lobbySetup = plugin.getConfig().getBoolean("location.lobby.setup");
        Integer lobbyX = plugin.getConfig().getInt("location.lobby.x");
        Integer lobbyY = plugin.getConfig().getInt("location.lobby.y");
        Integer lobbyZ = plugin.getConfig().getInt("location.lobby.z");
        String lobbyWorld = plugin.getConfig().getString("location.lobby.world");

        if(!(pH.checkPermission(player, "amongus.play"))){
            messagesFile.checkAndGetPermissionsMsg(player);
            return;
        }

        if(Singleton.getInstance().getAmongUsPlayers().contains(player.getUniqueId())){
            player.sendMessage(pre + RED + "You are already playing Among Us!");
            return;
        }

        if(!(lobbySetup)){
            player.sendMessage(pre + RED + "A lobby location has not been set!");
            if(pH.checkPermission(player, "amongus.setup")) {
                player.sendMessage(pre2 + RED + "Type " + GRAY + "/amongus setup" + RED + " to setup the game.");
            }else{
                player.sendMessage(pre2 + RED + "Contact an Administrator to setup the game.");
            }
            return;
        }

        if(Singleton.getInstance().getAmongUsPlayers().size() >= 10){
            player.sendMessage(pre + RED + "The Among Us game is currently full!");
            return;
        }

        player.teleport(new Location(Bukkit.getWorld(lobbyWorld), lobbyX, lobbyY, lobbyZ));
        Singleton.getInstance().getAmongUsPlayers().add(player.getUniqueId());
        Bukkit.getWorld(lobbyWorld).getPlayers().forEach(worldPlayer -> worldPlayer.sendMessage(
                pre + GREEN + player.getName() + GRAY + " joined the game! (" + Singleton.getInstance().getAmongUsPlayers().size() + "/10)"));

        if(Singleton.getInstance().getAmongUsPlayers().size() < 4){
            Bukkit.getWorld(lobbyWorld).getPlayers().forEach(worldPlayer -> worldPlayer.sendMessage(pre + RED + "4 players are required for the game to start."));
        }
    }
}
