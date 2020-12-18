package me.codingcookie.amongus.gui.tasks;

import me.codingcookie.amongus.AmongUs;
import me.codingcookie.amongus.gui.items.DownloadGUIItems;
import me.codingcookie.amongus.gui.items.UploadGUIItems;
import me.codingcookie.amongus.utility.CrewmateUtil;
import me.codingcookie.amongus.utility.MessagesUtil;
import me.codingcookie.amongus.utility.Singleton;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

public class DownloadGUI {

    private AmongUs plugin;

    public DownloadGUI(AmongUs plugin) {
        this.plugin = plugin;
        invDownloading = Bukkit.createInventory(null, 45, "DOWNLOAD TASK");
    }

    private Inventory invDownloading;
    private CrewmateUtil crewmateUtil;
    private DownloadGUIItems downloadGUIItems;

    public void openDownload(final HumanEntity ent) {
        ent.openInventory(getInvDownloading());
    }

    public Inventory getInvDownloading() {
        return invDownloading;
    }


    public void setPreDownloadGUI(Player player){
        downloadGUIItems = new DownloadGUIItems(plugin);

        if(Singleton.getInstance().getDownloadComplete().contains(player.getName())){
            MessagesUtil.NO_TASK.sendMessage(player);
            return;
        }

        getInvDownloading().setItem(22, downloadGUIItems.makeStartDownload());

        openDownload(player);
    }

    public void setDownloadGUI(Player player){
        downloadGUIItems = new DownloadGUIItems(plugin);
        crewmateUtil = new CrewmateUtil(plugin);
        getInvDownloading().clear();

        getInvDownloading().setItem(25, downloadGUIItems.makeDownloadBook(false, "", ""));
        for(int slot = 20; slot <= 24; slot++){
            getInvDownloading().setItem(slot, downloadGUIItems.makeLoading(false));
        }
        getInvDownloading().setItem(19, downloadGUIItems.makeDownloadBook(true, "", ""));

        openDownload(player);

        for(int loadingSlot = 5; loadingSlot >= 0; loadingSlot--){
            int finalLoadingSlot = loadingSlot;
            // I literally cannot figure out a better way to do this.
            int time = 0;
            if(finalLoadingSlot == 5)
                time = 1;
            if(finalLoadingSlot == 4)
                time = 2;
            if(finalLoadingSlot == 3)
                time = 3;
            if(finalLoadingSlot == 2)
                time = 4;
            if(finalLoadingSlot == 1)
                time = 5;
            if(finalLoadingSlot == 0)
                time = 6;
            new BukkitRunnable() {
                public void run() {
                    if(finalLoadingSlot >= 1) {
                        getInvDownloading().setItem(finalLoadingSlot + 19, downloadGUIItems.makeLoading(true));
                        player.updateInventory();
                    }else{
                        getInvDownloading().clear();
                        fillInventory();
                        getInvDownloading().setItem(22, downloadGUIItems.makeDownloadBook(true, "Click this book to", "complete the task!"));
                        Singleton.getInstance().getDownloadComplete().add(player.getName());
                    }
                }
            }.runTaskLater(plugin, time * 40);
        }
    }

    void fillInventory(){
        downloadGUIItems = new DownloadGUIItems(plugin);
        for(int slots = 0; slots <= 44; slots++){
            getInvDownloading().setItem(slots, downloadGUIItems.makeDownloadPane(Material.GRAY_STAINED_GLASS_PANE, " "));
        }
    }
}
