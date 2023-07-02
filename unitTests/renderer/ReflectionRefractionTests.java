/**
 *
 */
package renderer;

import geometries.Cylinder;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import static java.awt.Color.*;

/**
 * Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 *
 * @author dzilb
 */
public class ReflectionRefractionTests {
    private Scene scene = new Scene("Test scene");


    @Test
    public void xx() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setViewPlaneSize(150, 150).setViewPlaneDistance(1000) //set the DoF.
                .setDepthOfFiled(true)
                .setFPDistance(500).setNumOfPoints(100)
                .setApertureSize(1);

        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)));
        scene.geometries.add(// Triangle with transparency, reflection, and specular
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKt(0.3).setKr(0.2)),
                // Sphere with transparency, reflection, and diffuse
                new Sphere(30d,new Point(0, 0, -50)).setEmission(new Color(0, 0, 255))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.5)),
                // Cylinder with reflection, specular, and diffuse
                new Cylinder(10,new Ray(new Point(0, 0, -100), new Vector(0, 0, 1)), 40)
                        .setEmission(new Color(0, 255, 0))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100).setKr(0.5)),
                // Triangle with reflection, specular, and diffuse
                new Triangle(new Point(-50, -50, -50), new Point(50, -50, -50), new Point(0, 50, -50))
                        .setEmission(new Color(255, 0, 0))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100).setKr(0.8)),
                // Refracted sphere with specular and diffuse
                new Sphere(15d,new Point(-50, 50, -50)).setEmission(new Color(0, 255, 255))
                        .setMaterial(new Material().setKd(0.4).setKs(0.2).setShininess(80).setKt(0.8)),
                // Reflective sphere
                new Sphere(15d,new Point(50, 50, -50)).setEmission(new Color(255, 255, 0))
                        .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(80).setKr(1.0))
        );


        scene.lights.add(
                new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2))
                        .setKl(0.0004).setKq(0.0000006)
        );

        camera.setImageWriter(new ImageWriter("xx", 500, 500))
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
    }


    @Test
    public void manyShapes() {
        Camera camera = new Camera(new Point(0, 0, 1900), new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setViewPlaneSize(150, 150).setViewPlaneDistance(1000);


        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)));
        scene.geometries.add(
                new Sphere(20d, new Point(-100, 100, -100))
                        .setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(10d, new Point(-100, 100, -100))
                        .setEmission(new Color(RED))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)),
                new Sphere(15d, new Point(-50, 50, -50))
                        .setEmission(new Color(RED))
                        .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(80)),
                new Sphere(10d, new Point(50, -50, -50))
                        .setEmission(new Color(YELLOW))
                        .setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(120)),
                new Sphere(20d, new Point(-50, -50, -50))
                        .setEmission(new Color(GREEN))
                        .setMaterial(new Material().setKd(0.8).setKs(0.7).setShininess(90)),
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                        new Point(75, 75, -150))
                        .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(80).setKr(1.0)),
                new Triangle(new Point(-150, -150, -115), new Point(75, 75, -150),
                        new Point(-70, 70, -140))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                new Sphere(200d, new Point(-950, -900, -1000))
                        .setEmission(new Color(0, 50, 100))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                                .setKt(new Double3(0.5, 0, 0))),
                new Sphere(10d, new Point(-19.5, -9, -10))
                        .setEmission(new Color(0, 50, 150))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                                .setKt(new Double3(0.5, 0, 0))),
                new Sphere(9d, new Point(-19.5, -9, -10))
                        .setEmission(new Color(150, 50, 20))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                new Triangle(new Point(20, -15, -15), new Point(-15, 15, -15),
                        new Point(-15, -15, -20))
                        .setEmission(new Color(20, 20, 200))
                        .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(80).setKr(1.0))


        );


        scene.lights.add(
                new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2))
                        .setKl(0.0004).setKq(0.0000006)
        );

        camera.setImageWriter(new ImageWriter("manyShapes", 500, 500))
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
    }


