package net.ovski.minecraft.stats;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import net.ovski.exceptions.KeyNotSetException;
import net.ovski.exceptions.ServerNotFoundException;
import net.ovski.minecraft.stats.commands.*;
import net.ovski.minecraft.stats.events.*;
import net.ovski.minecraft.stats.tasks.UpdateTask;

import org.bukkit.Bukkit;
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
     * The api url
     */
    public static String apiUrl;

    /**
     * The configuration
     */
    public static FileConfiguration config;

    /**
     * initVariables method initialize the variables of the plugin
     */
    public void initVariables()
    {
	StatsPlugin.config = this.getConfig();
        StatsPlugin.timeBetweenSaves = 30; // in seconds;
        StatsPlugin.lastSaveTime = new Date().getTime();
        StatsPlugin.playerStatsList  = new ArrayList<PlayerStats>();
        StatsPlugin.apiUrl = "http://localhost:1337";
    }

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
        try {
	    HttpApiManager.serverConnect();
	    new UpdateTask().runTaskTimer(this, 400, this.getConfig().getInt("TimebetweenSaves")*20);
	    this.getLogger().info(this.getName()+" v"+this.getDescription().getVersion()+" enabled");
	} catch (KeyNotSetException e) {
	    this.getLogger().warning(
	        "The key is not set in the configuration file. " +
	        "You must edit you config.yml file and add the following line : 'key: myKey'."
            );
	    Bukkit.getPluginManager().disablePlugin(this);
	} catch (ServerNotFoundException e) {
	    this.getLogger().warning(
		"The key set in the configuration file is wrong, or your server has not been initialized yet. " +
		"Contact minelog administrators for more informations."
	    );
	    Bukkit.getPluginManager().disablePlugin(this);
	}
    }

    /**
     * onDisable method called when the plugin is unloading
     */
    @Override
    public void onDisable()
    {
        // Save all the stats datas
        for (PlayerStats playerStats : StatsPlugin.playerStatsList) {
            long timeOnServerDisable = new Date().getTime();
            long timePlayed = timeOnServerDisable-playerStats.getTimeSinceLastSave();
            playerStats.setTimePlayed(playerStats.getTimePlayed()+timePlayed);
            HttpApiManager.updatePlayerStats(playerStats);
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
        new OnBlockBreak(this);
        new OnBlockPlace(this);
        new OnPlayerDeath(this);
        new OnPlayerChat(this);
        new OnPlayerJoin(this);
        new OnPlayerQuit(this);
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

        // This is used for the /stats command if the player is not in the server but have registered stats
        PlayerStats playerStats = HttpApiManager.getPlayerStats(pseudo);
        if (playerStats != null) {
            long timeOnJoin = new Date().getTime();
            playerStats.setTimeSinceLastSave(timeOnJoin);

            return playerStats;
        }

        return null;
    }
}