package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import bricker.main.BrickerGameManager;
import bricker.ConstsBricker.ConstsBricker;

/**
 * FallingHeart class extends the GameObject class.
 * It represents a falling heart in the game which
 * can collide with the paddle and increase the player's life.
 * If the FallingHeart reaches the bottom of the window, it is removed from the game.
 * @author tomer gottman, michael messika
 */
public class FallingHeart extends GameObject {
    //The game manager instance.
    private final BrickerGameManager brickerGameManager;
    //The paddle instance.
    private final Paddle paddle;

    /**
     * Update the state of the FallingHeart.
     * If the FallingHeart reaches the bottom of the window, it is removed from the game.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(this.getCenter().y() >= ConstsBricker.HEIGHT_OF_THE_WINDOW)
        {
            brickerGameManager.removeGameObj(this,Layer.DEFAULT);
        }
    }

    /**
     * Determine whether this FallingHeart should collide with another GameObject.
     * This FallingHeart should only collide with the paddle.
     *
     * @param other The other GameObject.
     * @return True if the other GameObject is the paddle, false otherwise.
     */
    @Override
    public boolean shouldCollideWith(GameObject other)
    {
        return  other == paddle;
    }

    /**
     * Handle the event when a collision occurs.
     * When a collision occurs, the player's life is
     * increased and this FallingHeart object is removed from the game.
     *
     * @param other The other GameObject involved in the collision.
     * @param collision The Collision object containing details about the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        brickerGameManager.addLife();
        brickerGameManager.removeGameObj(this, Layer.DEFAULT);
    }

    /**
     * Construct a new FallingHeart instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the FallingHeart will not be rendered.
     * @param brickerGameManager The game manager instance.
     * @param paddle The paddle instance.
     */
    public FallingHeart(Vector2 topLeftCorner,
                        Vector2 dimensions,
                        Renderable renderable,
                        BrickerGameManager brickerGameManager,
                        Paddle paddle) {
        super(topLeftCorner, dimensions, renderable);
        this.brickerGameManager = brickerGameManager;
        this.paddle = paddle;
    }
}
