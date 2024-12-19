package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import bricker.main.BrickerGameManager;
import bricker.ConstsBricker.ConstsBricker;

import java.awt.*;

/**
 * UserGraphic class extends the GameObject class.
 * It represents a user graphic in the game which displays the number of lives the user has left.
 * The user graphic is displayed as a row of hearts, where each heart represents a life.
 * The user graphic is updated when the user loses a life or gains a life.
 * The user graphic also displays a counter for the number of lives left.
 * @author tomer gottman, michael messika
 */
public class UserGraphic extends GameObject {
    //The space between the hearts
    static final int SPACE_ = 10;
    //The game manager instance.
    private final BrickerGameManager brickerGameManager;
    //The number of lives
    private int lives = 0;
    //The array of hearts
    private final GameObject[] hurtArr;
    //The heart image
    private final Renderable hurtImage;
    //The counter for the number of lives
    private TextRenderable counterLives;
    //The location of the first heart
    private Vector2 loc = ConstsBricker.LOC_FIRST_HURT;

    /**
     * Construct a new UserGraphic instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the UserGraphic will not be rendered.
     * @param brickerGameManager The game manager instance.
     * @param numLives The initial number of lives.
     */
    public UserGraphic(Vector2 topLeftCorner,
                       Vector2 dimensions,
                       Renderable renderable,
                       BrickerGameManager brickerGameManager,
                       int numLives) {
        super(topLeftCorner, dimensions, renderable);
        this.brickerGameManager = brickerGameManager;
        this.hurtArr = new GameObject[ConstsBricker.MAX_LIVES];
        this.hurtImage = renderable;
        this.lives = numLives;
        for (int i = 0; i < ConstsBricker.MAX_LIVES; i++) {
            hurtArr[i] = null;
        }
    }

    /**
     * Add a heart to the user graphic.
     * If the number of lives is less than the maximum,
     * a new heart is added to the user graphic.
     */
    public void addHurt() {
        if(lives >= hurtArr.length){
            return;
        }
        GameObject add_hurt = new GameObject(loc, ConstsBricker.DIM_HURT,hurtImage);
        hurtArr[lives] = add_hurt;
        brickerGameManager.addGameObj(add_hurt, Layer.UI);
        loc = loc.add(new Vector2(ConstsBricker.DIM_HURT.x() + SPACE_,0));
        lives++;
        setCounterLives();
    }

    /**
     * Remove a heart from the user graphic.
     * If there are any hearts left, one is removed from the user graphic.
     */
    public void remove_hurt(){
        brickerGameManager.removeGameObj(hurtArr[lives - 1], Layer.UI);
        hurtArr[lives -1 ] = null;
        lives--;
        loc = loc.add(new Vector2(- ConstsBricker.DIM_HURT.x(),0));
        setCounterLives();
    }

    /**
     * Set the counter for the number of lives.
     * The counter is displayed in the user graphic. The color of the
     * counter changes based on the number of lives.
     */
    public void setCounterLives() {
        if(counterLives == null) {
            counterLives = new TextRenderable(Integer.toString(lives));
            GameObject counter = new GameObject(ConstsBricker.LOC_COUNTER,
                    ConstsBricker.DIM_COUNTER,counterLives);
            brickerGameManager.addGameObj(counter,Layer.UI);
        }
        switch (lives){
            case 1:
                counterLives.setColor(Color.RED);
                break;
            case 2:
                counterLives.setColor(Color.YELLOW);
                break;
            default:
                counterLives.setColor(Color.GREEN);
        }
        counterLives.setString(Integer.toString(lives));
    }
}