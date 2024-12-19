package image_char_matching;
import java.util.Map;
import java.util.TreeMap;
import java.util.*;

/**
 * The SubImgCharMatcher class is responsible for matching the characters to the brightness of the image.
 * The SubImgCharMatcher class has the following attributes:
 * charBrightnessMap - a TreeMap that maps each character to its brightness value.
 * normalizedBrightnessMap - a TreeMap that maps each character to its normalized brightness value.
 * minBrightness - a double that represents the minimum brightness value.
 * maxBrightness - a double that represents the maximum brightness value.
 * closestChar - a char that represents the character that is closest to the given brightness value.
 */
public class SubImgCharMatcher {

    //represents the brightness of each character
    private final TreeMap<Character,Double> charBrightnessMap;
    //represents the normalized brightness of each character
    private final TreeMap<Character,Double> normalizedBrightnessMap;
    private double minBrightness = 2.0;
    private double maxBrightness = 0.0;
    private char closestChar = ' ';

    /**
     * Counts the brightness of a character.
     * @param a the character
     * @return the brightness of the character
     */
    private double countBrightness(char a) {
        boolean[][] matrix = CharConverter.convertToBoolArray(a);
        double count = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j]) {
                    count++;
                }
            }
        }
        return count/(matrix.length * matrix[0].length);
    }




    /**
     * Normalizes the brightness of a character to a value between 0 and 1.
     * @author michael messika,Tomer guttman
     * @param charBrightness the brightness of the character
     * @param minBrightness the minimum brightness value
     * @param maxBrightness the maximum brightness value
     * @return the normalized brightness value
     */
    private double getNoramllBrightness(double charBrightness,
                                       double minBrightness,
                                       double maxBrightness) {
        return (charBrightness - minBrightness) / (maxBrightness - minBrightness);

    }

    /**
     * Updates the min and max brightness values if the given brightness value
     * is smaller or larger than the current min or max values.
     * @param brightness the brightness value
     * @return true if the min or max brightness values have been updated, false otherwise
     */
    private boolean newBrightness(double brightness) {
        if (brightness < minBrightness) {
            minBrightness = brightness;
            return true;
        }
        if (brightness > maxBrightness) {
            maxBrightness = brightness;
            return true;
        }
        return false;
    }



    /**
     * Constructs a SubImgCharMatcher object with the specified charset.
     * @param charset the charset
     */
    public SubImgCharMatcher(char[] charset) {
        this.charBrightnessMap = new TreeMap<>();
        this.normalizedBrightnessMap = new TreeMap<>();
        for (char character : charset) {
            double brightness = countBrightness(character);
            charBrightnessMap.put(character, brightness);
            newBrightness(brightness);
        }
        strechBrightNess();
    }

    /**
     * Updates the normalized brightness values of all characters.
     * This method should be called after the min and max brightness values
     * have been updated.
     */
    private void strechBrightNess() {
        for (Map.Entry<Character, Double> entry : charBrightnessMap.entrySet()){
            double normalizedBrightness =
                    getNoramllBrightness(entry.getValue(), minBrightness, maxBrightness);
            normalizedBrightnessMap.put(entry.getKey(), normalizedBrightness);
        }
    }



    /**
     * Returns the minimum brightness value.
     * if the diff is smaller than the minDiff, the diff is returned.
     * if the diff is equal to the minDiff, the character with the smaller ASCII value is returned.
     * @return the minimum brightness value
     */
    private double getMinDiff(Map.Entry<Character, Double> entry, double diff, double minDiff) {
        if (diff < minDiff) {
            closestChar = entry.getKey();
            return diff;
        }
        if(diff == minDiff) {
            if(closestChar > entry.getKey()) {
                closestChar = entry.getKey();

            }
        }
        return minDiff;
    }



    /**
     * Returns the character that is closest to the given brightness value.
     * If there are two characters with the same difference from the brightness value,
     * the character with the smaller ASCII value is returned.
     * @param brightness the brightness value
     * @return the character that is closest to the given brightness value
     */
    public char getCharByImageBrightness(double brightness) {
        double minDiff = Double.MAX_VALUE;
        closestChar = ' ';
        for (Map.Entry<Character, Double> entry : normalizedBrightnessMap.entrySet()) {
            double diff = Math.abs(entry.getValue() - brightness);
            minDiff = getMinDiff(entry, diff, minDiff);
        }
        return closestChar;

    }




    /**
     * Adds a character to the charset.
     * If the character is already in the charset, it is not added.
     * @param c the character to add
     */
    public void addChar(char c) {
        if (charBrightnessMap.containsKey(c)) {
            return;
        }

        double brightness = countBrightness(c);
        charBrightnessMap.put(c, brightness);
        //update min and max brightness values
        if (newBrightness(brightness)){
            // if the new brightness larger or smaller than the current min or max values.
            strechBrightNess();
            return;
        }
        //update the normalized brightness values
        double normalizedBrightness = getNoramllBrightness(brightness, minBrightness, maxBrightness);
        normalizedBrightnessMap.put(c, normalizedBrightness);
    }


    /**
     * Removes a character from the charset.
     * If the character is not in the charset, nothing happens.
     * @param c the character to remove
     */
    public void removeChar(char c) {
        if (!charBrightnessMap.containsKey(c)) {
            return;
        }
        double brightness = charBrightnessMap.get(c);
        charBrightnessMap.remove(c);
        normalizedBrightnessMap.remove(c);
        //update min and max brightness values
        if(updateMinMaxALL(brightness)) {
            //update the normalized brightness values if the
            // new brightness is equal to the current min or max values.
            strechBrightNess();
        }



    }



    /**
     * Updates the min and max brightness values if the given brightness value
     * is equal to the current min or max values.
     * @param brightness the brightness value
     * @return true if the min or max brightness values have been updated, false otherwise
     */
    private boolean updateMinMaxALL(double brightness) {
        if(brightness == minBrightness || brightness == maxBrightness) {
            for(Map.Entry<Character, Double> entry : charBrightnessMap.entrySet()) {
               newBrightness(entry.getValue());
            }
            return true;
        }
        return false;
    }

    /**
     * Prints the characters in the charset.
     */
    public void printChars() {
        if (normalizedBrightnessMap.isEmpty()){
            return;
        }
        for (Map.Entry<Character, Double> entry : normalizedBrightnessMap.entrySet()) {
            System.out.print(entry.getKey() + " ");
        }
        System.out.println();
    }

    /**
     * Returns the size of the charset.
     * @return the size of the charset
     */
    public int getCharBrightnessMapSize() {
        return charBrightnessMap.size();
    }

}

