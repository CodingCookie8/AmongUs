package me.codingcookie.amongus.permissions;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PermissionHandler {

    public boolean checkPermission(Player p, String permission){
        if(p.hasPermission(permission)){
            return true;
        }
        return false;
    }

    public boolean checkPermission(CommandSender sender, String permission){
        if(sender.hasPermission(permission)){
            return true;
        }
        return false;
    }

}
