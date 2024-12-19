package pepse.world;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.Const;

/**
 * Class for creating the block object
 * The block object is a square that is immovable
 */
public class Block extends GameObject {
    /**
     * The size of the block object
     */
    public static final int BLOCK_SIZE = 30;
    /**
     * Creates the block object
     * The block object is a square that is immovable
     * @param topLeftCorner the top left corner of the block object
     * @param renderable the renderable object
     */
    public Block(Vector2 topLeftCorner, Renderable renderable) {
        super(topLeftCorner,Vector2.ONES.mult(BLOCK_SIZE), renderable);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
    }

    /**
     * Checks if the block object should collide with another object
     * @param other the other object
     * @return true if the block object should collide with the other object
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other.getTag().equals(Const.AvatarTag);
    }
}
