package me.codingcookie.amongus.listeners;

import me.codingcookie.amongus.AmongUs;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {

    private AmongUs plugin;

    public DamageListener(AmongUs plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event){
        if(event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            event.setCancelled(true);
            player.setHealth(20);
        }
    }
}
