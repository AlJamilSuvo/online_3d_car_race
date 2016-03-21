package objConverter;

import org.lwjgl.util.vector.Vector3f;

import java.util.List;

/**
 * Created by Suvo on 26-Dec-15.
 */
public class CollideData
{
    List<CollideFace> faces;
    List<Vector3f> vertices;

    public CollideData(List<CollideFace> faces,List<Vector3f> vertices) {
        this.faces = faces;
        this.vertices=vertices;
    }

    public List<CollideFace> getFaces() {
        return faces;
    }

    public List<Vector3f> getVertices() {
        return vertices;
    }
}
