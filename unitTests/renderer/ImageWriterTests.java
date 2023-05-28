package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;


import static org.junit.jupiter.api.Assertions.*;

/**
 * A class to test the image writer class.
 *
 *  @author hodaya zohar && shoham shervi
 */
class ImageWriterTest {

    @Test
    void writeToImageTest() {
        ImageWriter imageWriter = new ImageWriter("test", 800, 500);
        for (int i = 0; i < 800; i++)
            for (int j = 0; j < 500; j++) {
                if ((i % 50 == 0) || (j % 50 == 0))
                    imageWriter.writePixel(i, j, new Color(0, 0, 0));
                else imageWriter.writePixel(i, j, new Color(204, 255, 153));

            }

        imageWriter.writeToImage();
    }
}