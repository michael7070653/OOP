package bricker.main;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import bricker.brick_strategies.CollisionStrategy;
import bricker.gameobjects.Ball;
import bricker.gameobjects.Paddle;
import danogl.GameManager;
import danogl.GameObject;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import bricker.gameobjects.Brick;
import bricker.gameobjects.UserGraphic;
import bricker.package_factory.strategyFactory;
import bricker.ConstsBricker.ConstsBricker;


import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * BrickerGameManager class extends the GameManager class.
 * It represents the game manager of the Bricker game.
 * It is responsible for initializing the game,
 * updating the game, and handling the game logic.
 * It creates the game components and adds them to the game.
 * It checks if the game has ended and asks the player if
 * they want to play another game.
 * @author tomer gottman, michael messika
 */
public class BrickerGameManager extends GameManager{
    // The dimensions of the window.
    private final Vector2 window_dim;
    // The window controller instance.
    private WindowController windowController;
    // The user graphic instance.
    private UserGraphic userGraphic;
    // The user paddle instance.
    private Paddle paddle_user;
    // The ball instance.
    private Ball ball;
    // The user input listener instance.
    private UserInputListener userInputListener;

    // The speed of the ball
    private static final float BALL_SPEED = ConstsBricker.BALL_SPEED;
    // The number of lives
    private int numLives;
    // The number of bricks
    private int numBricks;
    // The number of rows of bricks
    private final int numRowsBrick;
    // The number of columns of bricks
    private final int numColsBrick;



    /**
     * Construct a new BrickerGameManager instance.
     *
     * @param windowTitle The title of the window.
     * @param windowDimensions The dimensions of the window.
     * @param numRowsBrick The number of rows of bricks.
     * @param numColsBrick The number of columns of bricks.
     */
    public BrickerGameManager(String windowTitle,
                              Vector2 windowDimensions,
                              int numRowsBrick,
                              int numColsBrick) {
        super(windowTitle, windowDimensions);
        this.window_dim = windowDimensions;
        this.numRowsBrick = numRowsBrick;
        this.numColsBrick = numColsBrick;
    }

    /**
     * Update the game.
     * The game is updated by checking if the game has ended.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        double ballHeight = ball.getCenter().y();
        checkEndGame(ballHeight);
    }





    /**
     * Initialize the game.
     * The game is initialized with the image reader,
     * sound reader, user input listener, and window controller.
     * The game components are created and added to the game.
     *
     * @param imageReader The image reader instance.
     * @param soundReader The sound reader instance.
     * @param inputListener The user input listener instance.
     * @param windowController The window controller instance.
     */
    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        this.windowController = windowController;
        super.initializeGame
                (imageReader,
                soundReader,
                inputListener,
                windowController);
        windowController.setTargetFramerate(80);

        /**
         * The images and sounds are read from the files.
         * The game components are created and added to the game.
         */
        Renderable paddleImag = imageReader.readImage(ConstsBricker.PATH_TO_PADDLE_IMAGE,
                false);
        Renderable BR_image = imageReader.readImage(ConstsBricker.PATH_TO_BRICK_IMAGE,
                false);
        Renderable backgroundImage = imageReader.readImage(ConstsBricker.PATH_TO_BACKGROUND_IMAGE,
                true);
        Renderable ball_image =  imageReader.readImage(ConstsBricker.PATH_TO_BALL_IMAGE,
                true);
        Sound ColisionSound = soundReader.readSound(ConstsBricker.PATH_TO_BALL_SOUND);
        Renderable heartImage = imageReader.readImage(ConstsBricker.HEART_IMAGE, true);
        Renderable puckImage = imageReader.readImage(ConstsBricker.PUCK_BALLS, true);

        this.numLives= ConstsBricker.INITIAL_LIVES;
        this.numBricks = numColsBrick * numRowsBrick;
        this.userInputListener = inputListener;

        //put walls
        createWalls();
        //put background
        createBackground(window_dim, backgroundImage);
        //create user paddle
        createUserPaddle(inputListener, paddleImag, window_dim);
        //create ball
        createBall(window_dim,ball_image,ColisionSound);

        //size of length of the brick
        float len_Brick = (window_dim.x() - ((numColsBrick  + 1)) * ConstsBricker.SPACE_BRICK)
                / numColsBrick;

