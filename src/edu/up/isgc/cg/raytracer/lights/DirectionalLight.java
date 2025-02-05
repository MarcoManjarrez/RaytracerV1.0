package edu.up.isgc.cg.raytracer.lights;

import edu.up.isgc.cg.raytracer.Intersection;
import edu.up.isgc.cg.raytracer.Ray;
import edu.up.isgc.cg.raytracer.Vector3D;

import java.awt.*;
/**
 * DirectionalLight class, creates directional lights on the scene.
 * @author Jafet Rodriguez
 */
public class DirectionalLight extends Light {
    private Vector3D direction;

    public DirectionalLight(Vector3D direction, Color color, double intensity) {
        super(Vector3D.ZERO(), color, intensity);
        setDirection(direction);
    }

    public Vector3D getDirection() {
        return direction;
    }

    public void setDirection(Vector3D direction) {
        this.direction = Vector3D.normalize(direction);
    }

    /**
     * getNDotL method, returns a double value indicating how illuminated an object will be
     * @param intersection intersection between a ray cast by the light and an object
     * @return double value indicating how illuminated an object will be
     * @author Jafet Rodriguez
     */
    @Override
    public double getNDotL(Intersection intersection) {
        return Math.max(Vector3D.dotProduct(intersection.getNormal(), Vector3D.scalarMultiplication(getDirection(), -1.0)), 0.0);
    }

}
