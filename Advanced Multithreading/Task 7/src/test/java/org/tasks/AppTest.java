package org.tasks;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    @Test
    public void testForkBlurCorrectness() {
        int[] src = { 0x00ff0000, 0x0000ff00, 0x000000ff, 0x00ff00ff, 0x0000ffff };
        int[] dst = new int[src.length];
        ForkBlur task = new ForkBlur(src, 0, src.length, dst);

        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(task);

        // Direct blur calculation
        int[] expected = new int[src.length];
        int sidePixels = 16;
        for (int i = 0; i < src.length; i++) {
            float rt = 0, gt = 0, bt = 0;
            for (int j = -sidePixels; j <= sidePixels; j++) {
                int idx = Math.min(Math.max(j + i, 0), src.length - 1);
                int pixel = src[idx];
                rt += (float) ((pixel & 0x00ff0000) >> 16) / (sidePixels * 2 + 1);
                gt += (float) ((pixel & 0x0000ff00) >> 8) / (sidePixels * 2 + 1);
                bt += (float) ((pixel & 0x000000ff)) / (sidePixels * 2 + 1);
            }
            int dp = (0xff000000) | (((int) rt) << 16) | (((int) gt) << 8) | (((int) bt));
            expected[i] = dp;
        }

        assertArrayEquals(expected, dst);
    }
}
