package ascii_art;

import java.util.Scanner;

class KeyboardInput
{
    private static KeyboardInput keyboardInputObject = null;
    private Scanner scanner;
    
    private KeyboardInput()
    {
        this.scanner = new Scanner(System.in);
    }

    public static KeyboardInput getObject()
    {
        if(KeyboardInput.keyboardInputObject == null)
        {
            KeyboardInput.keyboardInputObject = new KeyboardInput();
        }
        return KeyboardInput.keyboardInputObject;
    }
    /**
     * Reads a line from the keyboard and returns it as a string.
     * @return the line read from the keyboard
     */

    public static String readLine()
    {
        return KeyboardInput.getObject().scanner.nextLine().trim();
    }
}