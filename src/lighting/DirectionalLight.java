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
     * constructor of direction light
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
        return super.getIntensity();
    }

    @Override
    public Vector getL(Point p) {
        return direction;
    }
}