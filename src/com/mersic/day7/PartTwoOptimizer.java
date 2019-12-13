package com.mersic.day7;

import java.util.Arrays;

public class PartTwoOptimizer {



    public static int getValForPhase(int[] originalData, int p1, int p2, int p3, int p4, int p5) {
        IOHelperQueue ioHelperEtoA = new IOHelperQueue(new MySemaphore());
        IOHelperQueue ioHelperAtoB = new IOHelperQueue(new MySemaphore());
        IOHelperQueue ioHelperBtoC = new IOHelperQueue(new MySemaphore());
        IOHelperQueue ioHelperCtoD = new IOHelperQueue(new MySemaphore());
        IOHelperQueue ioHelperDtoE = new IOHelperQueue(new MySemaphore());
        int[] dataA = Arrays.copyOf(originalData, originalData.length);
        int[] dataB = Arrays.copyOf(originalData, originalData.length);
        int[] dataC = Arrays.copyOf(originalData, originalData.length);
        int[] dataD = Arrays.copyOf(originalData, originalData.length);
        int[] dataE = Arrays.copyOf(originalData, originalData.length);
        ioHelperEtoA.write("" + p1);
        ioHelperEtoA.write("0");
        ioHelperAtoB.write("" + p2);
        ioHelperBtoC.write("" + p3);
        ioHelperCtoD.write("" + p4);
        ioHelperDtoE.write("" + p5);

        Thread[] T = new Thread[5];
        T[0] = new Thread(new IntCodeComputer(dataA, ioHelperEtoA, ioHelperAtoB), "IntCodeComputerThread-" + "A");
        T[1] = new Thread(new IntCodeComputer(dataB, ioHelperAtoB, ioHelperBtoC), "IntCodeComputerThread-" + "B");
        T[2] = new Thread(new IntCodeComputer(dataC, ioHelperBtoC, ioHelperCtoD), "IntCodeComputerThread-" + "C");
        T[3] = new Thread(new IntCodeComputer(dataD, ioHelperCtoD, ioHelperDtoE), "IntCodeComputerThread-" + "D");
        T[4] = new Thread(new IntCodeComputer(dataE, ioHelperDtoE, ioHelperEtoA), "IntCodeComputerThread-" + "E");

        for (int i = 0; i < 5; i++) {
            T[i].start();
        }
        for (int i = 0; i < 5; i++) {
            try {
                T[i].join();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        int val = Integer.parseInt(ioHelperEtoA.read());
//        System.out.println("final val: " + val);

        return val;
    }

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

    //d7.p2.ex1.input max val is 139629729 for phase 9 8 7 6 5
    //d7.p2.ex2.input max val is 18216 for phase 9 7 8 5 6
    //d7.p1.input max val is 89603079 for phase  7 6 5 8 9
    public static int findMax(int[] originalData) {
        int max = Integer.MIN_VALUE;
        for (int i = 5; i < 10; i++) {
            for (int j = 5; j < 10; j++) {
                for (int k = 5; k < 10; k++) {
                    for (int l = 5; l < 10; l++) {
                        for (int m = 5; m < 10; m++) {
                            if (!validPhaseSetting(i, j, k, l, m)) {
                                continue;
                            }
                            int[] data = Arrays.copyOf(originalData, originalData.length);
                            int currentVal = getValForPhase(data, i, j, k, l, m);
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
