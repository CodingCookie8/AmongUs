package me.codingcookie.amongus.listeners;

import me.codingcookie.amongus.AmongUs;
import me.codingcookie.amongus.gui.items.*;
import me.codingcookie.amongus.gui.sabotages.SabotageGUI;
import me.codingcookie.amongus.gui.tasks.*;
import me.codingcookie.amongus.utility.CrewmateUtil;
import me.codingcookie.amongus.utility.ImposterUtil;
import me.codingcookie.amongus.utility.Singleton;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

import static org.bukkit.ChatColor.*;

public class InventoryClickListener implements Listener {

    // TODO: CRUCIAL:
    // ADD DELAYS WHEN OPENING NEW INVENTORIES

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
    private InspectGUI inspectGUI;
    private InspectGUIItems inspectGUIItems;
    private NavigationGUI navigationGUI;
    private NavigationGUIItems navigationGUIItems;

    private ImposterUtil iU;

    public InventoryClickListener(AmongUs plugin){
        this.plugin = plugin;
    }

    private String pre2 = DARK_GRAY + "   > ";

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        ItemStack clicked = event.getCurrentItem();
        InventoryView view = player.getOpenInventory();

        crewmateUtil = new CrewmateUtil(plugin);

        iU = new ImposterUtil(plugin);

        if(view == null){ return; }

        if(clicked == null){
            event.setCancelled(false);
            return;
        }

        if(clicked.getType() == Material.AIR){
            event.setCancelled(false);
            if(!view.getTitle().equalsIgnoreCase("NAVIGATION TASK")) {
                return;
            }
        }

        if(!Singleton.getInstance().getAmongUsCurrentlyPlaying().containsKey(player.getName())){ return; }

        if(view.getTitle().equalsIgnoreCase("SABOTAGE")){
            sabotageGUI = new SabotageGUI(plugin);
            sabotageGUIItems = new SabotageGUIItems(plugin);

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
            uploadGUI = new UploadGUI(plugin);
            uploadGUIItems = new UploadGUIItems(plugin);

            if(clicked.equals(uploadGUIItems.makeStartUpload())){
                uploadGUI.setUploadGUI(player);
                event.setCancelled(true);
            } else if(clicked.equals(uploadGUIItems.makeUploadBook(true, "Click this book to", "complete the task!")) || clicked.getType() == Material.GRAY_STAINED_GLASS_PANE){
                if(Singleton.getInstance().getUploadComplete().contains(player.getName())) {
                    player.closeInventory();
                    taskComplete(player, "UPLOAD");
                }else{
                    event.setCancelled(true);
                }
            } else{
                event.setCancelled(true);
                return;
            }
        }

        if(view.getTitle().equalsIgnoreCase("DOWNLOAD TASK")){
            downloadGUI = new DownloadGUI(plugin);
            downloadGUIItems = new DownloadGUIItems(plugin);

            if(clicked.equals(downloadGUIItems.makeStartDownload())){
                downloadGUI.setDownloadGUI(player);
                event.setCancelled(true);
            } else if(clicked.equals(downloadGUIItems.makeDownloadBook(true, "Click this book to", "complete the task!")) || clicked.getType() == Material.GRAY_STAINED_GLASS_PANE){
                if(Singleton.getInstance().getDownloadComplete().contains(player.getName())) {
                    player.closeInventory();
                    taskComplete(player, "DOWNLOAD");
                }else{
                    event.setCancelled(true);
                }
            } else{
                event.setCancelled(true);
                return;
            }
        }

