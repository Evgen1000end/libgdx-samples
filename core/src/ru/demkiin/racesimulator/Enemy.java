package ru.demkiin.racesimulator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Enemy {


    private World world;
    public float centrePosX;
    public float centrePosY;
    public float width;
    public float height;
    public Rectangle bounds;

   // private float width = 0.5f;
    private float depth = 0.3f;

    private float rotation = 0.0f;
    private float rotationModifier;

    private float speed;
    private Vector2 velocity = new Vector2(0.0f, 0.0f);
    public Vector2 centrePos = new Vector2(0.0f, 0.0f);
    private Vector2 hitboxFrontRight = new Vector2(0.0f, 0.0f);
    private Vector2 hitboxBackRight = new Vector2(0.0f, 0.0f);
    private Vector2 hitboxBackLeft = new Vector2(0.0f, 0.0f);
    private Vector2 hitboxFrontLeft = new Vector2(0.0f, 0.0f);

    public Enemy(World world,  float centrePosX, float centrePosY, float width, float height, float speed) {

        this.speed = speed;

        this.centrePosX = centrePosX;
        this.centrePosY = centrePosY;
        this.width = width;
        this.height = height;
        bounds = new Rectangle(centrePosX - width/2, centrePosY - height/2, width, height);

        this.world = world;

        this.centrePos.x = centrePosX;
        this.centrePos.y = centrePosY;


      //  Gdx.app.log("Main","Враг (начальное положение):"+centrePosX+" "+centrePosY);
    }


    public void moveForward(float delta) {
        velocity.set(-(float)(Math.sin(Math.toRadians(rotation)) * speed), (float)(Math.cos(Math.toRadians(rotation)) * speed));
        rotationModifier = 0;
        tryMove(delta);
    }

    private void updateBounds() {
        float cosTheta = (float)(Math.cos(Math.toRadians(rotation)));
        float sinTheta = (float)(Math.sin(Math.toRadians(rotation)));
        hitboxFrontRight.x = (float) (centrePos.x + (width*0.3 * cosTheta) - (depth*0.3 * sinTheta));
        hitboxFrontRight.y = (float) (centrePos.y + (width*0.3 * sinTheta) + (depth*0.3 * cosTheta));
        hitboxBackRight.x = (float) (centrePos.x + (width*0.3 * cosTheta) - (-depth*0.3 * sinTheta));
        hitboxBackRight.y = (float) (centrePos.y + (width*0.3 * sinTheta) + (-depth*0.3 * cosTheta));
        hitboxBackLeft.x = (float) (centrePos.x + (-width*0.3 * cosTheta) - (-depth*0.3 * sinTheta));
        hitboxBackLeft.y = (float) (centrePos.y + (-width*0.3 * sinTheta) + (-depth*0.3 * cosTheta));
        hitboxFrontLeft.x = (float) (centrePos.x + (-width*0.3 * cosTheta) - (depth*0.3 * sinTheta));
        hitboxFrontLeft.y = (float) (centrePos.y + (-width*0.3 * sinTheta) + (depth*0.3 * cosTheta));
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    private void tryMove(float delta) {
        Vector2 centrePosBackup = new Vector2(centrePos);
        Vector2 velocityBackup = new Vector2(velocity);
        float rotationBackup = rotation;
        // apply movement
        centrePos.add(velocity.x * delta, velocity.y * delta);
        setRotation(rotation + rotationModifier);
        updateBounds();

        if (world.collision()) {
            // ...undo move
            centrePos = centrePosBackup;
            velocity = velocityBackup;
            setRotation(rotationBackup);
            updateBounds();
        }

      //  Gdx.app.log("Main","Враг (двинулся):"+centrePos.x+" "+centrePos.y);
    }
}
