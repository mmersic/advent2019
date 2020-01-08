package com.mersic.day12;

public class MathHelper {

    public static long gcd(long a, long b) {
        while (b != 0) {
            long t = b;
            b = a % b;
            a = t;
        }
        return a;
    }

    public static long lcm(long a, long b) {

        return (a*b)/gcd(a,b);
    }

    public static long lcm(long a, long b, long c) {
        return lcm(lcm(a, b), c);
    }
}
