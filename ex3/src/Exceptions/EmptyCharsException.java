package Exceptions;

/**
 * The EmptyCharsException class is an exception that is thrown when the user tries to
 * create a SubImgCharMatcher object with an empty array of characters.
 */
public class EmptyCharsException extends Exception{
    /**
     * The constructor of the EmptyCharsException class.
     * @param message the message of the exception
     */
    public EmptyCharsException(String message) {
        super(message);
    }
}
