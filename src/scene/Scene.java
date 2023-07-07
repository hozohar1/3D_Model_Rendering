package scene;

import geometries.Geometries;
import geometries.Intersectable;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;
import primitives.Double3;

import java.util.LinkedList;
import java.util.List;

import static java.awt.Color.BLACK;

/**
 * Scene class represents a scene with a background, lights and geometries.
 *
 * @author hodaya zohar && shoham shervi
 */
public class Scene {
    /**
     * The name of the scene.
     */
    public String name;

    /**
     * The background color of the scene.
     */
    public Color background;

    /**
     * The ambient color of the scene.
     */
    public AmbientLight ambientLight;

    /**
     * A list of all geometries in the scene.
     */
    public Geometries geometries;
    /**
     * A list of all kind of light
     */
    public List<LightSource> lights = new LinkedList<>();

    /**
     * Constructs a new scene with a given name.
     * Sets all colors as black and creates new empty lists for geometries and lights.
     *
     * @param name The name of the scene.
     */
    public Scene(String name) {
        this.name = name;
        geometries = new Geometries();
        background = new Color(BLACK);
        ambientLight = new AmbientLight(new Color(BLACK), Double3.ZERO);
    }

    /**
     * set the scene`s light
     *
     * @param lights new light
     * @return the updated scene itself
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }

    /**
     * Set the scene's background color
     *
     * @param background New color for the background
     * @return this
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Set the scene's ambientLight
     *
     * @param ambientLight New ambientLight
     * @return this
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Set the scene's geometry list.
     *
     * @param geometries New list of geometries
     * @return this (builder pattern)
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;

    }
    /**
     * Sets Conservative Bounding Region for creating the scene (for its 3D model).<br>
     * It must be called <b><u>before</u></b> creating the 3D model (adding bodyes to the scene).
     * @return scene object itself
     */
    public Scene setCBR() {
        Intersectable.setCbr();
        return this;
    }

    /**
     * Creates Bounding Volume Hierarchy in the scene's 3D model<br>
     * It must be called <b><u>after</u></b> creating the 3D model (adding bodyes to the scene).
     * @return scene object itself
     */
    public Scene setBVH() {
        geometries.setBVH();
        return this;
    }
//    /**
//     * the function parse xml file into scene object
//     *
//     * @param path of the xml files
//     * @return the object itself
//
//     */
//    public Scene setXml(String path) {
//        return this;
//    }
}
