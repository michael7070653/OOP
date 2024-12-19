package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Ball class extends the GameObject class.
 * It represents a ball in the game with its own
 * properties such as collision sound and collision counter.
 * @author tomer gottman, michael messika
 */
public class Ball extends GameObject {

   //The sound to be played when a collision occurs.
    private final Sound collisionSound;
    //The collision counter.
    private int collisionCounter = 0;

    /**
     * Construct a new Ball instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the Ball will not be rendered.
     * @param collisionSound The sound to be played when a collision occurs.
     */
    public Ball(Vector2 topLeftCorner,
                Vector2 dimensions,
                Renderable renderable,
                Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
    }

    /**
     * Get the current collision counter.
     *
     * @return The current collision counter.
     */
    public int getCollisionCounter() {
        return collisionCounter;
    }

    /**
     * Handle the event when a collision occurs.
     * The velocity of the ball is updated,
     * the collision sound is played, and the collision counter is incremented.
     *
     * @param other The other GameObject involved in the collision.
     * @param collision The Collision object containing details about the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        Vector2 new_vel = getVelocity().flipped(collision.getNormal());
        setVelocity(new_vel);
        collisionSound.play();
        collisionCounter++;
    }
}
