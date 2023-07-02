package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * Ambient light source represents a non-directional, fixed-intensity and fixed-color light source.
 *
 * @author hodaya zohar && shoham shervi
 */
public class AmbientLight extends Light {

    // ***************** Constructors ********************** //

    /**
     * Constructs a new instance of ambient light with intensity according to the parameters.<br>
     * The final intensity is iA * kA.
     *
     * @param iA Intensity of the ambient light.
     * @param kA The attenuation coefficient of the ambient light.
     */
    public AmbientLight(Color iA, Double3 kA) {
        super(iA.scale(kA));
    }

    /**
     * Constructs ambient light of complete darkness
     */
    public AmbientLight() {
        super(Color.BLACK);
    }
}
