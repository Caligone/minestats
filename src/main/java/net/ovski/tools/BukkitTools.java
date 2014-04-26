package net.ovski.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;

public class BukkitTools
{
    /**
     * Get bukkit version
     * 
     * @return String : the Bukkit version
     */
    public static String getBukkitVersionNumber()
    {
        String version = Bukkit.getServer().getVersion();
        Pattern pattern = Pattern.compile("([0-9]\\.[0-9]\\.[0-9])");
        Matcher matcher = pattern.matcher(version);
         
        if (matcher.find()) {
            return matcher.group(1);
        }

        return null;
    }
}
