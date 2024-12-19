package pepse.world;
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;
import danogl.util.Vector2;
import pepse.util.Const;
import pepse.util.MovingState;
import pepse.util.Observer;
import java.awt.event.KeyEvent;

/**
 * Class for creating the avatar object
 * the avatar is character that moves in the game
 * The avatar object is a rectangle that changes its color
 * when the avatar jumps he notifies the observer
 */
public class Avatar extends GameObject {


    private AnimationRenderable idleAnimation;
    private AnimationRenderable runAnimation;
    private AnimationRenderable jumpAnimation;
    private static final String PATH_IDLE_0 = "assets/idle_0.png";
    private float energy = 100;
    private static final float VELOCITY_X = 400;
    private static final float VELOCITY_Y = -600;
    private static final float GRAVITY = 600;

    private final UserInputListener userInputListener;
    private final float runEnergyCost = 0.5f;
    private final float jumpEnergyCost = 10.f;
    private final float idleEnergyCost = 1.0f;
    private MovingState movingState = MovingState.IDLE;
    private final Observer observer;


    /**
     * Creates the avatar object
     * The avatar object is a rectangle that changes its color
     * when the avatar jumps he notifies the observer
     * @param toLeftCorner the top left corner of the avatar object
     * @param userInputListener the user input listener object
     * @param imageReader the image reader object
     * @param observer the observer object
     */
    public Avatar(Vector2 toLeftCorner,
                  UserInputListener userInputListener,
                  ImageReader imageReader,
                  Observer observer) {
        super(toLeftCorner, Const.AVATAR_DIMENSIONS,
                imageReader.readImage(PATH_IDLE_0,
                        true));
        this.userInputListener = userInputListener;
        setCenter(toLeftCorner);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);
        initAnimation(imageReader);
        updateRender();
        this.observer = observer;
        this.setTag(Const.AvatarTag);

    }




    /**
     * Gets the energy of the avatar object
     * @return the energy of the avatar object
     */
    public float getEnergy(){
        return (float) Math.floor(energy);

    }

    /**
     * Adds energy to the avatar object
     * @param energy the energy to add
     */
    public void addEnergy(float energy){
        this.energy += energy;
       this.energy = Math.max(0, Math.min(Const.MAX_ENERGY, this.energy));
    }




    /**
     * Updates the velocity of the avatar object
     * when the avatar moves the energy is updated
     * when the avatar is idle the energy is updated
     */
    private void updateVelocityY(){
        float velocityX = 0;

        if(userInputListener.isKeyPressed(KeyEvent.VK_LEFT)){
            if(energy >= runEnergyCost){
            velocityX -= VELOCITY_X;
            addEnergy(-runEnergyCost);
            movingState = MovingState.RUNNING;
            renderer().setIsFlippedHorizontally(true);
            }
        }
        if(userInputListener.isKeyPressed(KeyEvent.VK_RIGHT)){
            if(energy >= runEnergyCost){
            velocityX += VELOCITY_X;
            addEnergy(-runEnergyCost);
            movingState = MovingState.RUNNING;
            renderer().setIsFlippedHorizontally(false);
            }
        }
        transform().setVelocityX(velocityX);

        if (userInputListener.isKeyPressed(KeyEvent.VK_SPACE)
                && getVelocity().y() == 0){
            if (energy >= jumpEnergyCost){
            transform().setVelocityY(VELOCITY_Y);
            addEnergy(-jumpEnergyCost);
            movingState = MovingState.JUMPING;
            observer.notify(Const.ON_JUMP);}
        }
        // feature if I stuck in the ground I press Q to get out
        if (userInputListener.isKeyPressed(KeyEvent.VK_Q)){
            this.setCenter(new Vector2(this.getCenter().x(),50));
        }

        if(getVelocity().isZero()){
            addEnergy(idleEnergyCost);
            movingState = MovingState.IDLE;
        }

    }


    /**
     * Updates the renderable object
     * when the avatar is idle the idle animation is set
     * when the avatar is running the run animation is set
     * when the avatar is jumping the jump animation is set
     */
    private void updateRender()
    {
      if(movingState == MovingState.IDLE)
      {
          renderer().setRenderable(idleAnimation);
          return;
      }
      if (movingState == MovingState.RUNNING)
      {
          renderer().setRenderable(runAnimation);
          return;
      }
      if (movingState == MovingState.JUMPING)
      {
          renderer().setRenderable(jumpAnimation);
      }
    }



    /**
     * Initializes the animation objects
     * @param imageReader the image reader object
     */
    private void initAnimation(ImageReader imageReader){
        idleAnimation = new AnimationRenderable(Const.AVATARS_PATH_IDLE,
                imageReader,true,0.5f);
        runAnimation = new AnimationRenderable(Const.AVATARS_PATH_RUN,
                imageReader,true,0.5f);
        jumpAnimation = new AnimationRenderable(Const.AVATARS_PATH_JUMP,
                imageReader,true,0.5f);
    }

    /**
     * Updates the avatar object
     * when the avatar jumps he notifies the observer and energy is updated
     * when the avatar moves the energy is updated
     * when the avatar is idle the energy is updated
     * @param deltaTime the time passed since the last update
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        updateVelocityY();
        updateRender();

    }
}
