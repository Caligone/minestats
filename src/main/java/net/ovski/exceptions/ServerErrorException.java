package net.ovski.exceptions;

public class ServerErrorException extends Exception
{
    private static final long serialVersionUID = 3450524107575524126L;

    /**
     * Constructor
     */
    public ServerErrorException(String error)
    {
	super("The server sent an error back : "+error);
    }
}
