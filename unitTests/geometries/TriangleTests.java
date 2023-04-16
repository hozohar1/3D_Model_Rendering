package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for {@link Triangle} class
 *
 * @author hodaya zohar && shoham shervi
 */
class TriangleTests {
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Triangle triangle=new Triangle(new Point(1,0,0),new Point(0,1,0),new Point(0,0,0));
        Vector myNormal=triangle.getNormal(new Point(4,4,0));
        assertEquals(myNormal,new Vector(0, 0, 1),"TC01:wrong normal to triangle");
    }

}