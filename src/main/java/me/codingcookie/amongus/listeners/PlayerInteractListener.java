package me.codingcookie.amongus.listeners;

import me.codingcookie.amongus.AmongUs;
import me.codingcookie.amongus.commands.subcommands.setupmodule.locationmenu.LocationUtility;
import me.codingcookie.amongus.entities.ArmorStandsUtil;
import me.codingcookie.amongus.gui.sabotages.SabotageGUI;
import me.codingcookie.amongus.gui.tasks.ClearAsteroidGUI;
import me.codingcookie.amongus.gui.tasks.DownloadGUI;
import me.codingcookie.amongus.gui.tasks.UploadGUI;
import me.codingcookie.amongus.utility.Singleton;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;

import static org.bukkit.ChatColor.*;

public class PlayerInteractListener implements Listener {

    private AmongUs plugin;
    private LocationUtility lU;
    private ArmorStandsUtil armorStandsUtil;
    private SabotageGUI sabotageGUI;
    private UploadGUI uploadGUI;
    private DownloadGUI downloadGUI;
    private ClearAsteroidGUI clearAsteroidGUI;

    public PlayerInteractListener(AmongUs plugin){
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();

        lU = new LocationUtility(plugin);
        armorStandsUtil = new ArmorStandsUtil(plugin);
        sabotageGUI = new SabotageGUI(plugin);
        uploadGUI = new UploadGUI(plugin);
        downloadGUI = new DownloadGUI(plugin);
        clearAsteroidGUI = new ClearAsteroidGUI(plugin);
        Block clicked = event.getClickedBlock();

        if (Singleton.getInstance().getAmongUsCurrentlyPlaying().containsKey(player.getName())) {
            if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.BELL) {
                sabotageGUI.setSabotageGUI(player);
            }
        }

        if(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK) {

            if(Singleton.getInstance().getAmongUsCurrentlyPlaying().containsKey(player.getName())){
                if (clicked.getType() != Material.STONE_BUTTON) {
                    return;
                }
                HashMap<Location, String> mapTask = new HashMap<>();
                ConfigurationSection locationConfig = plugin.getConfig().getConfigurationSection("location.task");
                for(String key : locationConfig.getKeys(false)){
                    if(key.equalsIgnoreCase("fixwiring")){
                        continue;
                    }
                    String world = locationConfig.getString(key + ".world");
                    double x = locationConfig.getInt(key + ".x");
                    double y = locationConfig.getInt(key + ".y");
                    double z = locationConfig.getInt(key + ".z");
                    Location location = new Location(Bukkit.getWorld(world), x, y, z);

                    mapTask.put(location, key);
                }
                if(!mapTask.containsKey(clicked.getLocation())) {
                    return;
                }
                if(mapTask.get(clicked.getLocation()).equalsIgnoreCase("upload")){
                    uploadGUI.setPreUploadGUI(player);
                }
                if(mapTask.get(clicked.getLocation()).equalsIgnoreCase("download")){
                    downloadGUI.setPreDownloadGUI(player);
                }
                if(mapTask.get(clicked.getLocation()).equalsIgnoreCase("clearasteroid")){
                    clearAsteroidGUI.setPreClearAsteroid(player);
                }
            }

            if (Singleton.getInstance().getWaitingForLocation().containsKey(player.getUniqueId())) {
                if (player.getInventory().getItemInMainHand().getType() != Material.WOODEN_SHOVEL) {
                    return;
                }
                event.setCancelled(true);

                World world = player.getWorld();
                double clickedX = clicked.getX();
                double clickedY = clicked.getY();
                double clickedZ = clicked.getZ();

                Location location = new Location(world, clickedX, clickedY, clickedZ);
                Location taskArmorStandLocation = new Location(world, clickedX, clickedY, clickedZ);

                if (singletonContains(player, "lobby") || singletonContains(player, "emergencymeeting")) {
                    if (singletonContains(player, "button")) {
                        if (clicked.getType() != Material.STONE_BUTTON) {
                            player.sendMessage(RED + "You must click on a button to register this location.");
                            return;
                        }
                        armorStandsUtil.updateArmorStand(player, world, "EMERGENCY MEETING BUTTON", taskArmorStandLocation);
                        setConfig(player, clickedX, clickedY, clickedZ, world.getName());
                        lU.changedLocation(player);
                        return;
                    } else {
                        setConfig(player, clickedX, clickedY + 1, clickedZ, world.getName());
                        lU.changedLocation(player);
                        return;
                    }
                }

                if (singletonContains(player, "task") || singletonContains(player, "sabotage")) {
                    if (clicked.getType() != Material.STONE_BUTTON) {
                        player.sendMessage(RED + "You must click on a button to register this location.");
                        return;
                    }
                    armorStandsUtil.updateArmorStand(player, world, armorStandName(Singleton.getInstance().getWaitingForLocation().get(player.getUniqueId())).toUpperCase(), taskArmorStandLocation);
                    setConfig(player, clickedX, clickedY, clickedZ, world.getName());
                    lU.changedLocation(player);
                    return;
                }

                if (singletonContains(player, "vent")) {
                    if (clicked.getType() != Material.HEAVY_WEIGHTED_PRESSURE_PLATE) {
                        player.sendMessage(RED + "You must click on an iron pressure plate to register this location.");
                        return;
                    }
                    armorStandsUtil.updateArmorStand(player, world, armorStandName(Singleton.getInstance().getWaitingForLocation().get(player.getUniqueId())).toUpperCase(), location);
                    setConfig(player, clickedX, clickedY, clickedZ, world.getName());
                    lU.changedLocation(player);
                    return;
                }
            }
        }
    }

    void setConfig(Player player, double clickedX, double clickedY, double clickedZ, String world){
        plugin.getConfig().set("location." + Singleton.getInstance().getWaitingForLocation().get(player.getUniqueId()) + ".setup", true);
        plugin.getConfig().set("location." + Singleton.getInstance().getWaitingForLocation().get(player.getUniqueId()) + ".x", clickedX);
        plugin.getConfig().set("location." + Singleton.getInstance().getWaitingForLocation().get(player.getUniqueId()) + ".y", clickedY);
        plugin.getConfig().set("location." + Singleton.getInstance().getWaitingForLocation().get(player.getUniqueId()) + ".z", clickedZ);
        plugin.getConfig().set("location." + Singleton.getInstance().getWaitingForLocation().get(player.getUniqueId()) + ".world", world);
        plugin.saveConfig();
    }

    Boolean singletonContains(Player player, String string){
        if(Singleton.getInstance().getWaitingForLocation().get(player.getUniqueId()).contains(string)){
            return true;
        }
        return false;
    }

    String armorStandName(String name) {
        if (name.contains("task")){
            name = name.replace("task.", "");
            return name;
        }
        if (name.contains("sabotage")){
            name = name.replace("sabotage.", "");
            name = name.replace(".", " #");
            return name;
        }
        if (name.contains("vent")){
            name = name.replace(".", " #");
            return name;
        }
        return "";
    }

}
