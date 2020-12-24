package me.codingcookie.amongus.utility;

import me.codingcookie.amongus.AmongUs;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
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
    private VoteUtil vU;

    public GameUtil(AmongUs plugin){
        this.plugin = plugin;
    }

    String pre = BLUE + "" + BOLD + "[AmongUs] ";
    private String pre2 = DARK_GRAY + "   > ";

    public void startRound(){
        teleportToEmergencyMeeting();
        assignRoles();

        Singleton.getInstance().getEmerMeetingList().clear();
        Singleton.getInstance().getEmerMeetingVotes().clear();
        Singleton.getInstance().getHasVoted().clear();

        // TODO: REMOVE THIS COMMENT ONCE DONE TESTING

        /*
        if(checkEndGame()){
            return;
        }
        */

        iU = new ImposterUtil(plugin);
        cU = new CrewmateUtil(plugin);

        BossBar bossBar;
        bossBar = Bukkit.createBossBar(
                "",
                BarColor.RED,
                BarStyle.SOLID);
        bossBar.setVisible(true);

        int emerCooldown = plugin.getConfig().getInt("settings.emergencycooldown");
        Singleton.getInstance().getEmerMeetingList().add("Placeholder for cooldown button");

        for(String playerString : Singleton.getInstance().getAmongUsCurrentlyPlaying().keySet()){
            Player player = Bukkit.getPlayer(playerString);
            if(player == null){
                continue;
            }

            bossBar.addPlayer(player);
            for (int i = 1; i <= 30; i++) {
                player.sendMessage("");
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
            if (Singleton.getInstance().getAmongUsCurrentlyPlaying().get(playerString).equalsIgnoreCase("dead")){
                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
            }

        }

        new BukkitRunnable(){
            int emerCountdown = emerCooldown;
            public void run(){
                emerCountdown--;
                if(emerCountdown != 0){
                    bossBar.setTitle(ChatColor.GOLD + "Emergency Button Cooldown: " + ChatColor.RED + emerCountdown + " seconds");
                }else{
                    bossBar.setVisible(false);
                    bossBar.removeAll();
                    Singleton.getInstance().getEmerMeetingList().clear();
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0, 20);
    }

    //
    //
    //
    //         EMERGENCY MEETING
    //
    //
    //

    public void emergencyMeeting(Player playerThatCalledMeeting, String bodyOrMeeting){
        vU = new VoteUtil(plugin);

        if(!Singleton.getInstance().getEmerMeetingList().isEmpty()){
            return;
        }

        if(Singleton.getInstance().getAmongUsCurrentlyPlaying().get(playerThatCalledMeeting.getName()).equalsIgnoreCase("dead")){
            return;
        }

        if(Singleton.getInstance().getEmerMeetingsCalled().get(playerThatCalledMeeting.getName()) == 0){
            return;
        }
        int current = Singleton.getInstance().getEmerMeetingsCalled().get(playerThatCalledMeeting.getName());
        Singleton.getInstance().getEmerMeetingsCalled().remove(playerThatCalledMeeting.getName());
        Singleton.getInstance().getEmerMeetingsCalled().put(playerThatCalledMeeting.getName(), current - 1);

        teleportToEmergencyMeeting();
        int discussion = plugin.getConfig().getInt("settings.discussion");
        int votingTime = plugin.getConfig().getInt("settings.voting");

        vU.preVote(playerThatCalledMeeting, bodyOrMeeting);

        new BukkitRunnable(){
            public void run() {
                vU = new VoteUtil(plugin);
                for(String playerString : Singleton.getInstance().getAmongUsCurrentlyPlaying().keySet()) {
                    Player players = Bukkit.getPlayer(playerString);
                    if (players == null) { continue; }
                    for (int i = 1; i <= 30; i++) {
                        players.sendMessage("");
                    }
                    vU.sendVoting(players);
                }
            }
        }.runTaskLater(plugin, discussion * 20);

        BossBar bossBar;
        bossBar = Bukkit.createBossBar(
                "",
                BarColor.RED,
                BarStyle.SOLID);

        bossBar.setVisible(false);

        for(String playerString : Singleton.getInstance().getAmongUsCurrentlyPlaying().keySet()){
            Player players = Bukkit.getPlayer(playerString);
            if(players == null){ continue; }
            bossBar.addPlayer(players);
        }

        new BukkitRunnable(){
            int votingCountdown = votingTime;
            public void run() {
                votingCountdown--;
                bossBar.setVisible(true);

                if(votingCountdown != 0) {
                    bossBar.setTitle(ChatColor.GOLD + "Voting time remaining: " + ChatColor.RED + votingCountdown + " seconds");
                    bossBar.setProgress(1);
                }else{
                    vU.checkVotedOut();
                    bossBar.setVisible(false);
                    bossBar.removeAll();
                    cancel();
                }
            }
        }.runTaskTimer(plugin, discussion * 20, 20);

        new BukkitRunnable(){
            public void run() {
                startRound();
            }
        }.runTaskLater(plugin, (votingTime * 20) + 180);
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
                MessagesUtil.ERROR_2.broadcastMessage();
                continue;
            }
            Player p = Bukkit.getPlayer(player);
            if (p != null) {
                p.teleport(locations.get(i).add(0.5,0,0.5));
            }
        }

    }

    public void ejectPlayer(String ejectedPlayerString){
        Player ejectedPlayerObject = Bukkit.getPlayer(ejectedPlayerString);
        if(ejectedPlayerObject == null){
            MessagesUtil.ERROR_2.broadcastMessage();
            return;
        }
        ejectedPlayerObject.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 140, 1));
        ejectedPlayerObject.sendTitle(RED + "" + BOLD + "EJECTED", GRAY + "Your fellow passengers ejected you from the ship.", 40, 60, 40);

        for(String playerString : Singleton.getInstance().getAmongUsCurrentlyPlaying().keySet()) {
            Player players = Bukkit.getPlayer(playerString);
            if (players == null) { continue; }
            if (Singleton.getInstance().getAmongUsCurrentlyPlaying().get(playerString).equalsIgnoreCase("dead")){ continue; }
            players.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 140, 1));

            if(!plugin.getConfig().getBoolean("settings.anonymousejection")){
                if(Singleton.getInstance().getAmongUsCurrentlyPlaying().get(ejectedPlayerString).equalsIgnoreCase("imposter")) {
                    players.sendTitle(RED + "" + BOLD + ejectedPlayerString, RED + "was an imposter.", 40, 100, 40);
                }
                if(Singleton.getInstance().getAmongUsCurrentlyPlaying().get(ejectedPlayerString).equalsIgnoreCase("crewmate")) {
                    players.sendTitle(GREEN + "" + BOLD + ejectedPlayerString, GREEN + "was not an imposter.", 40, 100, 40);
                }
            }else{
                players.sendTitle(RED + "" + BOLD + ejectedPlayerString, RED + "was ejected from the ship.", 40, 100, 40);
            }

        }
        Singleton.getInstance().getAmongUsCurrentlyPlaying().remove(ejectedPlayerString);
        Singleton.getInstance().getAmongUsCurrentlyPlaying().put(ejectedPlayerString, "dead");
    }

    Location getLocation(int i){
        String world = plugin.getConfig().getString("location.emergencymeeting." + i + ".world");
        int x = plugin.getConfig().getInt("location.emergencymeeting." + i + ".x");
        int y = plugin.getConfig().getInt("location.emergencymeeting." + i + ".y");
        int z = plugin.getConfig().getInt("location.emergencymeeting." + i + ".z");

        if(world == null){
            MessagesUtil.ERROR_1.broadcastMessage();
        }

        Location location = new Location(Bukkit.getWorld(world), x, y, z);

        return location;
    }

    //
    //
    //
    //         END GAME
    //
    //
    //

    public void endGame(ChatColor imposterColor, String imposterTitle, String imposterSubtitle, ChatColor crewmateColor, String crewmateTitle, String crewmateSubtitle){
        for(String playerString : Singleton.getInstance().getAmongUsCurrentlyPlaying().keySet()){
            if(Singleton.getInstance().getAmongUsCurrentlyPlaying().get(playerString).equalsIgnoreCase("imposter")){
                Player imposters = Bukkit.getPlayer(playerString);
                if(imposters == null){ continue; }
                imposters.sendTitle(imposterColor + "" + BOLD + imposterTitle, GRAY + imposterSubtitle, 20, 120, 20);
            }else{
                Player crewmates = Bukkit.getPlayer(playerString);
                if(crewmates == null){ continue; }
                crewmates.sendTitle(crewmateColor + "" + BOLD + crewmateTitle, GRAY + crewmateSubtitle, 20, 120, 20);
            }
        }

        int lobbyX = plugin.getConfig().getInt("location.lobby.x");
        int lobbyY = plugin.getConfig().getInt("location.lobby.y");
        int lobbyZ = plugin.getConfig().getInt("location.lobby.z");
        String lobbyWorld = plugin.getConfig().getString("location.lobby.world");

        for(String playerString : Singleton.getInstance().getAmongUsCurrentlyPlaying().keySet()) {
            Player player = Bukkit.getPlayer(playerString);
            if (player == null) {
                continue;
            }
            player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 10, 1);
            if(lobbyWorld == null){ MessagesUtil.ERROR_1.sendMessage(player); }
            Location lobby = new Location(Bukkit.getWorld(lobbyWorld), lobbyX, lobbyY, lobbyZ);
            player.teleport(lobby.add(0.5, 0, 0.5));

            player.removePotionEffect(PotionEffectType.INVISIBILITY);
            player.removePotionEffect(PotionEffectType.BLINDNESS);
        }
        clearArrayLists();
    }

    void clearArrayLists(){
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

        Singleton.getInstance().getEmerMeetingsCalled().clear();
        Singleton.getInstance().getEmerMeetingList().clear();
        Singleton.getInstance().getEmerMeetingVotes().clear();
    }

    public Boolean checkEndGame(){
        long countImposters = Singleton.getInstance().getAmongUsCurrentlyPlaying().values().stream().filter(v -> v.equals("imposter")).count();
        long countCrewmates = Singleton.getInstance().getAmongUsCurrentlyPlaying().values().stream().filter(v -> v.equals("crewmate")).count();

        //endGame(BLUE, "Victory!", "You won this round!", RED, "Defeat!", "The imposters killed all the crewmates!");

        if(countImposters >= countCrewmates){
            endGame(BLUE, "Victory!", "You won this round", RED, "Defeat!", "The imposters killed all the crewmates");
            return true;
        }else if (countImposters == 0){
            endGame(RED, "Defeat!", "All the imposters were voted out", BLUE, "Victory!", "All the imposters were voted out");
            return true;
        }else{
            return false;
        }
    }

    //
    //
    //
    //         ASSIGN ROLES
    //
    //
    //

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

            // If they haven't been assigned before, give them the role of imposter
            Singleton.getInstance().getAmongUsCurrentlyPlaying().put(randomPlayer, "imposter");

            // Convert player to an object
            Player randomPlayerO = Bukkit.getPlayer(randomPlayer);
            if(randomPlayerO != null) {
                roleTitle(randomPlayerO, true);
                Singleton.getInstance().getEmerMeetingsCalled().put(randomPlayerO.getName(), plugin.getConfig().getInt("settings.maxemergencymeetings"));
            }else {
                Singleton.getInstance().getAmongUsCurrentlyPlaying().remove(randomPlayer);
            }
        }

        for (int i = 0; i < Singleton.getInstance().getAmongUsPlayers().size(); i++) {
            // Get all players that are playing
            String player = Singleton.getInstance().getAmongUsPlayers().get(i);

            // See if they have already been assigned the imposter role
            if(!Singleton.getInstance().getAmongUsCurrentlyPlaying().containsKey(player)){
                // If not, then they are innocent
                Singleton.getInstance().getAmongUsCurrentlyPlaying().put(player, "crewmate");
                Player p = Bukkit.getPlayer(player);
                if (p != null) {
                    roleTitle(p, false);
                    Singleton.getInstance().getEmerMeetingsCalled().put(p.getName(), plugin.getConfig().getInt("settings.maxemergencymeetings"));
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

}
