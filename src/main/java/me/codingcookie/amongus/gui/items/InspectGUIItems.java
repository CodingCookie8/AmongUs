package me.codingcookie.amongus.gui.items;

import me.codingcookie.amongus.AmongUs;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.UUID;

import static org.bukkit.ChatColor.*;

public class InspectGUIItems {

    private final AmongUs plugin;

    private ItemStack inspectBorder;
    private ItemStack inspectHead;
    private ItemStack inspectArmor;
    private ItemStack inspectAnalyzing;
    private ItemStack inspectComplete;

    public InspectGUIItems(AmongUs plugin) {
        this.plugin = plugin;
        makeInspectBorder();
    }

    public ItemStack makeInspectBorder() {
        inspectBorder = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = inspectBorder.getItemMeta();
        meta.setDisplayName(BLACK + " ");
        inspectBorder.setItemMeta(meta);
        return inspectBorder;
    }

    public ItemStack makeInspectHead(UUID targetedOfflinePlayerUUID, String meta) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(targetedOfflinePlayerUUID);

        inspectHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta sm = (SkullMeta) inspectHead.getItemMeta();
        sm.setOwningPlayer(offlinePlayer);
        sm.setDisplayName(GREEN + "" + BOLD + offlinePlayer.getName());
        sm.setLore(Arrays.asList("",
                WHITE + meta,
                WHITE + offlinePlayer.getName() + ".",
                ""));
        inspectHead.setItemMeta(sm);
        return inspectHead;
    }

    public ItemStack makeInspectArmor(Material armorPiece, String name, Color color) {
        inspectArmor = new ItemStack(armorPiece);
        LeatherArmorMeta sm = (LeatherArmorMeta) inspectArmor.getItemMeta();
        sm.setDisplayName(name);
        sm.setColor(Color.fromRGB(color.asRGB()));
        inspectArmor.setItemMeta(sm);
        return inspectArmor;
    }

    public ItemStack makeInspectAnalyzing(Material glassPane, String name) {
        inspectAnalyzing = new ItemStack(glassPane);
        ItemMeta sm = inspectAnalyzing.getItemMeta();
        sm.setDisplayName(name);
        sm.setLore(Arrays.asList(""));
        inspectAnalyzing.setItemMeta(sm);
        return inspectAnalyzing;
    }

    public ItemStack makeInspectComplete(String name, String meta, String meta1) {
        inspectAnalyzing = new ItemStack(Material.COMMAND_BLOCK);
        ItemMeta sm = inspectAnalyzing.getItemMeta();
        sm.setDisplayName(name);
        sm.setLore(Arrays.asList("",
                meta,
                meta1,
                "",
                GRAY + "Click anywhere to close this task.",
                ""));
        inspectAnalyzing.setItemMeta(sm);
        return inspectAnalyzing;
    }
}
