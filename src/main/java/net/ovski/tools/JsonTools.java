package net.ovski.tools;

import net.ovski.minecraft.stats.PlayerStats;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JsonTools
{
    /**
     * Get a server id from json with is key
     * 
     * @param playerConnectJson : JSONObject
     * @param key : String
     * @return serverId : String
     */
    public static String getServerId(JSONObject playerConnectJson, String key)
    {
	JSONObject playerJson = (JSONObject) playerConnectJson.get("player");
	JSONArray serversJson = (JSONArray) playerJson.get("servers");
	for (Object server : serversJson) {
	    JSONObject serverJson = (JSONObject) server;
	    if (serverJson.get("key").toString().equals(key)) {
	        return serverJson.get("id").toString();
            }
        }

	return null;
    }

    /**
     * Get player stats from json with the server id
     * 
     * @param playerConnectJson : JSONObject
     * @param serverId : String
     * @return stats : PlayerStats
     */
    public static PlayerStats getPlayerStats(JSONObject playerConnectJson, String serverId)
    {
	JSONObject playerJson = (JSONObject) playerConnectJson.get("player");
	JSONArray statsJson = (JSONArray) playerJson.get("stats");
	for (Object stat : statsJson) {
	    JSONObject statJson = (JSONObject) stat;
	    if (statJson.get("server").toString().equals(serverId)) {
	        PlayerStats playerStats = new PlayerStats();
	        playerStats.setBlocksBroken(Integer.parseInt(statJson.get("blocksBroken").toString()));
	        playerStats.setBlocksPlaced(Integer.parseInt(statJson.get("blocksPlaced").toString()));
	        playerStats.setTimePlayed(Integer.parseInt(statJson.get("timePlayed").toString()));
	        playerStats.setVerbosity(Integer.parseInt(statJson.get("verbosity").toString()));
	        playerStats.setKillNumber(Integer.parseInt(statJson.get("kills").toString()));
	        playerStats.setNormalDeaths(Integer.parseInt(statJson.get("pvpDeaths").toString()));
	        playerStats.setStupidDeaths(Integer.parseInt(statJson.get("stupidDeaths").toString()));

	        return playerStats;
            }
        }

	return null;
    }
}
