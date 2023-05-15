package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;
import geometries.Geometries;

import java.util.List;

/**
 * A basic implementation of a ray tracer class.
 *  @author hodaya zohar && shoham shervi
 */

public class RayTracerBasic extends RayTracerBase{

    /**
     * Constructs a new instance of ray tracer with a given scene.
     * @param scene The given scene.
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }



    /**
     * A function to check the color of a point.
     * @param point The given point.
     * @return The color of the point.
     */
    private Color calcColor(Point point) {
        return scene.ambientLight.getIntensity();
    }

    @Override
    public Color traceRay(Ray ray) {
        Point closestPoint = ray.findClosestPoint(scene.geometries.findIntsersections(ray));
        return closestPoint == null ? scene.background : calcColor(closestPoint);
    }


}
