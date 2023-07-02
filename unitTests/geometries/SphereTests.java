package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for {@link Sphere} class
 *
 * @author hodaya zohar && shoham shervi
 */
class SphereTest {

    /**
     * Test method for {@link Sphere#getNormal(Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests =============
        //        //TC01: Test that the= normal is proper
        Sphere sph = new Sphere(1.0, new Point(0, 0, 1));
        assertEquals(new Vector(0, 0, 1), sph.getNormal(new Point(0, 0, 2)), "TC01: Wrong normal to sphere");

        Sphere sp = new Sphere(1, new Point(0, 0, 0));
        assertEquals(sp.getNormal(new Point(0, 0, 1)), new Vector(0, 0, 1), "TC01: Sphere.getNormal() gives wrong normal.");
    }

    /**
     * Test method for {@link Sphere#findIntersections(Ray)}.
     */
    @Test
    void testFindIntersections() {
        Sphere sphere = new Sphere(1, new Point(-3, 0, 0));
        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray does not intersect the sphere.
        Ray ray = new Ray(new Point(3, 0, 0), new Vector(-1, 0, 1));
        assertNull(sphere.findIntersections(ray), "TC01: Ray that doesn't intersect sphere.");

        // TC02: Ray intersects the sphere twice.
        ray = new Ray(new Point(3, 0, 0), new Vector(-1, 0, 0));
        List<Point> expRes = List.of(new Point(-2, 0, 0), new Point(-4, 0, 0));
        List<Point> res = sphere.findIntersections(ray);
        assertEquals(res.size(), 2, "TC02: Ray intersects sphere twice.");
        if (res.get(0).getX() < res.get(1).getX())
            res = List.of(res.get(1), res.get(0));
        assertEquals(res, expRes, "TC02: Ray intersects sphere twice.");

        // TC03: Ray intersects the sphere from inside the sphere.
        ray = new Ray(new Point(-3.5, 0, 0), new Vector(-1, 0, 0));
        expRes = List.of(new Point(-4, 0, 0));
        res = sphere.findIntersections(ray);
        assertEquals(res.size(), 1, "TC03: Ray from inside sphere.");
        assertEquals(res, expRes, "TC03: Ray from inside sphere.");


        // TC04: Ray goes to the opposite direction of the sphere (then 0 intersection points).
        ray = new Ray(new Point(3, 0, 0), new Vector(1, 0, 0));
        assertNull(sphere.findIntersections(ray), "TC04: Ray in opposite dir of sphere.");


        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside ( one points)
        ray = new Ray(new Point(-4, 0, 0), new Vector(1, 0, 1));
        expRes = List.of(new Point(-3, 0, 1));
        res = sphere.findIntersections(ray);
        assertEquals(res.size(), 1, "TC11: Ray from the sphere inwards.");
        assertEquals(expRes, res, "TC11: Ray from the sphere inwards.");

        // TC12: Ray starts at sphere and goes outside (0 points)
        ray = new Ray(new Point(-4, 0, 0), new Vector(-1, 0, -1));
        assertNull(sphere.findIntersections(ray), "TC12: Ray from the sphere outwards.");

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        ray = new Ray(new Point(-5, 0, 0), new Vector(1, 0, 0));
        expRes = List.of(new Point(-4, 0, 0), new Point(-2, 0, 0));
        res = sphere.findIntersections(ray);
        assertEquals(res.size(), 2, "TC13: Ray through center 2 points.");
        if (res.get(1).getX() < res.get(0).getX())
            res = List.of(res.get(1), res.get(0));
        assertEquals(expRes, res, "TC13: Ray through center 2 points.");

        // TC14: Ray starts at sphere and goes inside (1 points)
        ray = new Ray(new Point(-4, 0, 0), new Vector(1, 0, 0));
        expRes = List.of(new Point(-2, 0, 0));
        res = sphere.findIntersections(ray);
        assertEquals(res.size(), 1, "TC14: Ray on sphere through center inwards.");
        assertEquals(expRes, res, "TC14: Ray on sphere through center inwards.");

        // TC15: Ray starts inside after the center (1 points)
        ray = new Ray(new Point(-2.5, 0, 0), new Vector(1, 0, 0));
        expRes = List.of(new Point(-2, 0, 0));
        res = sphere.findIntersections(ray);
        assertEquals(res.size(), 1, "TC15: Ray in sphere through center");
        assertEquals(expRes, res, "TC15: Ray in sphere through center");

        // TC16: Ray starts at the center (1 points)
        ray = new Ray(new Point(-3, 0, 0), new Vector(1, 0, 0));
        expRes = List.of(new Point(-2, 0, 0));
        res = sphere.findIntersections(ray);
        assertEquals(res.size(), 1, "TC16: Ray from center.");
        assertEquals(expRes, res, "TC16: Ray from center.");

        // TC17: Ray starts at sphere and goes outside (0 points)
        ray = new Ray(new Point(-4, 0, 0), new Vector(-1, 0, 0));
        res = sphere.findIntersections(ray);
        assertNull(res, "TC17: Ray on sphere through center outwards.");

        // TC18: Ray starts after sphere (0 points)
        ray = new Ray(new Point(-1, 0, 0), new Vector(1, 0, 0));
        res = sphere.findIntersections(ray);
        assertNull(res, "TC18: Ray out of sphere through center.");


        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        ray = new Ray(new Point(0, 0, 1), new Vector(-1, 0, 0));
        assertNull(sphere.findIntersections(ray), "TC19: Ray tangent to the sphere.");

        // TC20: Ray starts at the tangent point
        ray = new Ray(new Point(-3, 0, 1), new Vector(-1, 0, 0));
        assertNull(sphere.findIntersections(ray), "TC20: Ray tangent to the sphere");

        // TC21: Ray starts after the tangent point
        ray = new Ray(new Point(-4, 0, 1), new Vector(-1, 0, 0));
        assertNull(sphere.findIntersections(ray), "TC21: Ray tangent to the sphere.");


        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        ray = new Ray(new Point(-1, 0, 0), new Vector(0, 0, 1));
        assertNull(sphere.findIntersections(ray), "TC22: Ray outside orthogonal to sphere center line.");

    }
}