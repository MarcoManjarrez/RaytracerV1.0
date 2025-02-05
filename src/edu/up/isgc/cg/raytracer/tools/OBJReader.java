package edu.up.isgc.cg.raytracer.tools;

import edu.up.isgc.cg.raytracer.Vector3D;
import edu.up.isgc.cg.raytracer.objects.Model3D;
import edu.up.isgc.cg.raytracer.objects.Triangle;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * OBJReader class, in charge of returning information from an obj file.
 * @author Jafet Rodriguez
 * @author Co-authoring by Marco Antonio Manjarrez
 */
public abstract class OBJReader {
        /**
        * getModel3D Method, calls the function to get a model from an obj file with an origin and color.
        * @param path path to obj file
         * @param color color to assign to the object
         * @param origin origin coordinates to position the object
        * @author Jafet Rodriguez
         * @author Co-authoring by Marco Antonio Manjarrez
        */
        public static Model3D getModel3D(String path, Vector3D origin, Color color) {
            return getModel3D(path, origin, color, 0,0,0, 1);
        }

    /**
     * getModel3D Method, calls the function to get a model from an obj file with an origin and color, as well as a scale.
     * @param path path to obj file
     * @param color color to assign to the object
     * @param origin origin coordinates to position the object
     * @param scale scale to give to the object in relation to its original size
     * @author Marco Antonio Manjarrez
     */
        public static Model3D getModel3D(String path, Vector3D origin, Color color, double scale) {
            return getModel3D(path, origin, color, 0,0,0, scale);
        }
    /**
     * getModel3D Method, calls the function to get a model from an obj file with an origin and color, as well as a rotation in all axis.
     * @param path path to obj file
     * @param color color to assign to the object
     * @param origin origin coordinates to position the object
     * @param angleX rotation to give to the object on the x axis
     * @param angleY rotation to give to the object on the y axis
     * @param angleZ rotation to give to the object on the z axis
     * @author Marco Antonio Manjarrez
     */
        public static Model3D getModel3D(String path, Vector3D origin, Color color, double angleX, double angleY, double angleZ) {
            return getModel3D(path, origin, color, angleX,angleY,angleZ, 1);
        }

