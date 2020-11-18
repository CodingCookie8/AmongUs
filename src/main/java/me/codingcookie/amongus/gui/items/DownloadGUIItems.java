package me.codingcookie.amongus.gui.items;

import me.codingcookie.amongus.AmongUs;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

import static org.bukkit.ChatColor.*;
import static org.bukkit.ChatColor.BOLD;

public class DownloadGUIItems {

    private final AmongUs plugin;

    private ItemStack startDownload;
    private ItemStack downloadBook;
    private ItemStack loading;

    public DownloadGUIItems(AmongUs plugin) {
        this.plugin = plugin;
        makeStartDownload();
        makeDownloadBook(false, "", "");
        makeLoading(false);
    }

    public ItemStack makeStartDownload() {
        startDownload = new ItemStack(Material.ENCHANTING_TABLE);
        ItemMeta downloadMeta = startDownload.getItemMeta();
        downloadMeta.setDisplayName(GREEN + "" + BOLD + "START DOWNLOAD");
        startDownload.setItemMeta(downloadMeta);
        return startDownload;
    }

    public ItemStack makeDownloadBook(Boolean finished, String lore1, String lore2) {
        if(!finished) {
            downloadBook = new ItemStack(Material.BOOK);
            ItemMeta downloadMeta = downloadBook.getItemMeta();
            downloadMeta.setDisplayName(GRAY + "" + BOLD + "DOWNLOADING...");
            downloadBook.setItemMeta(downloadMeta);
        }else{
            downloadBook = new ItemStack(Material.ENCHANTED_BOOK);
            ItemMeta downloadMeta = downloadBook.getItemMeta();
            downloadMeta.setDisplayName(GREEN + "" + BOLD + "DOWNLOAD COMPLETE!");
            downloadMeta.setLore(Arrays.asList("",
                    WHITE + lore1,
                    WHITE + lore2,
                    ""));
            downloadBook.setItemMeta(downloadMeta);
        }
        return downloadBook;
    }

    public ItemStack makeLoading(Boolean finished) {
        if(!finished) {
            loading = new ItemStack(Material.RED_STAINED_GLASS_PANE);
            ItemMeta downloadMeta = loading.getItemMeta();
            downloadMeta.setDisplayName(GRAY + "" + BOLD + "DOWNLOADING...");
            loading.setItemMeta(downloadMeta);
        }else{
            loading = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
            ItemMeta downloadMeta = loading.getItemMeta();
            downloadMeta.setDisplayName(GREEN + "" + BOLD + "DOWNLOADED!");
            loading.setItemMeta(downloadMeta);
        }
        return loading;
    }
}

