package com.mersic.day7;

import java.util.Arrays;

public class PartOneOptimizer {
    public static boolean validPhaseSetting(int i, int j, int k, int l, int m) {
        if (i == j || i == k || i == l || i == m) {
            return false;
        }
        if (j == k || j == l || j == m) {
            return false;
        }
        if (k == l || k == m) {
            return false;
        }
        if (l == m) {
            return false;
        }
        return true;
    }

    public static int findMax(int[] originalData) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 5; k++) {
                    for (int l = 0; l < 5; l++) {
                        for (int m = 0; m < 5; m++) {
                            if (!validPhaseSetting(i, j, k, l, m)) {
                                continue;
                            }
                            IOHelper ioHelper = new IOHelperStack(new MySemaphore());
                            int[]
                                    data = Arrays.copyOf(originalData, originalData.length);
                            ioHelper.write("0");
                            ioHelper.write("" + i);
                            IntCodeComputer.compute(data, ioHelper, ioHelper);
                            data = Arrays.copyOf(originalData, originalData.length);
                            ioHelper.write("" + j);
                            IntCodeComputer.compute(data, ioHelper, ioHelper);
                            data = Arrays.copyOf(originalData, originalData.length);
                            ioHelper.write("" + k);
                            IntCodeComputer.compute(data, ioHelper, ioHelper);
                            data = Arrays.copyOf(originalData, originalData.length);
                            ioHelper.write("" + l);
                            IntCodeComputer.compute(data, ioHelper, ioHelper);
                            data = Arrays.copyOf(originalData, originalData.length);
                            ioHelper.write("" + m);
                            IntCodeComputer.compute(data, ioHelper, ioHelper);

                            int currentVal = Integer.parseInt(ioHelper.read());
                            if (currentVal > max) {
                                max = currentVal;
                                System.out.println("new max: " + max + " for phase: " + i + " " + j + " " + k + " " + l + " " + m);
                            }
                        }
                    }
                }
            }
        }
        return max;
    }
}
