package image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A package-private class of the package image.
 * @author Dan Nirel
 */
public class Image implements ImageInterface{

    private final Color[][] pixelArray;
    private final int width;
    private final int height;


    /**
     * Constructs an image from the specified file.
     * @param filename the name of the file to read the image from
     * @throws IOException if the file cannot be read
     */

    public Image(String filename) throws IOException {
        BufferedImage im = ImageIO.read(new File(filename));
        width = im.getWidth();
        height = im.getHeight();


        pixelArray = new Color[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pixelArray[i][j]=new Color(im.getRGB(j, i));
            }
        }
    }
    /**
     * Constructs an image from the specified pixel array.
     * @param pixelArray the pixel array of the image
     * @param width the width of the image
     * @param height the height of the image
     */
    public Image(Color[][] pixelArray, int width, int height) {
        this.pixelArray = pixelArray;
        this.width = width;
        this.height = height;
    }

    /**
     * Returns the width of the image.
     * @return the width of the image
     */
    @Override
    public int getWidth() {
        return width;
    }
    /**
     * Returns the height of the image.
     * @return the height of the image
     */
    @Override
    public int getHeight() {
        return height;
    }

    /**
     * Returns the color of the pixel at the specified coordinates.
     * @param x the x coordinate of the pixel
     * @param y the y coordinate of the pixel
     * @return the color of the pixel at the specified coordinates
     */
    @Override
    public Color getPixel(int x, int y) {
        return pixelArray[x][y];
    }



    /**
     * Saves the image to a file with the specified name.
     * @param fileName the name of the file to save the image to
     */
    public void saveImage(String fileName){
        // Initialize BufferedImage, assuming Color[][] is already properly populated.
        BufferedImage bufferedImage = new BufferedImage(pixelArray[0].length, pixelArray.length,
                BufferedImage.TYPE_INT_RGB);
        // Set each pixel of the BufferedImage to the color from the Color[][].
        for (int x = 0; x < pixelArray.length; x++) {
            for (int y = 0; y < pixelArray[x].length; y++) {
                bufferedImage.setRGB(y, x, pixelArray[x][y].getRGB());
            }
        }
        File outputfile = new File(fileName+".jpeg");
        try {
            ImageIO.write(bufferedImage, "jpeg", outputfile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
