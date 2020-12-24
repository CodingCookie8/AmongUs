package me.codingcookie.amongus.listeners;

import me.codingcookie.amongus.AmongUs;
import me.codingcookie.amongus.utility.Singleton;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.spigotmc.event.entity.EntityDismountEvent;

public class VehicleExitListener implements Listener {

    private AmongUs plugin;

    public VehicleExitListener(AmongUs plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onExitVehicle(EntityDismountEvent event){

        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();

        if (event.getDismounted() instanceof ArmorStand) {
            ArmorStand armorStand = (ArmorStand) event.getDismounted();
            if(Singleton.getInstance().getEmerMeetingList().contains(player.getName())){
                armorStand.addPassenger(player);
                event.setCancelled(true);
            }
        }
    }
}
