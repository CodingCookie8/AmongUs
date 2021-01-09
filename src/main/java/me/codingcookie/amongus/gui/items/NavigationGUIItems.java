package me.codingcookie.amongus.gui.items;

import me.codingcookie.amongus.AmongUs;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import java.util.Arrays;

import static org.bukkit.ChatColor.*;

public class NavigationGUIItems {

    private final AmongUs plugin;

    private ItemStack navigationItems;
    private ItemStack map;
    private ItemStack emptyMap;
    private ItemStack glassPane;
    private ItemStack air;

    public NavigationGUIItems(AmongUs plugin) {
        this.plugin = plugin;
    }

    public ItemStack makeNavigationItems(Material material, String displayName, String lore, String lore2, String lore3, String lore4) {
        navigationItems = new ItemStack(material);
        ItemMeta meta = navigationItems.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(Arrays.asList("", lore, lore2, lore3, lore4, "", GRAY + "(Click anywhere to continue)", ""));
        navigationItems.setItemMeta(meta);
        return navigationItems;
    }

    public ItemStack makeEmptyMap(String displayName){
        emptyMap = new ItemStack(Material.MAP);
        ItemMeta meta = emptyMap.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(Arrays.asList(""));
        emptyMap.setItemMeta(meta);
        return emptyMap;
    }

    public ItemStack makeMap(String displayName){
        map = new ItemStack(Material.FILLED_MAP, 1);
        MapMeta meta = (MapMeta) map.getItemMeta();
        meta.setDisplayName(displayName);
        map.setItemMeta(meta);
        return map;

    }

    public ItemStack makeAir(){
        air = new ItemStack(Material.AIR);
        return air;
    }

    public ItemStack makeGlassPane(Material glassPane1){
        glassPane = new ItemStack(glassPane1);
        ItemMeta meta = glassPane.getItemMeta();
        meta.setDisplayName(" ");
        meta.setLore(Arrays.asList(""));
        glassPane.setItemMeta(meta);
        return glassPane;
    }

}
