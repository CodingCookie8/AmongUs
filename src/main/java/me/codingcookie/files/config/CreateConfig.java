package me.codingcookie.files.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CreateConfig {

    private File configFile = new File("plugins" + File.separator + "AmongUs" + File.separator + "config.yml");
    private FileConfiguration configFileConfig = YamlConfiguration.loadConfiguration(configFile);

    public FileConfiguration getConfigFileConfig(){
        return configFileConfig;
    }

    public File getConfigFile(){
        return configFile;
    }

    public void saveConfigFile(){
        try{
            getConfigFileConfig().save(configFile);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
