package me.codingcookie.utility;

import org.bukkit.GameMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Singleton {

    private static Singleton listInstance;
    private ArrayList<UUID> amongUsPlayers = null;
    private ArrayList<UUID> amongUsAlive = null;
    private ArrayList<UUID> amongUsDead = null;

    private ArrayList<UUID> setup = null;
    private HashMap<UUID, String> lastMenu = null;
    private HashMap<UUID, GameMode> preSetupGamemode = null;

    public static Singleton getInstance() {
        if(listInstance == null) {
            listInstance = new Singleton();
        }
        return listInstance;
    }

    private Singleton() {
        amongUsPlayers = new ArrayList<UUID>();
        amongUsAlive = new ArrayList<UUID>();
        amongUsDead = new ArrayList<UUID>();
        setup = new ArrayList<UUID>();
        lastMenu = new HashMap<UUID, String>();
        preSetupGamemode = new HashMap<UUID, GameMode>();
    }

    public ArrayList<UUID> getAmongUsPlayers() {
        return this.amongUsPlayers;
    }

    public ArrayList<UUID> getAmongUsAlive() {
        return this.amongUsAlive;
    }

    public ArrayList<UUID> getAmongUsDead() {
        return this.amongUsDead;
    }

    public ArrayList<UUID> getSetup() {
        return this.setup;
    }

    public HashMap<UUID, String> getLastMenu() {
        return this.lastMenu;
    }

    public HashMap<UUID, GameMode> getPreSetupGamemode() {
        return this.preSetupGamemode;
    }

}
