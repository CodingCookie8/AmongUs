package me.codingcookie.amongus.gui.tasks;

import me.codingcookie.amongus.AmongUs;
import me.codingcookie.amongus.gui.items.UploadGUIItems;
import me.codingcookie.amongus.utility.CrewmateUtil;
import me.codingcookie.amongus.utility.Singleton;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

public class UploadGUI {

    private AmongUs plugin;

    public UploadGUI(AmongUs plugin) {
        this.plugin = plugin;
        invUploading = Bukkit.createInventory(null, 45, "UPLOAD TASK");
    }

    private Inventory invUploading;
    private CrewmateUtil crewmateUtil;
    private UploadGUIItems uploadGUIItems;

    public void openUpload(final HumanEntity ent) {
        ent.openInventory(getInvUploading());
    }

    public Inventory getInvUploading() {
        return invUploading;
    }


    public void setPreUploadGUI(Player player){
        uploadGUIItems = new UploadGUIItems(plugin);

        getInvUploading().setItem(22, uploadGUIItems.makeStartUpload());

        openUpload(player);
    }

    public void setUploadGUI(Player player){
        uploadGUIItems = new UploadGUIItems(plugin);
        crewmateUtil = new CrewmateUtil(plugin);
        getInvUploading().clear();

        getInvUploading().setItem(19, uploadGUIItems.makeUploadBook(false, "", ""));
        for(int slot = 20; slot <= 24; slot++){
            getInvUploading().setItem(slot, uploadGUIItems.makeLoading(false));
        }
        getInvUploading().setItem(25, uploadGUIItems.makeUploadBook(true, "", ""));

        openUpload(player);

        for(int loadingSlot = 0; loadingSlot <= 5; loadingSlot++){
            int finalLoadingSlot = loadingSlot + 1;
            new BukkitRunnable() {
                public void run() {
                    if(finalLoadingSlot < 6) {
                        getInvUploading().setItem(finalLoadingSlot + 19, uploadGUIItems.makeLoading(true));
                        player.updateInventory();
                    }else{
                        getInvUploading().clear();
                        getInvUploading().setItem(22, uploadGUIItems.makeUploadBook(true, "Click this book to", "complete the task!"));
                    }
                }
            }.runTaskLater(plugin, finalLoadingSlot * 40);
        }
    }

}
