package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.Layer;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import bricker.main.BrickerGameManager;
import bricker.ConstsBricker.ConstsBricker;

/**
 * DoublePaddle class extends the Paddle class.
 * It represents a double paddle in the game with its own collision behavior and hit counter.
 * If the hit counter reaches a certain threshold, it is reset, the paddle counter is reset,
 * and this DoublePaddle object is removed from the game.
 * The double paddle is controlled by the user input listener.
 * @author tomer gottman, michael messika
 */
public class DoublePaddle extends Paddle {
    //Counter for the number of hits
    private final Counter counterHits;
    //Counter for the number of paddles
    private final Counter numPaddles;
    //The game manager instance
    private final BrickerGameManager brickerGameManager;

    /**
     * Construct a new DoublePaddle instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the DoublePaddle will not be rendered.
     * @param inputListener The listener for user input.
     * @param counterHits   The counter for the number of hits.
     * @param numPaddles    The counter for the number of paddles.
     * @param brickerGameManager The game manager instance.
     */
    public DoublePaddle(Vector2 topLeftCorner,
                        Vector2 dimensions,
                        Renderable renderable,
                        UserInputListener inputListener,
                        Counter counterHits,
                        Counter numPaddles,
                        BrickerGameManager brickerGameManager) {
        super(topLeftCorner, dimensions, renderable, inputListener);
        this.counterHits = counterHits;
        this.numPaddles = numPaddles;
        this.brickerGameManager = brickerGameManager;
    }

    /**
     * Handle the event when a collision occurs.
     * The hit counter is incremented. If the hit counter reaches a certain threshold,
     * it is reset, the paddle counter is reset, and this DoublePaddle object is removed from the game.
     *
     * @param other The other GameObject involved in the collision.
     * @param collision The Collision object containing details about the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if(other.getTag().equals(ConstsBricker.BALL_TAG)
                || other.getTag().equals(ConstsBricker.PUCK_BALL_TAG)){
            counterHits.increment();
        }
        if(counterHits.value() == ConstsBricker.MAX_HIT_PADDLE)
        {
            counterHits.reset();
            numPaddles.reset();
            brickerGameManager.removeGameObj(this, Layer.DEFAULT);
        }
    }
}
