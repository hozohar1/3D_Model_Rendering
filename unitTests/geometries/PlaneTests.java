package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for {@link Plane} class
 *
 * @author hodaya zohar && shoham shervi
 */
class PlaneTests {

    /**
     * Test method for {@link Plane#Plane(Point, Point, Point)}.
     */
    @Test
    void testConstructor() {
        // =============== Boundary Values Tests ==================
        Point p0 = new Point(1, 2, 3);
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(4, 5, 6);

        //TC11: Test that constructor doesn't accept two points equals
        assertThrows(IllegalArgumentException.class, () -> new Plane(p0, p1, p2),
                "TC11: the constructor must throw exception when two point equals");

        //TC12: Test that constructor doesn't accept three points that are co-lined
        Point p3 = new Point(2, 4, 6);
        Point p4 = new Point(4, 8, 12);
        assertThrows(IllegalArgumentException.class, () -> new Plane(p0, p3, p4),
                "TC12: the constructor must throw exception when three points are co-lined");
    }

    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here - using a quad
        Point[] pts =
                { new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1) };
        Plane plane = new Plane(pts[0],pts[1],pts[2]);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> plane.getNormal(new Point(0, 0, 1)), "");
        // generate the test result
        Vector result = plane.getNormal(new Point(0, 0, 1));
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Plane's normal is not a unit vector");
        // ensure the result is orthogonal to all the edges
        for (int i = 0; i < 3; ++i)
            assertTrue(isZero(result.dotProduct(pts[i].subtract(pts[i == 0 ? 3 : i - 1]))),
                    "Plane's normal is not orthogonal to one of the edges");
    }


    /**
     * Test method for {@link Plane#findIntsersections(Ray)}.
     */
    @Test
    void testFindIntersections() {
        Plane plane = new Plane(new Point(-0.5, -0.5, 0), new Point(1, 0, 0), new Point(0, 1, 0));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray intersects the plane.
        Ray ray = new Ray(new Point(1, 1, 1), new Vector(-1, 0, -1));
        List<Point> expRes = List.of(new Point(0, 1, 0));
        List<Point> res = plane.findIntsersections(ray);
        assertEquals(res.size(), 1, "TC01: Ray intersects the plane EP doesn't work.");
        assertEquals(res, expRes, "TC01: Ray intersects the plane EP doesn't work.");

        // TC02: Ray does not intersect the plane.
        ray = new Ray(new Point(1, 1, 1), new Vector(1, 1, 2));
        assertNull(plane.findIntsersections(ray), "TC01: Ray does not intersects the plane EP doesn't work.");


        // =============== Boundary Values Tests ==================
        // TC11: Ray is parallel and included in the plane.
        ray = new Ray(new Point(0, 1, 0), new Vector(1, 0, 0));
        assertNull(plane.findIntsersections(ray), "TC11: Ray is parallel and included in the plane BVA doesn't work.");

        // TC12: Ray is parallel and not included in the plane.
        ray = new Ray(new Point(0, 1, 1), new Vector(1, 0, 0));
        assertNull(plane.findIntsersections(ray), "TC12: Ray is parallel and not included in the plane BVA doesn't work.");

        // TC13: Ray is orthogonal to the plane and before the plane.
        ray = new Ray(new Point(0, 1, 1), new Vector(0, 0, -1));
        expRes = List.of(new Point(0, 1, 0));
        res = plane.findIntsersections(ray);
        assertEquals(res.size(), 1, "TC13: Ray is orthogonal to the plane and before the plane BVA doesn't work.");
        assertEquals(res, expRes, "TC13: Ray is orthogonal to the plane and before the plane BVA doesn't work.");

        // TC14: Ray is orthogonal to the plane and on the plane.
        ray = new Ray(new Point(0, 2, 0), new Vector(0, 0, -1));
        assertNull(plane.findIntsersections(ray), "TC14: Ray is orthogonal to the plane and in the plane BVA doesn't work.");

        // TC15: Ray is orthogonal to the plane and after the plane.
        ray = new Ray(new Point(0, 2, -1), new Vector(0, 0, -1));
        assertNull(plane.findIntsersections(ray), "TC15: Ray is orthogonal to the plane and after the plane BVA doesn't work.");

        // TC16: Ray begins in the same point which appears as the plane's reference point.
        ray = new Ray(plane.getQ0(), new Vector(1, 1, 0));
        assertNull(plane.findIntsersections(ray), "TC16: Ray begins in the same point which appears as the plane's reference point BVA doesn't work.");

        // TC17: Ray begins in the same plane but the ray it's not parallel or orthogonal to the plane
        ray = new Ray(new Point(0, 1, 0), new Vector(1, 1, -1));
        assertNull(plane.findIntsersections(ray), "TC17: Ray begins at the plane but the ray it's not parallel or orthogonal to the plane BVA doesn't work.");
    }

}