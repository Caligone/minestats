package net.ovski.minecraft.stats.commands;

import java.util.Date;

import net.ovski.minecraft.stats.HttpApiManager;
import net.ovski.minecraft.stats.PlayerStats;
import net.ovski.minecraft.stats.StatsPlugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

/**
 * StatsCommand
 * 
 * this command permit to display statistics of a player
 * 
 * @author baptiste <baptiste.bouchereau@gmail.com>
 */
public class StatsCommand implements CommandExecutor
{
    private StatsPlugin plugin;

    /**
     * Constructor
     * 
     * @param plugin
     */
    public StatsCommand(StatsPlugin plugin)
    {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (sender instanceof Player) {
            Player commandPlayer = (Player) sender;
            PlayerStats stats = StatsPlugin.getPlayerStats(commandPlayer.getName());
            if (stats == null) {
                commandPlayer.sendMessage(ChatColor.RED+"Vous devez vous enregistrer!");

                return true;
            }
            if (args.length == 0) {
                PlayerStats playerStats = StatsPlugin.getPlayerStats(commandPlayer.getName());
                if (plugin.getConfig().getBoolean("StatsToBeRegistered.timeplayed")) {
                    //set the time played by the player
                    long timeOnCommand = new Date().getTime();
                    long timePlayed = timeOnCommand-playerStats.getTimeSinceLastSave();
                    playerStats.setTimePlayed(playerStats.getTimePlayed()+timePlayed);
                    //reset the time since the last save for next time
                    playerStats.setTimeSinceLastSave(timeOnCommand);
                }

                plugin.reloadConfig();
                commandPlayer.sendMessage(getFormettedStats(playerStats));

                return true;
            }

            if (args.length == 1) {
                try {
                    String playerName = plugin.getServer().getPlayer(args[0]).getName();
                    PlayerStats playerStats = StatsPlugin.getPlayerStats(playerName);
                    if (plugin.getConfig().getBoolean("StatsToBeRegistered.timeplayed")) {
                        //set the time played by the player
                        long timeOnCommand = new Date().getTime();
                        long timePlayed = timeOnCommand-playerStats.getTimeSinceLastSave();
                        playerStats.setTimePlayed(playerStats.getTimePlayed()+timePlayed);
                        //reset the time since the last save for next time
                        playerStats.setTimeSinceLastSave(timeOnCommand);
                    }
                    plugin.reloadConfig();
                    commandPlayer.sendMessage(getFormettedStats(playerStats));

                    return true;
                } catch (NullPointerException e) {
                    //we check if the required player exists in database
                    PlayerStats playerStats = HttpApiManager.getPlayerStats(args[0]);
                    if (playerStats != null) {
                        plugin.reloadConfig();
                        commandPlayer.sendMessage(getFormettedStats(playerStats));
                    } else {
                        commandPlayer.sendMessage(ChatColor.RED+args[0]+" est inconnu sur ce serveur");
                    }

                    return true;
                }
            }
 
            commandPlayer.sendMessage(ChatColor.RED+"Syntaxe de la commande: /stats nomJoueur (nomJoueur facultatif pour vos propres statistiques)");

            return true;
        }

        return false;
    }

    /**
     * getFormettedStats method format the statistic of the player who launch the command in a string
     * 
     * @param playerStats
     * @return String formattedStats
     */
    public String getFormettedStats(PlayerStats playerStats)
    {
        boolean containStats = false;
        String blockBreakString = "";
        if (plugin.getConfig().getBoolean("StatsToBeRegistered.blockBreak")) {
            containStats = true;
            blockBreakString = ChatColor.BLUE+"blocs cassés: "+ChatColor.WHITE+playerStats.getBlocksBroken()+"\n";
        }
        String blockPlaceString = "";
        if (plugin.getConfig().getBoolean("StatsToBeRegistered.blockPlace")) {
            containStats = true;
            blockPlaceString =  ChatColor.BLUE+"blocs placés: "+ChatColor.WHITE+playerStats.getBlocksPlaced()+"\n";
        }
        String deathsString = "";
        if (plugin.getConfig().getBoolean("StatsToBeRegistered.deaths")) {
            containStats = true;
            deathsString =  ChatColor.BLUE+"tués: "+ChatColor.WHITE+playerStats.getKillNumber()+"\n"
                    + ChatColor.BLUE+"morts au combat: "+ChatColor.WHITE+playerStats.getNormalDeaths()+"\n"
                    + ChatColor.BLUE+"morts stupides: "+ChatColor.WHITE+playerStats.getStupidDeaths()+"\n"
                    + ChatColor.BLUE+"ratio tués/morts: "+ChatColor.WHITE+String.valueOf(playerStats.getRatio())+"\n";
        }
        String timeplayedString = "";
        if (plugin.getConfig().getBoolean("StatsToBeRegistered.timeplayed")) {
            containStats = true;
            timeplayedString = ChatColor.BLUE+"temps de jeu: "+ChatColor.WHITE+playerStats.getFormattedTimePlayed()+"\n";
        }
        String verbosityString = "";
        if (plugin.getConfig().getBoolean("StatsToBeRegistered.verbosity")) {
            containStats = true;
            verbosityString = ChatColor.BLUE+"bavardise: "+ChatColor.WHITE+playerStats.getVerbosity()+"\n";
        }

        if(containStats) {
            return "--------- "+playerStats.getPseudo()+" - Statistiques ---------\n"
                + blockBreakString
                + blockPlaceString
                + deathsString
                + timeplayedString
                + verbosityString
            ;
        } else {
            return "Desole, aucune statistique n'est activee";
        }
    }

}
