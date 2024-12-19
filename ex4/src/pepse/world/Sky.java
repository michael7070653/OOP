package pepse.world;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.Const;

import java.awt.*;
/**
 * Class for creating the sky object
 * The sky object is a rectangle that is the sky
 */
public class Sky {
    private static final Color BASIC_SKY_COLOR = Color.decode("#80C6E5");

    /**
     * Creates the sky object
     * The sky object is a rectangle that is the sky
     * @param windowDimensions the dimensions of the window
     * @return the sky object
     */
    public static GameObject create(Vector2 windowDimensions) {
        RectangleRenderable rectangle = new RectangleRenderable(BASIC_SKY_COLOR);
        GameObject sky = new GameObject(Vector2.ZERO, windowDimensions, rectangle);
        sky.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sky.setTag(Const.SKY_TAG);
        return sky;

    }
 }
