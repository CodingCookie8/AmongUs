package me.codingcookie;

import me.codingcookie.commands.AmongUsCommand;
import me.codingcookie.listeners.PlayerInteractListener;
import me.codingcookie.listeners.QuitListener;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class AmongUs extends JavaPlugin {

    private AmongUs plugin;
    private File folder;
    private File messagesFile;
    private File configFile;

    public QuitListener quitListener;
    public PlayerInteractListener playerInteractListener;

    @Override
    public void onEnable(){
        plugin = this;

        CommandExecutor mtExecutor = new AmongUsCommand(this);
        getCommand("amongus").setExecutor(mtExecutor);

        folder = new File("plugins" + File.separator + "AmongUs");
        if(!folder.exists()) {
            plugin.getLogger().warning("Config files missing, creating them now...");
            messagesFile = new File("plugins" + File.separator + "AmongUs" + File.separator + "messages" + File.separator  + ".yml");
            saveResource("messages.yml", false);
            configFile = new File("plugins" + File.separator + "AmongUs" + File.separator + "config" + File.separator  + ".yml");
            saveResource("config.yml", false);
        }else{
            plugin.getLogger().info("Config files loaded.");
        }

        quitListener = new QuitListener(this);
        playerInteractListener = new PlayerInteractListener(this);

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(quitListener, this);
        pm.registerEvents(playerInteractListener, this);
    }
}