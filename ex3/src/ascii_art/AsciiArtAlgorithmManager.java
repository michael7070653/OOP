package ascii_art;
import image.Image;
import image.ImageProcess;
import image_char_matching.SubImgCharMatcher;


/**
 * The AsciiArtAlgorithm class is responsible for the algorithm of the program.
 * The AsciiArtAlgorithm class has the following attributes:
 * imageProcess - an ImageProcess object that is used to process the image.
 * subImgCharMatcher - a SubImgCharMatcher object
 * that is used to match the characters to the brightness of the image.
 * output - an OutputOption object that is used to determine the output of the program.
 * currentRes - an integer that represents the resolution of the image.
 * flag - a boolean that represents if the image was processed.
 * asciiImage - a 2D array of characters that represents the ASCII image.
 */
public class AsciiArtAlgorithmManager {
    //attributes
    private ImageProcess imageProcess;
    private final SubImgCharMatcher subImgCharMatcher;
    private OutputOption output;
    private int currentRes;
    //flag to say if the image was processed if not then flag is true
    private boolean flag = true;
    //the ascii image
    private char[][] asciiImage = null;


    /**
     * The constructor of the AsciiArtAlgorithm class.
     * @param imageProcess the image process object
     * @param subImgCharMatcher the sub image char matcher object
     * @param output the output option
     * @param currentRes the resolution of the image
     */
    public AsciiArtAlgorithmManager(ImageProcess imageProcess,
                                    SubImgCharMatcher subImgCharMatcher,
                                    OutputOption output,
                                    int currentRes) {
        this.imageProcess = imageProcess;
        this.subImgCharMatcher = subImgCharMatcher;
        this.output = output;
        this.currentRes = currentRes;

    }


    /**
     * Sets the resolution of the image.
     * @param res the resolution of the image
     */
    public void setRes(int res) {
        flag = true;
        this.currentRes = res;
    }

    /**
     * Gets the resolution of the image.
     * Gets the resolution of the image.
     * @return the resolution of the image
     */
    public int getRes() {
        return currentRes;
    }

    /**
     * Sets the image process object.
     * @param newImage the new image
     */
    public void setImageProcess(Image newImage) {
        flag = true;
        this.imageProcess = new ImageProcess(newImage);
    }

    /**
     * Gets the image process object.
     * @return the image process object
     */
    public int getWidthProcessImage() {
        return imageProcess.getWidth();
    }

    /**
     * Gets the height of the image.
     * @return the height of the image
     */
    public int getHeightProcessImage() {
        return imageProcess.getHeight();
    }


    /**
     * Gets the number of characters in the sub image char matcher.
     * @return the number of characters in the sub image char matcher
     */
    public int getSubImgCharMatcherSize() {
        return subImgCharMatcher.getCharBrightnessMapSize();
    }

    /**
     * Adds a character to the sub image char matcher.
     * @param c the character to add
     */
    public void addCharSubImgCharMatcher(char c) {
        flag = true;
        subImgCharMatcher.addChar(c);
    }

    /**
     * Removes a character from the sub image char matcher.
     * @param c the character to remove
     */
    public void removeCharSubImgCharMatcher(char c) {
        flag = true;
        subImgCharMatcher.removeChar(c);
    }

    /**
     * Sets the output option.
     * @param output the output option
     */
    public void setOutput(OutputOption output) {
        this.output = output;
    }

    /**
     * Gets the output option.
     * @return the output option
     */
    public OutputOption getOutput() {
        return output;
    }

    /**
     * Processes the image and returns the ASCII image.
     * Creates a new AsciiArtAlgorithm object and runs the algorithm if changed was made.
     * If the image was already processed, returns the ASCII image.
     * @return the ASCII image
     */
    public char[][] run() {
        if (flag) {
            AsciiArtAlgorithm asciiArtAlgorithm =
                    new AsciiArtAlgorithm(imageProcess, subImgCharMatcher, currentRes);
            asciiImage = asciiArtAlgorithm.run();
            flag = false;
        }
        return asciiImage;
    }



    /**
     * prints the ASCII image to the console.
     */
    public void printChars() {
        subImgCharMatcher.printChars();
    }
}