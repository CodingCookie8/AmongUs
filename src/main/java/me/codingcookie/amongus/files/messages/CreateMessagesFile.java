package me.codingcookie.amongus.files.messages;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CreateMessagesFile extends YamlConfiguration {

    private File messagesFile = new File("plugins" + File.separator + "AmongUs" + File.separator + "messages.yml");
    private FileConfiguration messagesFileConfig = YamlConfiguration.loadConfiguration(messagesFile);

    public FileConfiguration getMessagesFileConfig(){
        return messagesFileConfig;
    }

    public File getMessagesFile(){
        return messagesFile;
    }

    public void saveMessagesFile(){
        try{
            getMessagesFileConfig().save(messagesFile);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
