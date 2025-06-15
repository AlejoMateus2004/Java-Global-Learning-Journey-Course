package org.tasks;

import java.util.concurrent.RecursiveAction;

public class ForkBlur extends RecursiveAction {
    private static final int THRESHOLD = 10000;
    private final int[] src;
    private final int start;
    private final int length;
    private final int[] dst;

    public ForkBlur(int[] src, int start, int length, int[] dst) {
        this.src = src;
        this.start = start;
        this.length = length;
        this.dst = dst;
    }

    @Override
    protected void compute() {
        if (length < THRESHOLD) {
            blur();
        } else {
            int split = length / 2;
            ForkBlur leftTask = new ForkBlur(src, start, split, dst);
            ForkBlur rightTask = new ForkBlur(src, start + split, length - split, dst);
            invokeAll(leftTask, rightTask);
        }
    }

    private void blur() {
        int sidePixels = 16;
        for (int i = start; i < start + length; i++) {
            float rt = 0, gt = 0, bt = 0;
            for (int j = -sidePixels; j <= sidePixels; j++) {
                int idx = Math.min(Math.max(j + i, 0), src.length - 1);
                int pixel = src[idx];
                rt += (float) ((pixel & 0x00ff0000) >> 16) / (sidePixels * 2 + 1);
                gt += (float) ((pixel & 0x0000ff00) >> 8) / (sidePixels * 2 + 1);
                bt += (float) ((pixel & 0x000000ff)) / (sidePixels * 2 + 1);
            }
            int dp = (0xff000000) | (((int) rt) << 16) | (((int) gt) << 8) | (((int) bt));
            dst[i] = dp;
        }
    }
}