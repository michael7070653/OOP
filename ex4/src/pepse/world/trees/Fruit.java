package pepse.world.trees;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import pepse.util.Const;
import pepse.util.Observer;
import pepse.util.executeFunc;

import java.util.function.BiConsumer;
/**
 * Class for creating the fruit object
 * The fruit object is a circle that changes its color
 * when the avatar jumps
 */

public class Fruit  extends GameObject implements executeFunc {
    private int x = 1;
    private final BiConsumer<Fruit, GameObject> onCollision;

    /**
     * Creates the fruit object
     * The fruit object is a circle that changes its color
     * when the avatar jumps
     * @param topLeftCorner the top left corner of the fruit object
     * @param dimensions the dimensions of the fruit object
     * @param onCollision the function that is called when the fruit collides with an object
     */
    public Fruit(Vector2 topLeftCorner,
                 Vector2 dimensions,
                 BiConsumer<Fruit, GameObject> onCollision) {
        super(topLeftCorner, dimensions, new OvalRenderable(Const.FruitColor[0]));
        this.onCollision = onCollision;
        this.setTag(Const.treeComponent);

    }

    /**
     * Determines if the fruit object should collide with another object
     * @param other the other object
     * @return true if the fruit object should collide with the other object
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other.getTag().equals(Const.AvatarTag);
    }

    /**
     * Determines what happens when the fruit object collides with another object
     * @param other the other object
     * @param collision the collision object
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        onCollision.accept(this, other);
    }

    /**
     * Changes the color of the fruit object
     * when the avatar jumps
     */
    @Override
    public void execute() {
        if(x >= Const.FruitColor.length) x = 0;
        this.renderer().setRenderable(new OvalRenderable(Const.FruitColor[x]));
        x++;
    }











}
