package me.codingcookie.amongus.commands.subcommands.setupmodule;

import me.codingcookie.amongus.AmongUs;

public class CheckSetup {

    private AmongUs plugin;

    public CheckSetup(AmongUs plugin){
        this.plugin = plugin;
    }

    public Boolean checkLocations(){
        if(!checkLobby() ||
                !checkEmergencyMeeting() ||
                !checkVents() ||
                !checkTask("upload") ||
                !checkTask("download") ||
                !checkTask("navigation") ||
                !checkTask("clearasteroid") ||
                !checkTask("swipecard") ||
                !checkTask("fuelengines") ||
                !checkTask("trashchute") ||
                !checkTask("inspect") ||
                !checkSabotage("meltdown") ||
                !checkSabotage("fixlights") ||
                !checkSabotage("cleano2")){
            return false;
        }
        return true;
    }

    public Boolean checkAllTasks(){
        if(!checkTask("upload") ||
                !checkTask("download") ||
                !checkTask("navigation") ||
                !checkTask("clearasteroid") ||
                !checkTask("swipecard") ||
                !checkTask("fuelengines") ||
                !checkTask("trashchute") ||
                !checkTask("inspect")){
            return false;
        }
        return true;
    }

    public Boolean checkAllSabotages(){
        if(!checkSabotage("meltdown") ||
                !checkSabotage("fixlights") ||
                !checkSabotage("cleano2")){
            return false;
        }
        return true;
    }

    public Boolean checkLobby(){
        if(!plugin.getConfig().getBoolean("location.lobby.setup")){
            return false;
        }
        return true;
    }
    public Boolean checkEmergencyMeeting(){
        for (int i = 1; i <= 10; i++) {
            if(!plugin.getConfig().getBoolean("location.emergencymeeting." + i + ".setup")){
                return false;
            }
        }
        return true;
    }
    public Boolean checkVents(){
        for (int i = 1; i <= 6; i++) {
            if(!plugin.getConfig().getBoolean("location.vent." + i + ".setup")){
                return false;
            }
        }
        return true;
    }
    public Boolean checkTask(String task){
        for (int i = 1; i <= 3; i++) {
            if(!plugin.getConfig().getBoolean("location.task.fixwiring." + i + ".setup")){
                return false;
            }
        }
        if(!plugin.getConfig().getBoolean("location.task." + task + ".setup")){
            return false;
        }
        return true;
    }
    public Boolean checkSabotage(String sabotage){
        for (int i = 1; i <= 2; i++) {
            if(!plugin.getConfig().getBoolean("location.sabotage." + sabotage + "." + i + ".setup")){
                return false;
            }
        }

        return true;
    }
}
