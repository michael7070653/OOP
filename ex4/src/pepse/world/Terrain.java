package pepse.world;

import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.NoiseGenerator;
import pepse.util.UtilsFunction;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
/**
 * Class for creating the terrain object
 * creats land in the game
 * The terrain object is a rectangle that is the terrain
 */
public class Terrain {
    private static final float initHeightFactor = 2.0f / 3.0f;
    private final float firsHeight;
    private static final String Ground_tag = "ground";
    private static final Color GROUND_COLOR  = new Color(212,123, 74);
    private static final float factorSize = Block.BLOCK_SIZE * 7;
    private static final int TERRAIN_DEPTH = 20;
    private NoiseGenerator noiseGenerator ;
    private final ArrayList<Block> allTerrain = new ArrayList<>();

    /**
     * Creates the terrain object
     * The terrain creates the land in the game with squares blocks
     * @param windowDimensions the dimensions of the window
     * @param seed the seed for the noise generator
     */
    public Terrain(Vector2 windowDimensions, int seed) {
        this.firsHeight =  windowDimensions.y() * initHeightFactor;
        noiseGenerator = new NoiseGenerator(seed, (int) firsHeight);
    }

    /**
     * Returns the height of the ground at a given x value
     * @param x the x value
     * @return the height of the ground at the x value
     */
    public float groundHeight(float x) {
        return (float) noiseGenerator.noise(x, factorSize) + firsHeight;
    }

    /**
     * Creates the terrain object
     * The terrain object is a rectangle that is the terrain
     * @param minX the minimum x value
     * @param maxX the maximum x value
     * @return the list of blocks that make up the terrain
     */
    public List<Block> createInRange(int minX, int maxX) {
        List<Block> blocks = new java.util.ArrayList<>();
        float xMin = UtilsFunction.roundMinX(minX);
        float xMax = UtilsFunction.roundMaxX(maxX);
        for(float i = xMin; i < xMax; i+= Block.BLOCK_SIZE)
        {
            float startHeight = groundHeight(i);
            int depth = (int) startHeight + (TERRAIN_DEPTH * Block.BLOCK_SIZE);
            for (int j = (int)startHeight; j < depth; j+= Block.BLOCK_SIZE)
            {
                RectangleRenderable rectangleRenderable =
                        new RectangleRenderable(ColorSupplier.approximateColor(GROUND_COLOR));
                Block block = new Block(new Vector2(i, j), rectangleRenderable);
                block.setTag(Ground_tag);
                blocks.add(block);
                allTerrain.add(block);
            }
        }
        return blocks;
     }


    /**
     * Returns all the terrain blocks that arr in the game
     * @return all the terrain blocks that arr in the game
     */
    public ArrayList<Block> getAllTerrain() {
        return allTerrain;}




}