//    @Test
//    public void zz() {
//        Camera camera = new Camera(new Point(0, 0, 1900), new Vector(0, 0, -1), new Vector(0, 1, 0))
//                .setViewPlaneSize(150, 150).setViewPlaneDistance(1000);
//
//        //  scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)));
//
//        // Number of spheres in the outer circle
//        int numSpheresOuter = 10;
//        // Radius of the outer circle
//        double circleRadiusOuter = 120;
//        // Angle increment for each sphere in the outer circle
//        double angleIncrementOuter = 360.0 / numSpheresOuter;
//        // Starting angle for the first sphere in the outer circle
//        double startAngleOuter = 0.0;
//
//        // Number of triangles in the inner circle
//        int numTrianglesInner = 8;
//        // Radius of the inner circle
//        double circleRadiusInner = 50;
//        // Angle increment for each triangle in the inner circle
//        double angleIncrementInner = 360.0 / numTrianglesInner;
//        // Starting angle for the first triangle in the inner circle
//        double startAngleInner = 0.0;
//
//        // Array of colors
//        Color[] colors = {
//                new Color(225, 138, 170),  // Red
//                new Color(120, 190, 121),  // Green
//                new Color(168, 181, 224),  // Blue
//                new Color(190, 169, 223),  // Yellow
//                // Add additional colors for the remaining spheres
//        };
//
//        // Creating the central sphere in the outer circle
//        // Sphere centerSphere = (Sphere) new Sphere(new Point(0, 0, -100), 20d)
//        //         .setEmission(colors[0])
//        //         .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(80).setKr(1.0));
//
//        // scene.geometries.add(centerSphere);
//
//        for (int i = 0; i < numSpheresOuter; i++) {
//            double angleOuter = Math.toRadians(startAngleOuter + angleIncrementOuter * i);
//            double xOuter = circleRadiusOuter * Math.cos(angleOuter);
//            double yOuter = circleRadiusOuter * Math.sin(angleOuter);
//
//            int colorIndexOuter = i % colors.length;  // Finding the index of the color in the array
//
//            Sphere outerSphere = (Sphere) new Sphere(25d, new Point(xOuter, yOuter, -100))
//                    .setEmission(colors[colorIndexOuter])
//                    .setMaterial(new Material().setKd(0.0).setKs(0.2).setShininess(60).setKr(0.5));
//
//            scene.geometries.add(outerSphere);
//
//            // Creating triangles in the inner circle
//            for (int j = 0; j < numTrianglesInner; j++) {
//                double angleInner = Math.toRadians(startAngleInner + angleIncrementInner * j);
//                double xInner = xOuter + circleRadiusInner * Math.cos(angleInner);
//                double yInner = yOuter + circleRadiusInner * Math.sin(angleInner);
//
//                int colorIndexInner = (i + j) % colors.length;  // Finding the index of the color in the array
//
//                Triangle triangle = (Triangle) new Triangle(
//                        new Point(xOuter, yOuter, -100),
//                        new Point(xInner, yInner, -100),
//                        new Point(xInner, yInner, -200)).setEmission(colors[colorIndexInner]).setMaterial(new Material().setKd(0.3).setKs(0.8).setShininess(250).setKr(0.3));
//
//                scene.geometries.add(triangle);
//            }
//        }
//
//        scene.lights.add(
//                new SpotLight(new Color(WHITE), new Point(0, 0, 1500), new Vector(0, 0, -2))
//                        .setKl(0.0004).setKq(0.006)
//        );
//        camera.setImageWriter(new ImageWriter("zz", 500, 500))
//                .setRayTracer(new RayTracerBasic(scene))
//                .renderImage()
//                .writeToImage();
//    }


    /**
     * Produce a picture of a sphere lighted by a spotlight
     */
    @Test
    public void twoSpheres() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(150, 150).setViewPlaneDistance(1000);

        scene.geometries.add( //
                new Sphere(50d, new Point(0, 0, -50)).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(25d, new Point(0, 0, -50)).setEmission(new Color(RED)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
        scene.lights.add( //
                new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
                        .setKl(0.0004).setKq(0.0000006));

        camera.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spotlight
     */
    @Test
    public void twoSpheresOnMirrors() {
        Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(2500, 2500).setViewPlaneDistance(10000); //

        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)));

        scene.geometries.add( //
                new Sphere(400d, new Point(-950, -900, -1000)).setEmission(new Color(0, 50, 100)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                                .setKt(new Double3(0.5, 0, 0))),
                new Sphere(200d, new Point(-950, -900, -1000)).setEmission(new Color(100, 50, 20)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(670, 670, 3000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setKr(1)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(-1500, -1500, -2000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setKr(new Double3(0.5, 0, 0.4))));

        scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
                .setKl(0.00001).setKq(0.000005));

        ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    /**
     * Produce a picture of  two triangles lighted by a spotlight with a
     * partially
     * transparent Sphere producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(200, 200).setViewPlaneDistance(1000);

        scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));

        scene.geometries.add( //
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                        new Point(75, 75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                new Sphere(30d, new Point(60, 50, -50)).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));

        scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
                .setKl(4E-5).setKq(2E-7));

        ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    @Test
    public void allFeature() {
        Camera camera = new Camera(new Point(0, 0, 5000),
                new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(200, 200).setViewPlaneDistance(1000);

        Scene scene = new Scene("Test scene")
                .setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)))
                .setBackground(new Color(94, 97, 99));

        scene.geometries.add( //
                new Triangle(new Point(450, -350, 0),
                        new Point(-500, -400, 0), new Point(0, 500, -80)) //
                        .setEmission(new Color(75, 75, 75)) //
                        .setMaterial(new Material().setKr(1).setKs(0.1).setShininess(10)),//
                new Sphere(100d, new Point(-100, 100, 300)).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(30).setKt(0.6)),//
                new Sphere(100, new Point(100, -200, 400)).setEmission(new Color(RED)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10).setKt(0.8)),//
                new Sphere(50d, new Point(200, 100, 800)).setEmission(new Color(GREEN)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(20).setKt(0.3)));

        scene.lights.add(new SpotLight(new Color(700, 400, 400),
                new Point(0, 0, 900), new Vector(0.5, 2.5, -7.5)) //
                .setKl(4E-5).setKq(2E-7));

        ImageWriter imageWriter = new ImageWriter("allFeature", 2000, 2000);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }


}
