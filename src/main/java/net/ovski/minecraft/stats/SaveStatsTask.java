package net.ovski.minecraft.stats;

import java.util.Date;

import org.bukkit.scheduler.BukkitRunnable;

/**
 * SaveStatsTask
 * 
 * Save the statistic each period (= timeBetweensaves in config.yml)
 * 
 * @author baptiste <baptiste.bouchereau@gmail.com>
 */
public class SaveStatsTask extends BukkitRunnable
{
    private StatsPlugin plugin;

    /**
     * Constructor
     * 
     * @param plugin
     */
    public SaveStatsTask(StatsPlugin plugin)
    {
        this.plugin = plugin;
    }

    public void run()
    {
        long thisTime = new Date().getTime();
        for (PlayerStats stats : StatsPlugin.playerStatsList) {
            if (plugin.getConfig().getBoolean("StatsToBeRegistered.timeplayed")) {
                //set the time played by the player
                long timeOnSave = new Date().getTime();
                long timePlayed = timeOnSave-stats.getTimeSinceLastSave();
                stats.setTimePlayed(stats.getTimePlayed()+timePlayed);
                //reset the time since the last save for next time
                stats.setTimeSinceLastSave(timeOnSave);
            }
            HTTPAPIManager.updatePlayerStats(stats);
        }
        StatsPlugin.lastSaveTime = thisTime;
    }
}