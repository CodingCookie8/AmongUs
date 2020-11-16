package me.codingcookie.commands.subcommands;

import me.codingcookie.AmongUs;
import me.codingcookie.files.messages.GetMessagesFile;
import me.codingcookie.permissions.PermissionHandler;
import org.bukkit.entity.Player;
import static org.bukkit.ChatColor.*;

public class HelpCommand {

    private AmongUs plugin;
    private PermissionHandler pH;
    private GetMessagesFile messagesFile;

    public HelpCommand(AmongUs plugin){
        this.plugin = plugin;
    }

    String pre = BLUE + "" + BOLD + "[AmongUs] ";
    String pre2 = DARK_GRAY + "   > ";

    public void helpCommand(Player player){
        pH = new PermissionHandler();
        messagesFile = new GetMessagesFile();

        if(!(pH.checkPermission(player, "amongus.help"))){
            messagesFile.checkAndGetPermissionsMsg(player);
            return;
        }

        player.sendMessage(GOLD + "" + STRIKETHROUGH + "------ " + pre + GOLD + "" + STRIKETHROUGH + "------");
        player.sendMessage(GRAY + "     All commands:");
        player.sendMessage(pre2 + GRAY + "/amongus help");
        player.sendMessage(pre2 + GRAY + "/amongus play");
        player.sendMessage(pre2 + GRAY + "/amongus forcestart");
        player.sendMessage(pre2 + GRAY + "/amongus setup");
    }
}
