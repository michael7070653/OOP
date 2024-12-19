package pepse.world.trees;
import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.Const;
import pepse.util.Observer;
import pepse.util.executeFunc;

/**
 * Class for creating the trunk object
 * The trunk object is a rectangle that changes its color
 * when the avatar jumps
 */
public class Trunk extends GameObject implements executeFunc {

    /**
     * Creates the trunk object
     * The trunk object is a rectangle that changes its color
     * when the avatar jumps
     * @param topLeftCorner the top left corner of the trunk object
     * @param dimensions the dimensions of the trunk object
     * @param renderable the renderable object
     */
    public Trunk(Vector2 topLeftCorner,
                 Vector2 dimensions,
                 Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
        this.setTag(Const.treeComponent);
    }

    /**
     * function that is called when the avatar jumps
     * Changes the color of the trunk object
     */
    @Override
    public void execute() {
        this.renderer().setRenderable(
                new RectangleRenderable(ColorSupplier.approximateColor(Const.TrunkColor)));
    }
}
