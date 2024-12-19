package bricker.gameobjects;

import danogl.collisions.Layer;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import bricker.main.BrickerGameManager;
import bricker.ConstsBricker.ConstsBricker;

/**
 * PuckBall class extends the Ball class.
 * It represents a puck ball in the game which can be removed
 * from the game when it reaches the bottom of the window.
 * @author tomer gottman, michael messika
 */
public class PuckBall extends Ball {

    //The game manager instance.
    private final BrickerGameManager brickerGameManager;

    /**
     * Construct a new PuckBall instance.
     *
     * @param topLeftCorner  Position of the object, in window coordinates (pixels).
     *                       Note that (0,0) is the top-left corner of the window.
     * @param dimensions     Width and height in window coordinates.
     * @param renderable     The renderable representing the object. Can be null, in which case
     *                       the PuckBall will not be rendered.
     * @param collisionSound The sound to be played when a collision occurs.
     * @param brickerGameManager The game manager instance.
     */
    public PuckBall(Vector2 topLeftCorner,
                    Vector2 dimensions,
                    Renderable renderable,
                    Sound collisionSound,
                    BrickerGameManager brickerGameManager) {
        super(topLeftCorner,
                dimensions,
                renderable,
                collisionSound);
        this.brickerGameManager = brickerGameManager;
    }

    /**
     * Update the state of the PuckBall.
     * If the PuckBall reaches the bottom of the window, it is removed from the game.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(this.getCenter().y() > ConstsBricker.HEIGHT_OF_THE_WINDOW)
        {
            brickerGameManager.removeGameObj(this, Layer.DEFAULT);
        }
    }
}
