package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * class for a direction light without position
 *
 * @author hodaya zohar && shoham shervi
 */
public class DirectionalLight extends Light implements LightSource {

    private Vector direction;

    /**
     * constructor of direction light with the direction
     *
     * @param intensity=the color of the light
     * @param direction=the direction of the light
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        return intensity;
    }

    @Override
    public Vector getL(Point p) {
        return direction;
    }

    @Override
    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY;
    }
}