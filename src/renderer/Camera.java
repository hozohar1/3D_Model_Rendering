package renderer;


import geometries.Plane;
import primitives.*;
import primitives.Color;
import primitives.Point;

import java.awt.*;
import java.util.LinkedList;
import java.util.MissingResourceException;

import static java.lang.Math.sqrt;
import static java.lang.System.out;
/**
 * Camera class represents the camera through which we see the scene.
 *  @author hodaya zohar && shoham shervi
 */
public class Camera {

    /**
     * The point of view of the camera.
     */
    private Point p0;

    //The directions of the camera:
    /**
     * vUp - The "up" direction in the camera.
     */
    private Vector vUp;

    /**
     * vTo - The "to" direction in the camera, where the scene is.
     */
    private Vector vTo;

    /**
     * vRight - The "right" direction in the camera.
     */
    private Vector vRight;

    // The attributes of the view plane:
    /**
     * The width of the view plane.
     */
    private double width;

    /**
     * The height of the view plane.
     */
    private double height;

    /**
     * The distance between the p0 and the view plane (in the direction of vTo).
     */
    private double distance;

    private ImageWriter imageWriter;

    private RayTracerBase rayTracer;
    /** Depth Of Filed properties. **/

    /**
     * A boolean variable that determines whether to use depth of filed.
     */
    private boolean depthOfFiled = false;

    /** Aperture properties. **/

    /**
     * number with integer square for the matrix of points.
     */
    private int APERTURE_NUMBER_OF_POINTS = 100;

    /**
     * Declaring a variable called apertureSize of type double.
     */
    private double apertureSize;
    /**
     * Creating an array of Point objects.
     */
    private Point[] aperturePoints;
    /**
     * Declaring a variable called FOCAL_PLANE of type Plane.
     */
    /** Focal plane parameters. **/

    /**
     * as instructed it is a constant value of the class.
     */
    private double FP_distance;

    private Plane FOCAL_PLANE;
    /**
     * Constructs an instance of Camera with point and to and up vectors.
     *
     * @param p0  The point of view of the camera.
     * @param vTo The "to" direction of the camera, where the scene is.
     * @param vUp The "up" direction of the camera.
     */
    public Camera(Point p0, Vector vTo, Vector vUp) {
        if (!(vUp.dotProduct(vTo) == 0))
            throw new IllegalArgumentException("vTo and vUp have to be orthogonal!!!");
        this.p0 = p0;
        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();
        this.vRight = vTo.crossProduct(vUp).normalize();
    }

    /**
     * get the width of the view plane
     *
     * @return The width from the view plane.
     */
    public double getWidth() {
        return width;
    }

    /**
     * get the height of the view plane
     *
     * @return The height from the view plane.
     */
    public double getHeight() {
        return height;
    }

    /**
     * get the distance from the p0 to the view plane
     *
     * @return The distance from the p0 to the view plane.
     */
    public double getDistance() {
        return distance;
    }

