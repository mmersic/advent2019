package com.mersic.day9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;

public class Main {

    //file d5.input
    public static void dayFiveTestCase(long[] originalData) {
        //Use with input file from day 5, d5.input.
        IOHelper ioHelper = new IOHelperStack(new MySemaphore());
        ioHelper.write("5");
        IntCodeComputer computer = new IntCodeComputer(originalData, ioHelper, ioHelper, 0);
        computer.run();
        String val = ioHelper.read();
        if ("11189491".equals(val)) {
            System.out.println("Correct value returned, 11189491");
        } else {
            System.out.println("result: " + val + " does not match expected value: 11189491");
        }
    }

    //file d9.ex1.input
    public static void dayNineQuineTestCase(long[] originalData) {
        //Use with input file from day 5, d5.input.
        IOHelper ioHelper = new IOHelperStack(new MySemaphore());
        IntCodeComputer computer = new IntCodeComputer(originalData, ioHelper, ioHelper, 86);
        computer.run();
        String val = ioHelper.read();
        if ("99".equals(val)) {
            System.out.println("Correct value returned, 99");
        } else {
            System.out.println("result: " + val + " does not match expected value: 99");
        }
    }

    //file d9.ex2.input
    public static void dayNineBigNumberTestCase(long[] originalData) {
        //Use with input file from day 5, d5.input.
        IOHelper ioHelper = new IOHelperStack(new MySemaphore());
        IntCodeComputer computer = new IntCodeComputer(originalData, ioHelper, ioHelper, 86);
        computer.run();
        String val = ioHelper.read();
        if ("99".equals(val)) {
            System.out.println("Correct value returned, 99");
        } else {
            System.out.println("result: " + val + " does not match expected value: 99");
        }
    }

    //file d9.input
    public static void dayNinePartOneTestCase(long[] originalData) {
        //Use with input file from day 5, d5.input.
        IOHelper ioHelper = new IOHelperStack(new MySemaphore());
        ioHelper.write("1"); //test mode
        IntCodeComputer computer = new IntCodeComputer(originalData, ioHelper, ioHelper, 400);
        computer.run();
        String val = ioHelper.read();
        if ("4261108180".equals(val)) {
            System.out.println("Correct value returned, 4261108180");
        } else {
            System.out.println("result: " + val + " does not match expected value: 4261108180");
        }
    }

    //file d9.input
    public static void dayNinePartTwoTestCase(long[] originalData) {
        //Use with input file from day 5, d5.input.
        IOHelper ioHelper = new IOHelperStack(new MySemaphore());
        ioHelper.write("2"); //boost mode
        IntCodeComputer computer = new IntCodeComputer(originalData, ioHelper, ioHelper, 400);
        computer.run();
        String val = ioHelper.read();
        if ("77944".equals(val)) {
            System.out.println("Correct value returned, 77944");
        } else {
            System.out.println("result: " + val + " does not match expected value: 77944");
        }
    }


    public static void main(String args[]) throws Exception {
        FileReader fr = new FileReader(args[0]);
        BufferedReader br = new BufferedReader(fr);

        BufferedReader input = new BufferedReader (new InputStreamReader(System.in));
        String inputString = br.readLine();

        String[] parts = inputString.split(",");
        long[] originalData = new long[parts.length];
        for (int i = 0; i < originalData.length; i++) {
            originalData[i] = Long.parseLong(parts[i]);
        }

        //dayFiveTestCase(originalData);

        //dayNineQuineTestCase(originalData);

        //dayNineBigNumberTestCase(originalData);

        dayNinePartTwoTestCase(originalData);

    }
}
