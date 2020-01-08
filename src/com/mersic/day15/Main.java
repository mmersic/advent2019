package com.mersic.day15;

import java.io.BufferedReader;
import java.io.FileReader;

public class Main {

    public static void dayfifteenPartOne(long[] originalData) {


    }

    public static void main(String args[]) throws Exception {
        FileReader fr = new FileReader(args[0]);
        BufferedReader br = new BufferedReader(fr);

        String inputString = br.readLine();

        String[] parts = inputString.split(",");
        long[] originalData = new long[parts.length];
        for (int i = 0; i < originalData.length; i++) {
            originalData[i] = Long.parseLong(parts[i]);
        }

        dayfifteenPartOne(originalData);
    }
}
