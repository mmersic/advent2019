package com.mersic.day7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;

public class Main {

    //file d5.input
    public static void dayFiveTestCase(int[] originalData) {
        //Use with input file from day 5, d5.input.
        IOHelper ioHelper = new IOHelperStack(new MySemaphore());
        ioHelper.write("5");
        IntCodeComputer computer = new IntCodeComputer(originalData, ioHelper, ioHelper);
        computer.run();
        String val = ioHelper.read();
        if ("11189491".equals(val)) {
            System.out.println("Correct value returned, 11189491");
        } else {
            System.out.println("result: " + val + " does not match expected value: 11189491");
        }
    }

    //file d7.p1.input
    public static void daySevenPartOneTestCase(int[] originalData) {
        int val = PartOneOptimizer.findMax(originalData);
        //day 7 part1: 116680

        if ("116680".equals(""+val)) {
            System.out.println("Correct value returned, 116680");
        } else {
            System.out.println("result: " + val + " does not match expected value: 116680");
        }
    }

    //file d7.p1.input
    public static void daySevenPartTwoTestCase(int[] originalData) {
        int val = PartTwoOptimizer.findMax(originalData);

        //day 7 part2: 89603079

        if ("89603079".equals(""+val)) {
            System.out.println("Correct value returned, 89603079");
        } else {
            System.out.println("result: " + val + " does not match expected value: 89603079");
        }
    }
    public static void main(String[] args) throws Exception {
        FileReader fr = new FileReader(args[0]);
        BufferedReader br = new BufferedReader(fr);

        BufferedReader input = new BufferedReader (new InputStreamReader(System.in));
        String inputString = br.readLine();

        String[] parts = inputString.split(",");
        int[] originalData = new int[parts.length];
        for (int i = 0; i < originalData.length; i++) {
            originalData[i] = Integer.parseInt(parts[i]);
        }

        //dayFiveTestCase(originalData);
        //daySevenPartOneTestCase(originalData);
        daySevenPartTwoTestCase(originalData);


    }
}
