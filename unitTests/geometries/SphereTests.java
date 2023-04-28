package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;
/**
 * Unit tests for {@link Sphere} class
 *
 * @author hodaya zohar && shoham shervi
 */
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
    @Test
    void testFindIntersections() {
        Sphere sphere = new Sphere(1,new Point(-3, 0, 0));
        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray does not intersect the sphere.
        Ray ray = new Ray(new Point(3, 0, 0), new Vector(-1, 0, 1));
        assertNull(sphere.findIntsersections(ray), "TC01: Ray that doesn't intersect sphere.");

        // TC02: Ray intersects the sphere twice.
        ray = new Ray(new Point(3, 0, 0), new Vector(-1, 0, 0));
        List<Point> expRes = List.of(new Point(-2, 0, 0), new Point(-4, 0, 0));
        List<Point> res = sphere.findIntsersections(ray);
        assertEquals(res.size(), 2, "TC02: Ray intersects sphere twice.");
        if (res.get(0).getX() < res.get(1).getX())
            res = List.of(res.get(1), res.get(0));
        assertEquals(res, expRes, "TC02: Ray intersects sphere twice.");

        // TC03: Ray intersects the sphere from inside the sphere.
        ray = new Ray(new Point(-3.5, 0, 0), new Vector(-1, 0, 0));
        expRes = List.of(new Point(-4, 0, 0));
        res = sphere.findIntsersections(ray);
        assertEquals(res.size(), 1, "TC03: Ray from inside sphere.");
        assertEquals(res, expRes, "TC03: Ray from inside sphere.");


        // TC04: Ray goes to the opposite direction of the sphere (then 0 intersection points).
        ray = new Ray(new Point(3, 0, 0), new Vector(1, 0, 0));
        assertNull(sphere.findIntsersections(ray), "TC04: Ray in opposite dir of sphere.");


        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        ray = new Ray(new Point(-4, 0, 0), new Vector(1, 0, 1));
        expRes = List.of(new Point(-3, 0, 1));
        res = sphere.findIntsersections(ray);
        assertEquals(res.size(), 1, "TC11: Ray from the sphere inwards.");
        assertEquals(expRes, res, "TC11: Ray from the sphere inwards.");

        // TC12: Ray starts at sphere and goes outside (0 points)
        ray = new Ray(new Point(-4, 0, 0), new Vector(-1, 0, -1));
        assertNull(sphere.findIntsersections(ray), "TC12: Ray from the sphere outwards.");

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        ray = new Ray(new Point(-5, 0, 0), new Vector(1, 0, 0));
        expRes = List.of(new Point(-4, 0, 0), new Point(-2, 0, 0));
        res = sphere.findIntsersections(ray);
        assertEquals(res.size(), 2, "TC13: Ray through center 2 points.");
        if (res.get(1).getX() < res.get(0).getX())
            res = List.of(res.get(1), res.get(0));
        assertEquals(expRes, res, "TC13: Ray through center 2 points.");

        // TC14: Ray starts at sphere and goes inside (1 points)
        ray = new Ray(new Point(-4, 0, 0), new Vector(1, 0, 0));
        expRes = List.of(new Point(-2, 0, 0));
        res = sphere.findIntsersections(ray);
        assertEquals(res.size(), 1, "TC14: Ray on sphere through center inwards.");
        assertEquals(expRes, res, "TC14: Ray on sphere through center inwards.");

        // TC15: Ray starts inside after the center (1 points)
        ray = new Ray(new Point(-2.5, 0, 0), new Vector(1, 0, 0));
        expRes = List.of(new Point(-2, 0, 0));
        res = sphere.findIntsersections(ray);
        assertEquals(res.size(), 1, "TC15: Ray in sphere through center");
        assertEquals(expRes, res, "TC15: Ray in sphere through center");

        // TC16: Ray starts at the center (1 points)
        ray = new Ray(new Point(-3, 0, 0), new Vector(1, 0, 0));
        expRes = List.of(new Point(-2, 0, 0));
        res = sphere.findIntsersections(ray);
        assertEquals(res.size(), 1, "TC16: Ray from center.");
        assertEquals(expRes, res, "TC16: Ray from center.");

        // TC17: Ray starts at sphere and goes outside (0 points)
        ray = new Ray(new Point(-4, 0, 0), new Vector(-1, 0, 0));
        res = sphere.findIntsersections(ray);
        assertNull(res, "TC17: Ray on sphere through center outwards.");

        // TC18: Ray starts after sphere (0 points)
        ray = new Ray(new Point(-1, 0, 0), new Vector(1, 0, 0));
        res = sphere.findIntsersections(ray);
        assertNull(res, "TC18: Ray out of sphere through center.");


        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        ray = new Ray(new Point(0, 0, 1), new Vector(-1, 0, 0));
        assertNull(sphere.findIntsersections(ray), "TC19: Ray tangent to the sphere.");

        // TC20: Ray starts at the tangent point
        ray = new Ray(new Point(-3, 0, 1), new Vector(-1, 0, 0));
        assertNull(sphere.findIntsersections(ray), "TC20: Ray tangent to the sphere");

        // TC21: Ray starts after the tangent point
        ray = new Ray(new Point(-4, 0, 1), new Vector(-1, 0, 0));
        assertNull(sphere.findIntsersections(ray), "TC21: Ray tangent to the sphere.");


        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        ray = new Ray(new Point(-1, 0, 0), new Vector(0, 0, 1));
        assertNull(sphere.findIntsersections(ray), "TC22: Ray outside orthogonal to sphere center line.");

    }
    }