    /**
     * getModel3D Method, gets a model from an obj file and assigns it an origin and color, as well as a rotation in all axis and a scale.
     * @param path path to obj file
     * @param color color to assign to the object
     * @param origin origin coordinates to position the object
     * @param angleX rotation to give to the object on the x axis
     * @param angleY rotation to give to the object on the y axis
     * @param angleZ rotation to give to the object on the z axis
     * @param scale scale to give to the object in relation to its original size
     * @author Marco Antonio Manjarrez
     *
     */
        public static Model3D getModel3D(String path, Vector3D origin, Color color, double angleX, double angleY, double angleZ, double scale) {
            angleX = Math.toRadians(angleX);
            angleY = Math.toRadians(angleY);
            angleZ = Math.toRadians(angleZ);

        try {
            Vector3D[] rotInX = {new Vector3D(1,0,0), new Vector3D(0, Math.cos(angleX), -Math.sin(angleX)), new Vector3D(0, Math.sin(angleX), Math.cos(angleX))};
            Vector3D[] rotInY = {new Vector3D(Math.cos(angleY),0,Math.sin(angleY)), new Vector3D(0, 1, 0), new Vector3D(-Math.sin(angleY), 0, Math.cos(angleY))};
            Vector3D[] rotInZ = {new Vector3D(Math.cos(angleZ),-Math.sin(angleZ),0), new Vector3D(Math.sin(angleZ), Math.cos(angleZ), 0), new Vector3D(0, 0, 1)};
            BufferedReader reader = new BufferedReader(new FileReader(path));

            List<Triangle> triangles = new ArrayList<>();
            List<Vector3D> vertices = new ArrayList<>();
            List<Vector3D> normals = new ArrayList<>();
            String line;
            int defaultSmoothingGroup = -1;
            int smoothingGroup = defaultSmoothingGroup;
            Map<Integer, List<Triangle>> smoothingMap = new HashMap<>();

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("v ") || line.startsWith("vn ")) {
                    String[] vertexComponents = line.split("(\\s)+");
                    if (vertexComponents.length >= 4) {
                        double x = Double.parseDouble(vertexComponents[1]);
                        double y = Double.parseDouble(vertexComponents[2]);
                        double z = Double.parseDouble(vertexComponents[3]);
                        Vector3D vec = new Vector3D(x, y, z);
                        double[] vecRot = new double[3];
                            for(int i =0; i< 3; i++){
                                vecRot[i] = Vector3D.dotProduct(rotInX[i], vec);
                            }
                            vec = new Vector3D(vecRot[0], vecRot[1], vecRot[2]);
                            for(int i =0; i< 3; i++){
                                vecRot[i] = Vector3D.dotProduct(rotInY[i], vec);
                            }
                            vec = new Vector3D(vecRot[0], vecRot[1], vecRot[2]);
                            for(int i =0; i< 3; i++){
                                vecRot[i] = Vector3D.dotProduct(rotInZ[i], vec);
                            }
                            vec = new Vector3D(vecRot[0], vecRot[1], vecRot[2]);


                            if (line.startsWith("v ")) {
                                vec = Vector3D.scalarMultiplication(vec, scale);
                                vertices.add(vec);
                            } else {
                                normals.add(vec);
                            }

                    }
                } else if (line.startsWith("f ")) {
                    String[] faceComponents = line.split("(\\s)+");
                    List<Integer> faceVertex = new ArrayList<>();
                    List<Integer> faceNormals = new ArrayList<>();

                    for (int i = 1; i < faceComponents.length; i++) {
                        String[] infoVertex = faceComponents[i].split("/");
                        if (infoVertex.length >= 1) {
                            int vertexIndex = Integer.parseInt(infoVertex[0]);
                            faceVertex.add(vertexIndex);
                        }
                        if (infoVertex.length >= 3) {
                            int normalIndex = Integer.parseInt(infoVertex[2]);
                            faceNormals.add(normalIndex);
                        }
                    }

                    if (faceVertex.size() >= 3) {
                        Vector3D[] triangleVertices = new Vector3D[faceVertex.size()];
                        Vector3D[] triangleNormals = new Vector3D[faceNormals.size()];

                        for (int i = 0; i < faceVertex.size(); i++) {
                            triangleVertices[i] = vertices.get(faceVertex.get(i) - 1);
                        }

                        Vector3D[] arrangedTriangleVertices = null;
                        Vector3D[] arrangedTriangleNormals = null;
                        if(normals.size() > 0 && !faceNormals.isEmpty()) {
                            for(int i = 0; i < faceNormals.size(); i++) {
                                triangleNormals[i] = normals.get(faceNormals.get(i) - 1);
                            }
                            arrangedTriangleNormals = new Vector3D[]{triangleNormals[1], triangleNormals[0], triangleNormals[2]};
                        }
                        arrangedTriangleVertices = new Vector3D[]{triangleVertices[1], triangleVertices[0], triangleVertices[2]};

                        Triangle tmpTriangle = new Triangle(arrangedTriangleVertices, arrangedTriangleNormals);
                        triangles.add(tmpTriangle);

                        List<Triangle> trianglesInMap = smoothingMap.get(smoothingGroup);
                        if(trianglesInMap == null) {
                            trianglesInMap = new ArrayList<>();
                        }
                        trianglesInMap.add(tmpTriangle);

                        if (faceVertex.size() == 4) {
                            arrangedTriangleVertices = new Vector3D[]{triangleVertices[2], triangleVertices[0], triangleVertices[3]};
                            if(arrangedTriangleNormals != null){
                                arrangedTriangleNormals = new Vector3D[]{triangleNormals[2], triangleNormals[0], triangleNormals[3]};
                            }
                            tmpTriangle = new Triangle(arrangedTriangleVertices, arrangedTriangleNormals);
                            triangles.add(tmpTriangle);
                            trianglesInMap.add(tmpTriangle);
                        }

                        if(smoothingGroup != defaultSmoothingGroup) {
                            smoothingMap.put(smoothingGroup, trianglesInMap);
                        }
                    }
                } else if(line.startsWith("s ")) {
                    String[] smoothingComponents = line.split("(\\s)+");
                    if(smoothingComponents.length > 1) {
                        if(smoothingComponents[1].equals("off")){
                            smoothingGroup = defaultSmoothingGroup;
                        } else {
                            try {
                                smoothingGroup = Integer.parseInt(smoothingComponents[1]);
                            } catch (NumberFormatException nfe){
                                smoothingGroup = defaultSmoothingGroup;
                            }
                        }
                    }
                }
            }
            reader.close();

            class NormalPair{
                Vector3D normal;
                int count;

                public NormalPair() {
                    normal = Vector3D.ZERO();
                    count = 0;
                }
            }

            //Smooth vertices normals
            for (Integer key : smoothingMap.keySet()) {
                Map<Vector3D, NormalPair> vertexMap = new HashMap<>();
                List<Triangle> trianglesInMap = smoothingMap.get(key);
                for (Triangle triangle : trianglesInMap) {
                    Vector3D[] triangleVertices = triangle.getVertices();
                    Vector3D[] triangleNormals = triangle.getNormals();
                    for (int i = 0; i < triangleVertices.length; i++) {
                        NormalPair normalsVertex = vertexMap.get(triangleVertices[i]);
                        if (normalsVertex == null) {
                            normalsVertex = new NormalPair();
                        }
                        if (triangleNormals.length > 0 && i < triangleNormals.length) {
                            normalsVertex.normal = Vector3D.add(normalsVertex.normal, triangleNormals[i]);
                            normalsVertex.count++;
                        }
                        vertexMap.put(triangleVertices[i], normalsVertex);
                    }
                }
                for (Triangle triangle : trianglesInMap) {
                    Vector3D[] triangleVertices = triangle.getVertices();
                    Vector3D[] triangleNormals = triangle.getNormals();
                    for (int i = 0; i < triangleVertices.length; i++) {
                        NormalPair normalsVertex = vertexMap.get(triangleVertices[i]);
                        triangleNormals[i] = Vector3D.scalarMultiplication(normalsVertex.normal, 1.0 / (double) normalsVertex.count);
                    }
                    triangle.setNormals(triangleNormals[0], triangleNormals[1], triangleNormals[2]);
                }
            }
            return new Model3D(origin, triangles.toArray(new Triangle[triangles.size()]), color);
        } catch (IOException e) {
            System.err.println(e.toString());
        }
        return null;
    }
}
