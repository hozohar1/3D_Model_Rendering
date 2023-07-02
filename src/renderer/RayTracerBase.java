package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * RayTracerBase Class is an abstract class as a template to
 * ray tracers, which are calculating the color of the point.
 */
public abstract class RayTracerBase {

    /**
     * The scene to trace.
     */
    protected final Scene scene;

    /**
     * Constructs a new instance of ray tracer (the inherit class) with a given scene.
     *
     * @param scene The given scene.
     */
    RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Find the intersection and the scene’s geometries
     * If there is no intersection, return the background color
     * Find the closest intersection point
     * Find the color of the intersection point (Ambient light)
     *
     * @param ray The ray to trace.
     * @return The color of the intersection point.
     */
    public abstract Color traceRay(Ray ray);
}