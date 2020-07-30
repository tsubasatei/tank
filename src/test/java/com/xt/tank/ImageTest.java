package com.xt.tank;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class ImageTest {
    @Test
    public void test () {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(ImageTest.class.getClassLoader().getResourceAsStream("images/0.gif"));
            assertNotNull(bufferedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
