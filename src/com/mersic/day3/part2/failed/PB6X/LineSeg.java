package com.mersic.day3.part2.failed.PB6X;

public class LineSeg {
    public Node n1;
    public Node n2;
    public LineSeg(Node n1, Node n2) {
        if (n1.x == n2.x) {
            if (n1.y > n2.y) {
                this.n1 = n1;
                this.n2 = n2;
            } else {
                this.n1 = n2;
                this.n2 = n1;
            }
        } else {
            if (n1.x < n2.x) {
                this.n1 = n1;
                this.n2 = n2;
            } else {
                this.n1 = n2;
                this.n2 = n1;
            }
        }
    }

    public String toString() {
        return "(" + n1.x + "," + n1.y + ")->(" + n2.x + "," + n2.y + ")";
    }
}