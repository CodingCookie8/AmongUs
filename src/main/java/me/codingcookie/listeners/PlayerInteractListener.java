package me.codingcookie.listeners;

import me.codingcookie.AmongUs;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Attachable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import static org.bukkit.ChatColor.BLUE;
import static org.bukkit.ChatColor.BOLD;

public class PlayerInteractListener implements Listener {

    private AmongUs plugin;

    public PlayerInteractListener(AmongUs plugin){
        this.plugin = plugin;
    }

    private String pre = BLUE + "" + BOLD + "[AmongUs] ";

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();

        Location loc = new Location(Bukkit.getWorld("world"), 1, 4, 1);

        if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
            Block clicked = event.getClickedBlock();
            if(clicked.getType() == Material.STONE_BUTTON && clicked.getLocation().equals(loc)){
                
            }
        }
    }
}
