package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import bricker.brick_strategies.CollisionStrategy;

/**
 * Brick class extends the GameObject class.
 * It represents a brick in the game with its own collision strategy.
 * When a collision occurs, the collision strategy is executed.
 * @author tomer gottman, michael messika
 */
public class Brick extends GameObject {
    //The collision strategy instance.
    private final CollisionStrategy collisionStrategy;

    /**
     * Construct a new Brick instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the Brick will not be rendered.
     * @param collisionStrategy The strategy to be used when a collision occurs.
     */
    public Brick(Vector2 topLeftCorner,
                 Vector2 dimensions,
                 Renderable renderable,
                 CollisionStrategy collisionStrategy) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
    }

    /**
     * Handle the event when a collision occurs.
     * The collision strategy is executed.
     *
     * @param other The other GameObject involved in the collision.
     * @param collision The Collision object containing details about the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other,collision);
        this.collisionStrategy.onCollision(other, this);
    }
}
