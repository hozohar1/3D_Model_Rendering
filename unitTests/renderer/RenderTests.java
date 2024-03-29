package renderer;

import geometries.Geometry;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;
import scene.SceneBuilder;

import static java.awt.Color.*;
import javax.xml.parsers.ParserConfigurationException;
/**
 * Test rendering a basic image
 *
 * @author Dan
 */
public class RenderTests {

    /**
     * Produce a scene with basic 3D model and render it into a png image with a
     * grid
     */
    @Test
    public void basicRenderTwoColorTest() {
        Scene scene = new Scene("Test scene")//
                .setAmbientLight(new AmbientLight(new Color(255, 191, 191), //
                        new Double3(1, 1, 1))) //
                .setBackground(new Color(75, 127, 90));

        scene.geometries.add(new Sphere(50d, new Point(0, 0, -100)),
                new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100)), // up
                // left
                new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100),
                        new Point(-100, -100, -100)), // down
                // left
                new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100))); // down
        // right
        Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneDistance(100) //
                .setViewPlaneSize(500, 500) //
                .setImageWriter(new ImageWriter("base render test", 1000, 1000))
                .setRayTracer(new RayTracerBasic(scene));

        camera.renderImage();
        camera.printGrid(100, new Color(YELLOW));
        camera.writeToImage();
    }

    @Test
    public void basicRenderMultiColorTest() {
        Scene scene = new Scene("Test scene")//
                .setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.2))); //

        scene.geometries.add( // center
                new Sphere(50, new Point(0, 0, -100)),
                // up left
                new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100))
                        .setEmission(new Color(GREEN)),
                // down left
                new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100), new Point(-100, -100, -100))
                        .setEmission(new Color(RED)),
                // down right
                new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100))
                        .setEmission(new Color(BLUE)));

        Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneDistance(100) //
                .setViewPlaneSize(500, 500) //
                .setImageWriter(new ImageWriter("color render test", 1000, 1000))
                .setRayTracer(new RayTracerBasic(scene));

        camera.renderImage();
        camera.printGrid(100, new Color(WHITE));
        camera.writeToImage();
    }
////    /** Test for XML based scene - for bonus
//  @Test public void basicRenderXml() {
//   Scene  scene  = new Scene("XML Test scene");
////     // enter XML file name and parse from XML file into scene object
////     // using the code you added in appropriate packages
////     // ...
////     // NB: unit tests is not the correct place to put XML parsing code
//
//     Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0))     //
//     .setViewPlaneDistance(100)                                                                //
//     .setViewPlaneSize(500, 500).setImageWriter(new ImageWriter("xml render test", 1000, 1000))
//    .setRayTracer(new RayTracerBasic(scene));
//    camera.renderImage();
//    camera.printGrid(100, new Color(YELLOW));
//    camera.writeToImage();
//   }
    /**
     * Test for XML based scene - for bonus
     *
     * @throws ParserConfigurationException
     */
//    @Test
//    public void basicRenderXml() throws Exception {
//        Scene scene = new Scene("XML Test scene");
//        // enter XML file name and parse from XML file into scene object
//        // ...
//        try {
//            scene.setXml(System.getProperty("user.dir") + "/xml/basicRenderTestTwoColors.xml");
//        } catch (Exception e) {
//            throw new Exception("cannot convert the xml file");
//        }
//        Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)) //
//                .setViewPlaneDistance(100) //
//                .setViewPlaneSize(500d, 500d).setImageWriter(new ImageWriter("xml render test", 1000, 1000))
//                .setRayTracer(new RayTracerBasic(scene));
//        camera.renderImage();
//        camera.printGrid(100, new Color(YELLOW));
//        camera.writeToImage();
//    }
    // For stage 6 - please disregard in stage 5


    // For stage 6 - please disregard in stage 5




    /**
     * Test for XML based scene - for bonus
     */
    @Test
    public void basicRenderXml() {
        Scene scene = new Scene("XML Test scene");
        SceneBuilder sceneBuilder = new SceneBuilder(scene);
        sceneBuilder.loadSceneFromFile("basicRenderTestTwoColors.xml");
        Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneDistance(100) //
                .setViewPlaneSize(500, 500)
                .setImageWriter(new ImageWriter("xml render test", 1000, 1000))
                .setRayTracer(new RayTracerBasic(scene));
        camera.renderImage();
        camera.printGrid(100, new Color(java.awt.Color.YELLOW));
        camera.writeToImage();
    }
    /** Produce a scene with basic 3D model - including individual lights of the
     * bodies and render it into a png image with a grid */
    @Test
    void testDepthOfField() {

        Scene scene = new Scene("Test scene");//.setCBR();

        // Create an array of spheres
        Geometry[] spheres = new Sphere[10];

        // Add spheres to the array
        for (int i = 0; i < 10; i++) {
            spheres[i] = new Sphere(3,new Point(10.0 - i * 8, 0.0, 800.0 - 100 * i)) //
                    .setEmission(new Color(236,71,233).reduce(2)) //
                    .setMaterial(new Material().setKd(new Double3(0.3)).setKs(new Double3(0.2)).setShininess(300).setKr(new Double3(0.5)));
        }
        // Create another set of spheres
        Geometry[] spheres2 = new Sphere[10];

        // Add spheres to the second array
        for (int i = 0; i < 10; i++) {
            spheres2[i] = new Sphere(3,new Point(20.0 - i * 8, 10.0, 900.0 - 100 * i)) //
                    .setEmission(new Color(31,190,114).reduce(2)) //
                    .setMaterial(new Material().setKd(new Double3(0.3)).setKs(new Double3(0.2)).setShininess(300).setKr(new Double3(0.5)));
        }

        Geometry polygon = new Polygon(
                new Point(100.0, -50.0, 1000.0),
                new Point(-100.0, -50.0, 1000.0),
                new Point(-100.0, -50.0, -100.0),
                new Point(100.0, -50.0, -100.0))
                .setEmission(new Color(gray))
                .setMaterial(new Material().setKd(new Double3(0.2)).setKs(new Double3(0.3)).setShininess(300).setKr(new Double3(0.5)));

        Camera camera = new Camera(new Point(0.0, 0.0, 1000.0), new Vector(0.0, 0.0, -1.0), new Vector(0.0, 1.0, 0.0)) //
                .setViewPlaneSize(150, 150) //
                .setViewPlaneDistance(1000)
                .setDepthOfFiled(true).setNumOfPoints(100)
                .setFPDistance(500)
                .setApertureSize(1);

        scene.geometries.add(spheres);
        scene.geometries.add(spheres2);
        scene.geometries.add(polygon);
        scene.lights.add(new DirectionalLight(new Color(800, 500, 0), new Vector(1.0, -1.0, -0.5)));
        scene.lights.add(new SpotLight(new Color(0, 255, 0), new Point(100.0, 100.0, 800.0), new Vector(-1.0, -1.0, 0.0)).setNarrowBeam(10));
        //scene.setBVH();

        ImageWriter imageWriter = new ImageWriter("lightSphereDirectionalDepthOfFieldTesting1", 500, 500);
        camera.setImageWriter(imageWriter)
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .setDebugPrint(0.2) //
                .setMultithreading(4) //
                .writeToImage();
    }



}


