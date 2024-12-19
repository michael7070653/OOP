package bricker.brick_strategies;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;
import bricker.main.BrickerGameManager;
import bricker.gameobjects.Ball;
import bricker.gameobjects.CameraControl;
import bricker.ConstsBricker.ConstsBricker;

/**
 * CameraStrategy class extends the BasicCollisionStrategy class.
 * It represents a camera strategy in the game where a camera
 * is added to the game when a collision occurs.
 * @author tomer gottman, michael messika
 */
public class CameraStrategy extends BasicCollisionStrategy{
    //The ball instance.
    private final Ball ball;
   //The game manager instance.
    private final BrickerGameManager brickerGameManager;

    /**
     * Construct a new CameraStrategy instance.
     *
     * @param brickerGameManager The game manager instance.
     * @param ball The ball instance.
     */
    public CameraStrategy(BrickerGameManager brickerGameManager, Ball ball) {
        super(brickerGameManager);
        this.ball = ball;
        this.brickerGameManager = brickerGameManager;
    }

    /**
     * Handle the event when a collision occurs.
     * The brick involved in the collision is removed from the game.
     * If the first GameObject involved in the collision is the ball, a camera strategy is applied.
     *
     * @param object1 The first GameObject involved in the collision.
     * @param brick The second GameObject involved in the collision, which is a brick.
     */
    @Override
    public void onCollision(GameObject object1, GameObject brick) {
        super.onCollision(object1, brick);
        if(object1 != ball)
        {
            return;
        }
        cameraStrategy();
    }

    /**
     * Apply the camera strategy.
     * If there is no camera in the game, a new camera is added to the game.
     * The camera follows the ball and a new CameraControl instance is added to the game.
     */
    private void cameraStrategy() {
        if(brickerGameManager.camera() == null)
        {
            Camera camera = new Camera(this.ball,
                    Vector2.ZERO,
                    ConstsBricker.WIND_DIMENSIONS.mult(1.2f),
                    ConstsBricker.WIND_DIMENSIONS);
            brickerGameManager.setCamera(camera);
            CameraControl cameraControl = new CameraControl(Vector2.ZERO,
                    Vector2.ZERO,
                    null,ball,
                    brickerGameManager);
            brickerGameManager.addGameObj(cameraControl, Layer.BACKGROUND);
        }
    }
}