package me.codingcookie.amongus;

import co.aikar.commands.BukkitCommandManager;
//import me.codingcookie.amongus.commands.AmongUsCommand;
import me.codingcookie.amongus.commands.AmongUsCommand;
import me.codingcookie.amongus.commands.subcommands.SetupCommand;
import me.codingcookie.amongus.commands.subcommands.setupmodule.locationmenu.LocationMainMenu;
import me.codingcookie.amongus.listeners.*;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class AmongUs extends JavaPlugin {

    private AmongUs plugin;
    private File folder;
    private File messagesFile;
    private File configFile;

    private AmongUsCommand amongUsCommandRedo;
    private SetupCommand setupCommand;
    private LocationMainMenu locationMainMenu;
    public QuitListener quitListener;
    public ChatListener chatListener;
    public PlayerInteractListener playerInteractListener;
    public InventoryClickListener inventoryClickListener;
    public FoodLevelChangeListener foodLevelChangeListener;
    public DamageListener damageListener;

    @Override
    public void onEnable(){
        plugin = this;

        amongUsCommandRedo = new AmongUsCommand(this);
        setupCommand = new SetupCommand(this);
        locationMainMenu = new LocationMainMenu(this);

        BukkitCommandManager manager = new BukkitCommandManager(this);
        manager.registerCommand(amongUsCommandRedo);
        manager.registerCommand(setupCommand);
        manager.registerCommand(locationMainMenu);

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

        chatListener = new ChatListener(this);
        quitListener = new QuitListener(this);
        playerInteractListener = new PlayerInteractListener(this);
        inventoryClickListener = new InventoryClickListener(this);
        foodLevelChangeListener = new FoodLevelChangeListener(this);
        damageListener = new DamageListener(this);

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(chatListener, this);
        pm.registerEvents(quitListener, this);
        pm.registerEvents(playerInteractListener, this);
        pm.registerEvents(inventoryClickListener, this);
        pm.registerEvents(foodLevelChangeListener, this);
        pm.registerEvents(damageListener, this);

        asciiArt();
    }

    void asciiArt(){
        plugin.getLogger().info("⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⣤⣤⣤⣤⣤⣶⣦⣤⣄⡀⠀⠀⠀⠀⠀⠀⠀⠀");
        plugin.getLogger().info("⠀⠀⠀⠀⠀⠀⠀⠀⢀⣴⣿⡿⠛⠉⠙⠛⠛⠛⠛⠻⢿⣿⣷⣤⡀⠀⠀⠀⠀⠀");
        plugin.getLogger().info("⠀⠀⠀⠀⠀⠀⠀⠀⣼⣿⠋⠀⠀⠀⠀⠀⠀⠀⢀⣀⣀⠈⢻⣿⣿⡄⠀⠀⠀⠀");
        plugin.getLogger().info("⠀⠀⠀⠀⠀⠀⠀⣸⣿⡏⠀⠀⠀⣠⣶⣾⣿⣿⣿⠿⠿⠿⢿⣿⣿⣿⣄⠀⠀⠀");
        plugin.getLogger().info("⠀⠀⠀⠀⠀⠀⠀⣿⣿⠁⠀⠀⢰⣿⣿⣯⠁⠀⠀⠀⠀⠀⠀⠀⠈⠙⢿⣷⡄⠀");
        plugin.getLogger().info("⠀⠀⣀⣤⣴⣶⣶⣿⡟⠀⠀⠀⢸⣿⣿⣿⣆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣷⠀");
        plugin.getLogger().info("⠀⢰⣿⡟⠋⠉⣹⣿⡇⠀⠀⠀⠘⣿⣿⣿⣿⣷⣦⣤⣤⣤⣶⣶⣶⣶⣿⣿⣿⠀");
        plugin.getLogger().info("⠀⢸⣿⡇⠀⠀⣿⣿⡇⠀⠀⠀⠀⠹⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠃⠀");
        plugin.getLogger().info("⠀⣸⣿⡇⠀⠀⣿⣿⡇⠀⠀⠀⠀⠀⠉⠻⠿⣿⣿⣿⣿⡿⠿⠿⠛⢻⣿⡇⠀⠀");
        plugin.getLogger().info("⠀⣿⣿⠁⠀⠀⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣧⠀⠀");
        plugin.getLogger().info("⠀⣿⣿⠀⠀⠀⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⠀⠀");
        plugin.getLogger().info("⠀⣿⣿⠀⠀⠀⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⠀⠀");
        plugin.getLogger().info("⠀⢿⣿⡆⠀⠀⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⡇⠀⠀");
        plugin.getLogger().info("⠀⠸⣿⣧⡀⠀⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⠃⠀⠀");
        plugin.getLogger().info("⠀⠀⠛⢿⣿⣿⣿⣿⣇⠀⠀⠀⠀⠀⣰⣿⣿⣷⣶⣶⣶⣶⠶⠀⢠⣿⣿⠀⠀⠀");
        plugin.getLogger().info("⠀⠀⠀⠀⠀⠀⠀⣿⣿⠀⠀⠀⠀⠀⣿⣿⡇⠀⣽⣿⡏⠁⠀⠀⢸⣿⡇⠀⠀⠀");
        plugin.getLogger().info("⠀⠀⠀⠀⠀⠀⠀⣿⣿⠀⠀⠀⠀⠀⣿⣿⡇⠀⢹⣿⡆⠀⠀⠀⣸⣿⠇⠀⠀⠀");
        plugin.getLogger().info("⠀⠀⠀⠀⠀⠀⠀⢿⣿⣦⣄⣀⣠⣴⣿⣿⠁⠀⠈⠻⣿⣿⣿⣿⡿⠏⠀⠀⠀⠀");
        plugin.getLogger().info("⠀⠀⠀⠀⠀⠀⠀⠈⠛⠻⠿⠿⠿⠿⠋⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀");
    }
}
