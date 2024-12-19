package bricker.brick_strategies;
import danogl.GameObject;

/**
 * CollisionStrategy interface.
 * It represents a collision strategy in
 * the game where specific actions are taken when a collision occurs.
 * @author tomer gottman, michael messika
 */
public interface CollisionStrategy {
    /**
     * Handle the event when a collision occurs.
     * The specific actions taken depend on the
     * implementation of this method in the classes that implement this interface.
     *
     * @param objects_1 The first GameObject involved in the collision.
     * @param objects_2 The second GameObject involved in the collision.
     */
    public void onCollision(GameObject objects_1 , GameObject objects_2);
}