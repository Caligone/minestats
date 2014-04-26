package net.ovski.minecraft.stats;

import net.ovski.exceptions.KeyNotSetException;
import net.ovski.exceptions.ServerErrorException;
import net.ovski.tools.HttpTools;
import net.ovski.tools.BukkitTools;
import net.ovski.tools.JsonTools;

import org.bukkit.Bukkit;
import org.json.simple.JSONObject;

/**
 * HTTPAPIManager
 * 
 * Manage request to be sent
 * 
 * @author baptiste
 */
public class HttpApiManager
{
    /**
     * getPlayerStats method get the statistics of a player
     * This method is used in case a player request another player stats, even though this one is not currently connected
     * /api/player?key=:key&pseudo=:pseudo
     *
     * @return playerStats : A PlayerStats object that contains the playerStats of a player
     * @param pseudo : the pseudo of the player
     */
    public static PlayerStats getPlayerStats(String pseudo)
    {
	String key = StatsPlugin.key;
	String[][] params = new String [][] {
	    {"key", key},
	    {"pseudo", pseudo}
	};
	String url = HttpTools.createApiUrl("/api/player", params);
	JSONObject json = HttpTools.sendHttpRequest(url);
	if (Integer.valueOf(json.get("status").toString()) != 0) {
	    String message = (String)json.get("message");
	    Bukkit.getPluginManager().getPlugin("MineStats").getLogger()
	        .warning("The server sent back an error : "+message);

	    return null;
    	} else {
    	    String serverId = JsonTools.getServerId(json, key);
    	    return JsonTools.getPlayerStats(json, serverId);
    	}
    }

    /**
     * insertKill method insert a new kill in database
     * /api/playerkilled?key=:key&killer=:killer&killed=:killed&weapon=:weapon&date=:date
     * 
     * @param killerPseudo : Contains the pseudo of the killer
     * @param killedPseudo : Contains the pseudo of the killed player
     * @param weaponId : Contains the id of the weapon use for the kill
     * @param date : Contains the date of the kill
     */
    public static void addKill(String killerPseudo, String killedPseudo, String weaponId, String date)
    {
	String key = StatsPlugin.key;
	String[][] params = new String [][] {
	    {"key", key},
	    {"killer", killerPseudo},
	    {"killed", killedPseudo},
	    {"weapon", weaponId},
	    {"date", date}
	};
	String url = HttpTools.createApiUrl("/api/playerkilled", params);
	JSONObject json = HttpTools.sendHttpRequest(url);
	if (Integer.valueOf(json.get("status").toString()) != 0) {
	    String message = (String)json.get("message");
	    Bukkit.getPluginManager().getPlugin("MineStats").getLogger()
	        .warning("The server sent back an error : "+message);
    	}
    }

    /**
     * updateStats method update the statistics of a player
     * /api/updatePlayer?key=:key&pseudo=:pseudo
     *  &verbosity=:verbosity&blocksBroken=:blocksBroken
     *  &blocksPlaced=:blocksPlaced&stupidDeaths=:stupidDeaths&level=:level
     * 
     * @param playerStats : A PlayerStats object that contains the playerStats of a player
     */
    public static void updatePlayerStats(PlayerStats playerStats)
    {
	String key = StatsPlugin.key;
	String pseudo = playerStats.getPseudo();
	String[][] params = new String [][] {
	    {"key", key},
	    {"pseudo", pseudo},
	    {"verbosity", String.valueOf(playerStats.getVerbosity())},
	    {"blocksBroken", String.valueOf(playerStats.getBlocksBroken())},
	    {"blocksPlaced", String.valueOf(playerStats.getBlocksPlaced())},
	    {"stupidDeaths", String.valueOf(playerStats.getStupidDeaths())},
	    {"level", String.valueOf(Bukkit.getPlayer(pseudo).getTotalExperience())}
	};
	String url = HttpTools.createApiUrl("/api/updatePlayer", params);
	JSONObject json = HttpTools.sendHttpRequest(url);
	if (Integer.valueOf(json.get("status").toString()) != 0) {
	    String message = (String)json.get("message");
	    Bukkit.getPluginManager().getPlugin("MineStats").getLogger()
	        .warning("The server sent back an error : "+message);
    	}
    }

    /**
     * playerConnect method create a new player in database, or update last login, then return stats informations anyway
     * /api/playerconnect?key=:key&pseudo=:pseudo
     * 
     * @param pseudo : the pseudo of the player
     * @return PlayerStats : a playerStats object;
     */
    public static PlayerStats playerConnect(String pseudo)
    {
	String key = StatsPlugin.key;
	String[][] params = new String [][] {
	    {"key", key},
	    {"pseudo", pseudo}
	};
	String url = HttpTools.createApiUrl("/api/playerconnect", params);
	JSONObject json = HttpTools.sendHttpRequest(url);
	if (Integer.valueOf(json.get("status").toString()) != 0) {
	    String message = (String)json.get("message");
	    Bukkit.getPluginManager().getPlugin("MineStats").getLogger()
	        .warning("The server sent back an error : "+message);

	    return null;
    	} else {
    	    String serverId = JsonTools.getServerId(json, key);

    	    return JsonTools.getPlayerStats(json, serverId);
    	}
    }

    /**
     * Create a server on connection or update it
     * /api/connect?key=:key&name=:name&version=:version&size=:size
     * 
     * @throws ServerErrorException 
     * @throws KeyNotSetException
     */
    public static void serverConnect(StatsPlugin plugin)
    {
	String name = StatsPlugin.config.getString("name");
	String size = String.valueOf(Bukkit.getServer().getMaxPlayers());
	String version = BukkitTools.getBukkitVersionNumber();
	String[][] params = new String [][] {
	    {"key", StatsPlugin.key},
	    {"name", name},
	    {"version", version},
	    {"size", size},
	};
	String url = HttpTools.createApiUrl("/api/connect", params);
	JSONObject json = HttpTools.sendHttpRequest(url);
	if (Integer.valueOf(json.get("status").toString()) != 0) {
	    String message = json.get("message").toString();
	    if (message.toString().equals("Server not found")) {
    	        plugin.getLogger().warning(
    	            "The key set in the configuration file could be wrong, the server down or your server has not been initialized yet. " +
    	            "Contact minelog administrators for more informations."
    	        );
    	        Bukkit.getPluginManager().disablePlugin(plugin);
	    } else {
		plugin.getLogger().warning("The server sent back an error : "+message);
	    }
    	}
    }

    /**
     * Update the server heartbeat
     * /api/heartbeat?key=:key
     */
    public static void updateHeartbeat()
    {
	String key = StatsPlugin.key;
	String[][] params = new String [][] {
	    {"key", key}
	};
	String url = HttpTools.createApiUrl("/api/heartbeat", params);
	JSONObject json = HttpTools.sendHttpRequest(url);
	if (Integer.valueOf(json.get("status").toString()) != 0) {
	    String message = (String)json.get("message");
	    Bukkit.getPluginManager().getPlugin("MineStats").getLogger()
	        .warning("The server sent back an error : "+message);
    	}
    }
}
