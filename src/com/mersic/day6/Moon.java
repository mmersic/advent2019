package com.mersic.day6;

public class Moon {
    String moon;
    String parent;
    int depth;

    public Moon(String moon, String parent) {
        this.moon = moon;
        this.parent = parent;
        this.depth = -1;
    }

    public String toString() {
        return this.moon + " depth: " + depth;
    }
}
