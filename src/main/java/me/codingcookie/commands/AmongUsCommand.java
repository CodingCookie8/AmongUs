package me.codingcookie.commands;

import me.codingcookie.AmongUs;
import me.codingcookie.commands.subcommands.HelpCommand;
import me.codingcookie.commands.subcommands.PlayCommand;
import me.codingcookie.commands.subcommands.SetupCommand;
import me.codingcookie.commands.subcommands.setupmodule.locationmenu.EmergencyMeetingMenu;
import me.codingcookie.commands.subcommands.setupmodule.locationmenu.LocationMainMenu;
import me.codingcookie.commands.subcommands.setupmodule.SettingsMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.*;

public class AmongUsCommand implements CommandExecutor {

    private final AmongUs plugin;

    public AmongUsCommand(AmongUs plugin){
        this.plugin = plugin;
    }

    private HelpCommand helpCommand;
    private PlayCommand playCommand;
    private SetupCommand setupCommand;
    private LocationMainMenu locationMenu;
    private SettingsMenu settingsMenu;
    private EmergencyMeetingMenu emergencyMeetingMenu;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(!(sender instanceof Player)){
            sender.sendMessage(RED + "All AmongUs commands must be run in game!");
        }

        Player player = (Player) sender;
        helpCommand = new HelpCommand(plugin);
        playCommand = new PlayCommand(plugin);
        setupCommand = new SetupCommand(plugin);
        locationMenu = new LocationMainMenu(plugin);
        settingsMenu = new SettingsMenu(plugin);
        emergencyMeetingMenu = new EmergencyMeetingMenu(plugin);

        if(args.length == 0){
           helpCommand.helpCommand(player);
        }
        if(args.length == 1){
            if(args[0].equalsIgnoreCase("help")){
                helpCommand.helpCommand(player);
            }
            if(args[0].equalsIgnoreCase("play")){
                playCommand.playCommand(player);
            }
            if(args[0].equalsIgnoreCase("setup")){
                setupCommand.setupCommand(player);
            }
        }
        if(args.length == 2){
            if(args[0].equalsIgnoreCase("setup")){
                if(args[1].equalsIgnoreCase("locations")){
                    locationMenu.locationMenu(player);
                }
                if(args[1].equalsIgnoreCase("settings")){
                    settingsMenu.settingsMenu(player);
                }
            }
        }
        if(args.length == 3){
            if(args[0].equalsIgnoreCase("setup")){
                if(args[1].equalsIgnoreCase("locations")){
                    if(args[2].equalsIgnoreCase("emermeeting")){
                        emergencyMeetingMenu.emerMeetingMenu(player);
                    }
                }
                if(args[1].equalsIgnoreCase("settings")){
                    if(args[2].equalsIgnoreCase("crewmatevision")){
                        settingsMenu.changeBooleanSetting("settings.crewmatevision");
                        settingsMenu.settingsMenu(player);
                    }
                    if(args[2].equalsIgnoreCase("impostervision")){
                        settingsMenu.changeBooleanSetting("settings.impostervision");
                        settingsMenu.settingsMenu(player);
                    }
                }


            }
        }

        return false;
    }
}
