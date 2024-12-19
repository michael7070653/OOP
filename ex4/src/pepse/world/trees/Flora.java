package pepse.world.trees;
import danogl.GameObject;
import danogl.util.Vector2;
import pepse.util.Observer;
import pepse.util.UtilsFunction;
import pepse.world.Block;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;


/**
 * Class for creating trees
 * Trees are created in a range of x values
 * The trees are created at random positions
 */
public class Flora {
    private static final float CHANCE = 0.1f;
    private static final int SPACE = 6 * Block.BLOCK_SIZE;
    private final Function<Float, Float> heightFunc;
    private static final int MinHeight = 4;
    private static final int MaxHeight = 6;
    private final Observer observer;
    private final BiConsumer<Fruit, GameObject> onCollisionFruit;
    private final ArrayList<Tree> TreesArray = new ArrayList<>();
    private final BiConsumer<GameObject,Integer> remove;
    private final BiConsumer<GameObject,Integer> add;

    /**
     * Creates the flora object
     * @param heightFunc the function that determines the height of the tree
     * @param onCollisionFruit the function that is called when a fruit collides with an object
     * @param observer the observer object
     */
    public Flora(Function<Float,Float> heightFunc,
                 BiConsumer<Fruit, GameObject> onCollisionFruit,
                 Observer observer,
                 BiConsumer<GameObject, Integer> remove,
                 BiConsumer<GameObject, Integer> add) {
        this.heightFunc = heightFunc;
        this.onCollisionFruit = onCollisionFruit;
        this.observer = observer;
        this.remove = remove;
        this.add = add;
    }

    /**
     * Creates trees in a range of x values
     * The trees are created at random positions
     * @param minX the minimum x value
     * @param maxX the maximum x value
     * @return the list of trees
     */
    public ArrayList<Tree> createInRange(int minX, int maxX) {
        ArrayList<Tree> trees = new ArrayList<>();
        float xMin = UtilsFunction.roundMinX(minX);
        float xMax = UtilsFunction.roundMaxX(maxX);
        for(float i = xMin; i < xMax; i += Block.BLOCK_SIZE)
        {
            Random r = new Random(Objects.hash(i, 0));
            float y = heightFunc.apply(i);
            if(r.nextDouble()<= CHANCE)
            {
                Tree newT = new Tree(new Vector2(i, y),
                    r.nextInt(MaxHeight) + MinHeight,
                    onCollisionFruit,
                    observer,remove,add);
                TreesArray.add(newT);
                trees.add(newT);

            }
        }
        return trees;
    }

    /**
     * Removes trees that are to the right of the maximum x value
     * @param maxX the maximum x value
     */
    public void removeObjectsFromRight(float maxX) {
        ArrayList<Tree> temp = new ArrayList<>();
        for (Tree tree : TreesArray) {
            if (tree.getTreeLoc().x() >= maxX) {
                temp.add(tree);
                tree.removeTree();
            }
        }
        TreesArray.removeAll(temp);
    }


    /**
     * Removes trees that are to the left of the minimum x value
     * @param minX the minimum x value
     */
    public void removeObjectsFromLeft(float minX) {
        ArrayList<Tree> temp = new ArrayList<>();
        for (Tree tree : TreesArray) {
            if (tree.getTreeLoc().x() <= minX) {
                temp.add(tree);
                tree.removeTree();
            }
        }
        TreesArray.removeAll(temp);
    }
}

