package bricker.brick_strategies;
import danogl.GameObject;
import bricker.main.BrickerGameManager;

/**
 * BasicCollisionStrategy class implements the CollisionStrategy interface.
 * It represents a basic collision strategy in the game where
 * a brick is removed when a collision occurs.
 * @author tomer gottman, michael messika
 */
public class BasicCollisionStrategy implements CollisionStrategy {
    //The game manager instance.
    private final BrickerGameManager brickerGameManager;

    /**
     * Construct a new BasicCollisionStrategy instance.
     *
     * @param brickerGameManager The game manager instance.
     */
    public BasicCollisionStrategy(BrickerGameManager brickerGameManager)
    {
        this.brickerGameManager = brickerGameManager;
    }

    /**
     * Handle the event when a collision occurs.
     * The brick involved in the collision is removed from the game.
     *
     * @param object1 The first GameObject involved in the collision.
     * @param brick The second GameObject involved in the collision, which is a brick.
     */
    @Override
    public void onCollision(GameObject object1, GameObject brick)
    {
        brickerGameManager.removeBrick(brick);
    }
}