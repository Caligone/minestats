package net.ovski.minecraft.stats;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import net.ovski.minecraft.stats.commands.*;
import net.ovski.minecraft.stats.events.*;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * StatsPlugin
 * Main class of the plugin
 * @author Ovski
 */
public class StatsPlugin extends JavaPlugin
{
    /**
     * A list of PlayerStats
     */
    public static List<PlayerStats> playerStatsList;

    /**
     * The time between 2 saves
     */
    public static long timeBetweenSaves;

    /**
     * The last save time
     */
    public static long lastSaveTime;

    /**
     * The configuration
     */
    public static FileConfiguration config;

    /**
     * onEnable method called when the plugin is loading
     */
    @Override
    public void onEnable()
    {
        this.initVariables();
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.listenEvents();
        this.getCommands();
        new SaveStatsTask(this).runTaskTimer(this, 400, this.getConfig().getInt("TimebetweenSaves")*20);
        getLogger().info(this.getName()+" v"+this.getDescription().getVersion()+" enabled");
    }

    /**
     * onDisable method called when the plugin is unloading
     */
    @Override
    public void onDisable()
    {
        // Save all the stats datas
        for (PlayerStats playerStats : StatsPlugin.playerStatsList) {
            if (StatsPlugin.config.getBoolean("StatsToBeRegistered.timeplayed")) {
                long timeOnServerDisable = new Date().getTime();
                long timePlayed = timeOnServerDisable-playerStats.getTimeSinceLastSave();
                playerStats.setTimePlayed(playerStats.getTimePlayed()+timePlayed);
            }
            HTTPAPIManager.updatePlayerStats(playerStats);
        }
        getLogger().info(this.getName()+" v"+this.getDescription().getVersion()+" disabled");
    }

    /**
     * getCommands method instantiate the "Commands" class
     */
    public void getCommands()
    {
        getCommand("stats").setExecutor(new StatsCommand(this));
    }

    /**
     * listenEvents method instantiate the "Events" class, according to the config
     */
    public void listenEvents()
    {
        if (this.getConfig().getBoolean("StatsToBeRegistered.blockBreak"))
            new OnBlockBreak(this);
        if (this.getConfig().getBoolean("StatsToBeRegistered.blockPlace"))
            new OnBlockPlace(this);
        if (this.getConfig().getBoolean("StatsToBeRegistered.deaths"))
            new OnPlayerDeath(this);
        if (this.getConfig().getBoolean("StatsToBeRegistered.verbosity"))
            new OnPlayerChat(this);
        new OnPlayerJoin(this);
        new OnPlayerQuit(this);
    }

    /**
     * initVariables method initialize the variables of the plugin
     */
    public void initVariables()
    {
        StatsPlugin.config = this.getConfig();
        StatsPlugin.timeBetweenSaves = StatsPlugin.config.getInt("TimebetweenSaves");
        StatsPlugin.lastSaveTime = new Date().getTime();
        StatsPlugin.playerStatsList  = new ArrayList<PlayerStats>();
    }

    /**
     * getPlayerStats method retrieve a PlayerStats object in the playerStatsList
     * 
     * @param pseudo
     * @return PlayerStats playerStats : Contains the stat object of a player
     */
    public static PlayerStats getPlayerStats(String pseudo)
    {
        for (PlayerStats playerStats : StatsPlugin.playerStatsList) {
            if(playerStats.getPseudo().equals(pseudo)) {
                return playerStats;
            }
        }
        // if the playerStats are not in the list, we check if the player have stats and add them to the list before returning the player
        // This is used for the /stats command if the player is not in the server but have registered stats
        PlayerStats playerStats = HTTPAPIManager.getPlayerStats(pseudo);
        if (playerStats != null) {
            if (StatsPlugin.config.getBoolean("StatsToBeRegistered.timeplayed")) {
                long timeOnJoin = new Date().getTime();
                playerStats.setTimeSinceLastSave(timeOnJoin);
            }
            StatsPlugin.playerStatsList.add(playerStats);

            return playerStats;
        }

        return null;
    }
}