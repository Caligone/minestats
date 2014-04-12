package net.ovski.minecraft.stats;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

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
        String url = StatsPlugin.apiUrl;
        url += "/user/create?pseudo="+pseudo;
        URL obj;
        try {
            obj = new URL(url);
            HttpURLConnection connexion;
            try {
                connexion = (HttpURLConnection) obj.openConnection();
                try {
                    connexion.setRequestMethod("GET");
                    connexion.setRequestProperty("User-Agent", "Mozilla/5.0");

                    int responseCode = connexion.getResponseCode();
                    System.out.println("\nSending 'GET' request to URL : " + url);
                    System.out.println("Response Code : " + responseCode);

                    BufferedReader in = new BufferedReader(new InputStreamReader(connexion.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    System.out.println(response.toString());
                    return null;
                } catch (ProtocolException e) {
                    e.printStackTrace();
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
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
