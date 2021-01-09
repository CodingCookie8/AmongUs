package me.codingcookie.amongus;

import co.aikar.commands.BukkitCommandManager;
import me.codingcookie.amongus.commands.AmongUsCommand;
import me.codingcookie.amongus.commands.subcommands.SetupCommand;
import me.codingcookie.amongus.commands.subcommands.setupmodule.locationmenu.LocationMainMenu;
import me.codingcookie.amongus.gui.items.NavigationGUIItems;
import me.codingcookie.amongus.gui.tasks.NavigationGUI;
import me.codingcookie.amongus.listeners.*;
import me.codingcookie.amongus.utility.MessagesUtil;
import me.codingcookie.amongus.utility.Singleton;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class AmongUs extends JavaPlugin {

    private AmongUs plugin;
    private File folder;
    private File messagesFile;
    private File configFile;

    private BufferedImage img = null;

    private AmongUsCommand amongUsCommandRedo;
    private SetupCommand setupCommand;
    private LocationMainMenu locationMainMenu;
    public QuitListener quitListener;
    public ChatListener chatListener;
    public PlayerInteractListener playerInteractListener;
    public InventoryClickListener inventoryClickListener;
    public FoodLevelChangeListener foodLevelChangeListener;
    public DamageListener damageListener;
    public VehicleExitListener vehicleExitListener;

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
        vehicleExitListener = new VehicleExitListener(this);

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(chatListener, this);
        pm.registerEvents(quitListener, this);
        pm.registerEvents(playerInteractListener, this);
        pm.registerEvents(inventoryClickListener, this);
        pm.registerEvents(foodLevelChangeListener, this);
        pm.registerEvents(damageListener, this);
        pm.registerEvents(vehicleExitListener, this);

        PlayerInteractListener.mapTask.clear();
        playerInteractListener.fillInHashMap();

        Bukkit.getPluginManager().disablePlugin(this);
    }

    @Override
    public void onDisable(){
        PlayerInteractListener.mapTask.clear();
    }

    public BufferedImage getNavImg(){
        return img;
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
