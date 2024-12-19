package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import bricker.main.BrickerGameManager;
import bricker.gameobjects.PuckBall;
import bricker.ConstsBricker.ConstsBricker;

import java.util.Random;

/**
 * addBallsStrategy class extends the BasicCollisionStrategy class.
 * It represents a strategy where additional balls are added
 * to the game when a collision occurs.
 * @author tomer gottman, michael messika
 */
public class addBallsStrategy extends BasicCollisionStrategy{
    //The game manager instance.
    private final BrickerGameManager brickerGameManager;
    //The image of the puck.
    private final Renderable puckImage;
    //The sound to be played when a collision occurs.
    private final Sound collisionSound;

    /**
     * Construct a new addBallsStrategy instance.
     *
     * @param brickerGameManager The game manager instance.
     * @param puckImage The image of the puck.
     * @param collisionSound The sound to be played when a collision occurs.
     */
    public addBallsStrategy(BrickerGameManager brickerGameManager,
                            Renderable puckImage,
                            Sound collisionSound) {
        super(brickerGameManager);
        this.puckImage = puckImage;
        this.brickerGameManager = brickerGameManager;
        this.collisionSound = collisionSound;
    }

    /**
     * Handle the event when a collision occurs.
     * When a collision occurs, additional balls are added to the game.
     *
     * @param object1 The first GameObject involved in the collision.
     * @param brick The second GameObject involved in the collision.
     */
    @Override
    public void onCollision(GameObject object1, GameObject brick) {
        super.onCollision(object1, brick);
        createPuckBalls(brick);
    }

    /**
     * Create additional balls.
     * The balls are created at the location of the brick and added to the game.
     *
     * @param brick The brick GameObject.
     */
    private void createPuckBalls(GameObject brick) {
        Vector2 loc = brick.getCenter();
        float size = 0.75f * ConstsBricker.BALL_SIZE;
        Vector2 dim = new Vector2(size, size);
        for(int i = 0; i < ConstsBricker.BALLS_TO_ADD; i++)
        {
            PuckBall newPuck = new PuckBall(Vector2.ZERO,
                    dim,puckImage,
                    collisionSound,
                    brickerGameManager);
            newPuck.setTag(ConstsBricker.PUCK_BALL_TAG);
            newPuck.setCenter(loc);
            newPuck.setVelocity(getNewVel());
            brickerGameManager.addGameObj(newPuck, Layer.DEFAULT);
        }
    }

    /**
     * Get a new velocity for the ball.
     * The velocity is calculated based on a random angle.
     *
     * @return The new velocity as a Vector2.
     */
    Vector2 getNewVel()
    {
        Random random  = new Random();
        double angle = random.nextDouble()*Math.PI;
        float velocityX = (float)Math.cos(angle) * ConstsBricker.BALL_SPEED;
        float velocityY = (float)Math.sin(angle) * ConstsBricker.BALL_SPEED;
        return new Vector2(velocityX,velocityY);
    }
}