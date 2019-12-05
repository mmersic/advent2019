package com.mersic.day2.part1;

import java.io.BufferedReader;
import java.io.FileReader;

//18:00
public class PB_3 {

    public static void main(String[] args) throws Exception {
        FileReader fr = new FileReader(args[0]);
        BufferedReader br = new BufferedReader(fr);

        String inputString = br.readLine();

        String[] parts = inputString.split(",");
        int[] data = new int[parts.length];
        for (int i = 0; i < data.length; i++) {
            data[i] = Integer.parseInt(parts[i]);
        }

        data[1] = 12;
        data[2] = 2;

        done: for (int i = 0; i < data.length; i+=4) {
            int opcode = data[i];
            switch (opcode) {
                case 1 : data[data[i+3]] = data[data[i+2]] + data[data[i+1]]; break;
                case 2 : data[data[i+3]] = data[data[i+2]] * data[data[i+1]]; break;
                case 99 : break done;
                default: throw new RuntimeException("Error opcode " + opcode + " in position: " + i);
            }
        }

        System.out.println("result: " + data[0]);
    }
}
