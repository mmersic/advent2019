package com.mersic.day3.part2.failed.PB6X;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    List<LineSeg> lineSegs = new ArrayList<>();

    public void addLineSeg(LineSeg l) {
        this.lineSegs.add(l);
    }
    public List<LineSeg> getLineSegs() { return this.lineSegs; }

    public void splitIntersections(List<Node> intersections) {
        for (Node n : intersections) {
            List<LineSeg> discardSegs = new ArrayList<>();
            List<LineSeg> newSegs = new ArrayList();
            for (LineSeg l : lineSegs) {
                if (l.n1.x == l.n2.x && l.n1.x == n.x && ((l.n1.y < n.y && l.n2.y > n.y) || (l.n2.y < n.y && l.n1.y > n.y))) {
//                    System.out.println("splitting: " + l);
                    Node n1 = new Node(n.x, l.n1.y);
                    Node n2 = new Node(n.x, l.n2.y);
                    LineSeg l1 = new LineSeg(n, n1);
                    LineSeg l2 = new LineSeg(n, n2);
//                    System.out.println("into: " + l1 + " and: " + l2);
                    newSegs.add(l1);
                    newSegs.add(l2);
                    discardSegs.add(l);
                } else if (l.n1.y == l.n2.y && l.n2.y == n.y && ((l.n1.x < n.x && l.n2.x > n.x) || (l.n2.x < n.x && l.n1.x > n.x))) {
//                    System.out.println("splitting: " + l);
                    Node n1 = new Node(l.n1.x, n.y);
                    Node n2 = new Node(l.n2.x, n.y);
                    LineSeg l1 = new LineSeg(n, n1);
                    LineSeg l2 = new LineSeg(n, n2);
//                    System.out.println("into: " + l1 + " and: " + l2);
                    newSegs.add(l1);
                    newSegs.add(l2);
                    discardSegs.add(l);
                }
            }
            this.lineSegs.removeAll(discardSegs);
            this.lineSegs.addAll(newSegs);
        }
    }

    public void resetDist() {
        for (LineSeg l : lineSegs) {
            l.n1.dist = -1;
            l.n2.dist = -1;
            if (l.n1.x == 0 && l.n1.y == 0) {
                l.n1.dist = 0;
            }
            if (l.n2.x == 0 && l.n2.y == 0) {
                l.n2.dist = 0;
            }
        }
    }
}
