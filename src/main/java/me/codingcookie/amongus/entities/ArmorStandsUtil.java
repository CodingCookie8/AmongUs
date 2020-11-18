package me.codingcookie.amongus.entities;

import me.codingcookie.amongus.AmongUs;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.*;

public class ArmorStandsUtil {

    private AmongUs plugin;

    public ArmorStandsUtil(AmongUs plugin){
        this.plugin = plugin;
    }

    public void createArmorStand(World world, Location location, String customName){
        location.setY(location.getBlockY());
        location.setX(location.getBlockX() + 0.5D);
        location.setZ(location.getBlockZ() + 0.5D);
        ArmorStand armorstand = (ArmorStand) world.spawnEntity(location, EntityType.ARMOR_STAND);
        armorstand.setVisible(false);
        armorstand.setCustomName(GREEN + "" + BOLD + customName);
        armorstand.setCustomNameVisible(true);
        armorstand.setCollidable(true);
        armorstand.setGravity(false);
        armorstand.setSmall(true);
    }

    public void removeArmorStands(World world){
        world.getEntitiesByClass(ArmorStand.class).forEach(Entity::remove);
    }

    public void updateArmorStand(Player player, World world, String customName, Location location){
        for(Entity entity : player.getWorld().getEntities()){
            if(entity instanceof ArmorStand){
                ArmorStand armorStand = (ArmorStand) entity;
                if(armorStand.getName().equalsIgnoreCase(GREEN + "" + BOLD + customName.toUpperCase())){
                    armorStand.remove();
                    armorStand.eject();
                }
            }
        }
        createArmorStand(world, location, customName);
    }
}
