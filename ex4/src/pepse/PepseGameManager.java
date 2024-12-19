package pepse;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.ScheduledTask;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import pepse.util.Const;
import pepse.util.Observer;
import pepse.world.Avatar;
import pepse.world.Block;
import pepse.world.Terrain;
import pepse.world.trees.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;


/**
 * The main class for the game
 * The game is a 2D platformer game where the player can jump and eat fruits
 * The player has a certain amount of energy that decreases over time
 * The player can gain energy by eating fruits
 * The player can jump and move left and right
 */
public class PepseGameManager extends GameManager {
    private static final int skyL = Layer.BACKGROUND;
    private static final String ENERGY = "Energy: ";
    private static final float INIT_ENERGY = 100;
    private static final float Buffer = 60;
    private Camera camera;
    private Avatar avatar;
    private WindowController windowController;
    private  GameObject userInterface ;
    private final Observer observer = new Observer();
    private Terrain terrain ;
    private int minX;
    private int maxX;
    private Vector2 avatarLocation;
    private final Random random = new Random(Objects.hash(60,0));
    private Flora flora;








    /**
     * Creates the user interface object
     * @param avatar the avatar object
     * @return the user interface object
     */
    private GameObject creatUserInterface(Avatar avatar) {
        TextRenderable energyText = new TextRenderable(ENERGY + INIT_ENERGY);
        GameObject lifeObject = new GameObject(new Vector2(10, 10),
                new Vector2(100, 20), energyText);
        lifeObject.setCoordinateSpace(danogl.components.CoordinateSpace.CAMERA_COORDINATES);
        lifeObject.addComponent((deltaTime) -> {
            energyText.setString(ENERGY + avatar.getEnergy());
        });
        lifeObject.setTag(Const.USER_INTERFACE_TAG);
        return lifeObject;
    }



    /**
     * Initializes the game
     * @param imageReader the image reader object
     * @param soundReader the sound reader object
     * @param inputListener the user input listener object
     * @param windowController the window controller object
     */
    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {

        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        windowController.setTargetFramerate(80);

        ///set the min and max x values
        minX = -150;
        maxX = (int)windowController.getWindowDimensions().x() + 150;

        ///add the jump event to the observer
        observer.addEvent(Const.ON_JUMP);
        this.windowController = windowController;
        Vector2 windowDimensions = windowController.getWindowDimensions();

        //create the sky, night, sun, terrain, avatar, and trees

        //sky
        creatSky(windowDimensions);
        ///night
        creatNight(windowDimensions);
        ///sun and sun halo
        crateSun(windowDimensions);

        ///terrain
        this.terrain = new Terrain(windowDimensions, 0);
        creatTerrainInRang(terrain, minX, maxX);
        ///avatar
        createAvatar(imageReader, inputListener, windowDimensions);

        /////CREATE TREES
        this.flora = new Flora(terrain::groundHeight,
                this::fruitCollision,
                observer,
                this::removal,
                this::add);
        createFlora(terrain, minX, maxX);

        ///user interface
        userInterface = creatUserInterface(avatar);
        gameObjects().addGameObject(userInterface, Layer.UI);
        camera = new Camera(avatar,Vector2.ZERO, windowDimensions,windowDimensions);
        setCamera(camera);


    }


    /**
     * Creates the avatar object
     * @param imageReader the image reader object
     * @param inputListener the user input listener object
     * @param windowDimensions the dimensions of the window
     */
    private void createAvatar(ImageReader imageReader,
                              UserInputListener inputListener,
                              Vector2 windowDimensions) {
        this.avatarLocation = new Vector2(windowDimensions.x()/2,
                windowDimensions.y() * (2.0f / 3.0f) - 50);
        this.avatar = new Avatar(avatarLocation, inputListener, imageReader, observer);
        avatarLocation = avatar.getCenter();
        gameObjects().addGameObject(avatar);
    }



    /**
     * Creates the terrain in the range of x values
     * @param terrain the terrain object
     * @param minX the minimum x value
     * @param maxX the maximum x value
     */
    private void creatTerrainInRang(Terrain terrain, int minX, int maxX) {
        List<Block> blocks = terrain.createInRange(minX, maxX);
        for (Block block : blocks) {
            gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
        }
    }


