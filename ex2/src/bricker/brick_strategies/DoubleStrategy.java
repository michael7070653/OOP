package bricker.brick_strategies;

import danogl.GameObject;
import bricker.main.BrickerGameManager;

/**
 * DoubleStrategy class extends the BasicCollisionStrategy class.
 * It represents a double strategy in the game where two basic
 * collision strategies are applied when a collision occurs.
 * @author tomer gottman, michael messika
 */
public class DoubleStrategy extends BasicCollisionStrategy{
    //The first basic collision strategy instance
    private final BasicCollisionStrategy basicCollisionStrategy1;
    //The second basic collision strategy instance
    private final BasicCollisionStrategy basicCollisionStrategy2;

    /**
     * Construct a new DoubleStrategy instance.
     *
     * @param brickerGameManager The game manager instance.
     * @param basicCollisionStrategy1 The first basic collision strategy instance.
     * @param basicCollisionStrategy2 The second basic collision strategy instance.
     */
    public DoubleStrategy(BrickerGameManager brickerGameManager,
                          BasicCollisionStrategy basicCollisionStrategy1,
                          BasicCollisionStrategy basicCollisionStrategy2) {
        super(brickerGameManager);
        this.basicCollisionStrategy1 = basicCollisionStrategy1;
        this.basicCollisionStrategy2 = basicCollisionStrategy2;
    }

    /**
     * Handle the event when a collision occurs.
     * The brick involved in the collision is removed from the game.
     * The first and second basic collision strategies are applied.
     *
     * @param object1 The first GameObject involved in the collision.
     * @param brick The second GameObject involved in the collision, which is a brick.
     */
    @Override
    public void onCollision(GameObject object1, GameObject brick) {
        super.onCollision(object1, brick);
        basicCollisionStrategy1.onCollision(object1,brick);
        basicCollisionStrategy2.onCollision(object1,brick);
    }
}