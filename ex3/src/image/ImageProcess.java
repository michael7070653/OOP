package image;

import java.awt.*;


/**
 * The ImageProcess class is responsible for processing the image.
 * The ImageProcess class has the following attributes:
 * width - an integer that represents the width of the image.
 * height - an integer that represents the height of the image.
 * padImageWidth - an integer that represents the width of the padded image.
 * padImageHeight - an integer that represents the height of the padded image.
 * diffPadHeight - an integer that represents the difference between the height of
 * the padded image and the original image.
 * diffPadWidth - an integer that represents the difference between the width of
 * the padded image and the original image.
 * padImage - an Image object that represents the padded image.
 */
public class ImageProcess implements ImageInterface {
    // the width of the original image
    private final int width;
    // the height of the original image
    private final int height;
    // the width of the padded image
    private final int padImageWidth;
    // the height of the padded image
    private final int padImageHeight;
    // the difference between the height of the padded image and the original image
    private final int diffPadHeight;
    // the difference between the width of the padded image and the original image
    private final int diffPadWidth;
    private final Image padImage;
    /**
     * The red coefficient of the RGB color model.
     */
    public static final double redRgb = 0.2126;
    /**
     * The green coefficient of the RGB color model.
     */
    public static final double greenRgb = 0.7152;
    /**
     * The blue coefficient of the RGB color model.
     */
    public static final double blueRgb = 0.0722;


    /**
     * Constructs a new ImageProcess object.
     * @param image the image
     */
    public ImageProcess(Image image)  {
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.padImageWidth = nearestPowerOf2(width);
        this.padImageHeight = nearestPowerOf2(height);
        System.out.println(padImageWidth + " " + padImageHeight);
        this.diffPadHeight = (padImageHeight - height)/2;
        this.diffPadWidth = (padImageWidth - width)/2;
        this.padImage = image;
    }





    /**
     * Returns the nearest power of 2 to the given number.
     * got it from geekforgeeks
     * @param N the number
     * @return the nearest power of 2 to the given number
     */
    private static int nearestPowerOf2(int N)
    {
        long a = (int)(Math.log(N) / Math.log(2));

        if (Math.pow(2, a) == N)
            return N;

        return (int) Math.pow(2, a + 1);
    }

    /**
     * Returns the width of the image.
     * @return the width of the image
     */
    @Override
    public int getWidth() {
        return padImageWidth;
    }
    /**
     * Returns the width of the image.
     * @return the width of the image
     */

    @Override
    public int getHeight() {
        return padImageHeight;
    }

    /**
     * Returns the pixel at the specified coordinates.
     * If the coordinates are out of bounds, returns white.
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the pixel at the specified coordinates
     */

    @Override
    public Color getPixel(int x, int y) {
        if (y < diffPadWidth // if the pixel out from the left
                || y >= diffPadWidth + width // if the pixel out from the right
                || x < diffPadHeight // if the pixel out from the top
                || x >= diffPadHeight + height // if the pixel out from the bottom
        ) {
            return new Color(255, 255, 255);
        }
        return padImage.getPixel(x - diffPadHeight, y - diffPadWidth);
    }


    /**
     * Creates a 2D array of sub-images of the image, each sub-image is of size res x res.
     * @param res the resolution of the sub-images
     * @return a 2D array of sub-images of the image
     */
    public Image[][] createSubImages(int res) {
        int num_sub_images_row = res;
        int size_sub_image = padImageWidth / num_sub_images_row;
        int num_sub_images_col = padImageHeight / size_sub_image;
        // create the sub images
        Image[][] new_sub_images = new Image[num_sub_images_col][num_sub_images_row];
        for (int i = 0; i < num_sub_images_col; i ++) {
            for (int j = 0; j < num_sub_images_row; j ++) {
                new_sub_images[i][j]
                        = createSubImage
                        (size_sub_image * i, size_sub_image * j , size_sub_image);
            }
        }
        return new_sub_images;
    }




    /**
     * Creates a sub-image of the image, starting at the specified coordinates and of size res x res.
     * @param x the x coordinate
     * @param y the y coordinate
     * @param res the resolution of the sub-image
     * @return a sub-image of the image
     */
    private Image createSubImage(int x, int y, int res) {
        Color[][] subImage = new Color[res][res];
        for (int i = 0; i <  res; i++) {
            for (int j = 0; j <res; j++) {
                subImage[i][j] = getPixel(i + x, j + y);
            }
        }
        return new Image(subImage, res, res);
    }


    /**
     * Calculates the brightness of a color.
     * @param color the color
     * @return the brightness of the color
     */
    public static double calcBrightness(Color color) {
        return redRgb * color.getRed() + greenRgb * color.getGreen() + blueRgb * color.getBlue();
    }


    /**
     * Calculates the average brightness of the image.
     * @return the average brightness of the image
     */
    public static double calcBrightnessSubImage(Image image) {
        double sum = 0;
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++){
                sum += calcBrightness(image.getPixel(i, j));
            }
        }
        return sum / (image.getWidth() * image.getWidth() * 255);
    }








}
