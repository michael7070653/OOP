package bricker.gameobjects;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import bricker.main.BrickerGameManager;
import bricker.ConstsBricker.ConstsBricker;

/**
 * CameraControl class extends the GameObject class.
 * It represents a camera control in the game which is responsible for managing the game camera
 * based on the ball's collision counter.
 * If the ball's collision counter reaches a certain threshold, the game camera is set to null
 * and this CameraControl object is removed from the game.
 * @author tomer gottman, michael messika
 */
public class CameraControl extends GameObject {

    //The game manager instance.
    private final BrickerGameManager brickerGameManager;
    //The ball instance.
    private final Ball ball;
    //The maximum count for the ball's collision counter.
    private final int MAX_COUNT;

    /**
     * Construct a new CameraControl instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the CameraControl will not be rendered.
     * @param ball          The ball instance.
     * @param brickerGameManager The game manager instance.
     */
    public CameraControl(Vector2 topLeftCorner,
                         Vector2 dimensions,
                         Renderable renderable,
                         Ball ball,
                         BrickerGameManager brickerGameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.brickerGameManager = brickerGameManager;
        this.ball = ball;
        this.MAX_COUNT = ball.getCollisionCounter();
    }

    /**
     * Update the state of the CameraControl.
     * If the ball's collision counter reaches a certain threshold,
     * the game camera is set to null and this CameraControl object is removed from the game.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(ball.getCollisionCounter() >= MAX_COUNT + ConstsBricker.FOUR_COUNT)
        {
            brickerGameManager.setCamera(null);
            brickerGameManager.removeGameObj(this, Layer.BACKGROUND);
        }
    }
}
