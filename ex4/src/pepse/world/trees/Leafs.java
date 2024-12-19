package pepse.world.trees;

import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.Const;
import pepse.util.Observer;
import pepse.util.executeFunc;

import java.util.Objects;
import java.util.Random;

/**
 * Class for creating the leafs object
 * The leafs object is a rectangle that rotates
 * and changes its dimensions
 */
public class Leafs extends GameObject implements executeFunc {
    private static final float ROTATE_TIME = 1.0F;
    private final Random random;
    private static final float MIN_LEAF_START = 0.0f;
    private static final float MAX_LEAF_START = 3.0f;
    private final Vector2 dimensions;
    private boolean inRotation = false;

    /**
     * Creates the leafs object
     * The leafs object is a rectangle that rotates
     * and changes its dimensions
     * @param topLeftCorner the top left corner of the leafs object
     * @param dimensions the dimensions of the leafs object
     * @param renderable the renderable object
     */
    public Leafs(Vector2 topLeftCorner,
                 Vector2 dimensions,
                 Renderable renderable) {

        super(topLeftCorner, dimensions, renderable);
        this.random = new Random(Objects.hash(topLeftCorner.x(), 0));
        this.dimensions = dimensions;
        new ScheduledTask(this,
                random.nextFloat(MIN_LEAF_START,MAX_LEAF_START),
                false,
                this::TransitionFloat);
        this.setTag(Const.treeComponent);
    }


    /**
     * Transitions the leafs object
     */
    private void TransitionFloat()
    {
        // The leaf rotates with a random angle
        new Transition<Float>(
                this,
                (Float angle)->this.renderer().setRenderableAngle(angle),
                MIN_LEAF_START,
                MAX_LEAF_START,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                random.nextFloat(MIN_LEAF_START,MAX_LEAF_START),
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);


        // The leaf changes its dimensions
        new Transition<Vector2>(
                this,
                this::setDimensions,
                dimensions,
                dimensions.mult(0.5f),
                Transition.LINEAR_INTERPOLATOR_VECTOR,
                random.nextFloat(MIN_LEAF_START,MAX_LEAF_START),
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);

    }
    /**
     * Executes the leafs object
     * The leafs object rotates with a random angle
     * wehn the avatar jumps
     */
    @Override
    public void execute()
    {
        this.inRotation = true;
        new Transition<Float>(
                this,
                (Float angle)->this.renderer().setRenderableAngle(angle),
                this.renderer().getRenderableAngle(),
                this.renderer().getRenderableAngle() + Const.LEAF_ROTATE_ANGLE,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                ROTATE_TIME,
                Transition.TransitionType.TRANSITION_ONCE,
                ()->this.inRotation = false);
    }

    /**
     * Determines if the leafs object should collide with another object
     * @param other the other object
     * @return true if the leafs object should collide with the other object
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return false;
    }
}
