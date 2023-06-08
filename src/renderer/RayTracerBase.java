package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * RayTracerBase Class is an abstract class as a template to
 * ray tracers, which are calculating the color of the point.
 *  @author hodaya zohar && shoham shervi
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


    public abstract Color traceRay(Ray ray);
}