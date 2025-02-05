package edu.up.isgc.cg.raytracer;

import edu.up.isgc.cg.raytracer.objects.Object3D;
/**
 * Intersection class, creates intersection objects.
 * @author Jafet Rodriguez
 */

public class Intersection {
    /**Stores the distance between the caster and the intersection**/
    private double distance;
    /**Stores the position of the intersection on the 3D space**/
    private Vector3D position;
    /**Stores the normal of the intersection's coordinate**/
    private Vector3D normal;
    /**Stores the object intersected with**/
    private Object3D object;

    public Intersection(Vector3D position, double distance, Vector3D normal, Object3D object) {
        setPosition(position);
        setDistance(distance);
        setNormal(normal);
        setObject(object);
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Vector3D getPosition() {
        return position;
    }

    public void setPosition(Vector3D position) {
        this.position = position;
    }

    public Vector3D getNormal() {
        return normal;
    }

    public void setNormal(Vector3D normal) {
        this.normal = normal;
    }

    public Object3D getObject() {
        return object;
    }

    public void setObject(Object3D object) {
        this.object = object;
    }

}
