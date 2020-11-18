package me.codingcookie.amongus.listeners;

import me.codingcookie.amongus.AmongUs;
import me.codingcookie.amongus.gui.items.ClearAsteroidGUIItems;
import me.codingcookie.amongus.gui.items.DownloadGUIItems;
import me.codingcookie.amongus.gui.sabotages.SabotageGUI;
import me.codingcookie.amongus.gui.items.SabotageGUIItems;
import me.codingcookie.amongus.gui.tasks.ClearAsteroidGUI;
import me.codingcookie.amongus.gui.tasks.DownloadGUI;
import me.codingcookie.amongus.gui.tasks.UploadGUI;
import me.codingcookie.amongus.gui.items.UploadGUIItems;
import me.codingcookie.amongus.utility.CrewmateUtil;
import me.codingcookie.amongus.utility.ImposterUtil;
import me.codingcookie.amongus.utility.Singleton;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class InventoryClickListener implements Listener {

    private AmongUs plugin;
    private SabotageGUI sabotageGUI;
    private SabotageGUIItems sabotageGUIItems;
    private CrewmateUtil crewmateUtil;
    private UploadGUI uploadGUI;
    private UploadGUIItems uploadGUIItems;
    private DownloadGUI downloadGUI;
    private DownloadGUIItems downloadGUIItems;
    private ClearAsteroidGUI clearAsteroidGUI;
    private ClearAsteroidGUIItems clearAsteroidGUIItems;

    private ImposterUtil iU;

    public InventoryClickListener(AmongUs plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        ItemStack clicked = event.getCurrentItem();
        InventoryView view = player.getOpenInventory();

        crewmateUtil = new CrewmateUtil(plugin);
        uploadGUI = new UploadGUI(plugin);
        uploadGUIItems = new UploadGUIItems(plugin);
        downloadGUI = new DownloadGUI(plugin);
        downloadGUIItems = new DownloadGUIItems(plugin);
        sabotageGUI = new SabotageGUI(plugin);
        sabotageGUIItems = new SabotageGUIItems(plugin);
        clearAsteroidGUI = new ClearAsteroidGUI(plugin);
        clearAsteroidGUIItems = new ClearAsteroidGUIItems(plugin);

        iU = new ImposterUtil(plugin);

        if(view == null){
            return;
        }

        if(!Singleton.getInstance().getAmongUsCurrentlyPlaying().containsKey(player.getName())){
            return;
        }

        if(clicked == null || clicked.getType() == Material.AIR){
            event.setCancelled(true);
            return;
        }

        // TODO: Clean up this page.

        if(view.getTitle().equalsIgnoreCase("SABOTAGE")){
            if(clicked.equals(sabotageGUIItems.makeKillLights())){
                iU.killLights(player);
                event.setCancelled(true);
                player.closeInventory();
            } else if(clicked.equals(sabotageGUIItems.makeCleanO2())){
                iU.startClean02(player);
                event.setCancelled(true);
                player.closeInventory();
            } else if(clicked.equals(sabotageGUIItems.makeStartMeltdown())){
                iU.startMeltdown(player);
                event.setCancelled(true);
                player.closeInventory();
            }else {
                event.setCancelled(true);
                return;
            }
        }

        if(view.getTitle().equalsIgnoreCase("UPLOAD TASK")){
            if(clicked.equals(uploadGUIItems.makeStartUpload())){
                uploadGUI.setUploadGUI(player);
                event.setCancelled(true);
            } else if(clicked.equals(uploadGUIItems.makeUploadBook(true, "Click this book to", "complete the task!"))){
                player.closeInventory();
                Singleton.getInstance().getUploadComplete().add(player.getName());
                taskComplete(player, "UPLOAD");
            } else{
                event.setCancelled(true);
                return;
            }
        }

        if(view.getTitle().equalsIgnoreCase("DOWNLOAD TASK")){
            if(clicked.equals(downloadGUIItems.makeStartDownload())){
                downloadGUI.setDownloadGUI(player);
                event.setCancelled(true);
            } else if(clicked.equals(downloadGUIItems.makeDownloadBook(true, "Click this book to", "complete the task!"))){
                player.closeInventory();
                Singleton.getInstance().getDownloadComplete().add(player.getName());
                taskComplete(player, "DOWNLOAD");
            } else{
                event.setCancelled(true);
                return;
            }
        }

        if(view.getTitle().equalsIgnoreCase("CLEAR ASTEROID TASK")){
            if(clicked.equals(clearAsteroidGUIItems.makeStartClearAsteroid())){
                clearAsteroidGUI.setClearAsteroid(player);
                event.setCancelled(true);
            } else if(clicked.equals(clearAsteroidGUIItems.makeAsteroid())){
                event.setCurrentItem(clearAsteroidGUIItems.makeAsteroidSpace());
                int current = Singleton.getInstance().getClearAsteroidNumber().get(player.getName());
                Singleton.getInstance().getClearAsteroidNumber().put(player.getName(), current + 1);
                event.setCancelled(true);
            } else if(clicked.equals(clearAsteroidGUIItems.makeEndClearAsteroid(true, Singleton.getInstance().getClearAsteroidNumber().get(player.getName())))){
                player.closeInventory();
                Singleton.getInstance().getClearAsteroidComplete().add(player.getName());
                Singleton.getInstance().getClearAsteroidNumber().remove(player.getName());
                event.setCancelled(true);
                taskComplete(player, "CLEAR ASTEROID");
            } else{
                event.setCancelled(true);
                return;
            }
        }
    }

    void taskComplete(Player player, String task){
        crewmateUtil = new CrewmateUtil(plugin);
        if(Singleton.getInstance().getAmongUsCurrentlyPlaying().get(player.getName()).equalsIgnoreCase("crewmate")) {
            crewmateUtil.taskComplete(player, task);
            new BukkitRunnable() {
                public void run() {
                    crewmateUtil.sendTasks(player);
                }
            }.runTaskLater(plugin, 60);
        }
    }
}
