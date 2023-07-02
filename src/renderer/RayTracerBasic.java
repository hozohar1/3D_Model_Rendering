package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * A basic implementation of a ray tracer class.
 * <p>
 * This class is responsible for tracing rays and calculating the color at intersection points
 * in a given scene.
 * <p>
 * The ray tracing algorithm includes handling of local and global effects such as shadows,
 * reflections, and refractions.
 * <p>
 * This implementation uses a basic algorithm without advanced optimization techniques.
 *
 * @author hodaya zohar && shoham shervi
 */
public class RayTracerBasic extends RayTracerBase {

    /**
     * A constant variable that is equal to 1
     */
    private static final Double3 INITIAL_K = Double3.ONE;

    /**
     * The maximum recursion level for calculating color
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;

    /**
     * The minimum attenuation factor for color calculation
     */
    private static final double MIN_CALC_COLOR_K = 0.001;

    /**
     * Constructs a new instance of the RayTracerBasic class with a given scene.
     *
     * @param scene The given scene.
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * Calculates the color at the intersection point of a ray with an object in the scene.
     *
     * @param ray The ray
     * @return The color at the intersection point
     */
    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.background : calcColor(closestPoint, ray);
    }

    /**
     * Calculates the color at the given intersection point using local and global effects.
     *
     * @param closestPoint The closest intersection point
     * @param ray          The ray
     * @return The color at the intersection point
     */
    private Color calcColor(GeoPoint closestPoint, Ray ray) {
        return calcColor(closestPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity());
    }

    /**
     * Calculates the color at the given intersection point using local and global effects.
     * Uses recursive calls to handle transparencies and reflections.
     *
     * @param intersection The intersection point
     * @param ray          The ray
     * @param level        The recursion level
     * @param k            The attenuation factor
     * @return The color at the intersection point
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(intersection, ray, k);
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
    }

    /**
     * Calculates the global effects (transparencies and reflections) at the given intersection point.
     *
     * @param gp    The intersection point
     * @param ray   The ray
     * @param level The recursion level
     * @param k     The attenuation factor
     * @return The color contribution from global effects
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = Color.BLACK;
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        return calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, k, material.kR)
                .add(calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, k, material.kT));
    }

    /**
     * Calculates the color contribution from a reflected ray at the given intersection point.
     *
     * @param ray   The reflected ray
     * @param level The recursion level
     * @param k     The attenuation factor
     * @param kx    The reflection factor of the material
     * @return The color contribution from the reflected ray
     */
    private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        GeoPoint gp = findClosestIntersection(ray);
        if (gp == null) return scene.background.scale(kx);
        return isZero(gp.geometry.getNormal(gp.point).dotProduct(ray.getDir())) ? Color.BLACK :
                calcColor(gp, ray, level - 1, kkx).scale(kx);
    }

    /**
     * Constructs the reflected ray with a shift in delta.
     *
     * @param p The initial point
     * @param v The direction of the ray to the current point
     * @param n The normal
     * @return The reflected ray
     */
    private Ray constructReflectedRay(Point p, Vector v, Vector n) {
        // r = v - 2 * (v.n) * n
        double vn = v.dotProduct(n);
        if (isZero(vn)) return null;

        Vector r = v.subtract(n.scale(2 * vn));
        return new Ray(p, r, n);
    }

    /**
     * Constructs the refracted ray with a shift in delta.
     *
     * @param p The initial point
     * @param v The direction of the ray to the current point
     * @param n The normal
     * @return The refracted ray
     */
    private Ray constructRefractedRay(Point p, Vector v, Vector n) {
        return new Ray(p, v, n);
    }

    /**
     * Calculates how shaded the point is by checking the transparency of the objects
     * between the point and the light source.
     *
     * @param gp    The intersection point
     * @param light The light source
     * @param l     The direction from the light to the point
     * @param n     The normal from the object at the point
     * @param nv    The dot product of n and v
     * @return The transparency level at the point
     */
    private Double3 transparency(GeoPoint gp, LightSource light, Vector l, Vector n, double nv) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(gp.point, lightDirection, n);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, light.getDistance(gp.point));
        Double3 ktr = Double3.ONE;
        if (intersections == null)
            return ktr;

        for (GeoPoint geoP : intersections) {
            ktr = ktr.product(geoP.geometry.getMaterial().kT);
            if (ktr.lowerThan(MIN_CALC_COLOR_K))
                return Double3.ZERO;
        }
        return ktr;
    }

    /**
     * Calculates the light contribution from all light sources at the given intersection point.
     *
     * @param geoPoint The intersection point
     * @param ray      The ray from the camera to the point
     * @param k        The attenuation factor
     * @return The color contribution from the lights at the point
     */
    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray, Double3 k) {
        Vector v = ray.getDir();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return Color.BLACK;
        int nShininess = geoPoint.geometry.getMaterial().nShininess;
        Material material = geoPoint.geometry.getMaterial();

        Color color = geoPoint.geometry.getEmission();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(geoPoint.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) {
                Double3 ktr = transparency(geoPoint, lightSource, l, n, nv);
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    Color iL = lightSource.getIntensity(geoPoint.point).scale(ktr);
                    color = color.add(
                            calcDiffuse(material.kD, nl, iL),
                            calcSpecular(material.kS, l, n, nl, v, nShininess, iL)
                    );
                }
            }
        }
        return color;
    }
    /**
     * Calculates the specular component of the light at the given point.
     *
     * @param ks             The specular component
     * @param l              The direction from the light to the point
     * @param n              The normal from the object at the point
     * @param nl             The dot product of n and l
     * @param v              The direction from the camera to the point
     * @param nShininess     The shininess level
     * @param lightIntensity The light intensity
     * @return The specular component at the point
     */
    private Color calcSpecular(Double3 ks, Vector l, Vector n, double nl, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.add(n.scale(-2 * nl));
        double vr = alignZero(v.dotProduct(r));
        if (vr >= 0) return Color.BLACK;
        return lightIntensity.scale(ks.scale(Math.pow(-vr, nShininess)));
    }

    /**
     * Calculates the diffuse component of the light at the given point.
     *
     * @param kd             The diffuse component
     * @param nl             The dot product of n and l
     * @param lightIntensity The light intensity
     * @return The diffuse component at the point
     */
    private Color calcDiffuse(Double3 kd, double nl, Color lightIntensity) {
        return lightIntensity.scale(kd.scale(Math.abs(nl)));
    }

    /**
     * Checks for shading between a point and the light source.
     *
     * @param light The light source
     * @param gp    The geo point which is shaded or not
     * @param l     The direction from the light to the point
     * @param n     The normal from the object at the point
     * @param nl    The dot product of n and l
     * @return True if the point is unshaded, false otherwise
     * @deprecated Please use transparency(...) instead of this method
     */
    @Deprecated
    @SuppressWarnings("unused")
    private boolean unshaded(LightSource light, GeoPoint gp, Vector l, Vector n, double nl) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(gp.point, lightDirection, n);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, light.getDistance(gp.point));
        if (intersections == null)
            return true;
        double lightDistance = light.getDistance(gp.point);
        for (GeoPoint geoP : intersections) {
            if (alignZero(geoP.point.distance(gp.point) - lightDistance) <= 0 && geoP.geometry.getMaterial().kT.equals(Double3.ZERO))
                return false;
        }
        return true;
    }

    /**
     * Scans the ray and looks for the first point that cuts the ray
     *
     * @param ray the ray
     * @return the closest point that cuts the ray and null if there is no points
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        return intersections == null ? null : ray.findClosestGeoPoint(intersections);
    }
}