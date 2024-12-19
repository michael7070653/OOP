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
 * currentRes - an integer that represents the resolution of the image.
 */
public class AsciiArtAlgorithm {
    private final ImageProcess imageProcess;
    private final SubImgCharMatcher subImgCharMatcher;
    private final int currentRes;

    /**
     * Constructs a new AsciiArtAlgorithm object.
     * @param imageProcess the image process object
     * @param subImgCharMatcher the sub image char matcher object
     * @param currentRes the resolution of the image
     */
    public AsciiArtAlgorithm(ImageProcess imageProcess,
                             SubImgCharMatcher subImgCharMatcher,
                             int currentRes) {
        this.imageProcess = imageProcess;
        this.subImgCharMatcher = subImgCharMatcher;
        this.currentRes = currentRes;
    }



    /**
     * Processes the image and returns the ASCII image.
     * If the image was already processed, returns the ASCII image.
     * @return the ASCII image
     */
    public char[][] run() {
        Image[][] image = imageProcess.createSubImages(currentRes);
        char[][] asciiImage = new char[image.length][image[0].length];
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[0].length; j++) {
                double brightNess = ImageProcess.calcBrightnessSubImage(image[i][j]);
                asciiImage[i][j] = subImgCharMatcher.getCharByImageBrightness(brightNess);
                }
            }
        return asciiImage;
    }


}
