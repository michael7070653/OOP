package bricker.brick_strategies;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import bricker.main.BrickerGameManager;
import bricker.gameobjects.DoublePaddle;
import bricker.ConstsBricker.ConstsBricker;

/**
 * DoublePaddleStrategy class extends the BasicCollisionStrategy class.
 * It represents a double paddle strategy in the
 * game where a double paddle is added to the game when a collision occurs.
 * @author tomer gottman, michael messika
 */
public class DoublePaddleStrategy extends BasicCollisionStrategy{
    //The user input listener instance.
    private final UserInputListener userInputListener;
    //The paddle image.
    private final Renderable paddleImag;
    //The window dimensions.
    private final Vector2 window_dim;
    //The counter for the number of hits.
    private static final Counter counterHits = new Counter();
    //The counter for the number of paddles.
    private static final Counter numPaddles = new Counter();
    //The game manager instance.
    private final BrickerGameManager brickerGameManager;

    /**
     * Construct a new DoublePaddleStrategy instance.
     *
     * @param brickerGameManager The game manager instance.
     * @param inputListener The user input listener instance.
     * @param paddleImag The paddle image.
     */
    public DoublePaddleStrategy(BrickerGameManager brickerGameManager,
                                UserInputListener inputListener,
                                Renderable paddleImag) {
        super(brickerGameManager);
        this.userInputListener = inputListener;
        this.paddleImag = paddleImag;
        this.window_dim = ConstsBricker.WIND_DIMENSIONS;
        this.brickerGameManager = brickerGameManager;
    }

    /**
     * Handle the event when a collision occurs.
     * The brick involved in the collision is removed from the game.
     * If there are no paddles in the game, a new double paddle is added to the game.
     *
     * @param object1 The first GameObject involved in the collision.
     * @param object2 The second GameObject involved in the collision.
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        super.onCollision(object1, object2);
        Vector2 loc = new Vector2(window_dim.x() /2, window_dim.y() /2);
        if(numPaddles.value() != 0)
        {
            return;
        }
        GameObject centerPaddle = new DoublePaddle(loc,
                ConstsBricker.PADDLE_SIZE,
                paddleImag,
                userInputListener,
                counterHits,numPaddles,
                brickerGameManager);
        centerPaddle.setCenter(new Vector2(window_dim.x() /2, window_dim.y() /2));
        brickerGameManager.addGameObj(centerPaddle, Layer.DEFAULT);
        numPaddles.increment();
    }
}