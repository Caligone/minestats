package net.ovski.minecraft.stats;

import net.ovski.exceptions.KeyNotSetException;
import net.ovski.exceptions.ServerNotFoundException;
import net.ovski.tools.HttpTools;
import net.ovski.tools.Tools;

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
     * 
     * @return playerStats : A PlayerStats object that contains the playerStats of a player
     * @param pseudo : the pseudo of the player
     */
    public static PlayerStats getPlayerStats(String pseudo)
    {
        return null;
    }

    /**
     * insertKill method insert a new kill in database
     * 
     * @param killerPseudo : Contains the pseudo of the killer
     * @param killedPseudo : Contains the pseudo of the killed player
     * @param weaponId : Contains the id of the weapon use for the kill
     * @param date : Contains the date of the kill
     */
    public static void addKill(String killerPseudo, String killedPseudo, String weaponId, String date)
    {
        
    }

    /**
     * updateStats method update the statistics of a player
     * 
     * @param playerStats : A PlayerStats object that contains the playerStats of a player
     */
    public static void updatePlayerStats(PlayerStats playerStats)
    {
        
    }

    /**
     * playerConnect method create a new player in database, or update last login, then return stats informations anyway
     * 
     * @param pseudo : the pseudo of the player
     * @return PlayerStats : a playerStats object;
     */
    public static PlayerStats playerConnect(String pseudo)
    {
        //String url = StatsPlugin.apiUrl+"/user/create?pseudo="+pseudo;
        //String response = HttpTools.sendHttpRequest(url);

        return null;
    }

    /**
     * Create a server on connection or update it
     * /api/connect?key=:key&name=:name&version=:version&size=:size
     * 
     * @throws ServerNotFoundException 
     * @throws Exception
     */
    public static void serverConnect() throws KeyNotSetException, ServerNotFoundException
    {
	String key = StatsPlugin.config.getString("key");
	if (key == null) {
	    throw new KeyNotSetException();
	} else {
    	    String name = StatsPlugin.config.getString("name");
    	    String size = String.valueOf(Bukkit.getServer().getMaxPlayers());
    	    String version = Tools.getBukkitVersionNumber();
    	    String[][] params = new String [][] {
    	        {"key", key},
    	        {"name", name},
    	        {"version", version},
    	        {"size", size},
    	    };
    	    String url = HttpTools.createApiUrl("/api/connect", params);
    	    JSONObject json = HttpTools.sendHttpRequest(url);
    	    if (Integer.valueOf(json.get("status").toString()) != 0) {
    		String message = (String)json.get("message");
    		if (message.equals("Server not found")) {
    		    throw new ServerNotFoundException(message);
    		}
    		Bukkit.getServer().getPluginManager().getPlugin("MineStats").getLogger()
    		    .warning("The server sent an error back :"+message);
    	    }
    	    // TODO TEST API/PLUGIN VERSION
	}
    }

    /**
     * Update the server heartbeat
     */
    public static void updateHeartbeat()
    {
        
    }

    
}
