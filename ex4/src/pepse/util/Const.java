package pepse.util;

import danogl.util.Vector2;

import java.awt.*;

/**
 * Constants used in the game
 */
public class Const {
    /**
     * Avatar's path to animation assets in idle state
     */
    public static final String[] AVATARS_PATH_IDLE = {
            "assets/jump_0.png",
            "assets/jump_1.png",
            "assets/jump_2.png",
            "assets/jump_3.png"};

    /**
     * Avatar's path to animation assets in running state
     */
    public static final String[] AVATARS_PATH_RUN = {
            "assets/run_0.png",
            "assets/run_1.png",
            "assets/run_2.png",
            "assets/run_3.png",
            "assets/run_4.png",
            "assets/run_5.png"};

    /**
     * Avatar's path to animation assets in jumping state
     */
    public static final String[] AVATARS_PATH_JUMP = {
            "assets/jump_0.png",
            "assets/jump_1.png",
            "assets/jump_2.png",
            "assets/jump_3.png"};

    /**
     * Trunk's dimensions
     */
    public static final Vector2 TrunkDimensions = new Vector2(30, 30);
    /**
     * Game's cycle time
     */
    public static final float GamaCycle = 30.0f;
    /**
     * Energy added when eating a fruit
     */
    public static final float AddFruitEnergy = 10.0f;

    /**
     * angle of rotation of the leaf
     */
    public static final float LEAF_ROTATE_ANGLE = 90.0f;
    /**
     * Avatar's dimensions
     */
    public static final Vector2 AVATAR_DIMENSIONS = new Vector2(50, 50);
    /**
     * Jumping event
     */
    public static final String ON_JUMP = "Jumping";


    /**
     * Tree's trunk color
     */
    public static final Color TrunkColor = new Color(100, 50, 20);

    /**
     * Tree's fruit colors
     */
    public static final Color[] FruitColor = {Color.red, Color.green, Color.yellow, Color.orange};


    /**
     * Leaf's dimensions
     */
    public static final Vector2 LEAF_DIM = new Vector2(30, 30);

    /**
     * Fruit's dimensions
     */
    public static final Vector2 FRUIT_DIM = new Vector2(15, 15);;

    /**
     * Leaf's color
     */
    public static final Color LEAF_COLOR = new Color(30 ,200 ,50);
    /**
     * Night color
     */
    public static final Color NIGHT_COLOR = Color.BLACK;

    /**
     * Sun Halo color
     */
    public static final Color HALO_COLOR = new Color(255,255,0,20);
    /**
     * Sun's tag
     */
    public static final String SUN_TAG = "sun";
    /**
     * Night tag
     */
    public static final String NIGHT_TAG = "night";
    /**
     * Sky's tag
     */
    public static final String SKY_TAG = "sky";

    /**
     * User interface tag
     */
    public static final String USER_INTERFACE_TAG = "userInterface";
    /**
     * avatar's tag
     */
    public static String AvatarTag = "avatar";
    /**
     * Tree's component tag
     */
    public static final String treeComponent = "TreeComponent";

    /**
     * Max energy
     */
    public static final float MAX_ENERGY = 1000.0f;
}
