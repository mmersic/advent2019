package com.mersic.day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Main {

    public static int[][] getInitialPositions(String filename) throws Exception {
        FileReader fr = new FileReader(filename);
        BufferedReader br = new BufferedReader(fr);

        String inputString = null;
        List<int[]> iniitialPositionList = new ArrayList<>();
        while ((inputString = br.readLine()) != null) {
            inputString = inputString.replace('<', ' ');
            inputString = inputString.replace(',', ' ');
            inputString = inputString.replace('>', ' ');
            inputString = inputString.replace('x', ' ');
            inputString = inputString.replace('y', ' ');
            inputString = inputString.replace('z', ' ');
            inputString = inputString.replace('=', ' ');
            System.out.println("inputString: "+ inputString);
            String[] parts = inputString.split(" ");
            int P[] = new int[3];
            int counter = 0;
            for (String p : parts) {
                String t = p.trim();
                if (!t.equals(""))
                    P[counter++] = Integer.parseInt(p);
            }
            iniitialPositionList.add(P);
        }

        int[][] P = new int[iniitialPositionList.size()][3];
        int counter = 0;
        for (int[] p : iniitialPositionList) {
            P[counter++] = p;
        }
        return P;
    }

    public static void updateVelocity(int[][] P, int[][] V) {
        for (int i = 0; i < P.length; i++) {
            for (int j = i+1; j < P.length; j++) {
                for (int k = 2; k < 3; k++) {
                    if (P[i][k] > P[j][k]) {
                        V[i][k]--;
                        V[j][k]++;
                    } else if (P[i][k] < P[j][k]) {
                            V[i][k]++;
                            V[j][k]--;
                    } else {
                        continue;
                    }
                }
            }
        }
    }

    public static void updatePositions(int[][] P, int[][] V) {
        for (int i = 0; i < P.length; i++) {
//            P[i][0] += V[i][0];
//            P[i][1] += V[i][1];
            P[i][2] += V[i][2];
        }
    }

    public static void timeStep(int[][] P, int[][] V) {
        updateVelocity(P, V);
        updatePositions(P, V);
    }

    public static int calcTotalEnergy(int[][] P, int[][] V) {
        int totalEnergy = 0;
        for (int i = 0; i < P.length; i++) {
            int pot = 0;
            int kin = 0;
            for (int j = 0; j < 3; j++) {
                pot += Math.abs(P[i][j]);
                kin += Math.abs(V[i][j]);
            }
            totalEnergy += (pot * kin);
        }

        return totalEnergy;
    }

    public static boolean areInitial(int[][] A, int[][] B, int[][] V) {
        if (V[0][0] != 0 || V[0][1] != 0 || V[0][2] != 0) {
            return false;
        }
        if (V[1][0] != 0 || V[1][1] != 0 || V[1][2] != 0) {
            return false;
        }
        if (V[2][0] != 0 || V[2][1] != 0 || V[2][2] != 0) {
            return false;
        }
        if (V[3][0] != 0 || V[3][1] != 0 || V[3][2] != 0) {
            return false;
        }
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                if (A[i][j] != B[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    //part 1 - 6678
    public static void main(String args[]) throws Exception {
        int[][] P = getInitialPositions(args[0]);
        int[][] copyInitialP = new int[P.length][P[0].length];
        for (int i = 0; i < P.length; i++) {
            for (int j = 0; j < P[0].length; j++) {
                copyInitialP[i][j] = P[i][j];
            }
        }
        int[][] V = new int[P.length][3];

        for (int[] p : P) {
            System.out.println("x: " + p[0] + " y: " + p[1] + " z: " + p[2]);
        }

        printpv(0, P, V);
        long timeStep = 0;
        int initialEnergy = calcTotalEnergy(P, V);
        System.out.println("initial total energy: " + initialEnergy);
        //2028, 5898, 4702
        for (int i = 0; i < 1000000; i++) {
            timeStep(P, V);
            timeStep++;
            if (areInitial(P, copyInitialP, V)) {
                System.out.println("equal at timeStep: " + timeStep);
                printpv(timeStep, P, V);
            }
        }
        System.out.println(MathHelper.lcm(167624, 231614, 102356));
        //System.out.println("total energy: " + calcTotalEnergy(P,V));
    }

    public static void printpv(long timestep, int[][] P, int[][] V) {
        System.out.println("After " + timestep + " steps.");
        for (int i = 0; i < P.length; i++) {
            System.out.println("pos=<x= " + P[i][0] + ", y= " + P[i][1] + ", z= " + P[i][2]
                         + ">, vel=<x= "  + V[i][0] + ", y= " + V[i][1] + ", z= " + V[i][2] + ">");
        }
    }
}
