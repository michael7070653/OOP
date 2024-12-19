package pepse.util;
import pepse.world.Block;
/**
 * Utility functions
 */
public class UtilsFunction {

    /**
     * Rounds the x value to the nearest block size
     * @param x the x value to be rounded
     * @return the rounded x value
     */
  public static float roundMinX(int x) {
      float minX = (float) x / Block.BLOCK_SIZE;
      return (float) Math.floor(minX) * Block.BLOCK_SIZE;
  }

  /**
   * Rounds the x value to the nearest block size
   * @param x the x value to be rounded
   * @return the rounded x value
   */
  public static float roundMaxX(int x){
      float maxX = (float) x / Block.BLOCK_SIZE;
      return (float) Math.ceil(maxX) * Block.BLOCK_SIZE;
    }
}
