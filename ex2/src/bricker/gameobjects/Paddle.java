package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import bricker.ConstsBricker.ConstsBricker;

import java.awt.event.KeyEvent;

/**
 * Paddle class extends the GameObject class.
 * It represents a paddle in the game which can be controlled by the user's input.
 * The paddle's movement is limited to the window's dimensions.
 * When a collision occurs, the paddle does not perform any actions.
 * @author tomer gottman, michael messika
 */
public class Paddle extends GameObject {

    //The window dimensions.
    private final Vector2 dimW;
    //The movement speed of the paddle.
    private static final float MOVMENT_SPEED = 300;
    //The user input listener instance.
    private final UserInputListener inputListener;
    //The dimensions of the paddle.
    private final Vector2 dimPaddle;

    /**
     * Construct a new Paddle instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the Paddle will not be rendered.
     * @param inputListener The listener for user input.
     */
    public Paddle(Vector2 topLeftCorner,
                  Vector2 dimensions,
                  Renderable renderable,
                  UserInputListener inputListener)
    {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.dimW = ConstsBricker.WIND_DIMENSIONS;
        this.dimPaddle = dimensions;
    }

    /**
     * Update the state of the Paddle.
     * The paddle's position is updated based on the user's input.
     * If the paddle reaches the edge of the window, it is prevented from moving further.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 speed = Vector2.ZERO;
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT))
        {
            speed = speed.add(Vector2.LEFT);
        }
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT))
        {
            speed = speed.add(Vector2.RIGHT);
        }
        Vector2 left_corner = getTopLeftCorner();
        if (left_corner.x() <  0)
        {
            setTopLeftCorner(new Vector2(0,left_corner.y()));
        }
        if(left_corner.x() + dimPaddle.x() > dimW.x()) {
            setTopLeftCorner(new Vector2(dimW.x() - dimPaddle.x(),left_corner.y()));
        }
        setVelocity(speed.mult(MOVMENT_SPEED));

    }

    /**
     * Handle the event when a collision occurs.
     * Currently, this method does not perform any actions when a collision occurs.
     *
     * @param other The other GameObject involved in the collision.
     * @param collision The Collision object containing details about the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other,
                                 Collision collision) {
        super.onCollisionEnter(other, collision);
    }

}
