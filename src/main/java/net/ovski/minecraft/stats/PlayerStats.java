package net.ovski.minecraft.stats;

/**
 * PlayerStats
 * 
 * The statistics of a player
 * 
 * @author baptiste <baptiste.bouchereau@gmail.com>
 */
public class PlayerStats
{
    private String pseudo;
    private int blocksBroken;
    private int blocksPlaced;
    private int stupidDeaths;
    private int normalDeaths;
    private int killNumber;
    private long timePlayed; //in milliseconds
    private int verbosity;
    private long timeSinceLastSave; //in milliseconds

    /**
     * getFormattedTimePlayed method format the TimePlayed in String
     * 
     * @return String formattedTime : Contains the formatted (Xh, Xmin, Xsec) played time
     */
    public String getFormattedTimePlayed()
    {
        long seconds = (timePlayed / 1000) % 60;
        long minutes = (timePlayed / (1000*60)) % 60;
        long hours   = timePlayed / (1000*60*60);
        return String.valueOf(hours)+" h, "+String.valueOf(minutes)+" min, "+String.valueOf(seconds)+" sec";
    }

    /**
     * getRatio method calculate the kill/death ratio
     * 
     * @return float ratio
     */
    public float getRatio()
    {
        if ((stupidDeaths+normalDeaths)==0) {
            return (float) killNumber;
        } else {
            return (float) killNumber/(stupidDeaths+normalDeaths);
        }
    }

    /**
     * getTotalDeaths method calcuate the total number of deaths (normal and stupid ones)
     * 
     * @return int totalDeaths
     */
    public int getTotalDeaths()
    {
        return stupidDeaths+normalDeaths;
    }

    /**
     * Increment broken blocks
     */
    public void incrementBlocksBroken()
    {
        this.blocksBroken++;
    }

    /**
     * Increment placed blocks
     */
    public void incrementBlocksPlaced()
    {
        this.blocksPlaced++;
    }

    /**
     * Increment stupid deaths
     */
    public void incrementStupidDeaths()
    {
        this.stupidDeaths++;
    }

    /**
     * Increment stupid deaths
     */
    public void incrementNormalDeaths()
    {
        this.normalDeaths++;
    }

    /**
     * Increment kills
     */
    public void incrementKills()
    {
        this.killNumber++;
    }

    /**
     * Increment verbosity
     */
    public void incrementVerbosity()
    {
        this.verbosity++;
    }

    /**
     * Get pseudo
     * 
     * @return the pseudo
     */
    public String getPseudo()
    {
        return pseudo;
    }

    /**
     * Set the pseudo
     * 
     * @param pseudo the pseudo to set
     */
    public void setPseudo(String pseudo)
    {
        this.pseudo = pseudo;
    }

    /**
     * Get broken blocks
     * 
     * @return the number of broken blocks
     */
    public int getBlocksBroken()
    {
        return blocksBroken;
    }

    /**
     * Set broken blocks
     * 
     * @param blocksBroken the blocksBroken to set
     */
    public void setBlocksBroken(int blocksBroken)
    {
        this.blocksBroken = blocksBroken;
    }

    /**
     * Get blocks placed
     * 
     * @return the blocksPlaced
     */
    public int getBlocksPlaced()
    {
        return blocksPlaced;
    }

    /**
     * Set blocks placed
     * 
     * @param blocksPlaced the blocksPlaced to set
     */
    public void setBlocksPlaced(int blocksPlaced)
    {
        this.blocksPlaced = blocksPlaced;
    }

    /**
     * Get stupid deaths
     * 
     * @return the stupidDeaths
     */
    public int getStupidDeaths()
    {
        return stupidDeaths;
    }

    /**
     * Set stupid deaths
     * 
     * @param stupidDeaths the stupidDeaths to set
     */
    public void setStupidDeaths(int stupidDeaths)
    {
        this.stupidDeaths = stupidDeaths;
    }

    /**
     * Get normal deaths
     * 
     * @return the normalDeaths
     */
    public int getNormalDeaths()
    {
        return normalDeaths;
    }

    /**
     * Set normal deaths
     * 
     * @param normalDeaths the normalDeaths to set
     */
    public void setNormalDeaths(int normalDeaths)
    {
        this.normalDeaths = normalDeaths;
    }

    /**
     * Get the kill number
     * 
     * @return the killNumber
     */
    public int getKillNumber()
    {
        return killNumber;
    }

    /**
     * Set the kill number
     * 
     * @param killNumber the killNumber to set
     */
    public void setKillNumber(int killNumber)
    {
        this.killNumber = killNumber;
    }

    /**
     * Get the time played
     * 
     * @return the timePlayed
     */
    public long getTimePlayed()
    {
        return timePlayed;
    }

    /**
     * Set the time played
     * 
     * @param timePlayed the timePlayed to set
     */
    public void setTimePlayed(long timePlayed)
    {
        this.timePlayed = timePlayed;
    }

    /**
     * Get the verbosity
     * (number of messages wrote in chat)
     * 
     * @return the verbosity
     */
    public int getVerbosity()
    {
        return verbosity;
    }

    /**
     * Set the verbosity
     * (number of messages wrote in chat)
     * 
     * @param verbosity the verbosity to set
     */
    public void setVerbosity(int verbosity)
    {
        this.verbosity = verbosity;
    }

    /**
     * Get time since last save
     * 
     * @return the timeSinceLastSave
     */
    public long getTimeSinceLastSave()
    {
        return timeSinceLastSave;
    }

    /**
     * Set time since lastSave
     * 
     * @param timeSinceLastSave the timeSinceLastSave to set
     */
    public void setTimeSinceLastSave(long timeSinceLastSave)
    {
        this.timeSinceLastSave = timeSinceLastSave;
    }
}
