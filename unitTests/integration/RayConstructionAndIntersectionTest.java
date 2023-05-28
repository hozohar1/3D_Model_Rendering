package integration;

import renderer.Camera;
import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing integration of constructing rays and intersections.
 *
 * @author hodaya zohar && shoham shervi
 */
public class RayConstructionAndIntersectionTest {

    /**
     * Tests the integration of ray construction and ray intersection.
     */
    @Test
    public void rayConstructionAndIntersectionTest() {
        Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setViewPlaneDistance(1).setViewPlaneSize(3, 3);

        // TC01: Sphere, 2 intersection points
        Sphere sp = new Sphere(new Point(0, 0, -3),1);


        assertEquals(2, intersectionPointCountThroughCamera(camera, sp), "TC01: Sphere, 2 intersection points test not working");

        // TC02: Sphere, 18 intersection points
        camera = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setViewPlaneDistance(1).setViewPlaneSize(3, 3);
        sp = new Sphere(new Point(0, 0, -2.5),2.5);

        assertEquals(18, intersectionPointCountThroughCamera(camera, sp), "TC02: Sphere, 18 intersection points test not working");

        // TC03: Sphere, 10 intersection points
        sp = new Sphere(new Point(0, 0, -2),2);

        assertEquals(10, intersectionPointCountThroughCamera(camera, sp), "TC03: Sphere, 10 intersection points test not working");

        // TC04: Sphere, 0 intersection points
        sp = new Sphere(new Point(0, 0, 1),0.5);

        assertEquals(0, intersectionPointCountThroughCamera(camera, sp), "TC04: Sphere, 0 intersection points test not working");

        // TC05: Straight plane, 9 intersection points
        Plane pl = new Plane(new Point(0, 0, -3), new Vector(0, 0, -1));

        assertEquals(9, intersectionPointCountThroughCamera(camera, pl), "TC05: Straight plane, 9 intersection points test not working");

        // TC06: Tilted plane, 9 intersection points
        pl = new Plane(new Point(0, 0, -2), new Vector(0, 2, 3));

        assertEquals(9, intersectionPointCountThroughCamera(camera, pl), "TC06: Tilted plane, 9 intersection points test not working");

        // TC07: Straight plane, 9 intersection points
        pl = new Plane(new Point(0, 0, -3), new Vector(1, 0, -1));

        assertEquals(6, intersectionPointCountThroughCamera(camera, pl), "TC07: Tilted plane, 6 intersection points test not working");

        // TC08: Triangle, 1 intersection point
        camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setViewPlaneDistance(1).setViewPlaneSize(3, 3);
        Triangle tr = new Triangle(new Point(0, 1, -2),
                new Point(1, -1, -2), new Point(-1, -1, -2));

        assertEquals(1, intersectionPointCountThroughCamera(camera, tr), "TC08: Triangle, 1 intersection point test not working");

        // TC09: Triangle, 2 intersection points
        tr = new Triangle(new Point(0, 20, -2),
                new Point(1, -1, -2), new Point(-1, -1, -2));

        assertEquals(2, intersectionPointCountThroughCamera(camera, tr), "TC09: Triangle, 2 intersection point test not working");
    }

    /**
     * Checks the amount of intersection points in a geometry from a camera.
     *
     * @param camera        The camera where to check intersection points from
     * @param intersectable The geometry to check intersections with.
     * @return The amount of intersection points from the given camera.
     */
    int intersectionPointCountThroughCamera(Camera camera, Intersectable intersectable) {
        int sum = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                var tmp = intersectable.findIntersections(camera.constructRayThroughPixel(3, 3, j, i));
                if (tmp != null)
                    sum += tmp.size();
            }
        }
        return sum;
    }
}