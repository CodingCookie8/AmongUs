package me.codingcookie.amongus.gui.tasks;

import me.codingcookie.amongus.AmongUs;
import me.codingcookie.amongus.gui.items.InspectGUIItems;
import me.codingcookie.amongus.utility.CrewmateUtil;
import me.codingcookie.amongus.utility.MessagesUtil;
import me.codingcookie.amongus.utility.Singleton;
import org.bukkit.*;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import static org.bukkit.ChatColor.*;

public class InspectGUI {

    private AmongUs plugin;

    public InspectGUI(AmongUs plugin) {
        this.plugin = plugin;
        invInspect = Bukkit.createInventory(null, 54, "INSPECT TASK");
    }

    private Inventory invInspect;
    private InspectGUIItems items;
    private CrewmateUtil crewmateUtil;

    public void openInspect(final HumanEntity ent) {
        ent.openInventory(getInvInspect());
    }

    public Inventory getInvInspect() {
        return invInspect;
    }

    public void setPreInspect(Player player) {
        crewmateUtil = new CrewmateUtil(plugin);
        items = new InspectGUIItems(plugin);

        if(Singleton.getInstance().getInspectComplete().contains(player.getName())){
            MessagesUtil.NO_TASK.sendMessage(player);
            return;
        }

        getInvInspect().clear();
        setBorder();

        ArrayList<UUID> offlinePlayersUUID = new ArrayList<UUID>();

        for(String playerString : Singleton.getInstance().getAmongUsCurrentlyPlaying().keySet()){
            Player players = Bukkit.getPlayer(playerString);
            if(players == null){
                continue;
            }
            // Instead of adding the player objects to a list and potentially causing a memory leak,
            // I'm getting their UUID and adding that to the list, then making that an object later
            UUID playersUUID = players.getUniqueId();
            offlinePlayersUUID.add(playersUUID);
        }

        for(int slot = 0; slot < offlinePlayersUUID.size(); slot++){
            if(slot <= 6){
                getInvInspect().setItem(slot + 10,items.makeInspectHead(offlinePlayersUUID.get(slot), "Click this head to inspect"));
            }if(slot > 6){
                getInvInspect().setItem(slot + 12,items.makeInspectHead(offlinePlayersUUID.get(slot), "Click this head to inspect"));
            }
        }

        offlinePlayersUUID.clear();

        openInspect(player);
    }

