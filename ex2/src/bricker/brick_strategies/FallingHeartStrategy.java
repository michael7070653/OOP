package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import bricker.main.BrickerGameManager;
import bricker.gameobjects.FallingHeart;
import bricker.gameobjects.Paddle;
import bricker.ConstsBricker.ConstsBricker;

/**
 * FallingHeartStrategy class extends the BasicCollisionStrategy class.
 * It represents a falling heart strategy in the
 * game where a heart falls when a collision occurs.
 * @author tomer gottman, michael messika
 */
public class FallingHeartStrategy extends BasicCollisionStrategy {
    //The game manager instance.
    private final BrickerGameManager brickerGameManager;
    //The heart image.
    private final Renderable HeartImage;
    //The paddle instance.
    private final Paddle paddle;

    /**
     * Construct a new FallingHeartStrategy instance.
     *
     * @param brickerGameManager The game manager instance.
     * @param HeartImage The heart image.
     * @param paddle The paddle instance.
     */
    public FallingHeartStrategy(BrickerGameManager brickerGameManager, Renderable HeartImage, Paddle paddle) {
        super(brickerGameManager);
        this.brickerGameManager = brickerGameManager;
        this.HeartImage = HeartImage;
        this.paddle = paddle;
    }

    /**
     * Handle the event when a collision occurs.
     * The brick involved in the collision is removed from the game.
     * A new heart is added to the game at the center of the brick involved in the collision.
     * The heart falls with a velocity defined in the game constants.
     *
     * @param object1 The first GameObject involved in the collision.
     * @param brick The second GameObject involved in the collision, which is a brick.
     */
    @Override
    public void onCollision(GameObject object1, GameObject brick) {
        super.onCollision(object1, brick);
        Vector2 centerBrick = brick.getCenter();
        GameObject newHeart = new FallingHeart(Vector2.ZERO,
                ConstsBricker.DIM_HURT,
                HeartImage,
                brickerGameManager,
                paddle);
        newHeart.setCenter(centerBrick);
        newHeart.setVelocity(ConstsBricker.HEART_VEL);
        brickerGameManager.addGameObj(newHeart, Layer.DEFAULT);
    }
}
