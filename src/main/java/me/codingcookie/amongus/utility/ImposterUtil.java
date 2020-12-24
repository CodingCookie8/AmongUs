package me.codingcookie.amongus.utility;

import me.codingcookie.amongus.AmongUs;
import me.codingcookie.amongus.items.ImposterItems;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import static org.bukkit.ChatColor.*;

public class ImposterUtil {

    private AmongUs plugin;
    private ImposterItems imposterItems;
    private GameUtil gU;
    private WorldBorder worldBorder;
    Boolean meltdownStarted = false;
    Boolean o2Started = false;
    Boolean lightsKilled = false;

    public ImposterUtil(AmongUs plugin){
        this.plugin = plugin;
    }

    String pre = BLUE + "" + BOLD + "[AmongUs] ";

    public void setSabotages(Player player){
        Singleton.getInstance().getLightsKilled().clear();
        Singleton.getInstance().getLightsKilled().put(player.getName(), false);

        Singleton.getInstance().getStartedMeltdown().clear();
        Singleton.getInstance().getStartedMeltdown().put(player.getName(), false);

        Singleton.getInstance().getStartedO2().clear();
        Singleton.getInstance().getStartedO2().put(player.getName(), false);

        if(!plugin.getConfig().getBoolean("settings.impostervision")){
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 1));
            MessagesUtil.VISION_OFF.sendMessage(player);
            Singleton.getInstance().getLightsKilled().put(player.getName(), true);
        }
    }

    public void killLights(Player player){
        if(meltdownStarted || o2Started){
            player.closeInventory();
            player.sendMessage(pre + RED + "There is another sabotage currently in process. You must wait for all sabotages to be done before starting another one.");
            return;
        }

        Singleton.getInstance().getLightsKilled().put(player.getName(), true);
        lightsKilled = true;

        for(String playerString : Singleton.getInstance().getAmongUsCurrentlyPlaying().keySet()){
            if(Singleton.getInstance().getAmongUsCurrentlyPlaying().get(playerString).equalsIgnoreCase("imposter")){
                Player imposters = Bukkit.getPlayer(playerString);
                if(imposters == null){
                    MessagesUtil.ERROR_2.sendMessage(player);
                    continue;
                }
                MessagesUtil.LIGHTS_OFF.sendMessage(player, imposters);
            }else{
                Player crewmates = Bukkit.getPlayer(playerString);
                if(crewmates == null){
                    MessagesUtil.ERROR_2.sendMessage(player);
                    continue;
                }
                crewmates.sendTitle(RED + "Lights Killed!", GRAY + "Head to the electricity room to turn them back on!", 0, 60, 20);
                crewmates.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 1));
                crewmates.playSound(player.getLocation(), Sound.BLOCK_REDSTONE_TORCH_BURNOUT, 10, 1);
            }
        }
    }

    public void startMeltdown(Player player){
        if(lightsKilled || o2Started){
            player.closeInventory();
            player.sendMessage(pre + RED + "There is another sabotage currently in process. You must wait for all sabotages to be done before starting another one.");
            return;
        }

        meltdownStarted = true;
        Singleton.getInstance().getStartedMeltdown().put(player.getName(), true);

        sabotageBase(player, "MELTDOWN");
    }

    public void startClean02(Player player){
        if(lightsKilled || meltdownStarted){
            player.closeInventory();
            player.sendMessage(pre + RED + "There is another sabotage currently in process. You must wait for all sabotages to be done before starting another one.");
            return;
        }

        o2Started = true;
        Singleton.getInstance().getStartedO2().put(player.getName(), true);

        sabotageBase(player, "CLEAN O2");
    }

    public void sabotageBase(Player player, String sabotage){
        gU = new GameUtil(plugin);
        int sabotageLength = plugin.getConfig().getInt("settings.sabotagelength");

        for(String playerString : Singleton.getInstance().getAmongUsCurrentlyPlaying().keySet()) {
            if (Singleton.getInstance().getAmongUsCurrentlyPlaying().get(playerString).equalsIgnoreCase("imposter")) {
                Player imposters = Bukkit.getPlayer(playerString);
                if (imposters == null) {
                    MessagesUtil.ERROR_2.sendMessage(player);
                    continue;
                }
                imposters.sendMessage(pre + GOLD + "The imposter " + RED + player.getName() + GOLD + " has started the " + sabotage + "!");
                sendCountdown(imposters, sabotage);
            } else {
                Player crewmates = Bukkit.getPlayer(playerString);
                if(crewmates == null){
                    MessagesUtil.ERROR_2.sendMessage(player);
                    continue;
                }
                crewmates.sendTitle(RED + "" + BOLD + sabotage + ": " + GOLD + sabotageLength, GRAY + "Find the " + sabotage + " buttons to save the ship!", 0, 20, 0);
                createVignetteEffect(crewmates);
                sendCountdown(crewmates, sabotage);
            }
        }
    }

    void sendCountdown(Player players, String sabotage){
        int sabotageLength = plugin.getConfig().getInt("settings.sabotagelength");
        gU = new GameUtil(plugin);

        new BukkitRunnable() {
            int sabotageCounter = sabotageLength;
            public void run() {
                sabotageCounter--;
                if(sabotageCounter != 0){
                    players.sendTitle(RED + "" + BOLD + sabotage + ": " + GOLD + sabotageCounter, GRAY + "Find the " + sabotage + " buttons to save the ship!", 0, 21, 0);
                }else{
                    gU.endGame(BLUE, "Victory!", "All the crewmates died from the " + sabotage + " sabotage!", RED, "Defeat!", "The imposters killed all the crewmates!");
                    removeVignetteEffect(players);
                    meltdownStarted = false;
                    o2Started = false;
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 20, 20);
    }

    WorldBorder getWorldBorder(){
        return worldBorder;
    }

    void setWorldBorder(WorldBorder input){
        worldBorder = input;
    }

    void createVignetteEffect(Player crewmates){
        setWorldBorder(Bukkit.getWorld(crewmates.getWorld().getName()).getWorldBorder());
        getWorldBorder().setCenter(Bukkit.getWorld(crewmates.getWorld().getName()).getSpawnLocation());
        getWorldBorder().setWarningDistance(10000);
        getWorldBorder().setSize(10000);
        getWorldBorder().setWarningTime(0);
        getWorldBorder().setDamageAmount(0);
    }

    void removeVignetteEffect(Player crewmates){
        Bukkit.getWorld(crewmates.getWorld().getName()).getWorldBorder().reset();
    }

    public void giveItems(Player player){
        int killCooldown = plugin.getConfig().getInt("settings.killcooldown");
        imposterItems = new ImposterItems(plugin);

        player.getInventory().setItem(8, imposterItems.makeSabotageItem());
        player.updateInventory();

        player.sendMessage("");
        player.sendMessage(pre + GOLD + "You'll receive your sword in " + RED + "" + BOLD + killCooldown + GOLD + " seconds.");

        new BukkitRunnable() {
            public void run() {
                player.getInventory().setItem(1, imposterItems.makeImposterSword());
                player.updateInventory();
            }
        }.runTaskLater(plugin, killCooldown * 20);
    }
}
