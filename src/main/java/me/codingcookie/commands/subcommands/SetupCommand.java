package me.codingcookie.commands.subcommands;

import me.codingcookie.AmongUs;
import me.codingcookie.commands.subcommands.setupmodule.MainMenu;
import me.codingcookie.files.messages.GetMessagesFile;
import me.codingcookie.permissions.PermissionHandler;
import me.codingcookie.utility.Singleton;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import static org.bukkit.ChatColor.*;

public class SetupCommand {

    private AmongUs plugin;
    private PermissionHandler pH;
    private GetMessagesFile messagesFile;

    public SetupCommand(AmongUs plugin){
        this.plugin = plugin;
    }

    private String module = GOLD + "" + STRIKETHROUGH + "   ----------- " + BLUE + "" + BOLD + "[Setup Module]" + GOLD + "" + STRIKETHROUGH + " -----------";
    private String pre = BLUE + "" + BOLD + "[AmongUs] ";
    private String pre2 = DARK_GRAY + "> ";

    private MainMenu mainMenu;

    public void setupCommand(Player player) {
        pH = new PermissionHandler();
        messagesFile = new GetMessagesFile();
        mainMenu = new MainMenu(plugin);

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

        if(!(hasAvaliableSlot(player, 2))){
            player.sendMessage(module);
            player.sendMessage(pre2 + RED + "You need at least two open slots in your inventory to setup the game.");
            return;
        }

        if(!Singleton.getInstance().getSetup().contains(player.getUniqueId())){
            Singleton.getInstance().getSetup().add(player.getUniqueId());
        }
        Singleton.getInstance().getPreSetupGamemode().put(player.getUniqueId(), player.getGameMode());

        player.setGameMode(GameMode.CREATIVE);
        player.getInventory().addItem(new ItemStack(Material.WOODEN_SHOVEL));
        player.getInventory().addItem(new ItemStack(Material.STONE_BUTTON));

        mainMenu.sendMainMenu(player);

    }

    boolean hasAvaliableSlot(Player player, int amount){
        Inventory inv = player.getInventory();
        int checkSlot = 0;

        for (ItemStack item: inv.getContents()) {
            if(item == null) {
                checkSlot++;
            }
        }
        if(checkSlot >= amount){
            return true;
        }else{
            return false;
        }
    }

    void checkSetup(){

    }

}