        if(view.getTitle().equalsIgnoreCase("CLEAR ASTEROID TASK")){
            clearAsteroidGUI = new ClearAsteroidGUI(plugin);
            clearAsteroidGUIItems = new ClearAsteroidGUIItems(plugin);

            if(clicked.equals(clearAsteroidGUIItems.makeStartClearAsteroid())){
                clearAsteroidGUI.setClearAsteroid(player);
                event.setCancelled(true);

            } else if(clicked.equals(clearAsteroidGUIItems.makeAsteroid())){
                event.setCurrentItem(clearAsteroidGUIItems.makeAsteroidSpace());
                int current = ClearAsteroidGUI.asteroidNumber.get(player.getName());
                ClearAsteroidGUI.asteroidNumber.put(player.getName(), current + 1);
                event.setCancelled(true);

            } else if(clicked.equals(clearAsteroidGUIItems.makeEndClearAsteroid(true, ClearAsteroidGUI.asteroidNumber.get(player.getName()))) || clicked.getType() == Material.GRAY_STAINED_GLASS_PANE){
                if(Singleton.getInstance().getClearAsteroidComplete().contains(player.getName())) {
                    player.closeInventory();
                    ClearAsteroidGUI.asteroidNumber.remove(player.getName());
                    event.setCancelled(true);
                    taskComplete(player, "CLEAR ASTEROID");
                }else{
                    event.setCancelled(true);
                }
            } else{
                event.setCancelled(true);
                return;
            }
        }

        if(view.getTitle().equalsIgnoreCase("INSPECT TASK")){
            inspectGUI = new InspectGUI(plugin);
            inspectGUIItems = new InspectGUIItems(plugin);

            if(clicked.getType() == Material.PLAYER_HEAD){
                SkullMeta meta = (SkullMeta) clicked.getItemMeta();
                if(meta == null){
                    return;
                }
                UUID clickedPlayerUUID = meta.getOwningPlayer().getUniqueId();
                inspectGUI.setInspect(player, clickedPlayerUUID);
                event.setCancelled(true);
            }else if(clicked.getType() == Material.COMMAND_BLOCK || clicked.getType() == Material.RED_STAINED_GLASS_PANE || clicked.getType() == Material.GREEN_STAINED_GLASS_PANE){
                if(Singleton.getInstance().getInspectComplete().contains(player.getName())) {
                    player.closeInventory();
                    taskComplete(player, "INSPECT");
                    event.setCancelled(true);
                }else{
                    event.setCancelled(true);
                }
            }else{
                event.setCancelled(true);
                return;
            }
        }

        if(view.getTitle().equalsIgnoreCase("NAVIGATION TASK") || view.getTitle().equalsIgnoreCase("ALIGNING MAPS...")){
            navigationGUI = new NavigationGUI(plugin);
            navigationGUIItems = new NavigationGUIItems(plugin);

            if(clicked.getType() == Material.ENDER_PEARL){
                player.closeInventory();
                new BukkitRunnable() {
                    public void run() {
                        navigationGUI.setNavigation(player);
                    }
                }.runTaskLater(plugin, 1);
                event.setCancelled(true);
            }
            else if(clicked.getType().equals(Material.FILLED_MAP) || clicked.getType().equals(Material.MAP) || event.isShiftClick()) {
                event.setCancelled(false);
            }
            else if(event.getRawSlot() == 13 || event.getRawSlot() == 31){
                view.setItem(event.getSlot(), clicked);
                event.setCancelled(false);
                player.updateInventory();
                new BukkitRunnable() {
                    public void run() {
                        navigationGUI.startAligning(player);
                    }
                }.runTaskLater(plugin, 1);
            }
            else if(clicked.getType() == Material.ENDER_EYE || clicked.getType() == Material.BLACK_STAINED_GLASS_PANE){
                if(Singleton.getInstance().getNavigationComplete().contains(player.getName())){
                    player.closeInventory();
                    taskComplete(player, "NAVIGATION");
                    event.setCancelled(true);
                }else{
                    event.setCancelled(true);
                }
            }else{
                event.setCancelled(true);
            }
        }
    }

    void taskComplete(Player player, String task){
        crewmateUtil = new CrewmateUtil(plugin);
        if(Singleton.getInstance().getAmongUsCurrentlyPlaying().get(player.getName()).equalsIgnoreCase("crewmate")) {
            for (int i = 1; i <= 30; i++) {
                player.sendMessage("");
            }

            player.sendMessage(pre2 + GREEN + "" + BOLD + task + GOLD + " task complete!");
            player.sendMessage("");
            new BukkitRunnable() {
                public void run() {
                    crewmateUtil.sendTasks(player);
                }
            }.runTaskLater(plugin, 60);
        }
    }

}
