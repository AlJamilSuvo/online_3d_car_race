package particles;


import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import randerEngine.DisplayManager;

public class Particle {

    private Vector3f position;
    private Vector3f velocity;
    private float gravityEffect;
    private float lifeLenght;
    private float rotation;
    private float scale;

    private static float GRAVITY=-50;

    private float escapeTime;

    public Particle(Vector3f position, Vector3f velocity, float gravityEffect, float lifeLenght, float rotation, float scale) {
        this.position = position;
        this.velocity = velocity;
        this.gravityEffect = gravityEffect;
        this.lifeLenght = lifeLenght;
        this.rotation = rotation;
        this.scale = scale;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public float getScale() {
        return scale;
    }

    public float getRotation() {
        return rotation;
    }

    protected boolean update(){
        velocity.y+=GRAVITY*gravityEffect* DisplayManager.getFrameTimeSeconds();
        Vector3f change=new Vector3f(velocity);
        Vector3f.add(change,position,position);
        escapeTime+=DisplayManager.getFrameTimeSeconds();
        return escapeTime<lifeLenght;
    }
}
