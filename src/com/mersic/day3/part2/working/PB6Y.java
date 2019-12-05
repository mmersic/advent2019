package com.mersic.day3.part2.working;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class PB6Y {


    static Map<String, Integer> DX = new HashMap<>();
    static Map<String, Integer> DY = new HashMap<>();
    static {
        DX.put("L", -1);
        DX.put("R", 1);
        DX.put("U", 0);
        DX.put("D", 0);
        DY.put("L", 0);
        DY.put("R", 0);
        DY.put("U", 1);
        DY.put("D", -1);
    }

    public static Map<String,Integer> getPoints(String[] A) {
        int x = 0;
        int y = 0;
        int length = 0;
        Map<String,Integer> ans = new HashMap();

        for (String cmd : A) {
            String d = ""+cmd.charAt(0);
            int  n = Integer.parseInt(cmd.substring(1));
            for (int i = 0; i < n; i++) {
                x += DX.get(d);
                y += DY.get(d);
                length += 1;
                String key = "" + x + "," + y;
                if (!ans.containsKey(key)) {
                    ans.put(key, length);
                }
            }
        }

        return ans;
    }

    static Set<String> intersection(Set<String> A, Set<String> B) {
        return A.stream().filter(B::contains).collect(Collectors.toSet());
    }

    static int minPointSum(Set<String> A) {
        int min = Integer.MAX_VALUE;
        for (String x : A) {
            String[] y = x.split(",");
            int c = Math.abs(Integer.parseInt(y[0])) + Math.abs(Integer.parseInt(y[1]));
            if (c < min) {
                min = c;
            }
        }
        return min;
    }

    private static int minPathSum(Map<String, Integer> pa, Map<String, Integer> pb, Set<String> both) {

        int min = Integer.MAX_VALUE;
        for (String p : both) {
            int c = pa.get(p) + pb.get(p);
            if (c < min) {
                min = c;
            }
        }

        return min;
    }

    public static void main(String args[]) throws Exception {
        FileReader fr = new FileReader(args[0]);
        BufferedReader br = new BufferedReader(fr);

        String[] A = br.readLine().split(",");
        String[] B = br.readLine().split(",");

        Map<String,Integer> PA = getPoints(A);
        Map<String,Integer> PB = getPoints(B);

        Set<String> both = intersection(PA.keySet(), PB.keySet());
        int part1 = minPointSum(both);
        int part2 = minPathSum(PA, PB, both);

        System.out.println("part1: " + part1);
        System.out.println("part2: " + part2);
    }


}
