package me.codingcookie.amongus.gui.sabotages;

import me.codingcookie.amongus.AmongUs;
import me.codingcookie.amongus.gui.items.SabotageGUIItems;
import me.codingcookie.amongus.utility.Singleton;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class SabotageGUI {

    private AmongUs plugin;

    public SabotageGUI(AmongUs plugin) {
        this.plugin = plugin;
        invSabotage = Bukkit.createInventory(null, 27, "SABOTAGE");
    }

    private Inventory invSabotage;
    private SabotageGUIItems sabotageGUIItems;

    public void openSabotageGUI(final HumanEntity ent) {
        ent.openInventory(getInvSabotage());
    }

    public Inventory getInvSabotage() {
        return invSabotage;
    }

    public void setSabotageGUI(Player player){
        sabotageGUIItems = new SabotageGUIItems(plugin);

        if(!Singleton.getInstance().getStartedMeltdown().get(player.getName())){
            getInvSabotage().setItem(11, sabotageGUIItems.makeStartMeltdown());
        }else{
            getInvSabotage().setItem(11, sabotageGUIItems.makeNoMeltdown());
        }

        if(!Singleton.getInstance().getLightsKilled().get(player.getName())) {
            getInvSabotage().setItem(13, sabotageGUIItems.makeKillLights());
        }else{
            getInvSabotage().setItem(13, sabotageGUIItems.makeLightsKilled());
        }

        if(!Singleton.getInstance().getStartedO2().get(player.getName())){
            getInvSabotage().setItem(15, sabotageGUIItems.makeCleanO2());
        }else{
            getInvSabotage().setItem(15, sabotageGUIItems.makeNoCleanO2());
        }

        openSabotageGUI(player);
    }
}
