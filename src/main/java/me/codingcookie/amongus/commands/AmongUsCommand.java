package me.codingcookie.amongus.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import me.codingcookie.amongus.AmongUs;
import me.codingcookie.amongus.commands.subcommands.HelpCommand;
import me.codingcookie.amongus.commands.subcommands.PlayCommand;
import me.codingcookie.amongus.commands.subcommands.SetupCommand;
import me.codingcookie.amongus.entities.ArmorStandsUtil;
import me.codingcookie.amongus.gui.tasks.UploadGUI;
import me.codingcookie.amongus.utility.GameUtil;
import me.codingcookie.amongus.utility.MessagesUtil;
import me.codingcookie.amongus.utility.Singleton;
import me.codingcookie.amongus.utility.VoteUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.*;

@CommandAlias("amongus")
public class AmongUsCommand extends BaseCommand {

    private final AmongUs plugin;

    public AmongUsCommand(AmongUs plugin){
        this.plugin = plugin;
    }

    private SetupCommand setupCommand;
    private me.codingcookie.amongus.commands.subcommands.HelpCommand helpCommand;
    private PlayCommand playCommand;
    private ArmorStandsUtil armorStandsUtil;
    private GameUtil gameUtil;
    private VoteUtil voteUtil;
    private UploadGUI uploadGUI;

    @Default
    @CommandPermission("amongus.help")
    public void onDefault(Player player){
        helpCommand = new HelpCommand(plugin);
        helpCommand.helpCommand(player);
    }

    @Subcommand("setup")
    @Description("Setup the AmongUs game")
    @CommandPermission("amongus.setup")
    public void onSetup(Player player, String[] args){
        if(args.length == 0){
            setupCommand = new SetupCommand(plugin);
            setupCommand.setupCommand(player);
        }
    }

    @Subcommand("help")
    @Description("See all available commands for the plugin")
    @CommandPermission("amongus.help")
    public void onHelp(Player player, String[] args){
        if(args.length == 0){
            helpCommand = new HelpCommand(plugin);
            helpCommand.helpCommand(player);
        }
    }

    @Subcommand("play")
    @Description("Play the game!")
    @CommandPermission("amongus.play")
    public void onPlay(Player player, String[] args){
        if(args.length == 0){
            playCommand = new PlayCommand(plugin);
            playCommand.playCommand(player);
        }
    }

    @Subcommand("kickall")
    @Description("Kick all players from the game.")
    @CommandPermission("amongus.kickall")
    public void onKickAll(Player player, String[] args){
        if(args.length == 0){
            gameUtil = new GameUtil(plugin);
            gameUtil.endGame(RED, "Kicked!", player.getName() + " kicked you from the game", RED, "Kicked!", player.getName() + " kicked you from the game");
        }
    }

    @Subcommand("vote")
    @Description("Vote to remove a player from the game.")
    @CommandPermission("amongus.vote")
    public void onVote(Player player, String[] args){
        if(args.length == 0){
            return;
        }
        if(args.length == 1){
            gameUtil = new GameUtil(plugin);
            voteUtil = new VoteUtil(plugin);
            Player clickedPlayer = Bukkit.getPlayer(args[0]);
            if(clickedPlayer == null){
                MessagesUtil.ERROR_2.sendMessage(player);
                return;
            }
            if(!Singleton.getInstance().getHasVoted().contains(player.getName())) {
                voteUtil.addVote(clickedPlayer.getName());
                Singleton.getInstance().getHasVoted().add(player.getName());
            }
            for(String playerString : Singleton.getInstance().getAmongUsCurrentlyPlaying().keySet()) {
                Player players = Bukkit.getPlayer(playerString);
                if (players == null) { continue; }
                for (int i = 1; i <= 30; i++) {
                    players.sendMessage("");
                }
                voteUtil.sendVoting(players);
            }
        }
    }

    @Subcommand("removearmorstands")
    @Description("temporary development command. if you see this please ignore and alert developer asap.")
    public void onRemoveArmorStands(Player player, String[] args){
        if(!player.isOp()){
            return;
        }
        armorStandsUtil = new ArmorStandsUtil(plugin);
        armorStandsUtil.removeArmorStands(player.getWorld());
    }

    @Subcommand("forcestart")
    @Description("temporary development command.")
    public void onForceStart(Player player, String[] args){
        if(!player.isOp()){
            return;
        }
        gameUtil = new GameUtil(plugin);
        gameUtil.startRound();
    }

    @Subcommand("openuploadtask")
    @Description("temporary development command.")
    public void onUploadTask(Player player, String[] args){
        if(!player.isOp()){
            return;
        }
        uploadGUI = new UploadGUI(plugin);
        uploadGUI.setUploadGUI(player);
    }
}
