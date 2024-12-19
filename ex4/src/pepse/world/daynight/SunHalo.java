package pepse.world.daynight;
import danogl.GameObject;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import pepse.util.Const;

/**
 * Class for creating the sun halo object
 * The sun halo object is a circle that moves in a circular path
 * and changes its position
 */
public class SunHalo {
    private static final String tag = "sunHalo";
    private static final float FACTOR_DIMENSION = 2.0f;
    /**
     * Creates the sun halo object
     * The sun halo object is a circle that moves in a circular path
     * and changes its position
     * @param sun the sun object
     * @return the sun halo object
     */
    public static GameObject create(GameObject sun) {
        Vector2 loc = sun.getCenter();
        OvalRenderable halo = new OvalRenderable(Const.HALO_COLOR);
        GameObject haloObject = new GameObject(Vector2.ZERO,
                sun.getDimensions().mult(FACTOR_DIMENSION), halo);
        haloObject.setCenter(loc);
        haloObject.setCoordinateSpace(danogl.components.CoordinateSpace.CAMERA_COORDINATES);
        haloObject.setTag(tag);
        haloObject.addComponent((deltaTime) -> {
            haloObject.setCenter(sun.getCenter());
        });

        return haloObject;
    }
}
