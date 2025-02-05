package edu.up.isgc.cg.raytracer.lights;

import edu.up.isgc.cg.raytracer.Intersection;
import edu.up.isgc.cg.raytracer.Vector3D;

import java.awt.*;
/**
 * Spotlight class, creates spotlight objects for the scene.
 * @author Marco Antonio Manjarrez
 */
public class SpotLight extends Light {
    /** Stores the position of the spotlight **/
    private Vector3D position;
    /** Stores the direction of the spotlight **/
    private Vector3D direction;
    /** Stores the inner cutoff angle of the spotlight **/
    private double innerCutoffAngle;
    /** Stores the outer cutoff angle of the spotlight **/
    private double outerCutoffAngle;
    /** Stores the attenuation factor of the spotlight **/
    private double attenuation;

    public SpotLight(Vector3D position, Vector3D direction, Color color, double intensity, double innerCutoffAngle, double outerCutoffAngle, double attenuation) {
        super(position, color, intensity);
        setDirection(direction);
        setInnerCutoffAngle(Math.cos(Math.toRadians(innerCutoffAngle)));
        setOuterCutoffAngle(Math.cos(Math.toRadians(outerCutoffAngle)));
        setAttenuation(attenuation);
    }

    /**
     * getNDotL method, returns a value to indicate if an object's intersection is inside of the spotlight's range.
     * @param intersection intersection between a ray cast by the light and an object
     * @return double value indicating how illuminated an object will be
     * @author Marco Antonio Manjarrez
     */
    @Override
    public double getNDotL(Intersection intersection) {
        Vector3D lightDir = Vector3D.normalize(Vector3D.substract(getPosition(), intersection.getPosition()));
        double angleCos = Vector3D.dotProduct(lightDir, Vector3D.normalize(getDirection()));

        if (angleCos > getOuterCutoffAngle()) {
            double distance = Vector3D.magnitude(Vector3D.substract(getPosition(), intersection.getPosition()));
            double attenuationFactor = 1.0 / (getAttenuation() * distance + 1.0);
            if(angleCos > getInnerCutoffAngle()){
                return Math.max(Vector3D.dotProduct(intersection.getNormal(), lightDir) * attenuationFactor, 0.0);
            } else {
                double lightFalloff = (angleCos - getOuterCutoffAngle())/ (getInnerCutoffAngle() - getOuterCutoffAngle());
                return Math.max(Vector3D.dotProduct(intersection.getNormal(), lightDir) * attenuationFactor * lightFalloff, 0.0);
            }
        }
        return 0.0;
    }

    public Vector3D getDirection() {
        return direction;
    }

    public void setDirection(Vector3D direction) {
        this.direction = direction;
    }


    public double getInnerCutoffAngle() {
        return innerCutoffAngle;
    }

    public void setInnerCutoffAngle(double cutoffAngle) {
        this.innerCutoffAngle = cutoffAngle;
    }

    public double getAttenuation() {
        return attenuation;
    }

    public void setAttenuation(double attenuation) {
        this.attenuation = attenuation;
    }

    public double getOuterCutoffAngle() {
        return outerCutoffAngle;
    }

    public void setOuterCutoffAngle(double outerCutoffAngle) {
        this.outerCutoffAngle = outerCutoffAngle;
    }
}