        //create factory of strategies
        strategyFactory collisionStrategy = new strategyFactory(this,
                ball,
                puckImage,ColisionSound,
                paddle_user,paddleImag, inputListener, heartImage);
        //create breaks
        creatBrickBoard(len_Brick, BR_image, collisionStrategy);
        //create user interface
        creatUserInterface(heartImage);
    }




    /**
     * Create the user interface of the game.
     * The user interface includes the number of lives of the player.
     *
     * @param heartImage The image of the heart.
     */
    private void creatUserInterface(Renderable heartImage) {
        userGraphic = new UserGraphic(Vector2.ZERO,
                Vector2.ZERO,
                heartImage,
                this,0);
        for (int i = 0; i < ConstsBricker.INITIAL_LIVES; i++) {
            userGraphic.addHurt();
        }
    }



    /**
     * Create the bricks of the game.
     * The bricks are created as game objects and added to the game.
     * The bricks are arranged in a grid pattern.
     * The number of rows and columns of the grid is defined in the game constants.
     * The length of the bricks is calculated based on the dimensions of the window.
     *
     * @param len_Brick The length of the bricks.
     * @param BR_image The image of the bricks.
     * @param c The strategy factory instance.
     */
    private void creatBrickBoard(float len_Brick,
                                 Renderable BR_image,
                                 strategyFactory c) {
        float breakY = ConstsBricker.SPACE_BRICK;
        for (int i = 0; i < numRowsBrick; i++) {
            float breakX = ConstsBricker.SPACE_BRICK;
            for (int j = 0; j < numColsBrick; j++)
            {
                Vector2 LOC = new Vector2(breakX,breakY);
                CollisionStrategy newStrategy = c.createStrategy();
                Brick b = new Brick(LOC,
                        new Vector2(len_Brick,
                                ConstsBricker.HEIGHT_BRICK), BR_image, newStrategy);
                this.gameObjects().addGameObject(b,Layer.STATIC_OBJECTS);
                breakX += len_Brick + ConstsBricker.SPACE_BRICK;
            }
            breakY += ConstsBricker.HEIGHT_BRICK + ConstsBricker.SPACE_BRICK;
        }
    }

    /**
     * Create the background of the game.
     * The background is created as a game object and added to the game.
     *
     * @param window_dim The dimensions of the window.
     * @param backgroundImage The image of the background.
     */

    private void createBackground(Vector2 window_dim,
                                  Renderable backgroundImage ) {
        GameObject background = new GameObject(Vector2.ZERO,
                                new Vector2(window_dim.x(), window_dim.y()),
                                    backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }


    /**
     * Create the user paddle.
     * The paddle is created as a game object and added to the game.
     *
     * @param inputListener The user input listener.
     * @param paddleImag The image of the paddle.
     * @param window_dim The dimensions of the window.
     */
    private void createUserPaddle(UserInputListener inputListener,
                                  Renderable paddleImag,
                                  Vector2 window_dim) {
        paddle_user = new Paddle
                        (Vector2.ZERO,
                        ConstsBricker.PADDLE_SIZE,
                                paddleImag, inputListener);
        paddle_user.setCenter(new Vector2 (window_dim.x()/2,
                window_dim.y() - 2 * ConstsBricker.WALL_WIDTH));
        this.gameObjects().addGameObject(paddle_user,Layer.DEFAULT);
    }

    /**
     * Create the ball of the game.
     * The ball is created as a game object and added to the game.
     *
     * @param window_dim The dimensions of the window.
     * @param ball_image The image of the ball.
     * @param ColisionSound The sound of the ball collision.
     */

    private void createBall
            (Vector2 window_dim,
             Renderable ball_image,
             Sound ColisionSound ) {
        ball = new Ball(Vector2.ZERO,new Vector2(ConstsBricker.BALL_SIZE,
                ConstsBricker.BALL_SIZE),ball_image, ColisionSound);
        ball.setTag(ConstsBricker.BALL_TAG);
        ball.setCenter(window_dim.mult(0.5f));
        this.gameObjects().addGameObject(ball, Layer.DEFAULT);
        ball.setVelocity(Vector2.DOWN.mult(ConstsBricker.BALL_SPEED));
        setBallInDiagonal();
    }


    /**
     * Set the ball in a diagonal direction.
     * The ball is set in a random diagonal direction.
     * The speed of the ball is defined in the game constants.
     */
    private void setBallInDiagonal() {
        ball.setCenter(window_dim.mult(0.5f));
        float BallVellX =BALL_SPEED ;
        float BallvellY = BALL_SPEED;
        Random rand = new Random();
        if(rand.nextBoolean())
        {
            BallVellX *= -1;
        }
        if(rand.nextBoolean())
        {
            BallvellY *=-1;
        }
        ball.setVelocity(new Vector2(BallVellX,BallvellY));
    }


    /**
     * Create the walls of the game.
     * The walls are created as game objects and added to the game.
     */
    private void createWalls() {

        GameObject leftWall = new GameObject(Vector2.ZERO,
                new Vector2(ConstsBricker.WALL_WIDTH,ConstsBricker.HEIGHT_OF_THE_WINDOW), null);
        gameObjects().addGameObject(leftWall,Layer.STATIC_OBJECTS);

        GameObject rightWall = new GameObject(new Vector2(window_dim.x() - ConstsBricker.WALL_WIDTH,
                0),
                new Vector2(ConstsBricker.WALL_WIDTH ,ConstsBricker.HEIGHT_OF_THE_WINDOW), null);
        gameObjects().addGameObject(rightWall,Layer.STATIC_OBJECTS);

        GameObject upWall = new GameObject(Vector2.ZERO, new Vector2(ConstsBricker.LEN_OF_THE_WINDOW,
                ConstsBricker.WALL_WIDTH), null);

        gameObjects().addGameObject(upWall,Layer.STATIC_OBJECTS);
    }




    //////FUNCTION TO ADD OR REMOVE LIFE AND OBJECT////


    /**
     * Decrease the number of lives by one.
     */
    public void downLife() {
        this.numLives -= 1;
    }

    /**
     * Increase the number of lives by one.
     * If the number of lives exceeds the maximum number of lives,
     * the number of lives remains the same.
     */
    public void addLife() {
        if (numLives >= ConstsBricker.MAX_LIVES)
        {
            return;
        }
        userGraphic.addHurt();
        this.numLives += 1;
    }




    /**
     * Add a game object to the game.
     *
     * @param object The game object to be added.
     * @param layer The layer in which the game object should be added.
     */
    public void addGameObj(GameObject object, int layer)
    {
        gameObjects().addGameObject(object,layer);
    }

    /**
     * Remove a game object from the game.
     *
     * @param object The game object to be removed.
     * @param layer The layer from which the game object should be removed.
     */

    public void removeGameObj(GameObject object, int layer)
    {
        gameObjects().removeGameObject(object,layer);
    }

    /**
     * Remove a brick from the game.
     *
     * @param gameObject The brick to be removed.
     */
    public void removeBrick(GameObject gameObject)
    {
        if(gameObjects().removeGameObject(gameObject,Layer.STATIC_OBJECTS)){
            numBricks--;
        }
    }



    /**
     * Check if the game has ended.
     * The game ends if all bricks are removed or the ball falls down.
     *
     * @param ballHeight The height of the ball.
     */
    private void checkEndGame(double ballHeight) {
        String prompt = "";
        //player winn or press w
        if (numBricks == 0  || userInputListener.isKeyPressed(KeyEvent.VK_W)) {
            prompt = ConstsBricker.WIN_STRING;
        }

        //ball fall down
        if (ballHeight > window_dim.y()){
            downLife();
            userGraphic.remove_hurt();
            if (numLives == 0){
                prompt = ConstsBricker.LOSS_STRING;
            }
            else {
                setBallInDiagonal();
                return;
            }
        }
        playAnotherGame(prompt);
    }


    /**
     * Ask the player if they want to play another game.
     *
     * @param prompt The prompt message.
     */
    private void playAnotherGame(String prompt) {
        if(!prompt.isEmpty()){
            prompt += ConstsBricker.PLAY_AGAIN;
            if(windowController.openYesNoDialog(prompt)){
                windowController.resetGame();
            }
            else {
                windowController.closeWindow();
            }
        }
    }






    /**
     * The main method of the game.
     * It creates a new game instance and starts the game.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        int num_r = ConstsBricker.DEFOULT_NUM_ROWS;
        int num_c = ConstsBricker.DEFOULT_NUM_COLS;

        if(args.length > 2 || args.length == 1)
        {
            return;
        }
        if(args.length == 2) {
            num_r = Integer.parseInt(args[0]);
            num_c = Integer.parseInt(args[1]);
        }
        BrickerGameManager newGame = new BrickerGameManager
                (ConstsBricker.NAME_OF_THE_GAME,
                new Vector2(ConstsBricker.LEN_OF_THE_WINDOW,
                        ConstsBricker.HEIGHT_OF_THE_WINDOW)
                ,num_r,num_c);
        newGame.run();
    }

}
