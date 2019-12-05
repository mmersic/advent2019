package com.mersic.day2.part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

//8:30
public class PB_4 {
    public static void main(String[] args) throws Exception {
        FileReader fr = new FileReader(args[0]);
        BufferedReader br = new BufferedReader(fr);

        String inputString = br.readLine();

        String[] parts = inputString.split(",");
        int[] original = new int[parts.length];
        for (int i = 0; i < original.length; i++) {
            original[i] = Integer.parseInt(parts[i]);
        }

        complete: for (int noun = 0; noun < 100; noun++) {
            for (int verb = 0; verb < 100; verb++) {
                int[] data = Arrays.copyOf(original, original.length);
                data[1] = noun;
                data[2] = verb;

                done: for (int i = 0; i < data.length; i+=4) {
                    int opcode = data[i];
                    switch (opcode) {
                        case 1 : data[data[i+3]] = data[data[i+2]] + data[data[i+1]]; break;
                        case 2 : data[data[i+3]] = data[data[i+2]] * data[data[i+1]]; break;
                        case 99 : break done;
                        default: throw new RuntimeException("Error opcode " + opcode + " in position: " + i);
                    }
                }
                if (data[0] == 19690720) {
                    System.out.println("noun: " + noun);
                    System.out.println("verb: " + verb);
                    System.out.println("total: " + ((100*noun)+verb));
                    break complete;
                }
            }
        }
    }
}
