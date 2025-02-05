package edu.up.isgc.cg.raytracer;

import edu.up.isgc.cg.raytracer.lights.DirectionalLight;
import edu.up.isgc.cg.raytracer.lights.Light;
import edu.up.isgc.cg.raytracer.lights.OmnidirectionalLight;
import edu.up.isgc.cg.raytracer.lights.SpotLight;
import edu.up.isgc.cg.raytracer.objects.*;
import edu.up.isgc.cg.raytracer.tools.OBJReader;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Main class, in charge of the raytracing execution.
 * @author Jafet Rodriguez
 * @author Co-authoring by Marco Antonio Manjarrez
*/
public class Raytracer {
    static BufferedImage image;

    public static void main(String[] args) {
        System.out.println(new Date());
        Scene scene01 = new Scene();
        scene01.setCamera(new Camera(new Vector3D(0, 0, -9), 80, 45,
                4096, 2160, 0.6, 50.0));
        
        //To add objects on the scene, use scene01.addObject(Object);
        //To add a light, use scene01.addLight(lightType);
        
        raytrace(scene01);
        File outputImage = new File("image.png");
        try {
            ImageIO.write(image, "png", outputImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(new Date());
    }

    /**
     * Raytrace method, in charge of calling for the draw class to make the renders.
     * @param scene scene to be rendered
     * @author Jafet Rodriguez
     * @author Co-authoring by Marco Antonio Manjarrez
     */
    public static void raytrace(Scene scene) {
        ExecutorService service = Executors.newFixedThreadPool(6);
        Camera mainCamera = scene.getCamera();
        double[] nearFarPlanes = mainCamera.getNearFarPlanes();
        image = new BufferedImage(mainCamera.getResolutionWidth(), mainCamera.getResolutionHeight(), BufferedImage.TYPE_INT_RGB);
        List<Object3D> objects = scene.getObjects();
        List<Light> lights = scene.getLights();
        Vector3D[][] posRaytrace = mainCamera.calculatePositionsToRay();
        Vector3D pos = mainCamera.getPosition();
        double cameraZ = pos.getZ();
        double shininess = 13;

        for (int x = 0; x < posRaytrace.length; x++) {
            for (int y = 0; y < posRaytrace[x].length; y++) {
                Runnable runnable = draw(x,y, posRaytrace, mainCamera, objects, cameraZ,nearFarPlanes, lights, shininess);
                service.execute(runnable);
            }
        }
        service.shutdown();
        try{
            if(!service.awaitTermination(7, TimeUnit.HOURS)){
                service.shutdownNow();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if(!service.isTerminated()){
                System.err.print("Cancel non-finished");
            }
        }
        service.shutdownNow();
    }

    /**
     * addColor method, in charge of clamping color values for the render.
     * @param original original color
     * @param otherColor  color to sum to the original
     * @author Jafet Rodriguez
     * @return Color variable
     */
    public static Color addColor(Color original, Color otherColor) {
        float red = (float) Math.clamp((original.getRed() / 255.0) + (otherColor.getRed() / 255.0), 0.0, 1.0);
        float green = (float) Math.clamp((original.getGreen() / 255.0) + (otherColor.getGreen() / 255.0), 0.0, 1.0);
        float blue = (float) Math.clamp((original.getBlue() / 255.0) + (otherColor.getBlue() / 255.0), 0.0, 1.0);
        return new Color(red, green, blue);
    }

    /**
     * Raycast method, in charge of the casting of rays.
     * @author Jafet Rodriguez
     * @author Co-authoring by Marco Antonio Manjarrez
     * @param caster object from which the object is cast, so that it may be ignored or re-cast if necessary
     * @param objects list of objects on the scene
     * @param ray ray that will be cast
     * @param clippingPlanes array of doubles that restricts the ray's travel distance
     * @return Intersection variable containing the closestIntersection
     */
    public static Intersection raycast(Ray ray, List<Object3D> objects, Object3D caster, double[] clippingPlanes) {
        Intersection closestIntersection = null;

        for(int i = 0; i < objects.size(); i++) {
            Object3D currObj = objects.get(i);
            if (caster == null) {
                Intersection intersection = currObj.getIntersection(ray);
                if (intersection != null) {
                    double distance = intersection.getDistance();
                    double intersectionZ = intersection.getPosition().getZ();

                    if (distance >= 0 &&
                            (closestIntersection == null || distance < closestIntersection.getDistance()) &&
                            (clippingPlanes == null || (intersectionZ >= clippingPlanes[0] && intersectionZ <= clippingPlanes[1]))) {
                        closestIntersection = intersection;
                    }
                }
            }else{
                if(!caster.equals(currObj)){
                    Intersection intersection = currObj.getIntersection(ray);
                    if (intersection != null) {
                        double distance = intersection.getDistance();
                        if (distance >= 0 && (closestIntersection == null || distance < closestIntersection.getDistance()) &&
                        (clippingPlanes == null || (distance >= clippingPlanes[0] && distance <= clippingPlanes[1]))){
                            closestIntersection = intersection;
                        }
                    }
                }
            }
        }
        return closestIntersection;
    }

    /**
     * draw method, in charge of returning a runnable for the color calculations.
     * @author Marco Antonio Manjarrez
     * @param objects list of objects on the scene
     * @param cameraZ the camera's position on the Z axis
     * @param lights list of lights on the scene
     * @param mainCamera the camera on the scene
     * @param nearFarPlanes double values indicating how near and far the camera can see
     * @param posRaytrace position from which the raytracing will occur
     * @param shininess how much an object will shine with the blinn-phong calculations
     * @param x x position being operated on the image
     * @param y y position being operated on the image
     * @return Runnable containing a variable and two methods
     */
    public static Runnable draw(int x, int y,Vector3D[][] posRaytrace, Camera mainCamera, List<Object3D> objects, double cameraZ, double[] nearFarPlanes, List<Light> lights, double shininess){
        return () -> {
            Color color = decideColor(x,y, posRaytrace,mainCamera,objects,cameraZ,nearFarPlanes,lights, shininess);
            setRGB(x,y,color);
        };
    }

    /**
     * draw method, in charge of returning a runnable for the color calculations.
     * @author Jafet Rodriguez
     * @author Co-authoring by Marco Antonio Manjarrez
     * @param objects list of objects on the scene
     * @param cameraZ the camera's position on the Z axis
     * @param lights list of lights on the scene
     * @param mainCamera the camera on the scene
     * @param nearFarPlanes double values indicating how near and far the camera can see
     * @param posRaytrace position from which the raytracing will occur
     * @param shininess how much an object will shine with the blinn-phong calculations
     * @param x x position being operated on the image
     * @param y y position being operated on the image
     * @return pixelColor, the color which will be added on a specific pixel in the image
     **/
    public static Color decideColor(int x, int y, Vector3D[][] posRaytrace, Camera mainCamera, List<Object3D> objects, double cameraZ, double[] nearFarPlanes, List<Light> lights, double shininess ){
        Vector3D pos = mainCamera.getPosition();
        double posX = posRaytrace[x][y].getX() + pos.getX();
        double posY = posRaytrace[x][y].getY() + pos.getY();
        double posZ = posRaytrace[x][y].getZ() + pos.getZ();

        double reflectionFactorStopper = 1;
        double refractionFactorStopper = 1;

        Ray ray = new Ray(mainCamera.getPosition(), new Vector3D(posX, posY, posZ));
        Intersection closestIntersection = raycast(ray, objects, null,
                new double[]{cameraZ + nearFarPlanes[0], cameraZ + nearFarPlanes[1]});

        Color pixelColor = Color.BLACK;
        if (closestIntersection!= null){
            int bounceCounter = 0;
            boolean refractive;
            do {
                refractive = true;
                double reflectionFactor = closestIntersection.getObject().getReflectionFactor();
                if(bounceCounter != 0) {
                    //Rayo para reflexion, solo entra a partir de la segunda vuelta
                    Vector3D intersectionPos = closestIntersection.getPosition();
                    Vector3D intersectionNormal = closestIntersection.getNormal();
                    Vector3D incidenceCoordinates = Vector3D.normalize(Vector3D.substract(closestIntersection.getPosition(), ray.getOrigin()));
                    if (closestIntersection.getObject().isRefractive() && bounceCounter == 1) {
                        Vector3D newRayDirection = refractionCalculations(incidenceCoordinates, intersectionNormal, 1, closestIntersection.getObject().getRefractionFactor());
                        if(newRayDirection != null) {
                            ray = new Ray(intersectionPos, newRayDirection);
                            refractive = false;
                        }
                        refractionFactorStopper *= closestIntersection.getObject().getRefractionFactor();
                    }
                    if (closestIntersection.getObject().isReflective() || refractive) {
                        Vector3D newRayDirection = Vector3D.substract(incidenceCoordinates, Vector3D.scalarMultiplication(intersectionNormal, 2 * Vector3D.dotProduct(incidenceCoordinates, intersectionNormal)));
                        ray = new Ray(intersectionPos, newRayDirection);
                        reflectionFactorStopper *= reflectionFactor;
                    }
                        closestIntersection = raycast(ray, objects, closestIntersection.getObject(), new double[]{cameraZ + nearFarPlanes[0], cameraZ + nearFarPlanes[1]});
                }

                if (closestIntersection != null) {
                    Color objColor = closestIntersection.getObject().getColor();

                    for (Light light : lights) {
                        Ray rayForShadow;
                        Intersection intersectionWithLight = null;
                        double nDotL = light.getNDotL(closestIntersection);
                        Color lightColor = light.getColor();
                        double intensity = light.getIntensity() * nDotL;
                        if (light.getClass().equals(OmnidirectionalLight.class)) {
                            rayForShadow = new Ray(closestIntersection.getPosition(), Vector3D.normalize(Vector3D.substract(light.getPosition(), closestIntersection.getPosition())));
                            intersectionWithLight = raycast(rayForShadow, objects, closestIntersection.getObject(), new double[]{0, Vector3D.magnitude(Vector3D.substract(light.getPosition(), closestIntersection.getPosition()))});
                        } else if (light.getClass().equals(DirectionalLight.class)) {
                            rayForShadow = new Ray(closestIntersection.getPosition(), Vector3D.scalarMultiplication(((DirectionalLight) light).getDirection(), -1));
                            intersectionWithLight = raycast(rayForShadow, objects, closestIntersection.getObject(), null);
                        } else if (light.getClass().equals(SpotLight.class)) {
                            SpotLight spotLight = (SpotLight) light;
                            Vector3D lightDir = Vector3D.normalize(Vector3D.substract(spotLight.getPosition(), closestIntersection.getPosition()));
                            double angleCos = Vector3D.dotProduct(lightDir, Vector3D.normalize(spotLight.getDirection()));
                            if (angleCos > spotLight.getOuterCutoffAngle()) {
                                rayForShadow = new Ray(closestIntersection.getPosition(), lightDir);
                                intersectionWithLight = raycast(rayForShadow, objects, closestIntersection.getObject(), new double[]{0, Vector3D.magnitude(Vector3D.substract(light.getPosition(), closestIntersection.getPosition()))});
                            } else {
                                intersectionWithLight = null;
                            }
                        }

                        if (intersectionWithLight == null) {
                            nDotL = light.getNDotL(closestIntersection);
                            lightColor = light.getColor();
                            intensity = light.getIntensity() * nDotL;
                            if (light.getClass().equals(OmnidirectionalLight.class)) {
                                double distance = Vector3D.magnitude(Vector3D.substract(closestIntersection.getPosition(), light.getPosition()));
                                double lightDiffuse = intensity / Math.pow(distance, 2);

                                //Blinn-Phong
                                Vector3D lightSource = Vector3D.substract(light.getPosition(), closestIntersection.getPosition());
                                Vector3D viewerPosition = ray.getDirection();
                                Vector3D viewerAndLightSource = Vector3D.add(lightSource, viewerPosition);
                                Vector3D halfVector = Vector3D.scalarMultiplication(viewerAndLightSource, 1 / Vector3D.magnitude(viewerAndLightSource));

                                double specularValue = Math.pow(Vector3D.dotProduct(closestIntersection.getNormal(), halfVector), shininess);
                                intensity = specularValue + lightDiffuse;
                            }

                            double[] lightColors = new double[]{lightColor.getRed() / 255.0, lightColor.getGreen() / 255.0, lightColor.getBlue() / 255.0};
                            double[] objColors = new double[]{objColor.getRed() / 255.0, objColor.getGreen() / 255.0, objColor.getBlue() / 255.0};

                             if(closestIntersection.getObject().isReflective()){
                                objColors = new double[]{objColor.getRed() * (1- reflectionFactor)  / 255.0, objColor.getGreen() * (1- reflectionFactor)  / 255.0, objColor.getBlue() * (1- reflectionFactor) / 255.0,};
                            }

                            for (int colorIndex = 0; colorIndex < objColors.length; colorIndex++) {
                                objColors[colorIndex] *= intensity * lightColors[colorIndex] * reflectionFactorStopper;
                            }

                            Color diffuse = new Color((float) Math.clamp(objColors[0], 0.0, 1.0),
                                    (float) Math.clamp(objColors[1], 0.0, 1.0),
                                    (float) Math.clamp(objColors[2], 0.0, 1.0));

                            pixelColor = addColor(pixelColor, diffuse);
                        }
                    }
                }
                bounceCounter++;
            }while(bounceCounter < 5 && closestIntersection != null && (reflectionFactorStopper > 0 || refractive));
        }
    return pixelColor;
    }

    /**
     * refractionCalculations method, in charge of applying Snell's law on refractive objects.
     * @author Marco Antonio Manjarrez
     * @param incidence 3D vector coordinate where the ray intersected with the object
     * @param normal normal of the incidence coordinate's intersection
     * @param refractionFactor1 refraction factor assigned by the user, represents the refraction factor of the medium ("Air" between objects)
     * @param refractionFactor2 refraction factor of the object
     * @return Vector3D representing the direction of the new, refracted ray
     **/
    public static Vector3D refractionCalculations(Vector3D incidence, Vector3D normal, double refractionFactor1, double refractionFactor2){
        double newRefractiveIndex = refractionFactor1/refractionFactor2;
        double cosI = -Vector3D.dotProduct(incidence, normal);
        double sinT2 = newRefractiveIndex * newRefractiveIndex * (1.0 - cosI *cosI);

        if(sinT2 > 1.0)
            return null;
        double cosT = Math.sqrt(1.0 - sinT2);

        return Vector3D.add(Vector3D.scalarMultiplication(incidence, newRefractiveIndex), Vector3D.scalarMultiplication(normal, newRefractiveIndex * cosI - cosT));
    }
/**
 * setRGB method, in charge of setting the pixel color on the proper position in the image. Made to make parallelization possible.
 * @author Marco Antonio Manjarrez
 * @param x x position of the image to be operated on
 * @param y y position of the image to be operated on
 * @param pixelColor color to add to the x,y coordinate on the image
 * **/
    public static synchronized void setRGB(int x, int y, Color pixelColor){
        image.setRGB(x,y,pixelColor.getRGB());
    }

}
