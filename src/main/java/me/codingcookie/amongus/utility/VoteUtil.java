package me.codingcookie.amongus.utility;

import me.codingcookie.amongus.AmongUs;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.bukkit.ChatColor.*;

public class VoteUtil {

    private AmongUs plugin;
    private GameUtil gU;
    private ImposterUtil iU;
    private CrewmateUtil cU;

    public VoteUtil(AmongUs plugin){
        this.plugin = plugin;
    }

    String pre = BLUE + "" + BOLD + "[AmongUs] ";
    private String pre2 = DARK_GRAY + "   > ";

    public void addVote(String playerToAddVote){
        if(!Singleton.getInstance().getAmongUsCurrentlyPlaying().containsKey(playerToAddVote)){
            return;
        }
        if(!Singleton.getInstance().getEmerMeetingList().contains(playerToAddVote)){
            return;
        }
        int current = Singleton.getInstance().getEmerMeetingVotes().get(playerToAddVote);
        Singleton.getInstance().getEmerMeetingVotes().put(playerToAddVote, current + 1);
    }

    public void sendVoting(Player player){
        if(!Singleton.getInstance().getAmongUsCurrentlyPlaying().containsKey(player.getName())){
            return;
        }
        if(!Singleton.getInstance().getEmerMeetingList().contains(player.getName())){
            return;
        }
        player.sendMessage(GOLD + "Click on the player below that you think is the imposter:");
        player.sendMessage("");
        for(int playerNumber = 0; playerNumber < Singleton.getInstance().getEmerMeetingList().size(); playerNumber++){
            String clickedPlayer = Singleton.getInstance().getEmerMeetingList().get(playerNumber);
            clickableVote(clickedPlayer, "/amongus vote " + clickedPlayer, player);
        }
        player.sendMessage("");
    }

    public void checkVotedOut(){
        gU = new GameUtil(plugin);

        int max = Collections.max(Singleton.getInstance().getEmerMeetingVotes().values());
        List<String> keys = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : Singleton.getInstance().getEmerMeetingVotes().entrySet()) {
            if (entry.getValue() == max) {
                keys.add(entry.getKey());
            }
        }
        if (keys.size() >= 2) {
            for(String playerString : Singleton.getInstance().getAmongUsCurrentlyPlaying().keySet()) {
                Player players = Bukkit.getPlayer(playerString);
                if (players == null) {
                    continue;
                }
                players.sendTitle(GOLD + "Tie!", GRAY + "Your crewmates could not come to a consensus", 40, 100, 40);
            }
        } else {
            gU.ejectPlayer(keys.get(0));
        }
    }

    public void preVote(Player playerThatCalledMeeting, String bodyOrMeeting){
        int discussion = plugin.getConfig().getInt("settings.discussion");
        int votingTime = plugin.getConfig().getInt("settings.voting");

        for(String playerString : Singleton.getInstance().getAmongUsCurrentlyPlaying().keySet()){
            Player players = Bukkit.getPlayer(playerString);
            if(players == null){ continue; }

            players.closeInventory();

            if (Singleton.getInstance().getAmongUsDead().contains(playerString)){ continue; }
            Singleton.getInstance().getEmerMeetingVotes().put(playerString, 0);
            Singleton.getInstance().getEmerMeetingList().add(playerString);

            ArmorStand aS = (ArmorStand) players.getWorld().spawnEntity(players.getLocation(), EntityType.ARMOR_STAND);
            aS.setSmall(true);
            aS.setVisible(false);
            aS.addPassenger(players);

            new BukkitRunnable(){
                public void run() {
                    aS.remove();
                }
            }.runTaskLater(plugin, (votingTime * 20) + 180);

            for (int i = 1; i <= 30; i++) {
                players.sendMessage("");
            }
            players.sendMessage(pre2 + GOLD + "" + BOLD + playerThatCalledMeeting.getName() + GOLD + bodyOrMeeting);
            players.sendMessage("");
            players.sendMessage(pre2 + GOLD + "You have " + RED + discussion + GOLD + " seconds to discuss before voting.");
            players.sendMessage("");
        }
        int meetingsRemaining = Singleton.getInstance().getEmerMeetingsCalled().get(playerThatCalledMeeting.getName());
        playerThatCalledMeeting.sendMessage(pre2 + GRAY + "(You have " + meetingsRemaining + " emergency meetings remaining)");
    }

    void messagePlayingCharacters(String s){
        for(String playerString : Singleton.getInstance().getAmongUsCurrentlyPlaying().keySet()) {
            Player player = Bukkit.getPlayer(playerString);
            if (player == null) {
                continue;
            }
            player.sendMessage(s);
        }
    }

    void clickableVote(String text, String runCommand, Player player){

        int votes = Singleton.getInstance().getEmerMeetingVotes().get(text);

        TextComponent setting = new TextComponent(GREEN + "              " + BOLD + text + " " + GRAY + votes + " votes.");
        setting.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, runCommand));
        setting.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(GOLD + "Click here to vote for " + text + ".")));
        player.spigot().sendMessage(setting);
    }
}
