package edu.up.isgc.cg.raytracer.objects;

import edu.up.isgc.cg.raytracer.Intersection;
import edu.up.isgc.cg.raytracer.Ray;
import edu.up.isgc.cg.raytracer.Vector3D;

import java.awt.*;

/**
 * Camera class, creates a camera for visualization of renders.
 * @author Jafet Rodriguez
 */
public class Camera extends Object3D {
    //FOV[0] = Horizontal | FOV[1] = Vertical
    /** Stores the field of view angles **/
    private double[] fieldOfView = new double[2];
    /** Stores default value of the camera's z position **/
    private double defaultZ = 15.0;
    /** Stores the image's resolution in pixels **/
    private int[] resolution = new int[2];
    /** Stores the near and far planes in double values to limit how far and near the camera can see **/
    private double[] nearFarPlanes = new double[2];

    public Camera(Vector3D position, double fovH, double fovV,
                  int width, int height, double nearPlane, double farPlane) {
        super(position, Color.BLACK);
        setFOV(fovH, fovV);
        setResolution(width, height);
        setNearFarPlanes(new double[]{nearPlane, farPlane});
    }

    public double[] getFieldOfView() {
        return fieldOfView;
    }

    private void setFieldOfView(double[] fieldOfView) {
        this.fieldOfView = fieldOfView;
    }

    public double getFOVHorizontal() {
        return fieldOfView[0];
    }

    public double getFOVVertical() {
        return fieldOfView[1];
    }

    public void setFOVHorizontal(double fovH) {
        fieldOfView[0] = fovH;
    }

    public void setFOVVertical(double fovV) {
        fieldOfView[1] = fovV;
    }

    public void setFOV(double fovH, double fovV) {
        setFOVHorizontal(fovH);
        setFOVVertical(fovV);
    }

    public double getDefaultZ() {
        return defaultZ;
    }

    public void setDefaultZ(double defaultZ) {
        this.defaultZ = defaultZ;
    }

    public int[] getResolution() {
        return resolution;
    }

    public void setResolutionWidth(int width) {
        resolution[0] = width;
    }

    public void setResolutionHeight(int height) {
        resolution[1] = height;
    }

    public void setResolution(int width, int height) {
        setResolutionWidth(width);
        setResolutionHeight(height);
    }

    public int getResolutionWidth() {
        return resolution[0];
    }

    public int getResolutionHeight() {
        return resolution[1];
    }

    private void setResolution(int[] resolution) {
        this.resolution = resolution;
    }

    public double[] getNearFarPlanes() {
        return nearFarPlanes;
    }

    private void setNearFarPlanes(double[] nearFarPlanes) {
        this.nearFarPlanes = nearFarPlanes;
    }


    /**
     * calculatePositions to ray method, returns an array of 3D vectors from which rays will be cast onto the scene.
     * @return Vector3D[][] of positions to cast rays from
     * @author Jafet Rodriguez
     */
    public Vector3D[][] calculatePositionsToRay() {
        double angleMaxX = getFOVHorizontal() / 2.0;
        double radiusMaxX = getDefaultZ() / Math.cos(Math.toRadians(angleMaxX));

        double maxX = Math.sin(Math.toRadians(angleMaxX)) * radiusMaxX;
        double minX = -maxX;

        double angleMaxY = getFOVVertical() / 2.0;
        double radiusMaxY = getDefaultZ() / Math.cos(Math.toRadians(angleMaxY));

        double maxY = Math.sin(Math.toRadians(angleMaxY)) * radiusMaxY;
        double minY = -maxY;

        Vector3D[][] positions = new Vector3D[getResolutionWidth()][getResolutionHeight()];
        double posZ = defaultZ;

        double stepX = (maxX - minX) / getResolutionWidth();
        double stepY = (maxY - minY) / getResolutionHeight();
        for (int x = 0; x < positions.length; x++) {
            for (int y = 0; y < positions[x].length; y++) {
                double posX = minX + (stepX * x);
                double posY = maxY - (stepY * y);
                positions[x][y] = new Vector3D(posX, posY, posZ);
            }
        }
        return positions;
    }

    /**
     * getIntersection method, returns the camera was intersected. Since we do not wish for the camera to be visible, the distance is given a negative value so that the intersection may be ignored
     * @param ray  ray that intersected with the model
     * @return Intersection variable on where the model was intersected
     * @author Jafet Rodriguez
     */
    @Override
    public Intersection getIntersection(Ray ray) {
        return new Intersection(Vector3D.ZERO(), -1, Vector3D.ZERO(), null);
    }
}
