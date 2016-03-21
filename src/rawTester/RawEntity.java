package rawTester;

import objConverter.CollideData;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import toolBoxs.Maths;

/**
 * Created by Suvo on 28-Dec-15.
 */
public class RawEntity {
    CollideData data;
    Vector3f pos;
    float rotX,rotY,rotZ;
    float scale;
    Matrix4f tranformation;

    public RawEntity(CollideData data, Vector3f pos, float rotX, float rotY, float rotZ, float scale) {
        this.data = data;
        this.pos = pos;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
    }

    public Vector3f getPos() {
        return pos;
    }

    public float getRotX() {
        return rotX;
    }

    public float getRotY() {
        return rotY;
    }

    public float getRotZ() {
        return rotZ;
    }

    public float getScale() {
        return scale;
    }

    public Matrix4f getTranformation() {
        tranformation= Maths.createTransformationMatrix(pos,rotX,rotY,rotZ,scale);
        return tranformation;
    }


    public void increasePosition(float dx,float dy,float dz){
        pos.x+=dx;
        pos.y+=dy;
        pos.z+=dz;
    }

    public void increaseRotation(float dx,float dy,float dz){
        rotX+=dx;
        rotY+=dy;
        rotZ+=dz;
    }




}
