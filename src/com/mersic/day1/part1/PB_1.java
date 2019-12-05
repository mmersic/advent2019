package com.mersic.day1.part1;


import java.io.BufferedReader;
import java.io.FileReader;

public class PB_1 {

    public static void main(String[] args) throws Exception {
        FileReader fr = new FileReader(args[0]);
        BufferedReader br = new BufferedReader(fr);

        String nextLine = null;
        int sum = 0;
        while ((nextLine = br.readLine()) != null) {
            int input = Integer.parseInt(nextLine);
            input = input / 3;
            input = input - 2;
            sum += input;
        }

        System.out.println("sum: " + sum);
    }
}
