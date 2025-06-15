package org.tasks;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) throws IOException {
        String srcName = "src/main/resources/img.png";
        BufferedImage image = ImageIO.read(new File(srcName));

        int width = image.getWidth();
        int height = image.getHeight();
        int[] src = image.getRGB(0, 0, width, height, null, 0, width);
        int[] dst = new int[src.length];

        ForkBlur fb = new ForkBlur(src, 0, src.length, dst);
        ForkJoinPool pool = new ForkJoinPool();

        long startTime = System.currentTimeMillis();
        pool.invoke(fb);
        long endTime = System.currentTimeMillis();

        System.out.println("Fork/Join task time: " + (endTime - startTime) + " ms");

        BufferedImage dstImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        dstImage.setRGB(0, 0, width, height, dst, 0, width);

        String dstName = "src/main/resources/blurred.png";
        ImageIO.write(dstImage, "png", new File(dstName));
    }
}
