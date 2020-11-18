package me.codingcookie.amongus.files.messages;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class GetMessagesFile {

    private CreateMessagesFile createMessagesFile;

    public String getMessage(String file, String defaultString){
        createMessagesFile = new CreateMessagesFile();
        if(!(createMessagesFile.getMessagesFileConfig().contains(file + ".message"))) {
            createMessagesFile.getMessagesFileConfig().set(file + ".message", defaultString);
            createMessagesFile.saveMessagesFile();
        }
        return createMessagesFile.getMessagesFileConfig().getString(file + ".message");
    }

    public boolean getEnabled(String file, boolean defaultBoolean){
        createMessagesFile = new CreateMessagesFile();
        if(!(createMessagesFile.getMessagesFileConfig().contains(file + ".enabled"))) {
            createMessagesFile.getMessagesFileConfig().set(file + ".enabled", defaultBoolean);
            createMessagesFile.saveMessagesFile();
        }
        return createMessagesFile.getMessagesFileConfig().getBoolean(file + ".enabled");
    }

    public void checkAndGetPermissionsMsg(Player p){
        if(getEnabled("no-permissions", true)) {
            String message = ChatColor.translateAlternateColorCodes('&', getMessage("no-permission", "&4You do not have permission to perform that command."));
            p.sendMessage(message);
        }
    }

}
