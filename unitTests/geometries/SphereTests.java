package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

class SphereTests {

    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============

        Sphere sphere = new Sphere(1,new Point(0,0,1));
        // ensure there are no exceptions
        assertDoesNotThrow(() -> sphere.getNormal(new Point(0, 0, 6)), "");
        // generate the test result
         Vector result = sphere.getNormal(new Point(0, 0, 6));
        // ensure |result| = 1
       assertEquals(1, result.length(), 0.00000001, "sphere's normal is not a unit vector");
    }

}