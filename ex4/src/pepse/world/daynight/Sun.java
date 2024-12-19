package pepse.world.daynight;
import pepse.util.Const;
import danogl.GameObject;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import java.awt.*;

/**
 * Class for creating the sun object
 * The sun object is a circle that moves in a circular path
 * and changes its position
 */
public class Sun {
    private static final Vector2 DIMENSIONS = new Vector2(50, 50);
    private static final Color SUN_COLOR = Color.YELLOW;
    private static final float CYCLE_HEIGHT_FACTOR = 2.0f / 3.0f;
    private static final float INIT_HEIGHT_FACTOR = 1 - CYCLE_HEIGHT_FACTOR ;
    private static final float initialValue = 0.0f;
    private static final float finalValue = 360.0f;

    /**
     * Creates the sun object
     * The sun object is a circle that moves in a circular path
     * and changes its position
     * @param windowDimensions the dimensions of the window
     * @param cycleLength the length of the day-night cycle
     * @return the sun object
     */
    public static GameObject create(Vector2 windowDimensions,
                                    float cycleLength) {
        Vector2 init_loc = new Vector2(windowDimensions.x()/2,
                windowDimensions.y() * INIT_HEIGHT_FACTOR);
        Vector2 cycle = new Vector2(windowDimensions.x()/2,
                windowDimensions.y() * CYCLE_HEIGHT_FACTOR);
        OvalRenderable sun = new OvalRenderable(SUN_COLOR);
        GameObject sunObject = new GameObject(init_loc, DIMENSIONS, sun);
        sunObject.setCoordinateSpace(danogl.components.CoordinateSpace.CAMERA_COORDINATES);
        sunObject.setTag(Const.SUN_TAG);
        
        // The sun moves in a circular path
        new Transition<Float>(
                sunObject,
                (Float angle)-> sunObject.setCenter(init_loc.subtract(cycle).rotated(angle).add(cycle)),
                initialValue,
                finalValue,
                        Transition.LINEAR_INTERPOLATOR_FLOAT,
                        cycleLength,
                        Transition.TransitionType.TRANSITION_LOOP,
                        null);

    return sunObject;
    }
}
