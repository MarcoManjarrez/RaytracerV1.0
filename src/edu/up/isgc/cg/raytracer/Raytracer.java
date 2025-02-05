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
        scene01.addLight(new DirectionalLight(new Vector3D(-2.0, -1.0, 1.0), Color.WHITE, 0.5));
        scene01.addLight(new SpotLight(new Vector3D(7,5, 1), new Vector3D(0,1,0), Color.WHITE, 2.1, 30, 40, 0.5));
        //scene01.addObject(new Sphere(new Vector3D(3, 2, 0), 0.8, Color.RED));
        //scene01.addObject(new Sphere(new Vector3D(-1, -3, 6), 0.8, Color.BLUE));
        //scene01.addObject(new Sphere(new Vector3D(0.1, 1, 6), 0.5, Color.BLUE));
        //scene01.addObject(OBJReader.getModel3D("Ring.obj", new Vector3D(-3,1,5), Color.gray, 90,180,0, 1));
        //scene01.addObject(OBJReader.getModel3D("Ring.obj", new Vector3D(3,1,5), Color.gray, 0,180,0, 2));
        /*scene01.addObject(new Model3D(new Vector3D(-1, -1, 3),
                new Triangle[]{
                        new Triangle(Vector3D.ZERO(), new Vector3D(1, -1, 0), new Vector3D(1, 0, 0)),
                        new Triangle(Vector3D.ZERO(), new Vector3D(0, -1, 0), new Vector3D(1, -1, 0))},
                Color.GREEN));*/
        /*Object3D plane = OBJReader.getModel3D("plane.obj", new Vector3D(0,-4.5,5), Color.white, 90,180,0,3);
        plane.setReflectionFactor(0.3);*/
        //scene01.addObject(plane);
        /*Object3D plane = OBJReader.getModel3D("plane.obj", new Vector3D(4,-2,6), Color.white, 90,180,0,0.7);
        plane.setReflectionFactor(0.3);
        scene01.addObject(plane);
        Object3D pillar = OBJReader.getModel3D("cubePillar.obj", new Vector3D(-3,-5,3), Color.orange, 0,0,0,1);
        pillar.setRefractionFactor(0.3);
        scene01.addObject(pillar);
        Object3D teapot2 = OBJReader.getModel3D("SmallTeapot.obj", new Vector3D(-3,-5,5.5), Color.CYAN, 1);
        scene01.addObject(teapot2);*/

        scene01.addLight(new OmnidirectionalLight(new Vector3D(6,0,-2), Color.WHITE, 1.0));
        scene01.addLight(new OmnidirectionalLight(new Vector3D(-6,0,-2), Color.WHITE, 1.0));
        //scene01.addLight(new OmnidirectionalLight(new Vector3D(-3,0,2), Color.WHITE, 1.0));
        //scene01.addLight(new OmnidirectionalLight(new Vector3D(3,5,2), Color.WHITE, 1.0));
        //scene01.addLight(new OmnidirectionalLight(new Vector3D(3,0,3), Color.WHITE, 1.1));
        //scene01.addLight(new OmnidirectionalLight(new Vector3D(6,5,2), Color.WHITE, 1.0));
        //scene01.addLight(new OmnidirectionalLight(new Vector3D(8,5,3), Color.WHITE, 1.1));
        //scene01.addLight(new OmnidirectionalLight(new Vector3D(12,0,2), Color.WHITE, 1.1));
        //scene01.addLight(new SpotLight(new Vector3D(9.5,4.8, 4.5), new Vector3D(-1.5,1,0), Color.WHITE, 5.1, 10, 20, 0.5));

        //scene01.addObject(OBJReader.getModel3D("SmallTeapot.obj", new Vector3D(0,-2,3), Color.CYAN,0,0,20));
        //scene01.addObject(OBJReader.getModel3D("SmallTeapot.obj", new Vector3D(1,0,3), Color.CYAN,0,0,-20));
        //scene01.addObject(OBJReader.getModel3D("plane.obj", new Vector3D(0,-5,1), Color.CYAN, 5));
        //scene01.addObject(OBJReader.getModel3D("plane.obj", new Vector3D(0,-3,7), Color.gray, 90,180,0,5));
        Object3D plane = OBJReader.getModel3D("plane.obj", new Vector3D(0,-3,7), Color.gray, 90,180,0,7);
        scene01.addObject(plane);
        Object3D plane2 = OBJReader.getModel3D("plane.obj", new Vector3D(0,-5,1), Color.CYAN, 7);
        plane2.setReflectionFactor(0.4);
        scene01.addObject(plane2);
        //scene01.addObject(OBJReader.getModel3D("plane.obj", new Vector3D(0,-3,-11), Color.WHITE, 90,360,0,5));


        /*Object3D cabinetBody = OBJReader.getModel3D("cabinetBody.obj", new Vector3D(0,-5,5.5), new Color(101,67,33), 0,0,0,0.5);
        scene01.addObject(cabinetBody);
        Object3D cabinetMirror = OBJReader.getModel3D("mirror.obj", new Vector3D(0,-5,5.5), new Color(170,206,165), 0,0,0,0.5);
        cabinetMirror.setReflectionFactor(0.8);
        scene01.addObject(cabinetMirror);

        Object3D cabinetHandles = OBJReader.getModel3D("cabinetHandles.obj", new Vector3D(0,-5,5.5), new Color(187,165,61), 0,0,0,0.5);
        cabinetHandles.setReflectionFactor(0.2);
        scene01.addObject(cabinetHandles);

        Object3D bedMetalFrame = OBJReader.getModel3D("bedMetalFrame.obj", new Vector3D(7,-5,1), new Color(181,184,177), 0,0,0,0.2);
        bedMetalFrame.setReflectionFactor(0.3);
        scene01.addObject(bedMetalFrame);
        Object3D mattress = OBJReader.getModel3D("mattress.obj", new Vector3D(7,-5,5.5), new Color(170,206,165), 0,0,0,0.2);
        scene01.addObject(mattress);
        Object3D pillowAndSheets = OBJReader.getModel3D("pillowAndSheets.obj", new Vector3D(7,-5,5.5), new Color(220,170,217), 0,0,0,0.2);
        scene01.addObject(pillowAndSheets);
        Object3D wheelBases = OBJReader.getModel3D("wheelBases.obj", new Vector3D(7,-5,5.5), new Color(181,184,177), 0,0,0,0.2);
        scene01.addObject(wheelBases);
        Object3D wheels = OBJReader.getModel3D("wheels.obj", new Vector3D(7,-5,5.5), Color.BLACK, 0,0,0,0.2);
        scene01.addObject(wheels);

        Object3D cup = OBJReader.getModel3D("cup.obj", new Vector3D(13.5,-0.8,4.5), new Color(106,13,173), 0,0,0,0.3);
        cup.setRefractionFactor(1.3);
        scene01.addObject(cup);
        Object3D lampBody = OBJReader.getModel3D("lamp.obj", new Vector3D(10.5,-0.8,4.5), new Color(65,65,65), 0,0,0,0.2);
        scene01.addObject(lampBody);
        Object3D lampStick = OBJReader.getModel3D("lampStick.obj", new Vector3D(10.5,-0.8,4.5), new Color(65,65,65), 0,0,0,0.2);
        scene01.addObject(lampStick);
        Object3D table = OBJReader.getModel3D("table.obj", new Vector3D(12,-4.5,4.5), new Color(101,67,33), 0,270,0, 0.3);
        scene01.addObject(table);
        Object3D closetWall = OBJReader.getModel3D("closetWall.obj", new Vector3D(19,-5,1), Color.WHITE, 0,270,0, 0.3);
        scene01.addObject(closetWall);
        Object3D closetDoor = OBJReader.getModel3D("closetDoors.obj", new Vector3D(19,-5,1), new Color(101,67,33), 0,270,0, 0.3);
        scene01.addObject(closetDoor);


        Object3D wallMirror = OBJReader.getModel3D("wallMirrorR.obj", new Vector3D(0,-4,-8.5), new Color(170,206,165), 0,180,0,0.5);
        wallMirror.setReflectionFactor(0.8);
        scene01.addObject(wallMirror);
        Object3D wallMirrorWood = OBJReader.getModel3D("wallMirrorWood.obj", new Vector3D(0,-4,-8.5), new Color(101,67,33), 0,180,0,0.5);
        scene01.addObject(wallMirrorWood);
*/
        /*
        Object3D cup = OBJReader.getModel3D("cup.obj", new Vector3D(1.5,-6,3), new Color(191,148,228), 0,0,0,2);
        scene01.addObject(cup);
        Object3D cube1 = OBJReader.getModel3D("iceCube.obj", new Vector3D(-4.5,-5,2), new Color(173,216,230), 0,0,0,1);
        cube1.setRefractionFactor(2.4);
        scene01.addObject(cube1);
        Object3D cube2 = OBJReader.getModel3D("iceCube.obj", new Vector3D(-7.5,-5,2), new Color(173,216,230), 0,0,0,1);
        cube2.setRefractionFactor(2.4);
        scene01.addObject(cube2);
        Object3D cube3 = OBJReader.getModel3D("iceCube.obj", new Vector3D(-6,-2,2), new Color(173,216,230), 0,45,0,1);
        cube3.setRefractionFactor(2.4);
        scene01.addObject(cube3);
        Object3D melted = OBJReader.getModel3D("meltedCube.obj", new Vector3D(-16,-5,0), new Color(173,216,230), 0,45,0,1);
        melted.setRefractionFactor(2.4);
        scene01.addObject(melted);
        Object3D iceCream = OBJReader.getModel3D("iceCream.obj", new Vector3D(16,-5,3), new Color(252,90,141), 0,90,0,1);
        scene01.addObject(iceCream);
        Object3D iceCreamCone = OBJReader.getModel3D("iceCreamCone.obj", new Vector3D(16,-5,3), new Color(212,187,177), 0,90,0,1);
        scene01.addObject(iceCreamCone);
        Object3D icePop = OBJReader.getModel3D("icePop.obj", new Vector3D(1.5,-2.8,-2), Color.blue, 0,0,45,1);
        icePop.setRefractionFactor(2.4);
        scene01.addObject(icePop);
        Object3D stick = OBJReader.getModel3D("stick.obj", new Vector3D(1.5,-2.8,-2), new Color(101,67,33), 0,0,45,1);
        scene01.addObject(stick);
*/
        Object3D tvBody = OBJReader.getModel3D("tvFrame.obj", new Vector3D(-9,0,-2), Color.BLACK, 0,0,0,0.1);
        scene01.addObject(tvBody);
        Object3D tvScreen = OBJReader.getModel3D("screen.obj", new Vector3D(-9,0,-2), new Color(170,206,165), 0,0,0,0.1);
        tvScreen.setReflectionFactor(100);
        scene01.addObject(tvScreen);
        Object3D mattress = OBJReader.getModel3D("mattress.obj", new Vector3D(5,-10,1), new Color(170,206,165), 0,90,0,0.4);
        scene01.addObject(mattress);
        Object3D pillowAndSheets = OBJReader.getModel3D("pillowAndSheets.obj", new Vector3D(5,-10,1), new Color(220,170,217), 0,90,0,0.4);
        scene01.addObject(pillowAndSheets);
        Object3D table = OBJReader.getModel3D("table.obj", new Vector3D(-11,-5,0), new Color(101,67,33), 0,90,0,0.4);
        scene01.addObject(table);
        Object3D curtain1 = OBJReader.getModel3D("curtain.obj", new Vector3D(-17,-3,5.5), Color.WHITE, 0,180,0,1);
        scene01.addObject(curtain1);
        Object3D curtain2 = OBJReader.getModel3D("curtain.obj", new Vector3D(7,-3,5.5), Color.WHITE, 0,180,0,1);
        scene01.addObject(curtain2);
        Object3D curtain3 = OBJReader.getModel3D("curtain.obj", new Vector3D(31,-3,5.5), Color.WHITE, 0,180,0,1);
        scene01.addObject(curtain3);
        //Object3D curtain4 = OBJReader.getModel3D("curtain.obj", new Vector3D(2,-3,5.5), Color.WHITE, 0,180,0,1);
        //scene01.addObject(curtain4);


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
