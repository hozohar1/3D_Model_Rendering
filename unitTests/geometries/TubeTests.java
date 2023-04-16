package geometries;

import org.junit.jupiter.api.Test;
import primitives.Double3;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for {@link Tube} class
 *
 * @author hodaya zohar && shoham shervi
 */
class TubeTests {

    @Test
    void getNormal() {
        Ray r = new Ray(new Point(0, 0, 0), new Vector(0, 0, 1));
        Tube t = new Tube(Math.sqrt(2), r);

        // =============== Equivalence Partitions Tests ==============
        // TC01: simple check
        assertEquals(new Vector(1, 1, 0), t.getNormal(new Point(1, 1, 2)), "the normal is not correct");

        // =============== Boundary Values Tests ==================
        // TC11: checking if the
        assertEquals(new Vector(1, 1, 0), t.getNormal(new Point(1, 1, 1)), "the normal is not correct");
    }
}

