package me.codingcookie.amongus.gui.items;

import me.codingcookie.amongus.AmongUs;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

import static org.bukkit.ChatColor.*;

public class UploadGUIItems {

    private final AmongUs plugin;

    private ItemStack startUpload;
    private ItemStack uploadBook;
    private ItemStack loading;

    public UploadGUIItems(AmongUs plugin) {
        this.plugin = plugin;
        makeStartUpload();
        makeUploadBook(false, "", "");
        makeLoading(false);
    }

    public ItemStack makeStartUpload() {
        startUpload = new ItemStack(Material.ENCHANTING_TABLE);
        ItemMeta uploadMeta = startUpload.getItemMeta();
        uploadMeta.setDisplayName(GREEN + "" + BOLD + "START UPLOAD");
        startUpload.setItemMeta(uploadMeta);
        return startUpload;
    }

    public ItemStack makeUploadBook(Boolean finished, String lore1, String lore2) {
        if(!finished) {
            uploadBook = new ItemStack(Material.BOOK);
            ItemMeta uploadMeta = uploadBook.getItemMeta();
            uploadMeta.setDisplayName(GRAY + "" + BOLD + "UPLOADING...");
            uploadBook.setItemMeta(uploadMeta);
        }else{
            uploadBook = new ItemStack(Material.ENCHANTED_BOOK);
            ItemMeta uploadMeta = uploadBook.getItemMeta();
            uploadMeta.setDisplayName(GREEN + "" + BOLD + "UPLOAD COMPLETE!");
            uploadMeta.setLore(Arrays.asList("",
                    WHITE + lore1,
                    WHITE + lore2,
                    ""));
            uploadBook.setItemMeta(uploadMeta);
        }
        return uploadBook;
    }

    public ItemStack makeLoading(Boolean finished) {
        if(!finished) {
            loading = new ItemStack(Material.RED_STAINED_GLASS_PANE);
            ItemMeta uploadMeta = loading.getItemMeta();
            uploadMeta.setDisplayName(GRAY + "" + BOLD + "UPLOADING...");
            loading.setItemMeta(uploadMeta);
        }else{
            loading = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
            ItemMeta uploadMeta = loading.getItemMeta();
            uploadMeta.setDisplayName(GREEN + "" + BOLD + "UPLOADED!");
            loading.setItemMeta(uploadMeta);
        }
        return loading;
    }

}