    /**
     * Creates the sun object
     * @param windowDimensions the dimensions of the window
     */
    private void crateSun(Vector2 windowDimensions) {
        GameObject sun = pepse.world.daynight.Sun.create(windowDimensions, Const.GamaCycle);
        GameObject sunHalo = pepse.world.daynight.SunHalo.create(sun);
        gameObjects().addGameObject(sunHalo, Layer.BACKGROUND);
        gameObjects().addGameObject(sun, Layer.BACKGROUND);
    }


    /**
     * Creates the night object
     * @param windowDimensions the dimensions of the window
     */
    private void creatNight(Vector2 windowDimensions) {
        GameObject night = pepse.world.daynight.Night.create(windowDimensions, Const.GamaCycle);
        gameObjects().addGameObject(night, Layer.FOREGROUND);
    }

    /**
     * Creates the sky object
     * @param windowDimensions the dimensions of the window
     */
    private void creatSky(Vector2 windowDimensions) {
        GameObject sky = pepse.world.Sky.create(windowDimensions);
        gameObjects().addGameObject(sky, skyL);
    }

    
    /**
     * Removes the objects from the left
     * @param minX the minimum x value
     */
    private void removeObjectsFromLeft(float minX) {
        ArrayList<Block> temp = new ArrayList<>();
        for (Block gameObject :terrain.getAllTerrain()) {
            if (gameObject.getTopLeftCorner().x() <= minX ) {
                gameObjects().removeGameObject(gameObject,Layer.STATIC_OBJECTS);
                temp.add(gameObject);
            }
        }
        terrain.getAllTerrain().removeAll(temp);
        flora.removeObjectsFromLeft(minX);
    }

    /**
     * Removes the objects from the right
     * @param maxX the maximum x value
     */
    private void removeObjectsFromRight(float maxX) {
        ArrayList<Block> temp = new ArrayList<>();
        for (Block gameObject :terrain.getAllTerrain()) {
            if (gameObject.getTopLeftCorner().x() >= maxX ) {
                gameObjects().removeGameObject(gameObject,Layer.STATIC_OBJECTS);
                temp.add(gameObject);
            }
        }
        terrain.getAllTerrain().removeAll(temp);
        flora.removeObjectsFromRight(maxX);
    }


    /**
     * Creates the objects in the range of x values
     * @param minX the minimum x value
     * @param maxX the maximum x value
     */
    private void createObjects(int minX, int maxX) {

        creatTerrainInRang(terrain, minX, maxX);
        createFlora(terrain, minX, maxX);
    }






    /**
     * Creates the trees in the range of x values
     * @param terrain the terrain object
     * @param minX the minimum x value
     * @param maxX the maximum x value
     */
    private void createFlora(Terrain terrain, int minX, int maxX) {
        ArrayList<Tree> trees = flora.createInRange(minX, maxX);
    }



    /**
     * Function that is called when the avatar eats a fruit
     */
    private void fruitCollision(Fruit fruit, GameObject other) {
        if(other!= avatar){return;}
        avatar.addEnergy(Const.AddFruitEnergy);
        gameObjects().removeGameObject(fruit);
        new ScheduledTask(avatar,
                Const.GamaCycle,
                false,
                ()->{gameObjects().addGameObject(fruit);});

    }



    /**
     * Updates the min and max x values
     * make changes to the game objects
     * remove all objects that are not in the range of min max x values
     * add all objects that are in the range of min max x values
     */
    void updateMinMax() {
        float avatarCurX = avatar.getCenter().x();
        float avatarPrevX = avatarLocation.x();
        float diff = avatarCurX - avatarPrevX;
        if(diff > Buffer) {
            int tampMax = (int)(diff + maxX);
            createObjects(maxX, tampMax);
            removeObjectsFromLeft(minX + diff);
            maxX = tampMax;
            minX = minX + (int)diff;
            avatarLocation = avatar.getCenter();
        } else if(diff < -Buffer){
            int tampMin = (int)(diff + minX);
            createObjects(tampMin, minX);
            removeObjectsFromRight(maxX + diff);
            minX = tampMin;
            maxX = maxX + (int)diff;
            avatarLocation = avatar.getCenter();

        }

    }

    /**
     * Updates the game
     * @param deltaTime the time since the last update
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        updateMinMax();
    }



    private void removal(GameObject obj, int layer) {
        gameObjects().removeGameObject(obj,layer);
    }

    private void add(GameObject obj, int layer) {
        gameObjects().addGameObject(obj,layer);
    }

    /**
     * The main function
     * @param args the arguments
     */

    public static void main(String[] args) {
        GameManager gameManager = new PepseGameManager();
        gameManager.run();

    }
}









