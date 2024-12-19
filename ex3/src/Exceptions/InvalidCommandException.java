package Exceptions;
/**
 * The InvalidCommandException class is an exception that is thrown when the user
 * enters an invalid command.
 */
public class InvalidCommandException extends Exception{
    /**
     * The constructor of the InvalidCommandException class.
     * @param message the message of the exception
     */
    public InvalidCommandException(String message) {
        super(message);
    }
}
