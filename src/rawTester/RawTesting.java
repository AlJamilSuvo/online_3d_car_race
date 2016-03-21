package rawTester;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;
/**
 * Created by Suvo on 28-Dec-15.
 */
public class RawTesting {
    RawEntity rawEntity;
    RawCamera rawCamera;


    public RawTesting() {
        init();
        render();
    }

    public void init(){
        try {
            Display.setDisplayMode(new DisplayMode(640, 480));
            Display.setTitle("Three Dee Demo");
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            Display.destroy();
            System.exit(1);
        }

        glLoadIdentity();
        gluPerspective((float) 30, 640f / 480f, 0.001f, 100);
        glMatrixMode(GL_MODELVIEW);


        glEnable(GL_DEPTH_TEST);

    }

    public void render(){
        while (!Display.isCloseRequested()){
            glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
            glBegin(GL_LINE);
                glVertex3f(-.75f,-.75f,-1f);
                glVertex3f(-.75f,-.75f,-10f);
            glEnd();
            Display.sync(120);
            Display.update();
        }
        Display.destroy();


    }

    public static void main(String[] args) {
        new RawTesting();
    }

}
