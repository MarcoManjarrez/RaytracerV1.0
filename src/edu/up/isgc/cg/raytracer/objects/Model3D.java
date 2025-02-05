package edu.up.isgc.cg.raytracer.objects;

import edu.up.isgc.cg.raytracer.Intersection;
import edu.up.isgc.cg.raytracer.Ray;
import edu.up.isgc.cg.raytracer.Vector3D;
import edu.up.isgc.cg.raytracer.tools.Barycentric;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Model3D class, creates 3D objects after models set in obj files.
 * @author Jafet Rodriguez
 */
public class Model3D extends Object3D{
    /**Stores the triangles used to make the model's mesh**/
    private List<Triangle> triangles;
    /**Stores the angles for each triangle on all 3 axis**/
    private int angleX, angleY, angleZ;
    public Model3D(Vector3D position, Triangle[] triangles, Color color) {
        super(position, color);
        setTriangles(triangles);
    }
    public List<Triangle> getTriangles() {
        return triangles;
    }

    public void setTriangles(Triangle[] triangles) {
        Vector3D position = getPosition();
        Set<Vector3D> uniqueVertices = new HashSet<>();
        for(Triangle triangle : triangles){
            uniqueVertices.addAll(Arrays.asList(triangle.getVertices()));
        }

        for(Vector3D vertex : uniqueVertices){
            vertex.setX(vertex.getX() + position.getX());
            vertex.setY(vertex.getY() + position.getY());
            vertex.setZ(vertex.getZ() + position.getZ());
        }
        this.triangles = Arrays.asList(triangles);
    }

    /**
     * getIntersection method, returns the place where the mesh was intersected.
     * @param ray  ray that intersected with the model
     * @return Intersection variable on where the model was intersected
     * @author Jafet Rodriguez
     */
    @Override
    public Intersection getIntersection(Ray ray) {
        double distance = -1;
        Vector3D position = Vector3D.ZERO();
        Vector3D normal = Vector3D.ZERO();

        for(Triangle triangle : getTriangles()){
            Intersection intersection = triangle.getIntersection(ray);
            double intersectionDistance = intersection.getDistance();
            if(intersectionDistance > 0 &&
                    (intersectionDistance < distance || distance < 0)){
                distance = intersectionDistance;
                position = Vector3D.add(ray.getOrigin(), Vector3D.scalarMultiplication(ray.getDirection(), distance));
                normal = Vector3D.ZERO();
                double[] uVw = Barycentric.CalculateBarycentricCoordinates(position, triangle);
                Vector3D[] normals = triangle.getNormals();
                for(int i = 0; i < uVw.length; i++){
                    normal = Vector3D.add(normal, Vector3D.scalarMultiplication(normals[i], uVw[i]));
                }
            }
        }

        if(distance == -1){
            return null;
        }

        return new Intersection(position, distance, normal, this);
    }

    public int getAngleX() {
        return angleX;
    }

    public void setAngleX(int angleX) {
        this.angleX = angleX;
    }

    public int getAngleY() {
        return angleY;
    }

    public void setAngleY(int angleY) {
        this.angleY = angleY;
    }

    public int getAngleZ() {
        return angleZ;
    }

    public void setAngleZ(int angleZ) {
        this.angleZ = angleZ;
    }
}
