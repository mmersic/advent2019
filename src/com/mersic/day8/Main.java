package com.mersic.day8;

import java.io.BufferedReader;
import java.io.FileReader;

public class Main {

    public static void main(String[] args) throws Exception {
        FileReader fr = new FileReader(args[0]);
        BufferedReader br = new BufferedReader(fr);

        String s = br.readLine();

        char[] c = s.toCharArray();
        int layer_width = 25;
        int layer_height = 6;

        int num_1s_times_num2s = Integer.MIN_VALUE;
        int min_zero_count = Integer.MAX_VALUE;
        int i = 0;
        char[][][] layers = new char[c.length/(layer_height*layer_width)][layer_height][layer_width];
        int current_layer = 0;
        while (i < c.length) {
            int current_layer_zero_count = 0;
            int num_one_cnt = 0;
            int num_two_cnt = 0;
            for (int j = 0; j < layer_height; j++) {
                for (int k = 0; k < layer_width; k++) {
                    layers[current_layer][j][k] = c[i];
                    switch (c[i]) {
                        case '0' : current_layer_zero_count++; break;
                        case '1' : num_one_cnt++; break;
                        case '2' : num_two_cnt++; break;
                        default : break;
                    }
                    i++;
                }
            }
            current_layer++;
            if (current_layer_zero_count < min_zero_count) {
                min_zero_count = current_layer_zero_count;
                num_1s_times_num2s = num_one_cnt*num_two_cnt;
            }
        }

        System.out.println("min: " + min_zero_count + " and num1s*num2s: " + num_1s_times_num2s);

        for (i = 0; i < layer_height; i++) {
            for (int j = 0; j < layer_width; j++) {
                next: for (int k = 0; k < layers.length; k++) {
                    switch (layers[k][i][j]) {
                        case '0' : System.out.print(' '); break next;
                        case '1' : System.out.print('*'); break next;
                        case '2' : continue;
                    }
                }
            }
            System.out.println();
        }


    }
}
