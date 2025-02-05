package edu.up.isgc.cg.raytracer.objects;

import edu.up.isgc.cg.raytracer.Intersection;
import edu.up.isgc.cg.raytracer.Ray;
import edu.up.isgc.cg.raytracer.Vector3D;

import java.awt.*;

/**
 * Object3D class, creates  3D objects.
 * @author Jafet Rodriguez
 * @author Co-authoring by Marco Antonio Manjarrez
 */
public abstract class Object3D implements IIntersectable{
    /**Stores the object's color**/
    private Color color;
    /**Stores the object's position on a 3D plane**/
    private Vector3D position;
    /**Stores the object's reflection factor**/
    private double reflectionFactor;
    /**Stores the object's refraction factor**/
    private double refractionFactor;

    public Object3D(Vector3D position, Color color) {
        setPosition(position);
        setColor(color);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Vector3D getPosition() {
        return position;
    }

    public void setPosition(Vector3D position) {
        this.position = position;
    }

    public double getReflectionFactor() {
        return reflectionFactor;
    }

    public void setReflectionFactor(double reflectionFactor) {
        this.reflectionFactor = reflectionFactor;
    }

    public boolean isReflective(){
        return this.reflectionFactor > 0;
    }
    public boolean isRefractive(){
        return this.refractionFactor > 0;
    }
    public double getRefractionFactor() {
        return refractionFactor;
    }

    public void setRefractionFactor(double refractionFactor) {
        this.refractionFactor = refractionFactor;
    }
}
