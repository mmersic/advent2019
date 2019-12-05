package com.mersic.day3.part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

class LineSeg {
    public int x1;
    public int y1;
    public int x2;
    public int y2;
    public LineSeg(int x1, int y1, int x2, int y2) {
        if (x1 == x2) {
            if (y1 > y2) {
                this.x1 = x1;
                this.y1 = y1;
                this.x2 = x2;
                this.y2 = y2;
            } else {
                this.x1 = x2;
                this.y1 = y2;
                this.x2 = x1;
                this.y2 = y1;
            }
        } else {
            if (x1 < x2) {
                this.x1 = x1;
                this.y1 = y1;
                this.x2 = x2;
                this.y2 = y2;
            } else {
                this.x1 = x2;
                this.y1 = y2;
                this.x2 = x1;
                this.y2 = y1;
            }
        }
    }

    public String toString() {
        return "(" + x1 + "," + y1 + ")->(" + x2 + "," + y2 + ")";
    }
}

public class PB_5 {


    public static int distanceToIntersection(LineSeg l1, LineSeg l2) {
        if (l1.x1 == l1.x2 && l2.x1 == l2.x2) {
            return -1;
        }
        if (l1.y1 == l1.y2 && l2.y1 == l2.y2) {
            return -1;
        }

        if (l1.y1 != l1.y2) {
            LineSeg temp = l1;
            l1 = l2;
            l2 = temp;
        }

        if (l1.y1 <= l2.y1 && l1.y1 >= l2.y2 && l1.x1 <= l2.x2 && l1.x2 >= l2.x2) {
            int x = l2.x1;
            int y = l1.y1;
            if (x < 0) { x *= -1; }
            if (y < 0) { y *= -1; }
            System.out.println("intersecting d: " + (x+y) + " for " + l1 + " and " + l2 + " at: (" + l2.x2 + ", " + l1.y1 + ")");
            return x+y;
        }
        return -1;
    }


    public static void main(String[] args) throws Exception {
//        LineSeg r1 = new LineSeg(107, 11, 147, 11);
//        LineSeg r2 = new LineSeg(124, 47, 124, -16);
//        System.out.println("d: " + distanceToIntersection(r1, r2));
//        System.exit(0);
        FileReader fr = new FileReader(args[0]);
        BufferedReader br = new BufferedReader(fr);
        List<List<LineSeg>> setList = new ArrayList<List<LineSeg>>();
        String nextLine = null;

        while ((nextLine = br.readLine()) != null) {
            List<LineSeg> lineSegs = new ArrayList<LineSeg>();
            String[] moves = nextLine.split(",");
            int x1 = 0;
            int y1 = 0;
            int x2 = 0;
            int y2 = 0;
            for (String m : moves) {
                char opcode = m.charAt(0);
                int d = Integer.parseInt(m.substring(1));
                switch (opcode) {
                    case 'R' : x2 = x1 + d; break;
                    case 'L' : x2 = x1 - d; break;
                    case 'U' : y2 = y1 + d; break;
                    case 'D' : y2 = y1 - d; break;
                    default : throw new RuntimeException("opcode error: " + opcode);
                }
                lineSegs.add(new LineSeg(x1,y1,x2,y2));
                x1 = x2;
                y1 = y2;
            }
            setList.add(lineSegs);
        }

        int closest = 999999999;
        for (int i = 0; i < setList.size(); i++) {
            for (int j = i+1; j < setList.size(); j++) {
                List<LineSeg> s = setList.get(i);
                List<LineSeg> r = setList.get(j);
                for (LineSeg l1 : s) {
                    for (LineSeg l2 : r) {
                        int d = distanceToIntersection(l1,l2);
                        if (d > 0) {
                            System.out.println("intersecting d: " + d + " for " + l1 + " and " + l2);
                        }
                        if (d > 0 && d < closest) {
                            closest = d;
                        }
                    }
                }
            }
        }

        System.out.println("closest: " + closest);

    }
}
