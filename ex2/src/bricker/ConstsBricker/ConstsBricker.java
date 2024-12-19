package bricker.ConstsBricker;
import danogl.util.Vector2;


/**
 * ConstsBricker class contains all the constants used in the Bricker game.
 * It is used to store the values of the game parameters and settings.
 * It is used to avoid hardcoding values in the game code.
 * @author tomer gottman, michael messika
 */
public class ConstsBricker {


    /**
     * ball tag name
     */
    public static final String BALL_TAG = "BALL";

    /**
     * puck ball tag name
     */
    public static final String PUCK_BALL_TAG = "PUCK_BALL";


    /**
     * Maximum number of times the ball can hit the paddle
     */
    public static final int MAX_HIT_PADDLE = 4;

    /**
     * Speed of the ball
     */
    public static final float BALL_SPEED = 200;

    /**
     * Size of the ball
     */
    public static final float BALL_SIZE = 20;

    /**
     * Space between bricks
     */
    public static final float SPACE_BRICK = 2.0f;

    /**
     * Initial number of lives
     */
    public static int INITIAL_LIVES = 3;

    /**
     * Maximum number of lives
     */
    public static int MAX_LIVES = 4;

    /**
     * Length of the window
     */
    public static int LEN_OF_THE_WINDOW = 700;

    /**
     * Height of the window
     */
    public static int HEIGHT_OF_THE_WINDOW = 500;

    /**
     * Constant value of 4
     */
    public static int FOUR_COUNT = 4;

    /**
     * Number of balls to add
     */
    public static int BALLS_TO_ADD = 2;

    /**
     * Height of a brick
     */
    public static int HEIGHT_BRICK = 15;

    /**
     * Width of the wall
     */
    public static float  WALL_WIDTH = 5.0f;

    /**
     * Default number of rows
     */
    public static int DEFOULT_NUM_ROWS = 7;

    /**
     * Default number of columns
     */
    public static int DEFOULT_NUM_COLS = 8;

    /**
     * Location of the counter
     */
    public static Vector2 LOC_COUNTER = new Vector2(10, 410);

    /**
     * Dimensions of the counter
     */
    public static Vector2 DIM_COUNTER = new Vector2(35, 35);

    /**
     * Location of the first heart
     */
    public static Vector2 LOC_FIRST_HURT = new Vector2(10, 450);

    /**
     * Dimensions of the heart
     */
    public static Vector2 DIM_HURT = new Vector2(25, 25);

    /**
     * Size of the paddle
     */
    public static Vector2 PADDLE_SIZE = new Vector2(100,20);

    /**
     * Dimensions of the window
     */
    public static Vector2 WIND_DIMENSIONS = new Vector2(700,500);

    /**
     * Velocity of the heart
     */
    public static Vector2 HEART_VEL = new Vector2(0,100);

    /**
     * Path to the paddle image
     */
    public static String PATH_TO_PADDLE_IMAGE = "assets/paddle.png";

    /**
     * Path to the ball image
     */
    public static String PATH_TO_BALL_IMAGE = "assets/ball.png";

    /**
     * Path to the ball sound
     */
    public static String PATH_TO_BALL_SOUND = "assets/blop.wav";

    /**
     * Path to the background image
     */
    public static String PATH_TO_BACKGROUND_IMAGE =  "assets/DARK_BG2_small.jpeg";

    /**
     * Path to the brick image
     */
    public static String PATH_TO_BRICK_IMAGE =  "assets/brick.png";

    /**
     * Name of the game
     */
    public static String NAME_OF_THE_GAME =  "Bricker";

    /**
     * Path to the puck balls image
     */
    public static String PUCK_BALLS = "assets/mockBall.png";

    /**
     * Path to the heart image
     */
    public static String HEART_IMAGE = "assets/heart.png";

    /**
     * Win string
     */
    public static final String WIN_STRING = "You win!   ";

    /**
     * Loss string
     */
    public static final String LOSS_STRING = "You loss! ";

    /**
     * Play again string
     */
    public static final String PLAY_AGAIN = "Play again?";
}
