package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import static primitives.Util.alignZero;

/**
 * class for a spotLight - light with direction and position
 *
 * @author hodaya zohar && shoham shervi
 */
public class SpotLight extends PointLight {

    private Vector direction;

    /**
     * constructor of spotLight with position, direction and intensity
     *
     * @param intensity=the color of the light
     * @param position=the  position of the light
     * @param direction=the direction of the light
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        double attenuation = getL(p).dotProduct(direction);
        return alignZero(attenuation) <= 0 ? Color.BLACK : super.getIntensity(p).scale(attenuation);
    }

    //bonus
    public SpotLight setNarrowBeam(int i) {
        return this;
    }
}