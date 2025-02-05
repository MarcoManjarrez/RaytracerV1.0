package edu.up.isgc.cg.raytracer;

/**
 * Vector3D class, creates Vector3D objects.
 * @author Jafet Rodriguez
 */
public class Vector3D {
    /**Zero Vector for operations that may require it**/
    private static final Vector3D ZERO = new Vector3D(0.0, 0.0, 0.0);
    /**x, y and z values for the 3D vector**/
    private double x, y, z;

    public Vector3D(double x, double y, double z){
        setX(x);
        setY(y);
        setZ(z);
    }
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public Vector3D clone(){
        return new Vector3D(getX(), getY(), getZ());
    }

    public static Vector3D ZERO(){
        return ZERO.clone();
    }

    @Override
    public String toString(){
        return "Vector3D{" +
                "x=" + getX() +
                ", y=" + getY() +
                ", z=" + getZ() +
                "}";
    }

    /**
     * dotProduct Method, calculates the dot product between 2 3D vectors.
     * @param vectorA 3D vector 1
     * @param vectorB 3D vector 2
     * @author Jafet Rodriguez
     */
    public static double dotProduct(Vector3D vectorA, Vector3D vectorB){
        return (vectorA.getX() * vectorB.getX()) + (vectorA.getY() * vectorB.getY()) + (vectorA.getZ() * vectorB.getZ());
    }

    /**
     * crossProduct Method, calculates the cross product between 2 3D vectors.
     * @param vectorA 3D vector 1
     * @param vectorB 3D vector 2
     * @author Jafet Rodriguez
     */
    public static Vector3D crossProduct(Vector3D vectorA, Vector3D vectorB){
        return new Vector3D((vectorA.getY() * vectorB.getZ()) - (vectorA.getZ() * vectorB.getY()),
                (vectorA.getZ() * vectorB.getX()) - (vectorA.getX() * vectorB.getZ()),
                (vectorA.getX() * vectorB.getY()) - (vectorA.getY() * vectorB.getX()));
    }

    /**
     * magnitude Method, calculates the magnitude of a vector.
     * @param vectorA 3D vector to get the magnitude of
     * @author Jafet Rodriguez
     */
    public static double magnitude (Vector3D vectorA){
        return Math.sqrt(dotProduct(vectorA, vectorA));
    }

    /**
     * add Method, adds two 3D vectors.
     * @param vectorA 3D vector 1
     * @param vectorB 3D vector 2
     * @author Jafet Rodriguez
     */
    public static Vector3D add(Vector3D vectorA, Vector3D vectorB){
        return new Vector3D(vectorA.getX() + vectorB.getX(), vectorA.getY() + vectorB.getY(), vectorA.getZ() + vectorB.getZ());
    }

    /**
     * substract Method, substracts two 3D vectors.
     * @param vectorA 3D vector 1
     * @param vectorB 3D vector 2
     * @author Jafet Rodriguez
     */
    public static Vector3D substract(Vector3D vectorA, Vector3D vectorB){
        return new Vector3D(vectorA.getX() - vectorB.getX(), vectorA.getY() - vectorB.getY(), vectorA.getZ() - vectorB.getZ());
    }

    /**
     * normalize Method, normalizes a 3D vector.
     * @param vectorA 3D vector to normalize
     * @author Jafet Rodriguez
     */
    public static Vector3D normalize(Vector3D vectorA){
        double mag = Vector3D.magnitude(vectorA);
        return new Vector3D(vectorA.getX() / mag, vectorA.getY() / mag, vectorA.getZ() / mag);
    }

    /**
     * scalarMultiplication method, multiplies a vector by a scalar value.
     * @param vectorA 3D vector
     * @param scalar scalar value to multiply with
     * @author Jafet Rodriguez
     */
    public static Vector3D scalarMultiplication(Vector3D vectorA, double scalar){
        return new Vector3D(vectorA.getX() * scalar, vectorA.getY() * scalar, vectorA.getZ() * scalar);
    }
}
