package com.mersic.day3.part2.failed.PB6;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Node {

    int[] dist = {-1, -1};
    int x;
    int y;
    Set<Integer> wires = new HashSet<>();

    List<Edge> w0 = new ArrayList<Edge>();
    List<Edge> w1 = new ArrayList<Edge>();

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Node) {
            return this.x == ((Node)o).x && this.y == ((Node)o).y;
        }
        return false;
    }



    @Override
    public int hashCode() {
        return x^y;
    }

    public void addNextEdge(Edge e, int wire) {
        if (wire == 0) {
            w0.add(e);
        } else if (wire == 1) {
            w1.add(e);
        }
    }

    public List<Edge> getNextEdges(int wire) {
        if (wire == 0) {
            return w0;
        } else {
            return w1;
        }
    }

    public void addWire(int i) {
        wires.add(i);
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
