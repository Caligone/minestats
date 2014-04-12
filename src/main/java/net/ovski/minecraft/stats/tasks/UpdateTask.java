package net.ovski.minecraft.stats.tasks;

import java.util.Date;

import net.ovski.minecraft.stats.HttpApiManager;
import net.ovski.minecraft.stats.PlayerStats;
import net.ovski.minecraft.stats.StatsPlugin;

import org.bukkit.scheduler.BukkitRunnable;

/**
 * SaveStatsTask
 * 
 * Save the statistic each period (= timeBetweensaves in config.yml)
 * 
 * @author baptiste <baptiste.bouchereau@gmail.com>
 */
public class UpdateTask extends BukkitRunnable
{
    private StatsPlugin plugin;

    /**
     * Constructor
     * 
     * @param plugin
     */
    public UpdateTask(StatsPlugin plugin)
    {
        this.plugin = plugin;
    }

    public void run()
    {
        long thisTime = new Date().getTime();
        for (PlayerStats stats : StatsPlugin.playerStatsList) {
            //set the time played by the player
            long timeOnSave = new Date().getTime();
            long timePlayed = timeOnSave-stats.getTimeSinceLastSave();
            stats.setTimePlayed(stats.getTimePlayed()+timePlayed);
            //reset the time since the last save for next time
            stats.setTimeSinceLastSave(timeOnSave);
            HttpApiManager.updatePlayerStats(stats);
        }
        StatsPlugin.lastSaveTime = thisTime;
    }
}