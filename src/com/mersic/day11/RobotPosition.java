package com.mersic.day11;

public class RobotPosition {
    int x;
    int y;

    public RobotPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
