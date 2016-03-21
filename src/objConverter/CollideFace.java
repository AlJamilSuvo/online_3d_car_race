package objConverter;

import org.lwjgl.util.vector.Vector3f;

import java.util.List;

/**
 * Created by Suvo on 26-Dec-15.
 */
public class CollideFace {
    List<Vector3f> vertices;
    Vector3f normal;

    public CollideFace(List<Vector3f> vertices, Vector3f normal) {
        this.vertices = vertices;
        this.normal = normal;
    }

    public List<Vector3f> getVertices() {
        return vertices;
    }

    public Vector3f getNormal() {
        return normal;
    }
}
