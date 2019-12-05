package com.mersic.day1.part2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class PB_2 {

    public static void main(String[] args) throws Exception {
        FileReader fr = new FileReader(args[0]);
        BufferedReader br = new BufferedReader(fr);

        FileWriter fw = new FileWriter(args[1]);
        BufferedWriter bw = new BufferedWriter(fw);

        String nextLine = null;
        int sum = 0;
        while ((nextLine = br.readLine()) != null) {
            int input = Integer.parseInt(nextLine);
            input = input / 3;
            input = input - 2;
            sum += input;
            while (true) {
                input /= 3;
                input -= 2;
                if (input < 0) {
                    break;
                }
                sum += input;
            }
        }

        System.out.println("sum: " + sum);
    }
}
