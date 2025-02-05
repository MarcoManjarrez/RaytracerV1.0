package edu.up.isgc.cg.raytracer;

import edu.up.isgc.cg.raytracer.lights.Light;
import edu.up.isgc.cg.raytracer.objects.Object3D;
import edu.up.isgc.cg.raytracer.objects.Camera;

import java.util.ArrayList;
import java.util.List;

/**
 * Scene class, creates scene objects.
 * @author Jafet Rodriguez
 */
public class Scene {
    /**Stores the camera inside the scene**/
    private Camera camera;
    /**Stores the objects inside the scene**/
    private List<Object3D> objects;
    /**Stores the lights inside the scene**/
    private List<Light> lights;

    public Scene() {
        setObjects(new ArrayList<>());
        setLights(new ArrayList<>());
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void addObject(Object3D object){
        getObjects().add(object);
    }

    public List<Object3D> getObjects() {
        if(objects == null){
            objects = new ArrayList<>();
        }
        return objects;
    }

    public void setObjects(List<Object3D> objects) {
        this.objects = objects;
    }

    public List<Light> getLights() {
        if(lights == null){
            lights = new ArrayList<>();
        }
        return lights;
    }

    public void setLights(List<Light> lights) {
        this.lights = lights;
    }

    public void addLight(Light light){
        getLights().add(light);
    }
}
