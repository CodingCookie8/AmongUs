package me.codingcookie.amongus.listeners;

import me.codingcookie.amongus.AmongUs;
import me.codingcookie.amongus.utility.Singleton;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevelChangeListener implements Listener {

    private AmongUs plugin;

    public FoodLevelChangeListener(AmongUs plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent event){
        Player player = (Player) event.getEntity();

        if(Singleton.getInstance().getAmongUsCurrentlyPlaying().containsKey(player.getName())){
            event.setFoodLevel(20);
            event.setCancelled(true);
        }
    }
}
