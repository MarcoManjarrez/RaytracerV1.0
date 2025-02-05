package edu.up.isgc.cg.raytracer.lights;

import edu.up.isgc.cg.raytracer.Intersection;
import edu.up.isgc.cg.raytracer.Vector3D;

import java.awt.*;

/**
 * Omnidirectional class, creates point lights for the scene.
 * @author Marco Antonio Manjarrez
 */
public class OmnidirectionalLight extends Light {
    private Vector3D position;

    public OmnidirectionalLight(Vector3D position, Color color, double intensity) {
        super(position, color, intensity);
    }

    /**
     * getNDotL method, returns a value to indicate if an object's intersection was reached by the point light.
     * @param intersection intersection between a ray cast by the light and an object
     * @return double value indicating how illuminated an object will be
     * @author Marco Antonio Manjarrez
     */
    @Override
    public double getNDotL(Intersection intersection) {
        return Math.max(Vector3D.dotProduct(intersection.getNormal(), Vector3D.normalize(Vector3D.substract(getPosition(), intersection.getPosition()))), 0.0);
    }

}
