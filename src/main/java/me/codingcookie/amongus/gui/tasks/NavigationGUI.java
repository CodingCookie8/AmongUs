package me.codingcookie.amongus.gui.tasks;

import me.codingcookie.amongus.AmongUs;
import me.codingcookie.amongus.utility.CrewmateUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class NavigationGUI {

    private AmongUs plugin;

    public NavigationGUI(AmongUs plugin) {
        this.plugin = plugin;
        invNavigation = Bukkit.createInventory(null, 45, "NAVIGATION TASK");
    }

    private Inventory invNavigation;
    private CrewmateUtil crewmateUtil;

    public void openNavigation(final HumanEntity ent) {
        ent.openInventory(getInvNavigation());
    }

    public Inventory getInvNavigation() {
        return invNavigation;
    }


    public void setPreNavigation(Player player){
        //getInvClearAsteroid().setItem(22, );

        openNavigation(player);
    }

    public void setNavigation(Player player){
        crewmateUtil = new CrewmateUtil(plugin);
        getInvNavigation().clear();

        openNavigation(player);

    }

}
