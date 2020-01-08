package com.mersic.day13;

import java.io.BufferedReader;
import java.io.FileReader;

public class Main {

    //376
    private static void day13PartOne(long[] originalData) {
        ArcadeIOHelper arcadeIOHelper = new ArcadeIOHelper();
        IntCodeComputer intCodeComputer = new IntCodeComputer(originalData, arcadeIOHelper, arcadeIOHelper, 10000);
        intCodeComputer.run();
        System.out.println("blocks count: " + arcadeIOHelper.blocksCount());

    }

    //18509
    private static void day13PartTwo(long[] originalData) {
        ArcadeIOHelper arcadeIOHelper = new ArcadeIOHelper();
        originalData[0] = 2;
        IntCodeComputer intCodeComputer = new IntCodeComputer(originalData, arcadeIOHelper, arcadeIOHelper, 10000);
        intCodeComputer.run();
        System.out.println("blocks count: " + arcadeIOHelper.blocksCount());

    }

    public static void main(String[] args) throws Exception {
        FileReader fr = new FileReader(args[0]);
        BufferedReader br = new BufferedReader(fr);

        String inputString = br.readLine();

        String[] parts = inputString.split(",");
        long[] originalData = new long[parts.length];
        for (int i = 0; i < originalData.length; i++) {
            originalData[i] = Long.parseLong(parts[i]);
        }

        day13PartTwo(originalData);
    }

}
