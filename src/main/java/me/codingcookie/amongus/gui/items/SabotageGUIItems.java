package me.codingcookie.amongus.gui.items;

import me.codingcookie.amongus.AmongUs;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

import static org.bukkit.ChatColor.*;

public class SabotageGUIItems {

    private final AmongUs plugin;

    private ItemStack startMeltdown;
    private ItemStack noMeltdown;
    private ItemStack killLights;
    private ItemStack lightsKilled;
    private ItemStack startCleanO2;
    private ItemStack noCleanO2;

    public SabotageGUIItems(AmongUs plugin) {
        this.plugin = plugin;
        makeStartMeltdown();
        makeNoMeltdown();
        makeKillLights();
        makeLightsKilled();
        makeCleanO2();
        makeNoCleanO2();
    }

    public ItemStack makeStartMeltdown() {
        startMeltdown = new ItemStack(Material.LAVA_BUCKET);
        ItemMeta meltdownMeta = startMeltdown.getItemMeta();
        meltdownMeta.setDisplayName(GREEN + "" + BOLD + "START MELTDOWN");
        startMeltdown.setItemMeta(meltdownMeta);
        return startMeltdown;
    }

    public ItemStack makeNoMeltdown() {
        noMeltdown = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta meltdownMeta = noMeltdown.getItemMeta();
        meltdownMeta.setDisplayName(RED + "" + BOLD + "START MELTDOWN");
        meltdownMeta.setLore(Arrays.asList("",
                WHITE + "You've already started",
                WHITE + "the meltdown this round!",
                ""));
        noMeltdown.setItemMeta(meltdownMeta);
        return noMeltdown;
    }

    public ItemStack makeKillLights() {
        killLights = new ItemStack(Material.REDSTONE_LAMP);
        ItemMeta killLightsMeta = killLights.getItemMeta();
        killLightsMeta.setDisplayName(GREEN + "" + BOLD + "KILL LIGHTS");
        killLights.setItemMeta(killLightsMeta);
        return killLights;
    }

    public ItemStack makeLightsKilled() {
        lightsKilled = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta lightsKilledMeta = lightsKilled.getItemMeta();
        lightsKilledMeta.setDisplayName(RED + "" + BOLD + "LIGHTS KILLED");
        lightsKilledMeta.setLore(Arrays.asList("",
                WHITE + "You've already killed the",
                WHITE + "lights this round!",
                "",
                WHITE + "(Or vision is already",
                WHITE + "turned off in the settings)",
                ""));
        lightsKilled.setItemMeta(lightsKilledMeta);
        return lightsKilled;
    }

    public ItemStack makeCleanO2() {
        startCleanO2 = new ItemStack(Material.WATER_BUCKET);
        ItemMeta cleanO2Meta = startCleanO2.getItemMeta();
        cleanO2Meta.setDisplayName(GREEN + "" + BOLD + "KILL OXYGEN");
        startCleanO2.setItemMeta(cleanO2Meta);
        return startCleanO2;
    }

    public ItemStack makeNoCleanO2() {
        noCleanO2 = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta cleanO2Meta = noCleanO2.getItemMeta();
        cleanO2Meta.setDisplayName(RED + "" + BOLD + "KILL OXYGEN");
        cleanO2Meta.setLore(Arrays.asList("",
                WHITE + "You've already killed the",
                WHITE + "oxygen this round!",
                ""));
        noCleanO2.setItemMeta(cleanO2Meta);
        return noCleanO2;
    }
}
