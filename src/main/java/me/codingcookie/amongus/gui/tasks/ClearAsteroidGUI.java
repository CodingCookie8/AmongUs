package me.codingcookie.amongus.gui.tasks;

import me.codingcookie.amongus.AmongUs;
import me.codingcookie.amongus.gui.items.ClearAsteroidGUIItems;
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

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class ClearAsteroidGUI {

    private AmongUs plugin;

    public ClearAsteroidGUI(AmongUs plugin) {
        this.plugin = plugin;
        invClearAsteroid = Bukkit.createInventory(null, 54, "CLEAR ASTEROID TASK");
    }

    private Inventory invClearAsteroid;
    private ClearAsteroidGUIItems items;
    private CrewmateUtil crewmateUtil;

    public void openClearAsteroid(final HumanEntity ent) {
        ent.openInventory(getInvClearAsteroid());
    }

    public Inventory getInvClearAsteroid() {
        return invClearAsteroid;
    }

    public void setPreClearAsteroid(Player player){
        items = new ClearAsteroidGUIItems(plugin);

        if(Singleton.getInstance().getClearAsteroidComplete().contains(player.getName())){
            MessagesUtil.NO_TASK.sendMessage(player);
            return;
        }

        getInvClearAsteroid().setItem(22, items.makeStartClearAsteroid());

        openClearAsteroid(player);
    }

    public void setClearAsteroid(Player player){
        crewmateUtil = new CrewmateUtil(plugin);
        items = new ClearAsteroidGUIItems(plugin);
        getInvClearAsteroid().clear();
        Singleton.getInstance().getClearAsteroidNumber().put(player.getName(), 0);

        for(int slot = 0; slot <= 53; slot++){
            getInvClearAsteroid().setItem(slot, items.makeAsteroidSpace());
        }
        openClearAsteroid(player);

        Random rng = new Random();
        ArrayList<Integer> generated = new ArrayList<Integer>();
        while (generated.size() < 5) {
            Integer next = rng.nextInt(53) + 1;
            generated.add(next);
        }

        setAsteroid(generated.get(0), 0);

        removeAsteroid(generated.get(0), 3 * 20);
        setAsteroid(generated.get(1), 3 * 20);

        removeAsteroid(generated.get(1), 6 * 20);
        setAsteroid(generated.get(2), 6 * 20);

        removeAsteroid(generated.get(2), 9 * 20);
        setAsteroid(generated.get(3), 9 * 20);

        removeAsteroid(generated.get(3), 12 * 20);
        setAsteroid(generated.get(4), 12 * 20);

        removeAsteroid(generated.get(4), 15 * 20);

        new BukkitRunnable(){
            public void run(){
                getInvClearAsteroid().clear();
                if(Singleton.getInstance().getClearAsteroidNumber().get(player.getName()) <= 4){
                    getInvClearAsteroid().setItem(22, items.makeEndClearAsteroid(false, Singleton.getInstance().getClearAsteroidNumber().get(player.getName())));
                }else{
                    fillInventory();
                    getInvClearAsteroid().setItem(22, items.makeEndClearAsteroid(true, Singleton.getInstance().getClearAsteroidNumber().get(player.getName())));
                    Singleton.getInstance().getClearAsteroidComplete().add(player.getName());
                }
            }
        }.runTaskLater(plugin, 15 * 20);
    }

    public void setAsteroid(int slot, int delay){
        items = new ClearAsteroidGUIItems(plugin);
        new BukkitRunnable(){
            public void run() {
                getInvClearAsteroid().setItem(slot, items.makeAsteroid());
            }
        }.runTaskLater(plugin, delay);
    }

    public void removeAsteroid(int slot, int delay){
        items = new ClearAsteroidGUIItems(plugin);
        new BukkitRunnable(){
            public void run() {
                getInvClearAsteroid().setItem(slot, items.makeAsteroidSpace());
            }
        }.runTaskLater(plugin, delay);
    }

    void fillInventory(){
        items = new ClearAsteroidGUIItems(plugin);
        for(int slots = 0; slots <= 53; slots++){
            getInvClearAsteroid().setItem(slots, items.makeAsteroidPane(Material.GRAY_STAINED_GLASS_PANE, " "));
        }
    }
}
