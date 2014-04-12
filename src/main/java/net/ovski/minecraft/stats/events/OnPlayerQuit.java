package net.ovski.minecraft.stats.events;

import java.util.Date;

import net.ovski.minecraft.stats.HttpApiManager;
import net.ovski.minecraft.stats.PlayerStats;
import net.ovski.minecraft.stats.StatsPlugin;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * OnPlayerQuit
 * 
 * Things to do on player quit event
 *
 * @author baptiste <baptiste.bouchereau@gmail.com>
 */
public class OnPlayerQuit implements Listener
{
    StatsPlugin plugin;

    /**
     * Constructor
     * 
     * @param plugin
     */
    public OnPlayerQuit(StatsPlugin plugin)
    {
        this.plugin = plugin;
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    /**
     * On player quit
     * 
     * @param event
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        //update the stats of the player and remove him from the list
        Player player = event.getPlayer();
        try {
            PlayerStats playerStats = StatsPlugin.getPlayerStats(player.getName());
            if (plugin.getConfig().getBoolean("StatsToBeRegistered.timeplayed")) {
                long timeOnQuit = new Date().getTime();
                long timePlayed = timeOnQuit-playerStats.getTimeSinceLastSave();
                playerStats.setTimePlayed(playerStats.getTimePlayed()+timePlayed);
            }
            HttpApiManager.updatePlayerStats(playerStats);
            StatsPlugin.playerStatsList.remove(playerStats);
        } catch (NullPointerException e) {
            return; 
        }
    }
}