package ascii_art;
import Exceptions.EmptyCharsException;
import Exceptions.InvalidCommandException;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;

import java.io.IOException;
import java.util.LinkedList;


/**
 * The ShellCommands class is responsible for executing the commands given by the user.
 * It contains methods for adding and removing characters, changing the resolution, setting the image,
 * changing the output method, and running the asciiArt algorithm.
 * The class also contains constants for error messages.
 * The class has a constructor that takes an AsciiArtAlgorithm object as a parameter.
 * The class has a method execute that takes a string array as a
 * parameter and executes the command given by the user.
 * @author Michael messika, Tomer guttman
 */
public class ShellCommands {
    /////////////////////////////Constants
    private final static String CommandError =
            "Did not execute due to incorrect command.";
    private final static String ExceedResolutionError =
            "Did not change resolution due to exceeding boundaries.";
    private static final String ResolutionFormatError=
            "Did not change resolution due to incorrect format.";
    private static final String CharsFormatError=
            "Did not add due to incorrect format.";
    private final static String OutputFormatError =
            "Did not change output method due to incorrect format.";
    private final static String ReplaceImageFormatError =
            "Did not add due to incorrect format.";
    private final static String ImageLoadingError =
            "Did not execute due to problem with image file.";
    private final static String ImageFormatError =
            "Did not execute due to problem with image file.";
    private final static String CharSetSizeError =
            "Did not execute. Charset is too small.";
    private final static String ResChange = "Resolution is set to ";
    private final static String HtmlPth = "out.html";
    private final static String FONT = "Courier New";
    private final static char SpaceChar = ' ';
    private final static String AllChars = "all";
    private final static char RangeChar = '-';
    private final static String SpaceCommand = "space";
    private final static int MinChar = 32;
    private final static int MaxChar = 126;
    private final static String HTML = "html";
    private final static String CONSOLE = "console";


    /////////////////////////////////////////////////////////////////////
    private final AsciiArtAlgorithmManager asciiArtAlgorithmManager;
    private final ConsoleAsciiOutput consoleAsciiOutput = new ConsoleAsciiOutput();
    private final HtmlAsciiOutput htmlAsciiOutput = new HtmlAsciiOutput(HtmlPth,FONT);
    ////////////////////////////////////////////////////////////////////



    /**
     * Constructor for the ShellCommands class.
     * @param asciiArtAlgorithmManager the asciiArtAlgorithm object
     */
    public ShellCommands(AsciiArtAlgorithmManager asciiArtAlgorithmManager) {
        this.asciiArtAlgorithmManager = asciiArtAlgorithmManager;
    }


    /**
     * Checks if the number of arguments is valid.
     * @param execute the command
     * @return true if the number of arguments is valid, false otherwise
     */

    private boolean checkNumArgs(String[] execute) {
        return execute.length >= 2;
    }



    /////////////////////////////////////////// Chars commands
    /**
     * Adds the chars to the list of chars to add.
     * @param charList the list of chars to add
     * @param execute the command
     */
    private void chardToAdd(LinkedList<Character> charList, String[] execute) {
        if (!checkNumArgs(execute)) {
            return;
        }
            //check if the command is to add all chars
            addAllChars(execute[1], charList);
            //check if the command is to add the space char
            addSpaceChar(execute[1], charList);
            //check if the command is to add a single char
            addSingleChar(execute[1], charList);
            //check if the command is to add a range of chars
            rangeCharToAdd(execute[1], charList);
        }


    /**
     * Adds all the chars to the list of chars to add.
     * @param execute  the char to add
     * @param charList the list of chars to add
     */
    private void addAllChars(String execute, LinkedList<Character> charList) {
        if (execute.equals(AllChars)) {
            for (int i = MinChar; i <= MaxChar; i++) {
                charList.add((char) i);
            }
        }
    }

    /**
     * Adds the space char to the list of chars to add.
     * @param execute  the char to add
     * @param charList the list of chars to add
     */
    private void addSpaceChar(String execute, LinkedList<Character> charList) {
        if (execute.equals(SpaceCommand)) {
            charList.add(SpaceChar);
        }
    }


