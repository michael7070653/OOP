package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.Const;

/**
 * Class for creating the night object
 * The night object is a rectangle that covers the
 * screen and changes its opacity
 */

public class Night {
    private static final Float MIDNIGHT_OPACITY = 0.5f;
    private static final Float INIT_ = 0.0f;
    /**
     * Creates the night object
     * @param windowDimensions the dimensions of the window
     * @param cycleLength the length of the day-night cycle
     * @return the night object
     */
    public static GameObject create(Vector2 windowDimensions,float cycleLength) {
        RectangleRenderable rectangle = new RectangleRenderable(Const.NIGHT_COLOR);
        GameObject night = new GameObject(Vector2.ZERO,windowDimensions,rectangle);
        night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        night.setTag(Const.NIGHT_TAG);

        new Transition<>(night,
                night.renderer()::setOpaqueness,
                INIT_,
                MIDNIGHT_OPACITY,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                //half day-night cycle
                cycleLength/2.0f,
                 Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);
        return night;
    }
}
