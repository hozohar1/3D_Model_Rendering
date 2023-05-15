package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.*;

import static java.awt.Color.BLACK;

/**
 * Scene class represents a scene with a background, lights and geometries.
 *
 *  @author hodaya zohar && shoham shervi
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
     * Constructs a new scene with a given name.
     * Sets all colors as black and creates new empty lists for geometries and lights.
     * @param name The name of the scene.
     */
    public Scene(String name) {
        this.name = name;
        geometries = new Geometries();
        background = new Color(BLACK);
        ambientLight = new AmbientLight(new Color(BLACK), Double3.ZERO);
    }

    /**
     * Set the scene's background color
     * @param background New color for the background
     * @return this
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Set the scene's ambientLight
     * @param ambientLight New ambientLight
     * @return this
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Set the scene's geometry list.
     * @param geometries New list of geometries
     * @return this (builder pattern)
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;

    }
}