    /**
     * init the image writer
     *
     * @param imageWriter The imageWriter to set.
     * @return The current instance (Builder pattern).
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * init the ray tracer
     *
     * @param rayTracer The rayTracer to set.
     * @return The current instance (Builder pattern).
     */
    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    /**
     * init the view plane by the width and height
     *
     * @param width  The number to set as the view plane's width.
     * @param height The number to set as the view plane's height.
     * @return The current instance (Builder pattern).
     */
    public Camera setViewPlaneSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * init the distance of the view plane
     *
     * @param distance The number to set as the distance between the p0 and the view plane.
     * @return The current instance (Builder pattern).
     */
    public Camera setViewPlaneDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * Creates a ray that goes through a given pixel
     *
     * @param nX number of pixels on X axis in the view plane
     * @param nY number of pixels on Y axis in the view plane
     * @param j  X coordinate of the pixel
     * @param i  Y coordinate of the pixel
     * @return The ray from the camera to the pixel
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
        Point imgCenter = p0.add(vTo.scale(distance));
        double rY = height / nY, rX = width / nX;
        double iY = -(i - (nY - 1d) / 2) * rY, jX = (j - (nX - 1d) / 2) * rX;
        Point ijP = imgCenter;
        if (jX != 0) ijP = ijP.add(vRight.scale(jX));
        if (iY != 0) ijP = ijP.add(vUp.scale(iY));
        Vector ijV = ijP.subtract(p0);
        return new Ray(p0, ijV);
    }

    private final String RESOURCE = "Renderer resource not set";
    private final String CAMERA_CLASS = "Camera";
    private final String IMAGE_WRITER = "Image writer";
    private final String CAMERA = "Camera";
    private final String RAY_TRACER = "Ray tracer";

    /**
     * the method check if all the fields are set
     *
     * @return The current instance (Builder pattern).
     */
    public Camera renderImage() {
        if (imageWriter == null)
            throw new MissingResourceException(RESOURCE, CAMERA_CLASS, IMAGE_WRITER);
        if (p0 == null || vTo == null || vUp == null || vRight == null || width == 0 || height == 0 || distance == 0)
            throw new MissingResourceException(RESOURCE, CAMERA_CLASS, CAMERA);
        if (rayTracer == null)
            throw new MissingResourceException(RESOURCE, CAMERA_CLASS, RAY_TRACER);

        final int nX = imageWriter.getNx();
        final int nY = imageWriter.getNy();
        for (int i = 0; i < nY; ++i)
            for (int j = 0; j < nX; ++j)
                this.imageWriter.writePixel(j, i, castRay(nX, nY, j, i));
        return this;
    }


    /**
     * Given a pixel's coordinates, construct a ray and trace it through the scene
     *
     * @param nX The amount of columns (row width) of the pixel in the image.
     * @param nY The amount of rows (column height) of the pixel in the image.
     * @param j  The column of the pixel in the image.
     * @param i  The row of the pixel in the image.
     * @return The color of the pixel.
     */
    private Color castRay(int nX, int nY, int j, int i) {
        Ray ray = this.constructRayThroughPixel(nX, nY, j, i);
        if (depthOfFiled) // if there is the improvement of depth of filed
            return averagedBeamColor(ray);

        return this.rayTracer.traceRay(ray);
    }


    /**
     * Create a grid [over the picture] in the pixel color map. given the grid's
     * step and color.
     *
     * @param interval grid's interval
     * @param color    grid's color
     */
    public void printGrid(int interval, Color color) {
        if (imageWriter == null)
            throw new MissingResourceException(RESOURCE, CAMERA_CLASS, IMAGE_WRITER);

        final int nX = imageWriter.getNx();
        final int nY = imageWriter.getNy();

        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                if (i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(j, i, color);
                }
            }
        }
    }

    /**
     * Produce a rendered image file
     */
    public void writeToImage() {
        if (imageWriter == null)
            throw new MissingResourceException(RESOURCE, CAMERA, IMAGE_WRITER);

        imageWriter.writeToImage();
    }
    /** Depth Of Filed improvements **/

    /**
     * This function sets the depth of field to the value of the parameter.
     *
     * @param depthOfFiled If true, the camera will have a depth of field effect.
     */
    public Camera setDepthOfFiled(boolean depthOfFiled) {
        this.depthOfFiled = depthOfFiled;
        return this;
    }
    /**
     * It takes a ray, finds the point where it intersects the focal plane, and then shoots rays from the aperture points
     * to that point. It then averages the colors of all the rays
     *
     * @param ray The ray that is being traced.
     * @return The average color of the image.
     */
    private Color averagedBeamColor(Ray ray) {
        Color averageColor = Color.BLACK, apertureColor;
        int numOfPoints = this.aperturePoints.length;
        Ray apertureRay;
        Point focalPoint = this.FOCAL_PLANE.findGeoIntersections(ray).get(0).point;
        for (Point aperturePoint : this.aperturePoints) {
            apertureRay = new Ray(aperturePoint, focalPoint.subtract(aperturePoint));
            apertureColor = rayTracer.traceRay(apertureRay);
            averageColor = averageColor.add(apertureColor.reduce(numOfPoints));
        }
        return averageColor;
    }
    /**
     * The function sets the distance of the focal plane from the camera's position
     *
     * @param distance The distance from the camera to the focal plane.
     * @return The camera itself.
     */
    public Camera setFPDistance(double distance) {
        this.FP_distance = distance;
        this.FOCAL_PLANE = new Plane(this.p0.add(this.vTo.scale(FP_distance)), this.vTo);
        return this;
    }
    /**
     * This function sets the aperture size of the camera and initializes the points of the aperture.
     *
     * @param size the size of the aperture.
     * @return The camera object itself.
     */
    public Camera setApertureSize(double size) {
        this.apertureSize = size;

        //initializing the points of the aperture.
        if (size != 0) initializeAperturePoint();

        return this;
    }
    /**
     * The function initializes the aperture points array by calculating the distance between the points and the initial
     * point, and then initializing the array with the points
     */
    private void initializeAperturePoint() {
        //the number of points in a row
        int pointsInRow = (int) sqrt(this.APERTURE_NUMBER_OF_POINTS);

        //the array of point saved as an array
        this.aperturePoints = new Point[pointsInRow * pointsInRow];

        //calculating the initial values.
        double pointsDistance = (this.apertureSize * 2) / pointsInRow;
        //calculate the initial point to be the point with coordinates outside the aperture in the down left point, so we won`t have to deal with illegal vectors.
        Point initialPoint = this.p0
                .add(this.vUp.scale(-this.apertureSize - pointsDistance / 2)
                        .add(this.vRight.scale(-this.apertureSize - pointsDistance / 2)));

        //initializing the points array
        for (int i = 1; i <= pointsInRow; i++) {
            for (int j = 1; j <= pointsInRow; j++) {
                this.aperturePoints[(i - 1) + (j - 1) * pointsInRow] = initialPoint
                        .add(this.vUp.scale(i * pointsDistance).add(this.vRight.scale(j * pointsDistance)));
            }
        }
    }
}
