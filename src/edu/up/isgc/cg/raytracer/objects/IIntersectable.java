package edu.up.isgc.cg.raytracer.objects;

import edu.up.isgc.cg.raytracer.Intersection;
import edu.up.isgc.cg.raytracer.Ray;

/**
 *  Intersectable interface, to force objects to be intersectable.
 * @author Jafet Rodriguez
 */
public interface IIntersectable {
    public abstract Intersection getIntersection(Ray ray);
}