    /**
     * Adds a single char to the list of chars to add.
     * @param execute  the char to add
     * @param charList the list of chars to add
     */
    private void addSingleChar(String execute, LinkedList<Character> charList) {
        if (execute.length() == 1) {
            //check if the char is in the ascii table
            if (execute.charAt(0) >= MinChar && execute.charAt(0) <= MaxChar) {
                charList.add(execute.charAt(0));
            }
        }
    }


    /**
     * Adds a range of chars to the list of chars to add.
     * @param execute  the range of chars to add
     * @param charList the list of chars to add
     */
    private void rangeCharToAdd(String execute, LinkedList<Character> charList) {
        if (execute.length() == 3) {
            if (execute.charAt(1) == RangeChar) {
                if (execute.charAt(0) > execute.charAt(2) && execute.charAt(2)
                        >= MinChar && execute.charAt(2) <= MaxChar) {
                    for (int i = execute.charAt(2); i <= execute.charAt(0); i++) {
                        charList.add((char) i);
                    }
                }
                if (execute.charAt(0) <= execute.charAt(2) && execute.charAt(0) >=
                        MinChar && execute.charAt(0) <= MaxChar) {
                    for (int i = execute.charAt(0); i <= execute.charAt(2); i++) {
                        charList.add((char) i);
                    }
                }
            }
        }
    }

    /**
     * Adds the chars to the list of chars to add.
     * @param execute the command
     * @throws InvalidCommandException if the command is invalid
     */

    private void addChars(String[] execute) throws InvalidCommandException {
        LinkedList<Character> charList = new LinkedList<>();
        chardToAdd(charList, execute);
        if (charList.isEmpty()) {
            throw new InvalidCommandException(CharsFormatError);
        }
        for (char c : charList) {
            asciiArtAlgorithmManager.addCharSubImgCharMatcher(c);
        }
    }

    /**
     * Removes the chars from the list of chars to add.
     * @param execute the command
     * @throws InvalidCommandException if the command is invalid
     */
    private void removeChars(String[] execute) throws InvalidCommandException {
        LinkedList<Character> charList = new LinkedList<>();
        chardToAdd(charList, execute);
        if (charList.isEmpty()) {
            throw new InvalidCommandException(CharsFormatError);
        }
        for (char c : charList) {
            asciiArtAlgorithmManager.removeCharSubImgCharMatcher(c);
        }
    }



    ///////////////////////////////// Resolution Command

    /**
     * Checks if the resolution is valid and adds the resolution.
     * @throws InvalidCommandException if the resolution is invalid
     */
    private void checkResAdd() throws InvalidCommandException {
        int widthImage = asciiArtAlgorithmManager.getWidthProcessImage();
        int new_res = asciiArtAlgorithmManager.getRes() * 2;
        if (new_res > widthImage) {
            throw new InvalidCommandException(ExceedResolutionError);
        }
        asciiArtAlgorithmManager.setRes(new_res);

    }

    /**
     * Checks if the resolution is valid and subtracts the resolution.
     * @throws InvalidCommandException if the resolution is invalid
     */
    void checkResSub()throws InvalidCommandException {
        int heightImage = asciiArtAlgorithmManager.getHeightProcessImage();
        int widthImage = asciiArtAlgorithmManager.getWidthProcessImage();
        int minCharsInRow = Math.max(1, widthImage / heightImage);

        int new_res = asciiArtAlgorithmManager.getRes() / 2;

        //check if the new size is not 0
        if (new_res == 0) {
            throw new InvalidCommandException(ExceedResolutionError);
        }
        //check if the new size is a multiple of the image columns
        int new_size_sub = widthImage / new_res;
        if ((heightImage/ new_size_sub) < minCharsInRow) {
            throw new InvalidCommandException(ExceedResolutionError);
        }
        //set the new resolution
        asciiArtAlgorithmManager.setRes(new_res);
    }

