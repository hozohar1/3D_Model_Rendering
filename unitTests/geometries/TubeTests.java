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

    /**
     * Test method for {@link Tube#getNormal(Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Tube tube = new Tube(1.0,new Ray(new Point(0, 0, 1), new Vector(0, -1, 0)));
        Vector normal = tube.getNormal(new Point(0, 8, 2));
        double check = normal.dotProduct(tube.getAxisRay().getDir());

        //TC01: Test that normal is orthogonal to the tube
        assertEquals(0d, check, "TC01: normal is not orthogonal to the tube");

        //TC02: Test if the normal is proper
        assertEquals(new Vector(0, 0, 1), normal, "TC02: wrong normal to tube");

        // =============== Boundary Values Tests ==================
        normal = tube.getNormal(new Point(0, 0, 2));
        check = normal.dotProduct(tube.getAxisRay().getDir());

        //TC11: Test that normal is orthogonal to the tube
        assertEquals(0d, check, "TC11: normal is not orthogonal to the tube");

        //TC12: Test if the normal is proper
        assertEquals(new Vector(0, 0, 1), normal, "TC12: wrong normal to tube");
    }

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

