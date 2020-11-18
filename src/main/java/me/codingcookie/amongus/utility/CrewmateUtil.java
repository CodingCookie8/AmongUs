package me.codingcookie.amongus.utility;

import me.codingcookie.amongus.AmongUs;
import me.codingcookie.amongus.items.CrewmateItems;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static org.bukkit.ChatColor.*;

public class CrewmateUtil {

    private AmongUs plugin;
    private CrewmateItems crewmateItems;

    public CrewmateUtil(AmongUs plugin){
        this.plugin = plugin;
    }

    private String pre = BLUE + "" + BOLD + "[AmongUs] ";
    private String pre2 = DARK_GRAY + "   > ";

    public void setVision(Player player){
        if(!plugin.getConfig().getBoolean("settings.crewmatevision")){
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 1));
            player.sendMessage(BLUE + "" + BOLD + "[AmongUs] " + GOLD + "Your vision is turned off!");
        }
    }

    public void giveItems(Player player){
        crewmateItems = new CrewmateItems(plugin);
        player.getInventory().setItem(8, crewmateItems.makeReportBodyItem());
        player.updateInventory();
    }

    public void sendTasks(Player player){
        for (int i = 1; i <= 10; i++) {
            player.sendMessage("");
        }

        player.sendMessage(GOLD + "Below are your tasks that still need to be completed!");
        player.sendMessage("");

        if(!Singleton.getInstance().getUploadComplete().contains(player.getName()))
            player.sendMessage(pre2 + RED + "" + BOLD + "UPLOAD");
        if(!Singleton.getInstance().getDownloadComplete().contains(player.getName()))
            player.sendMessage(pre2 + RED + "" + BOLD + "DOWNLOAD");
        if(!Singleton.getInstance().getNavigationComplete().contains(player.getName()))
            player.sendMessage(pre2 + RED + "" + BOLD + "NAVIGATION");
        if(!Singleton.getInstance().getClearAsteroidComplete().contains(player.getName()))
            player.sendMessage(pre2 + RED + "" + BOLD + "CLEAR ASTEROID");
        if(!Singleton.getInstance().getSwipeCardComplete().contains(player.getName()))
            player.sendMessage(pre2 + RED + "" + BOLD + "SWIPE CARD");
        if(!Singleton.getInstance().getFuelEnginesComplete().contains(player.getName()))
            player.sendMessage(pre2 + RED + "" + BOLD + "FUEL ENGINES");
        if(!Singleton.getInstance().getTrashChuteComplete().contains(player.getName()))
            player.sendMessage(pre2 + RED + "" + BOLD + "TRASH CHUTE");
        if(!Singleton.getInstance().getInspectComplete().contains(player.getName()))
            player.sendMessage(pre2 + RED + "" + BOLD + "INSPECT");

        if(!Singleton.getInstance().getFixWiringComplete().containsKey(player.getName())) {
            player.sendMessage(pre2 + RED + "" + BOLD + "FIX WIRING");
        }else if(Singleton.getInstance().getFixWiringComplete().get(player.getName()) != 3) {
            player.sendMessage(pre2 + YELLOW + "" + BOLD + "FIX WIRING");
        }

        player.sendMessage("");
    }

    public void taskComplete(Player player, String task){
        for (int i = 1; i <= 30; i++) {
            player.sendMessage("");
        }

        player.sendMessage(pre2 + GREEN + "" + BOLD + task + GOLD + " task complete!");
        player.sendMessage("");
    }
}