    /**
     * Changes the resolution.
     * @param execute the command
     * @throws InvalidCommandException if the command is invalid or the resolution is invalid
     */
    public void changeRes(String[] execute) throws InvalidCommandException{
        int newRes = asciiArtAlgorithmManager.getRes();
        if(execute.length == 1)
        {
            System.out.println(ResChange + newRes + ".");
            return;
        }
        switch (execute[1]) {
            case "up":
                checkResAdd();
                newRes = asciiArtAlgorithmManager.getRes();
                System.out.println(ResChange + newRes + ".");
                break;
            case "down":
                checkResSub();
                newRes = asciiArtAlgorithmManager.getRes();
                System.out.println(ResChange + newRes + ".");
                break;
            default:
                throw new InvalidCommandException(ResolutionFormatError);
        }
    }





    ///////////////////////////////////////////////////////////// set image command

    /**
     * Sets the image.
     * @param execute the command
     * @throws InvalidCommandException if the command is invalid or the image is invalid
     */
    private void setImage(String[] execute) throws InvalidCommandException {
        if (!checkNumArgs(execute)) {
            throw new InvalidCommandException(ReplaceImageFormatError);/// change to exception not valid args
        }
        Image image;
        try {
            image = new Image(execute[1]);
        } catch (IOException e) {
            throw new InvalidCommandException(ImageLoadingError);
        }
        this.asciiArtAlgorithmManager.setImageProcess(image);
        if (asciiArtAlgorithmManager.getRes() > asciiArtAlgorithmManager.getHeightProcessImage()) {
            asciiArtAlgorithmManager.setRes(2);
        }
    }


    ///////////////////////////////////////////////////////////////// Output command
    /**
     * Changes the output method.
     * @param execute the command
     * @throws InvalidCommandException if the command is invalid
     */
    private void changeOutput(String[] execute) throws InvalidCommandException {
        if (!checkNumArgs(execute)) {
            throw new InvalidCommandException(OutputFormatError);
        }
        switch (execute[1]) {
            case HTML:
                asciiArtAlgorithmManager.setOutput(OutputOption.HTML);
                break;
            case CONSOLE:
                asciiArtAlgorithmManager.setOutput(OutputOption.CONSOLE);
                break;
            default:
                throw new InvalidCommandException(OutputFormatError);
        }
    }


    ///////////////////////////////////////////////////////////////// asciiArt command


    /**
     * Runs the asciiArt algorithm.
     * @throws EmptyCharsException if the charset is too small
     */
    private void runAsciiArt() throws EmptyCharsException {
        int sizeSubChar = asciiArtAlgorithmManager.getSubImgCharMatcherSize();
       if(sizeSubChar < 2)
       {
              throw new EmptyCharsException(CharSetSizeError);
       }
       char[][] newImage = asciiArtAlgorithmManager.run();
       switch (asciiArtAlgorithmManager.getOutput()) {
              case HTML:
                htmlAsciiOutput.out(newImage);
                break;
              case CONSOLE:
                consoleAsciiOutput.out(newImage);
                break;
         }
    }


    /**
     * Executes the command given by the user.
     * @param execute the command
     * @throws InvalidCommandException if the command is invalid.
     */
    public void execute(String[] execute) throws InvalidCommandException
    {
        switch (execute[0]) {
            case "chars":
            {
                asciiArtAlgorithmManager.printChars();
                break;
            }
            case "add":
                try {
                    addChars(execute);
                }
                catch (InvalidCommandException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "remove":
                try {
                    removeChars(execute);
                }
                catch (InvalidCommandException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "res":
                try {
                    changeRes(execute);
                }
                catch (InvalidCommandException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "image":
                try {
                    setImage(execute);
                }
                catch (InvalidCommandException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "output":
                try {
                    changeOutput(execute);
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "asciiArt":
                try {
                    runAsciiArt();
                }
                catch (EmptyCharsException e) {
                    System.out.println(e.getMessage());
                }
                break;
            default:
                throw new InvalidCommandException(CommandError);
        }
        }

}



