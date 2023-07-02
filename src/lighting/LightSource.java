package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * @author hodaya zohar && shoham shervi
 * interface for all the lights
 */
public interface LightSource {

    /**
     * get the light intensity at a point
     *
     * @param p the point
     * @return the light intensity at the point as color
     */
    /**
     * get the light intensity at a point
     *
     * @param p the point
     * @return the light intensity at the point as color
     */
    public Color getIntensity(Point p);

    /**
     * get the direction of the light towards the point
     *
     * @param p the point
     * @return the direction from the light to the point
     */
    public Vector getL(Point p);

    /**
     * calculate the distance between the point to the light source
     *
     * @param point the point
     * @return the distance from point to the light
     */
    double getDistance(Point point);

    /**
     * Get the direction of the light from a point
     *
     * @param p         the point
     * @param numOfRays the number of rays
     * @return the direction
     */
    Vector[][] getList(Point p, int numOfRays);
}