package com.mersic.day3.part2.failed.PB6X;

public class Node {
    public int x;
    public int y;
    public int dist = -1;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }

    public boolean equals(Object o) {
        Node n = (Node) o;
        return this.x == n.x && this.y == n.y;
    }

    public int hashCode() {
        return this.x^this.y;
    }
}
