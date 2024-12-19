package pepse.world.trees;
import java.util.Objects;
import java.util.function.BiConsumer;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.Const;
import pepse.util.Observer;
import pepse.world.Block;
import java.util.ArrayList;
import java.util.Random;
import pepse.util.ColorSupplier;

/**
 * Class for creating trees
 * Trees are created in a range of x values
 * The trees are created at random positions
 */
public class Tree {
    private final float block_size = Block.BLOCK_SIZE;
    private final BiConsumer<Fruit,GameObject> collision;
    private static final int leafBlock = 5;
    private final ArrayList<Leafs> leafs;
    private Trunk trunks;
    private final ArrayList<Fruit> fruits;
    private final Observer observer;
    private final Vector2 TreeLoc;
    private final BiConsumer<GameObject,Integer> remove;
    private final BiConsumer<GameObject,Integer> add;


    /**
     * Creates the tree object
     * The tree object is a trunk with leafs and fruits
     * @param trunkLoc the location of the trunk
     * @param height the height of the tree
     * @param onCollisionFruit the function that is called when a fruit collides with an object
     * @param observer the observer object
     * @param remove the function that removes an object from the game
     * @param add the function that adds an object to the game
     */
    public Tree(Vector2 trunkLoc,
                int height,
                BiConsumer<Fruit, GameObject> onCollisionFruit,
                Observer observer,BiConsumer<GameObject, Integer> remove,
                BiConsumer<GameObject, Integer> add)
    {
        this.leafs = new ArrayList<>();
        this.trunks = null;
        this.fruits = new ArrayList<>();
        this.collision = onCollisionFruit;
        this.observer = observer;
        this.TreeLoc = trunkLoc;
        this.add = add;
        this.remove = remove;
        // Create the trunk
        createTrunk(height, trunkLoc);
        // Create the leafs and fruits in random positions
        createLeafs(height, trunkLoc);

    }







    /**
     * Gets the trunk object
     * @return the trunk object
     */
    public Trunk GetTrunks()
    {
        return trunks;
    }

    /**
     * Gets the leafs objects
     * @return the leafs objects
     */
    public ArrayList<Leafs> GetLeafs()
    {
        return leafs;
    }

    /**
     * Gets the fruit objects
     * @return the fruit objects
     */
    public ArrayList<Fruit> GetFruits()
    {
        return fruits;
    }


    /**
     * Gets the location of the tree
     * @return the location of the tree
     */
    public Vector2 getTreeLoc()
    {
        return TreeLoc;
    }

    private void createLeafs(int height, Vector2 trunkLoc)
    {
        float y_loc =  trunkLoc.y() - height * block_size - (int)(leafBlock/2) * block_size;
        float x_loc = trunkLoc.x() - (int)(leafBlock/2) * block_size;
        Random hashRandom = new Random(Objects.hash(trunkLoc.x(),0));
        for(int i = 0; i < leafBlock; i++)
        {
            for(int j = 0; j < leafBlock; j++)
            {
                if(hashRandom.nextDouble() <= 0.3f ){continue;}
                Vector2 leafLoc = new Vector2(x_loc + i * block_size, y_loc + j * block_size);
                Leafs leaf = new Leafs(leafLoc, Const.LEAF_DIM, new RectangleRenderable(Const.LEAF_COLOR));
                leafs.add(leaf);
                add.accept(leaf, Layer.STATIC_OBJECTS);
                //register to the event when the avatar jumps
                observer.registerEvent(Const.ON_JUMP, leaf);
                if(hashRandom.nextDouble() >= 0.7f)
                {
                    Fruit fruit = createFruit(leafLoc, Const.FRUIT_DIM);
                    fruit.setCenter(leaf.getCenter());
                    //add the fruit to the list of fruits
                    fruits.add(fruit);
                    //add the fruit to the game
                    add.accept(fruit, Layer.DEFAULT);
                    //register to the event when the avatar jumps
                    observer.registerEvent(Const.ON_JUMP, fruit);
                }
            }
        }

    }

    /**
     * Creates the trunk object
     * The trunk object is a rectangle
     * @param height the height of the trunk object
     * @param trunkLoc the location of the trunk object
     */

    private void createTrunk(int height, Vector2 trunkLoc)
    {   float f_loc = trunkLoc.y() - block_size * height;
        Vector2 l = new Vector2(trunkLoc.x(), f_loc);
        RectangleRenderable trunkColor =
                new RectangleRenderable(ColorSupplier.approximateColor(Const.TrunkColor));
        trunks = new Trunk(l ,new Vector2(block_size,block_size *height),trunkColor);
        add.accept(trunks, Layer.STATIC_OBJECTS);
        //register to the event when the avatar jumps
        observer.registerEvent(Const.ON_JUMP, trunks);
    }


    /**
     * Removes the tree object
     * Removes the trunk, leafs and fruits
     * from the game
     */
    public void removeTree()
    {
        remove.accept(trunks, Layer.STATIC_OBJECTS);
        observer.unregisterEvent(Const.ON_JUMP, trunks);
        for (Leafs leaf : leafs) {
            remove.accept(leaf, Layer.STATIC_OBJECTS);
            observer.unregisterEvent(Const.ON_JUMP, leaf);
        }
        for (Fruit fruit : fruits) {
            remove.accept(fruit, Layer.DEFAULT);
            observer.unregisterEvent(Const.ON_JUMP, fruit);
        }
    }
    /**
     * Creates the fruit object
     * The fruit object is a circle that changes its color
     * when the avatar jumps
     * @param fruitLoc the location of the fruit object
     * @param fruitDim the dimensions of the fruit object
     * @return the fruit object
     */
    private Fruit createFruit(Vector2 fruitLoc, Vector2 fruitDim)
    {
        return new Fruit(fruitLoc, fruitDim, this.collision);
    }
}
