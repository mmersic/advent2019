package com.mersic.day11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main {

    //file d11.input
    public static void dayElevenPartOneTestCase(long[] originalData) {
        IOHelperRobot ioHelperRobot = new IOHelperRobot();
        IntCodeComputer computer = new IntCodeComputer(originalData, ioHelperRobot, ioHelperRobot, 4000);
        computer.run();
        String val = ""+ioHelperRobot.getSquaresPainted();
        if ("2322".equals(val)) {
            System.out.println("Correct value returned, 2322");
        } else {
            System.out.println("result: " + val + " does not match expected value: 77944");
        }
    }

    //file d11.input
    public static void dayElevenPartTwoTestCase(long[] originalData) {
        Map<String, String> stateSpace = new HashMap<>();
        stateSpace.put("(0,0)", "1");
        IOHelperRobot ioHelperRobot = new IOHelperRobot(stateSpace);
        IntCodeComputer computer = new IntCodeComputer(originalData, ioHelperRobot, ioHelperRobot, 4000);
        computer.run();
        String val = ""+ioHelperRobot.getSquaresPainted();

        //JHARBGCU
        for (int y = 0; y >- 10; y--) {
            for (int x = 0; x <= 40; x++) {
                String key = "(" + x + "," + y + ")";
                val = stateSpace.get(key);
                if (val == null || val.equals("0")) {
                    System.out.print(" ");
                } else if (val.equals("1")) {
                    System.out.print("O");
                } else {
                    throw new RuntimeException("Invalid paint color: " + val);
                }
            }
            System.out.println();
        }
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

        //dayElevenPartOneTestCase(originalData);
        dayElevenPartTwoTestCase(originalData);


    }
}


