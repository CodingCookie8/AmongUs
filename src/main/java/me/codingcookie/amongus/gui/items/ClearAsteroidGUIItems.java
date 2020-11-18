package me.codingcookie.amongus.gui.items;

import me.codingcookie.amongus.AmongUs;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

import static org.bukkit.ChatColor.*;
import static org.bukkit.ChatColor.BOLD;

public class ClearAsteroidGUIItems {

    private final AmongUs plugin;

    private ItemStack startClearAsteroid;
    private ItemStack endClearAsteroid;
    private ItemStack clearAsteroidSpace;
    private ItemStack asteroid;

    public ClearAsteroidGUIItems(AmongUs plugin) {
        this.plugin = plugin;
        makeStartClearAsteroid();
        makeEndClearAsteroid(true, 0);
        makeAsteroidSpace();
        makeAsteroid();
    }

    public ItemStack makeStartClearAsteroid() {
        startClearAsteroid = new ItemStack(Material.COBBLESTONE);
        ItemMeta meta = startClearAsteroid.getItemMeta();
        meta.setDisplayName(GREEN + "" + BOLD + "START CLEAR ASTEROID TASK");
        meta.setLore(Arrays.asList("",
                WHITE + "Click on the cobblestone",
                WHITE + "blocks as they pop up!",
                ""));
        startClearAsteroid.setItemMeta(meta);
        return startClearAsteroid;
    }

    public ItemStack makeEndClearAsteroid(boolean completed, int number) {
        startClearAsteroid = new ItemStack(Material.COBBLESTONE);
        ItemMeta meta = startClearAsteroid.getItemMeta();
        meta.setDisplayName(GREEN + "" + BOLD + "END CLEAR ASTEROID TASK");
        if(completed) {
            meta.setLore(Arrays.asList("",
                    WHITE + "You clicked on: " + GREEN + number + WHITE + " asteroids!",
                    "",
                    GREEN + "" + BOLD + "SUCCESS!",
                    ""));
        }else{
            meta.setLore(Arrays.asList("",
                    WHITE + "You clicked on: " + RED + number + WHITE + " asteroids!",
                    "",
                    RED + "" + BOLD + "FAILED!",
                    ""));
        }
        startClearAsteroid.setItemMeta(meta);
        return startClearAsteroid;
    }

    public ItemStack makeAsteroidSpace() {
        clearAsteroidSpace = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = clearAsteroidSpace.getItemMeta();
        meta.setDisplayName(BLACK + " ");
        clearAsteroidSpace.setItemMeta(meta);
        return clearAsteroidSpace;
    }

    public ItemStack makeAsteroid() {
        asteroid = new ItemStack(Material.COBBLESTONE);
        ItemMeta meta = asteroid.getItemMeta();
        meta.setDisplayName(WHITE + "" + BOLD + "CLICK!");
        asteroid.setItemMeta(meta);
        return asteroid;
    }

}
