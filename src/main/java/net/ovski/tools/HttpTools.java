package net.ovski.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import net.ovski.minecraft.stats.StatsPlugin;

public class HttpTools
{
    /**
     * Create an url for the api
     * 
     * @param baseUrl String (/api/connect)
     * @param params String [][] ([] => 'param_name', 'param_value')
     * @return String url
     */
    public static String createApiUrl(String baseUrl, String[][] params)
    {
	String url = StatsPlugin.apiUrl+baseUrl;
	int j = 0;
	for (String[] param : params) {
	    j++;
	    if (j==1) {
		url += "?";
	    }
	    if (param[1] != null) {
		url += param[0]+"="+param[1];
		if (j!=params.length) {
		    url += "&";
		}
	    }
	}

	return url;
    }

    /**
     * Get response from the http request determined by the url
     * 
     * @param String url
     * @return JSONObject response
     */
    public static JSONObject sendHttpRequest(String url)
    {
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
                    System.out.println("Sending 'GET' request to " + url +". Response code is "+responseCode);
                    BufferedReader in = new BufferedReader(new InputStreamReader(connexion.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    try {
                	System.out.println("Response is : "+response.toString());
			JSONObject jsonObject = (JSONObject) new JSONParser().parse(response.toString());
			return jsonObject;
		    } catch (ParseException e) {
			e.printStackTrace();
			return null;
		    }
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

}
