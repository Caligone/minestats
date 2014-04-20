package net.ovski.exceptions;

public class KeyNotSetException extends Exception
{
    private static final long serialVersionUID = 5497424085362739103L;

    /**
     * Constructor
     */
    public KeyNotSetException()
    {
	super("The key is not set in the configuration file");
    }
}
