package unittests.renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;
import renderer.*;


public class ImageWriterTest {

    @Test
    void testWriteToImage() {

        ImageWriter imageWriter = new ImageWriter("testImage", 800, 500);

        Color yellow = new Color(255d, 255d, 0d);
        Color red = new Color(255d, 0d, 0d);

        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                if (i % 50 == 0 || j % 50 == 0) {
                    imageWriter.writePixel(i, j, red);
                } else {
                    imageWriter.writePixel(i, j, yellow);
                }
            }
        }
        imageWriter.writeToImage();
    }

}