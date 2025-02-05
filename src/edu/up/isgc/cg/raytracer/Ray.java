package edu.up.isgc.cg.raytracer;


/**
 * Ray class, creates ray objects.
 * @author Jafet Rodriguez
 */
public class Ray {
    /**Stores the origin of the ray**/
    private Vector3D origin;
    /**Stores the direction of the ray**/
    private Vector3D direction;

    public Ray(Vector3D origin, Vector3D direction) {
        setOrigin(origin);
        setDirection(direction);
    }

    public Vector3D getOrigin() {
        return origin;
    }

    public void setOrigin(Vector3D origin) {
        this.origin = origin;
    }

    public Vector3D getDirection() {
        return Vector3D.normalize(direction);
    }

    public void setDirection(Vector3D direction) {
        this.direction = direction;
    }
}
