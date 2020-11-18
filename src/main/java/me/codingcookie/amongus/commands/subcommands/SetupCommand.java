package me.codingcookie.amongus.commands.subcommands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import me.codingcookie.amongus.AmongUs;
import me.codingcookie.amongus.commands.subcommands.setupmodule.CheckSetup;
import me.codingcookie.amongus.commands.subcommands.setupmodule.MenuUtility;
import me.codingcookie.amongus.commands.subcommands.setupmodule.locationmenu.*;
import me.codingcookie.amongus.commands.subcommands.setupmodule.settingsmenu.SettingsMenu;
import me.codingcookie.amongus.commands.subcommands.setupmodule.settingsmenu.SettingsUtility;
import me.codingcookie.amongus.files.messages.GetMessagesFile;
import me.codingcookie.amongus.permissions.PermissionHandler;
import me.codingcookie.amongus.utility.Singleton;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static org.bukkit.ChatColor.*;

@CommandAlias("amongus")
@Subcommand("setup")
public class SetupCommand extends BaseCommand {

    private AmongUs plugin;
    private PermissionHandler pH;
    private GetMessagesFile messagesFile;
    private LocationMainMenu locationMainMenu;
    private SettingsMenu settingsMenu;
    private CheckSetup checkSetup;
    private MenuUtility menuUtility;
    private SettingsUtility sU;

    public SetupCommand(AmongUs plugin){
        this.plugin = plugin;
    }

    private String module = GOLD + "" + STRIKETHROUGH + "   ----------- " + BLUE + "" + BOLD + "[Setup Module]" + GOLD + "" + STRIKETHROUGH + " -----------";
    private String pre2 = DARK_GRAY + "> ";

    @Subcommand("locations")
    public void onLocations(Player player, String[] args){
        if(!Singleton.getInstance().getSetup().contains(player.getUniqueId())){
            player.sendMessage(pre2 + RED + "You aren't in setup mode!");
            return;
        }
        if(args.length == 0){
            locationMainMenu = new LocationMainMenu(plugin);
            locationMainMenu.locationMenu(player);
        }
    }

    @Subcommand("settings")
    public void onSettings(Player player, String[] args){
        if(!Singleton.getInstance().getSetup().contains(player.getUniqueId())){
            player.sendMessage(pre2 + RED + "You aren't in setup mode!");
            return;
        }
        if(args.length == 0){
            settingsMenu = new SettingsMenu(plugin);
            settingsMenu.settingsMenu(player);
        }
        if(args.length == 1){
            sU = new SettingsUtility(plugin);
            if(args[0].equalsIgnoreCase("crewmatevision")){
                sU.changeBooleanSetting("settings.crewmatevision");
                settingsMenu.settingsMenu(player);
            }else if(args[0].equalsIgnoreCase("impostervision")){
                sU.changeBooleanSetting("settings.impostervision");
                settingsMenu.settingsMenu(player);
            }else {
                try {
                    sU.waitingForInt(player, "settings." + args[0]);
                }catch(NullPointerException e){
                    player.sendMessage(RED + "Please do '/amongus setup' and use the clickable menu to setup the game.");
                }
            }
        }
    }

    @Subcommand("leave")
    public void onLeave(Player player, String[] args){
        menuUtility = new MenuUtility(plugin);
        if(args.length == 0){
            menuUtility.leaveSetupMode(player);
        }
    }

    public void setupCommand(Player player) {
        pH = new PermissionHandler();
        messagesFile = new GetMessagesFile();

        if(!(pH.checkPermission(player, "amongus.setup"))){
            messagesFile.checkAndGetPermissionsMsg(player);
            return;
        }

        if(!(Singleton.getInstance().getAmongUsPlayers().isEmpty())){
            player.sendMessage(module);
            player.sendMessage(pre2 + RED + "You cannot go into " + GOLD + "SETUP" + RED + " mode while someone is playing the game.");
            player.sendMessage(pre2 + RED + "Tell all players to run the command " + GOLD + "/amongus leave" + RED + " or type " + GOLD + "/amongus kickall");
            player.sendMessage(pre2 + RED + "To prevent further players from joining, type " + GOLD + "/amongus close");
            return;
        }

        if(!Singleton.getInstance().getSetup().contains(player.getUniqueId())){
            Singleton.getInstance().getSetup().add(player.getUniqueId());
            Singleton.getInstance().getPreSetupGamemode().put(player.getUniqueId(), player.getGameMode());
        }

        player.setGameMode(GameMode.CREATIVE);
        player.getInventory().addItem(new ItemStack(Material.WOODEN_SHOVEL));
        player.getInventory().addItem(new ItemStack(Material.STONE_BUTTON));

        sendMainMenu(player);

    }

    void sendMainMenu(Player player){
        checkSetup = new CheckSetup(plugin);
        menuUtility = new MenuUtility(plugin);

        for (int i = 1; i <= 10; i++) {
            player.sendMessage("");
        }

        player.sendMessage(module);
        player.sendMessage(GOLD + "Below are all the settings you can change. Click one of them to get started.");

        player.sendMessage(GOLD + "");
        player.sendMessage(GOLD + "To undo anything, type the word " + RED + "undo" + GOLD + " into chat. You can also type " + RED + "reset" + GOLD + " to start from the beginning.");
        player.sendMessage(GOLD + "Everything you do is saved, so feel free to leave or close Minecraft if need be. You can also type " + RED + "end" + GOLD + " to leave Setup mode.");

        player.sendMessage("");
        menuUtility.clickableLink(checkSetup.checkLocations(),"LOCATIONS", "/amongus setup locations", "Some locations haven't been set, click to setup", player);
        menuUtility.clickableLink(true,"SETTINGS", "/amongus setup settings", "Click me to change settings", player);

        player.sendMessage("");

        menuUtility.clickableLink(false, "QUIT", "/amongus setup leave", "Click me to quit setup mode!", player);
    }

}
