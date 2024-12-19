package bricker.package_factory;
import danogl.gui.Sound;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import bricker.main.BrickerGameManager;
import bricker.brick_strategies.*;
import bricker.gameobjects.Ball;
import bricker.gameobjects.Paddle;

import java.util.Random;

/**
 * strategyFactory class.
 * It is responsible for creating instances of
 * BasicCollisionStrategy and its subclasses.
 * The specific type of strategy is chosen randomly.
 * @author tomer gottman, michael messika
 */
public class strategyFactory {

    // Various game components and resources
    // The game manager instance.
    private final BrickerGameManager brickerGameManager;
    // The ball instance.
    private final Ball ball;
    // The puck image.
    private final Renderable PuckImage;
    // The ball sound.
    private final Sound ballSound;
    // The paddle instance.
    private final Paddle paddle;
    // The paddle image.
    private final Renderable paddleImage;
    // The user input listener instance.
    private final UserInputListener userInputListener;
    // The heart image.
    private final Renderable heartImage;
    // Counter for the number of times the DOUBLE strategy has been chosen.
    private int counterDoubleStrategy;

    /**
     * Construct a new strategyFactory instance.
     *
     * @param brickerGameManager The game manager instance.
     * @param ball The ball instance.
     * @param PuckImage The puck image.
     * @param ballSound The ball sound.
     * @param paddle The paddle instance.
     * @param paddleImage The paddle image.
     * @param userInputListener The user input listener instance.
     * @param heartImage The heart image.
     */
    public strategyFactory(BrickerGameManager brickerGameManager,
                           Ball ball,
                           Renderable PuckImage,
                           Sound ballSound,
                           Paddle paddle,
                           Renderable paddleImage,
                           UserInputListener userInputListener,
                           Renderable heartImage)
    {
        this.brickerGameManager = brickerGameManager;
        this.ball = ball;
        this.PuckImage = PuckImage;
        this.ballSound = ballSound;
        this.paddle = paddle;
        this.paddleImage = paddleImage;
        this.userInputListener = userInputListener;
        this.heartImage = heartImage;
        this.counterDoubleStrategy = 2;
    }

    /**
     * Create a new BasicCollisionStrategy instance.
     * The specific type of strategy is chosen randomly.
     *
     * @return The created BasicCollisionStrategy instance.
     */
    public BasicCollisionStrategy createStrategy(){
        Random randomStrategy = new Random();
        if (randomStrategy.nextBoolean()) {
            return new BasicCollisionStrategy(brickerGameManager);
        }
        return chooseSpecialStrategy();
    }

    /**
     * Create an array of specialStrategiesOptions.
     * The array includes all special strategies,
     * except for the DOUBLE strategy if it has already been chosen twice.
     *
     * @return The created array of specialStrategiesOptions.
     */
    private specialStrategiesOptions[] createArrayStrategy(){
        if(counterDoubleStrategy >= 1)
        {
            return new specialStrategiesOptions[]{specialStrategiesOptions.ADD_BALLS,
                    specialStrategiesOptions.CAMERA,
                    specialStrategiesOptions.DOUBLE_PADDLE,
                    specialStrategiesOptions.FALLING_HEART,
                    specialStrategiesOptions.DOUBLE};
        }
        return new specialStrategiesOptions []{specialStrategiesOptions.ADD_BALLS,
                specialStrategiesOptions.CAMERA,
                specialStrategiesOptions.DOUBLE_PADDLE,
                specialStrategiesOptions.FALLING_HEART};
    }

    /**
     * Choose a special strategy randomly and create a new instance of
     * the corresponding BasicCollisionStrategy subclass.
     *
     * @return The created BasicCollisionStrategy instance.
     */
    private BasicCollisionStrategy chooseSpecialStrategy(){
        Random random = new Random();
        specialStrategiesOptions [] arr = createArrayStrategy();
        specialStrategiesOptions chooseS = arr[random.nextInt(arr.length)];

        switch (chooseS){
            case ADD_BALLS:
                return new addBallsStrategy(brickerGameManager,PuckImage,ballSound);
            case CAMERA:
                return new CameraStrategy(brickerGameManager,ball);
            case DOUBLE_PADDLE:
                return new DoublePaddleStrategy(brickerGameManager, userInputListener, paddleImage);
            case FALLING_HEART:
                return new FallingHeartStrategy(brickerGameManager, heartImage, paddle);
            case DOUBLE:
                counterDoubleStrategy--;
                BasicCollisionStrategy strategy1 = chooseSpecialStrategy();
                BasicCollisionStrategy strategy2 = chooseSpecialStrategy();
                return new DoubleStrategy(brickerGameManager,strategy1, strategy2);
            default:
                //never append.
                return null;

        }
    }

}