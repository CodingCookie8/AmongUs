package me.codingcookie.amongus.utility;

import org.bukkit.GameMode;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;

public class Singleton {

    // Game
    private static Singleton listInstance;
    private ArrayList<String> amongUsPlayers = null;
    private HashMap<String, String> amongUsCurrentlyPlaying = null;
    private ArrayList<String> amongUsAlive = null;
    private ArrayList<String> amongUsDead = null;

    // Tasks
    private ArrayList<String> uploadComplete = null;
    private ArrayList<String> downloadComplete = null;
    private ArrayList<String> navigationComplete = null;
    private HashMap<String, ItemStack> navMap = null;
    private ArrayList<String> clearAsteroidComplete = null;
    private ArrayList<String> swipeCardComplete = null;
    private ArrayList<String> fuelEnginesComplete = null;
    private ArrayList<String> trashChuteComplete = null;
    private ArrayList<String> inspectComplete = null;
    private HashMap<String, Integer> fixWiringComplete = null;

    // Voting
    private ArrayList<String> emerMeetingList = new ArrayList<String>();
    private HashMap<String, Integer> emerMeetingVotes = new HashMap<String, Integer>();
    private ArrayList<String> hasVoted = new ArrayList<String>();
    private HashMap<String, Integer> emerMeetingsCalled = new HashMap<String, Integer>();

    // Sabotage
    private HashMap<String, Boolean> startedMeltdown = null;
    private HashMap<String, Boolean> lightsKilled = null;
    private HashMap<String, Boolean> startedO2 = null;

    // Setup
    private ArrayList<UUID> setup = null;
    private HashMap<UUID, String> waitingForSettingInt = null;
    private HashMap<UUID, String> waitingForLocation = null;
    private HashMap<UUID, String> lastMenu = null;
    private HashMap<UUID, GameMode> preSetupGamemode = null;

    public static Singleton getInstance() {
        if(listInstance == null) {
            listInstance = new Singleton();
        }
        return listInstance;
    }

    private Singleton() {
        amongUsPlayers = new ArrayList<String>();
        amongUsCurrentlyPlaying = new HashMap<String, String>();
        amongUsAlive = new ArrayList<String>();
        amongUsDead = new ArrayList<String>();

        uploadComplete = new ArrayList<String>();
        downloadComplete = new ArrayList<String>();
        navigationComplete = new ArrayList<String>();
        navMap = new HashMap<String, ItemStack>();
        clearAsteroidComplete = new ArrayList<String>();
        swipeCardComplete = new ArrayList<String>();
        fuelEnginesComplete = new ArrayList<String>();
        trashChuteComplete = new ArrayList<String>();
        inspectComplete = new ArrayList<String>();
        fixWiringComplete = new HashMap<String, Integer>();

        emerMeetingList = new ArrayList<String>();
        emerMeetingVotes = new HashMap<String, Integer>();
        hasVoted = new ArrayList<String>();
        emerMeetingsCalled = new HashMap<String, Integer>();

        startedMeltdown = new HashMap<String, Boolean>();
        lightsKilled = new HashMap<String, Boolean>();
        startedO2 = new HashMap<String, Boolean>();

        setup = new ArrayList<UUID>();
        waitingForSettingInt = new HashMap<UUID, String>();
        waitingForLocation = new HashMap<UUID, String>();
        lastMenu = new HashMap<UUID, String>();
        preSetupGamemode = new HashMap<UUID, GameMode>();

    }

    public ArrayList<String> getAmongUsPlayers() {
        return this.amongUsPlayers;
    }
    public HashMap<String, String> getAmongUsCurrentlyPlaying() {
        return this.amongUsCurrentlyPlaying;
    }
    public ArrayList<String> getAmongUsAlive() {
        return this.amongUsAlive;
    }
    public ArrayList<String> getAmongUsDead() {
        return this.amongUsDead;
    }

    public ArrayList<String> getClearAsteroidComplete() {
        return this.clearAsteroidComplete;
    }
    public ArrayList<String> getDownloadComplete() {
        return this.downloadComplete;
    }
    public ArrayList<String> getFuelEnginesComplete() {
        return this.fuelEnginesComplete;
    }
    public ArrayList<String> getInspectComplete() {
        return this.inspectComplete;
    }
    public ArrayList<String> getNavigationComplete() {
        return this.navigationComplete;
    }
    public HashMap<String, ItemStack> getNavMap() {
        return navMap;
    }
    public ArrayList<String> getSwipeCardComplete() {
        return this.swipeCardComplete;
    }
    public ArrayList<String> getTrashChuteComplete() {
        return this.trashChuteComplete;
    }
    public ArrayList<String> getUploadComplete() {
        return this.uploadComplete;
    }
    public HashMap<String, Integer> getFixWiringComplete() {
        return this.fixWiringComplete;
    }

    public ArrayList<String> getEmerMeetingList() {
        return this.emerMeetingList;
    }
    public ArrayList<String> getHasVoted() {
        return this.hasVoted;
    }
    public HashMap<String, Integer> getEmerMeetingVotes() {
        return this.emerMeetingVotes;
    }
    public HashMap<String, Integer> getEmerMeetingsCalled() {
        return this.emerMeetingsCalled;
    }

    public HashMap<String, Boolean> getStartedMeltdown() {
        return this.startedMeltdown;
    }
    public HashMap<String, Boolean> getLightsKilled() {
        return this.lightsKilled;
    }
    public HashMap<String, Boolean> getStartedO2() {
        return this.startedO2;
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
    public HashMap<UUID, String> getWaitingForSettingInt() { return this.waitingForSettingInt; }
    public HashMap<UUID, String> getWaitingForLocation() { return this.waitingForLocation; }

}
