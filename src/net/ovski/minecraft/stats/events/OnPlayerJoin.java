package net.ovski.minecraft.stats.events;

import java.util.Date;

import net.ovski.minecraft.stats.StatsPlugin;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import net.ovski.minecraft.api.entities.PlayerStats;
import net.ovski.minecraft.api.mysql.MysqlPlayerManager;

public class OnPlayerJoin implements Listener
{
    StatsPlugin plugin;

    public OnPlayerJoin(StatsPlugin plugin)
    {
        this.plugin = plugin;
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
    	Player player = event.getPlayer();
        //add the player to the list
        PlayerStats playerStats = MysqlPlayerManager.getStats(player.getName());
        if (playerStats != null)
        {
	        if (plugin.getConfig().getBoolean("StatsToBeRegistered.timeplayed"))
	        {
	            long timeOnJoin = new Date().getTime();
	            playerStats.setTimeSinceLastSave(timeOnJoin);
	        }
	        StatsPlugin.playerStatsList.add(playerStats);
        }
    }
}