package net.ovski.exceptions;

public class ServerNotFoundException extends Exception
{
    private static final long serialVersionUID = 3450524107575524126L;

    /**
     * Constructor
     */
    public ServerNotFoundException(String error)
    {
	super("The server sent an error back : "+error);
    }
}
