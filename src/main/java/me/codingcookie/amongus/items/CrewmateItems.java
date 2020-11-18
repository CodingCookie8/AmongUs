package me.codingcookie.amongus.items;

import me.codingcookie.amongus.AmongUs;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static org.bukkit.ChatColor.*;

public class CrewmateItems {

    private final AmongUs plugin;

    private ItemStack reportBodyItem;

    public CrewmateItems(AmongUs plugin) {
        this.plugin = plugin;
        makeReportBodyItem();
    }

    public ItemStack makeReportBodyItem() {
        reportBodyItem = new ItemStack(Material.END_CRYSTAL);
        ItemMeta reportMeta = reportBodyItem.getItemMeta();
        reportMeta.setDisplayName(GREEN + "" + BOLD + "REPORT BODY");
        reportBodyItem.setItemMeta(reportMeta);
        return reportBodyItem;
    }
}
