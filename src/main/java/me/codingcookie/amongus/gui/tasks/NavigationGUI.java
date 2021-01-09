package me.codingcookie.amongus.gui.tasks;

import me.codingcookie.amongus.AmongUs;
import me.codingcookie.amongus.gui.items.NavigationGUIItems;
import me.codingcookie.amongus.utility.CrewmateUtil;
import me.codingcookie.amongus.utility.Singleton;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

import static org.bukkit.ChatColor.*;

public class NavigationGUI {

    private AmongUs plugin;

    public NavigationGUI(AmongUs plugin) {
        this.plugin = plugin;
        invNavigation = Bukkit.createInventory(null, 45, "NAVIGATION TASK");
    }

    private Inventory invNavigation;
    private NavigationGUIItems items;
    private CrewmateUtil crewmateUtil;

    public void openNavigation(final HumanEntity ent){
        ent.openInventory(getInvNavigation());
    }
    public void openNewNav(final HumanEntity ent, Inventory inv){
        ent.openInventory(inv);
    }

    public Inventory getInvNavigation(){
        return invNavigation;
    }

    public void setPreNavigation(Player player){
        items = new NavigationGUIItems(plugin);
        getInvNavigation().setItem(22, items.makeNavigationItems(Material.ENDER_PEARL,
                GREEN + "" + BOLD + "START NAVIGATION TASK",
                GRAY + "Match up the filled map with the",
                GRAY + "empty map in the empty slots.",
                GRAY + "It may take a few seconds to",
                GRAY + "align the two maps."));

        openNavigation(player);
    }

    public void setNavigation(Player player) {
        crewmateUtil = new CrewmateUtil(plugin);
        items = new NavigationGUIItems(plugin);
        getInvNavigation().clear();

        player.getInventory().setItem(21, items.makeMap("NAVIGATION"));
        player.getInventory().setItem(23, items.makeEmptyMap("DESTINATION"));

        fillInventory(getInvNavigation());
        getInvNavigation().setItem(13, items.makeAir());
        getInvNavigation().setItem(31, items.makeAir());

        for(int i = 20; i <= 24; i++) {
            getInvNavigation().setItem(i, items.makeGlassPane(Material.WHITE_STAINED_GLASS_PANE));
        }

        openNavigation(player);

        new BukkitRunnable(){
            public void run() {
                if(getInvNavigation().contains(Material.FILLED_MAP) && getInvNavigation().contains(Material.MAP)) {
                    startAligning(player);
                }else{
                    player.closeInventory();
                    crewmateUtil.failedTask(player, "NAVIGATION");
                }
            }
        }.runTaskLater(plugin, 10 * 20);
    }

    public void startAligning(Player player){
        items = new NavigationGUIItems(plugin);
        if(getInvNavigation().contains(Material.FILLED_MAP) && getInvNavigation().contains(Material.MAP)) {
            player.closeInventory();

            new BukkitRunnable(){
                public void run() {
                    Inventory aligningInv = Bukkit.createInventory(null, 45, "ALIGNING MAPS...");
                    aligningInv.setContents(getInvNavigation().getContents());
                    openNewNav(player, aligningInv);

                    for (int i = 1; i <= 6; i++) {
                        final int i2 = i;
                        new BukkitRunnable() {
                            public void run() {
                                if (i2 <= 5) {
                                    aligningInv.setItem(i2 + 19, items.makeGlassPane(Material.GREEN_STAINED_GLASS_PANE));
                                } else {
                                    setPostNavigation(player);
                                }
                            }
                        }.runTaskLater(plugin, i * 20);
                    }
                }
            }.runTaskLater(plugin, 1);
        }
    }

    public void setPostNavigation(Player player){
        items = new NavigationGUIItems(plugin);
        getInvNavigation().clear();

        fillInventory(getInvNavigation());
        Singleton.getInstance().getNavigationComplete().add(player.getName());
        getInvNavigation().setItem(22, items.makeNavigationItems(Material.ENDER_EYE,
                GREEN + "" + BOLD + "NAVIGATION TASK COMPLETE",
                GRAY + "You've steered the ship",
                GRAY + "towards the correct direction.",
                GRAY + "Click anywhere to end",
                GRAY + "the task and move on."));

        openNavigation(player);
    }

    void fillInventory(Inventory inventory){
        items = new NavigationGUIItems(plugin);
        for(int slots = 0; slots <= 44; slots++){
            inventory.setItem(slots, items.makeGlassPane(Material.BLACK_STAINED_GLASS_PANE));
        }
    }

}