    public void setInspect(Player player, UUID clickedPlayerUUID){
        crewmateUtil = new CrewmateUtil(plugin);
        items = new InspectGUIItems(plugin);
        getInvInspect().clear();
        setBorder();

        Player clickedPlayer = Bukkit.getPlayer(clickedPlayerUUID);
        if(clickedPlayer == null){
            return;
        }

        setHeadPiece(clickedPlayer, clickedPlayerUUID, Material.RED_STAINED_GLASS_PANE, ChatColor.RED);
        setChestArmor(clickedPlayer, Material.RED_STAINED_GLASS_PANE, Color.RED, ChatColor.RED);
        setLeggingArmor(clickedPlayer, Material.RED_STAINED_GLASS_PANE, Color.RED, ChatColor.RED);
        setBootArmor(clickedPlayer, Material.RED_STAINED_GLASS_PANE, Color.RED, ChatColor.RED);

        openInspect(player);

        new BukkitRunnable() {
            int counter = 0;
            public void run() {
                counter++;
                if(counter == 2){
                    setHeadPiece(clickedPlayer, clickedPlayerUUID, Material.YELLOW_STAINED_GLASS_PANE, ChatColor.YELLOW);
                }else if(counter == 4){
                    setChestArmor(clickedPlayer, Material.YELLOW_STAINED_GLASS_PANE, Color.YELLOW, ChatColor.YELLOW);
                }else if(counter == 6){
                    setLeggingArmor(clickedPlayer, Material.YELLOW_STAINED_GLASS_PANE, Color.YELLOW, ChatColor.YELLOW);
                }else if(counter == 8){
                    setBootArmor(clickedPlayer, Material.YELLOW_STAINED_GLASS_PANE, Color.YELLOW, ChatColor.YELLOW);
                }else if(counter == 9){
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 40, 20);

        new BukkitRunnable() {
            int counter = 0;
            public void run() {
                counter++;
                if(counter == 4){
                    setHeadPiece(clickedPlayer, clickedPlayerUUID, Material.GREEN_STAINED_GLASS_PANE, ChatColor.GREEN);
                }else if(counter == 6){
                    setChestArmor(clickedPlayer, Material.GREEN_STAINED_GLASS_PANE, Color.GREEN, ChatColor.GREEN);
                }else if(counter == 8){
                    setLeggingArmor(clickedPlayer, Material.GREEN_STAINED_GLASS_PANE, Color.GREEN, ChatColor.GREEN);
                }else if(counter == 10){
                    setBootArmor(clickedPlayer, Material.GREEN_STAINED_GLASS_PANE, Color.GREEN, ChatColor.GREEN);
                }else if(counter == 11){
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 140, 20);

        new BukkitRunnable(){
            public void run(){
                Singleton.getInstance().getInspectComplete().add(player.getName());
                if(Singleton.getInstance().getAmongUsCurrentlyPlaying().get(clickedPlayer.getName()).equalsIgnoreCase("imposter")){
                    randomFinish(80, clickedPlayer);
                }
                if(Singleton.getInstance().getAmongUsCurrentlyPlaying().get(clickedPlayer.getName()).equalsIgnoreCase("crewmate")) {
                    randomFinish(20, clickedPlayer);
                }
            }
        }.runTaskLater(plugin, 365);
    }

    void setHeadPiece(Player clickedPlayer, UUID clickedPlayerUUID, Material headStainedGlass, ChatColor headChat){
        items = new InspectGUIItems(plugin);
        getInvInspect().setItem(12, items.makeInspectAnalyzing(headStainedGlass, headChat + "Inspecting player: " + clickedPlayer.getName()));
        getInvInspect().setItem(13, items.makeInspectHead(clickedPlayerUUID, "Inspecting player:"));
        getInvInspect().setItem(14, items.makeInspectAnalyzing(headStainedGlass, headChat + "Inspecting player: " + clickedPlayer.getName()));
    }

    void setChestArmor(Player clickedPlayer, Material chestStainedGlass, Color chestColor, ChatColor chestChat){
        items = new InspectGUIItems(plugin);
        getInvInspect().setItem(21, items.makeInspectAnalyzing(chestStainedGlass, chestChat + "Inspecting player: " + clickedPlayer.getName()));
        getInvInspect().setItem(22, items.makeInspectArmor(Material.LEATHER_CHESTPLATE, chestChat + "Inspecting player: " + clickedPlayer.getName(), chestColor));
        getInvInspect().setItem(23, items.makeInspectAnalyzing(chestStainedGlass, chestChat + "Inspecting player: " + clickedPlayer.getName()));
    }

    void setLeggingArmor(Player clickedPlayer, Material leggingStainedGlass, Color leggingColor, ChatColor leggingChat){
        items = new InspectGUIItems(plugin);
        getInvInspect().setItem(30, items.makeInspectAnalyzing(leggingStainedGlass, leggingChat + "Inspecting player: " + clickedPlayer.getName()));
        getInvInspect().setItem(31, items.makeInspectArmor(Material.LEATHER_LEGGINGS, leggingChat + "Inspecting player: " + clickedPlayer.getName(), leggingColor));
        getInvInspect().setItem(32, items.makeInspectAnalyzing(leggingStainedGlass, leggingChat + "Inspecting player: " + clickedPlayer.getName()));
    }

    void setBootArmor(Player clickedPlayer, Material bootStainedGlass, Color bootColor, ChatColor bootChat){
        items = new InspectGUIItems(plugin);
        getInvInspect().setItem(39, items.makeInspectAnalyzing(bootStainedGlass, bootChat + "Inspecting player: " + clickedPlayer.getName()));
        getInvInspect().setItem(40, items.makeInspectArmor(Material.LEATHER_BOOTS, bootChat + "Inspecting player: " + clickedPlayer.getName(), bootColor));
        getInvInspect().setItem(41, items.makeInspectAnalyzing(bootStainedGlass, bootChat + "Inspecting player: " + clickedPlayer.getName()));
    }

    public void setBorder(){
        items = new InspectGUIItems(plugin);
        int[] placeHolders = {0,1,2,3,4,5,6,7,8,9,18,27,36,45,17,26,35,44,45,46,47,48,49,50,51,52,53};
        for(int i : placeHolders) {
            getInvInspect().setItem(i, items.makeInspectBorder());
        }
    }

    void fillInventory(Material glassPane){
        items = new InspectGUIItems(plugin);
        for(int slots = 0; slots <= 53; slots++){
            getInvInspect().setItem(slots, items.makeInspectAnalyzing(glassPane, " "));
        }
    }

    void randomFinish(int i, Player clickedPlayer){
        items = new InspectGUIItems(plugin);
        Random rand = new Random();
        int randomInteger = rand.nextInt(100);

        if(randomInteger < i){
            fillInventory(Material.RED_STAINED_GLASS_PANE);
            getInvInspect().setItem(22, items.makeInspectComplete(RED + "" + BOLD + "IMPOSTER!", RED + clickedPlayer.getName() + " might be an imposter,", RED + "but this machine tends to be faulty."));
        }else{
            fillInventory(Material.GREEN_STAINED_GLASS_PANE);
            getInvInspect().setItem(22, items.makeInspectComplete(GREEN + "" + BOLD + "CREWMATE!", GREEN + clickedPlayer.getName() + " is a crewmate,", GREEN + "but this machine tends to be faulty."));
        }
    }
}
