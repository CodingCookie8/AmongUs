package me.codingcookie.amongus.utility;

import me.codingcookie.amongus.AmongUs;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

import static org.bukkit.ChatColor.*;

public class GameUtil {

    private AmongUs plugin;
    private ImposterUtil iU;
    private CrewmateUtil cU;

    public GameUtil(AmongUs plugin){
        this.plugin = plugin;
    }

    String pre = BLUE + "" + BOLD + "[AmongUs] ";

    public void startRound(){
        teleportToEmergencyMeeting();
        assignRoles();
        iU = new ImposterUtil(plugin);
        cU = new CrewmateUtil(plugin);

        for(String playerString : Singleton.getInstance().getAmongUsCurrentlyPlaying().keySet()){
            Player player = Bukkit.getPlayer(playerString);
            if(player == null){
                continue;
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 120, 1));
            player.playSound(player.getLocation(), Sound.AMBIENT_NETHER_WASTES_MOOD, 10, 1);
            player.setGameMode(GameMode.ADVENTURE);

            if(Singleton.getInstance().getAmongUsCurrentlyPlaying().get(player.getName()).equalsIgnoreCase("crewmate")) {
                cU.sendTasks(player);
                cU.giveItems(player);
                cU.setVision(player);
            }
            if(Singleton.getInstance().getAmongUsCurrentlyPlaying().get(player.getName()).equalsIgnoreCase("imposter")) {
                iU.giveItems(player);
                iU.setSabotages(player);
            }
        }
    }

    public void emergencyMeeting(){
        // TODO: Emergency meeting
    }

    // Could these two things be the same method? Probably.

    public void reportBody(){
        // TODO: Report body
    }

    public void endGame(ChatColor imposterColor, String imposterTitle, String imposterSubtitle, ChatColor crewmateColor, String crewmateTitle, String crewmateSubtitle){
        for(String playerString : Singleton.getInstance().getAmongUsCurrentlyPlaying().keySet()){
            if(Singleton.getInstance().getAmongUsCurrentlyPlaying().get(playerString).equalsIgnoreCase("imposter")){
                Player imposters = Bukkit.getPlayer(playerString);
                if(imposters == null){
                    continue;
                }
                imposters.sendTitle(imposterColor + "" + BOLD + imposterTitle, GRAY + imposterSubtitle, 20, 120, 20);
                continue;
            }else{
                Player crewmates = Bukkit.getPlayer(playerString);
                if(crewmates == null){
                    continue;
                }
                crewmates.sendTitle(crewmateColor + "" + BOLD + crewmateTitle, GRAY + crewmateSubtitle, 20, 120, 20);
            }
        }

        Integer lobbyX = plugin.getConfig().getInt("location.lobby.x");
        Integer lobbyY = plugin.getConfig().getInt("location.lobby.y");
        Integer lobbyZ = plugin.getConfig().getInt("location.lobby.z");
        String lobbyWorld = plugin.getConfig().getString("location.lobby.world");

        for(String playerString : Singleton.getInstance().getAmongUsCurrentlyPlaying().keySet()) {
            Player player = Bukkit.getPlayer(playerString);
            if (player == null) {
                continue;
            }
            player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 10, 1);
            if(lobbyWorld == null){
                /*
                Error 1: The string world is null. The only way this would be possible is if they
                manually removed it in config.
                 */
                MessagesUtil.ERROR_1.sendMessage(player);
            }
            Location lobby = new Location(Bukkit.getWorld(lobbyWorld), lobbyX, lobbyY, lobbyZ);
            player.teleport(lobby.add(0.5, 0, 0.5));
        }
        /*

        Clearing the ArrayLists

         */
        Singleton.getInstance().getAmongUsCurrentlyPlaying().clear();
        Singleton.getInstance().getStartedO2().clear();
        Singleton.getInstance().getStartedMeltdown().clear();
        Singleton.getInstance().getLightsKilled().clear();
        Singleton.getInstance().getFixWiringComplete().clear();
        Singleton.getInstance().getInspectComplete().clear();
        Singleton.getInstance().getTrashChuteComplete().clear();
        Singleton.getInstance().getFuelEnginesComplete().clear();
        Singleton.getInstance().getSwipeCardComplete().clear();
        Singleton.getInstance().getClearAsteroidComplete().clear();
        Singleton.getInstance().getNavigationComplete().clear();
        Singleton.getInstance().getDownloadComplete().clear();
        Singleton.getInstance().getUploadComplete().clear();
    }

    void assignRoles(){
        if(!Singleton.getInstance().getAmongUsCurrentlyPlaying().isEmpty()){
            return;
        }
        iU = new ImposterUtil(plugin);
        int imposterCount = plugin.getConfig().getInt("settings.imposters");

        for(int i = 1; i <= imposterCount; i++){
            Random random = new Random();

            // Choose random player out of ones playing the game
            String randomPlayer = Singleton.getInstance().getAmongUsPlayers().get(random.nextInt(Singleton.getInstance().getAmongUsPlayers().size()));

            // Check if player has already been assigned imposter role
            if(Singleton.getInstance().getAmongUsCurrentlyPlaying().containsKey(randomPlayer)){
                continue;
            }

            // If they haven't been assigned before, give them the roll of imposter
            Singleton.getInstance().getAmongUsCurrentlyPlaying().put(randomPlayer, "imposter");

            // Convert player to an object
            Player randomPlayerO = Bukkit.getPlayer(randomPlayer);
            if(randomPlayerO != null) {
                roleTitle(randomPlayerO, true);
            }else {
                Singleton.getInstance().getAmongUsCurrentlyPlaying().remove(randomPlayer);
            }
        }

        for (int i = 0; i < Singleton.getInstance().getAmongUsPlayers().size(); i++) {
            // Get all players that are playing
            String player = Singleton.getInstance().getAmongUsPlayers().get(i);

            // See if they have already been assigned the imposter roll
            if(!Singleton.getInstance().getAmongUsCurrentlyPlaying().containsKey(player)){
                // If not, then they are innocent
                Singleton.getInstance().getAmongUsCurrentlyPlaying().put(player, "crewmate");
                Player p = Bukkit.getPlayer(player);
                if (p != null) {
                    roleTitle(p, false);
                }else{
                    Singleton.getInstance().getAmongUsCurrentlyPlaying().remove(player);
                }

            }
        }
    }

    void roleTitle(Player player, boolean imposter){
        player.sendTitle(BLUE + "" + BOLD + "SHHH!", "", 40, 40, 0);
        if(imposter){
            new BukkitRunnable() {
                public void run() {
                    player.sendTitle(WHITE + "" + BOLD + "You are an", RED + "" + BOLD + "imposter!", 40, 60, 0);
                }
            }.runTaskLater(plugin, 40L);
        }if(!imposter){
            new BukkitRunnable() {
                public void run() {
                    player.sendTitle(WHITE + "" + BOLD + "You are a", AQUA + "" + BOLD + "crewmate!", 40, 60, 0);
                }
            }.runTaskLater(plugin, 40L);
        }
    }

    void teleportToEmergencyMeeting(){
        ArrayList<Location> locations = new ArrayList<Location>();

        for(int i = 1; i <= 10; i++) {
            Location emer = getLocation(i);
            locations.add(emer);
        }

        for (int i = 0; i < Singleton.getInstance().getAmongUsPlayers().size(); i++) {
            String player = Singleton.getInstance().getAmongUsPlayers().get(i);
            if(player == null){
                continue;
            }
            Player p = Bukkit.getPlayer(player);
            if (p != null) {
                p.teleport(locations.get(i).add(0.5,0,0.5));
            }
        }

    }

    Location getLocation(int i){
        String world = plugin.getConfig().getString("location.emergencymeeting." + i + ".world");
        int x = plugin.getConfig().getInt("location.emergencymeeting." + i + ".x");
        int y = plugin.getConfig().getInt("location.emergencymeeting." + i + ".y");
        int z = plugin.getConfig().getInt("location.emergencymeeting." + i + ".z");

        if(world == null){
            /*
            Error 1: The string world is null. The only way this would be possible is if they
                     manually removed it in config.
             */
            MessagesUtil.ERROR_1.broadcastMessage();
        }

        Location location = new Location(Bukkit.getWorld(world), x, y, z);

        return location;
    }

}
