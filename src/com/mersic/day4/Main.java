package com.mersic.day4;

public class Main {
    public static void main(String[] args) {
        int[] d = new int[6];
        int part1Count = 0;
        int part2Count = 0;
        for (int i = 245318; i<=765747; i++) {
            int j = 100000;
            int temp = i;
            for (int x = 5; x>= 0; x--) {
                d[x] = temp / j;
                temp = temp % j;
                j/=10;
            }
            if (d[0] >= d[1] && d[1] >= d[2] && d[2] >= d[3] && d[3] >= d[4] && d[4] >= d[5]) {
                //good
            } else {
                continue;
            }
            if (d[0] == d[1] || d[1] == d[2] || d[2] == d[3] || d[3] == d[4] || d[4] == d[5]) {
                //good
            } else {
                continue;
            }
            part1Count++;
            //Additional condition for part2.
            if ((d[0] == d[1] && d[1] != d[2])
                || (d[1] == d[2] && d[0] != d[1] && d[2] != d[3])
                || (d[2] == d[3] && d[1] != d[2] && d[3] != d[4])
                || (d[3] == d[4] && d[2] != d[3] && d[4] != d[5])
                || (d[4] == d[5] && d[3] != d[4])
            ) {
                //good
            } else {
                continue;
            }
            part2Count++;
        }
        System.out.println("part1Count: " + part1Count);
        System.out.println("part2Count: " + part2Count);
    }
}
