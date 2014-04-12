package net.ovski.minecraft.stats;


/**
 * HTTPAPIManager
 * 
 * Manage request to be sent
 * 
 * @author baptiste
 */
public class HTTPAPIManager
{
    /**
     * getPlayerStats method get the statistics of a player
     * This method is used in case a player request another player stats, even though this one is not currently connected
     * 
     * @return playerStats : A PlayerStats object that contains the playerStats of a player
     * @param pseudo : the pseudo of the player
     */
    public static PlayerStats getPlayerStats(String speudo)
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
     * playerCOnnect method create a new player in database, or update last login, then return stats informations anyway
     * 
     * @param pseudo : the pseudo of the player
     * @return PlayerStats : a playerStats object;
     */
    public static PlayerStats playerConnect(String pseudo)
    {
        return null;
    }

    /**
     * Create a server on connection or update it
     */
    public static void serverConnect()
    {
        
    }

    /**
     * Update the server heartbeat
     */
    public static void updateHeartbeat()
    {
        
    }
}
