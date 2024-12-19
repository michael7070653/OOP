package image;

import java.awt.*;
/**
 * The ImageInterface interface is an interface that represents an image.
 * The ImageInterface interface has the following methods:
 * getWidth - returns the width of the image.
 * getHeight - returns the height of the image.
 * getPixel - returns the color of the pixel at the given coordinates.
 */
public interface ImageInterface {
    /**
     * Returns the width of the image.
     * @return the width of the image
     */
    public int getWidth();

    /**
     * Returns the height of the image.
     * @return the height of the image
     */
    public int getHeight();

    /**
     * Retunrs the Pixel in cord(x,y)
     * @param x
     * @param y
     * @return the color of the pixel at the specified coordinates
     */
    Color getPixel(int x, int y);
}
