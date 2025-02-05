package edu.up.isgc.cg.raytracer.lights;

import edu.up.isgc.cg.raytracer.Intersection;
import edu.up.isgc.cg.raytracer.Ray;
import edu.up.isgc.cg.raytracer.Vector3D;
import edu.up.isgc.cg.raytracer.objects.Object3D;

import java.awt.*;
/**
 * Light class, parent class for light creation.
 * @author Jafet Rodriguez
 */

public abstract class Light extends Object3D {
    private double intensity;

    public Light(Vector3D position, Color color, double intensity) {
        super(position, color);
        setIntensity(intensity);
    }

    public double getIntensity() {
        return intensity;
    }

    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }

    public abstract double getNDotL(Intersection intersection);

    /**
     * getIntersection method, since we do not wish for lights to be intersectable, we return a negative value on the distance so that it may be ignored
     * @author Jafet Rodriguez
     */
    @Override
    public Intersection getIntersection(Ray ray) {
        return new Intersection(Vector3D.ZERO(), -1, Vector3D.ZERO(), null);
    }
}
