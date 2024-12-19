package ascii_art;

import Exceptions.InvalidCommandException;
import image.Image;
import image.ImageProcess;
import image_char_matching.SubImgCharMatcher;

import java.io.IOException;

/**
 * The Shell class is the main class of the program. It is responsible for the
 * interaction with the user. It reads the input from the user and executes the
 * commands.x
 * The Shell class is a singleton class.
 * The Shell class has the following attributes:
 * keyboardInput - a KeyboardInput object that is used to read the input from the user.
 */
public class Shell {
    ///constants
    private static final char[] defaultChars = {'0', '1','2','3','4','5','6','7','8','9'};
    private static final String EXIT = "exit";
    private static final String prefix = ">>> ";
    private static final String defaultImage = "cat.jpeg";
    private static final int defaultRes = 128;
    private static final String empty = "Inavalid empty input";
    ///////////////


    /**
     * The default constructor of the Shell class.
     */
    public Shell() {
    }

    /**
     * Initializes the default values of the program.
     *
     * @return an AsciiArtAlgorithm object with the default values.
     * @throws IOException if the image file is not found.
     */

    public AsciiArtAlgorithmManager defaultInit() throws IOException {
        SubImgCharMatcher subImgCharMatcher = new SubImgCharMatcher(defaultChars);
        Image image = new Image(defaultImage);
        ImageProcess imageProcess = new ImageProcess(image);
        return new AsciiArtAlgorithmManager
                (imageProcess, subImgCharMatcher, OutputOption.CONSOLE, defaultRes);
    }

    /**
     * The main method of the program. It is responsible for the interaction with the user.
     * It reads the input from the user and executes the commands.
     * The main method reads the input from the user and executes the commands.
     * The main method uses the KeyboardInput class to read the input from the user.
     * The main method uses the ShellCommands class to execute the commands.
     */
    public void run() {
        AsciiArtAlgorithmManager asciiArtAlgorithmManager;
        try {
            asciiArtAlgorithmManager = defaultInit();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
        String[] execute;
        ShellCommands shellCommands = new ShellCommands(asciiArtAlgorithmManager);
        String input;

        //continue reading the input from the user until the user types "exit"
        while (true) {
            System.out.print(prefix);
            input = KeyboardInput.readLine();
            if (input.isEmpty()) {
                System.out.println(empty);
                continue;
            }
            //if the user types "exit", the program stops
            if (input.equals(EXIT)) {
                break;
            }
            //execute the command
            execute = input.split(" ");
            try {
                shellCommands.execute(execute);
            } catch (InvalidCommandException e) {
                System.out.println(e.getMessage());
            }
        }

    }


    /**
     * The main method of the program. It creates a Shell object and runs the program.
     *
     * @param args the command line arguments
     */

    public static void main(String[] args) {
        Shell shell = new Shell();
        shell.run();

    }
}
