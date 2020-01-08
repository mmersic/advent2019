package com.mersic.day13;

import java.util.HashMap;
import java.util.Map;

public class ArcadeIOHelper implements IOHelper {

    int ws = 0;
    int next_x = -1;
    int next_y = -1;
    int next_tile = -1;
    int currentScore;
    int ball_x = -1;
    int paddle_x = -1;
    Map<String, Integer> tileMap = new HashMap<>();

    @Override
    public void write(String s) {
        switch (ws%3) {
            case 0: { next_x = Integer.parseInt(s); break; }
            case 1: { next_y = Integer.parseInt(s); break; }
            case 2: {
                if (next_x == -1 && next_y == 0) {
                    this.currentScore = Integer.parseInt(s);
                    System.out.println("currentScore: " + currentScore);
                } else {
                    next_tile = Integer.parseInt(s);
                    switch (next_tile) {
                        case 3: { paddle_x = next_x; break; }
                        case 4: { ball_x = next_x;   break; }
                    }
                    tileMap.put("(" + next_x + "," + next_y + ")", next_tile);
                    break;
                }
            }
        }
        if (ws % 100 == 0)
            printScreen();
        ws++;
    }

    @Override
    public String read() {
       String joystick = determineJoystick();
       System.out.println("Joystick: " + joystick  + " ball_x: " + ball_x + " paddle: " + paddle_x);
       return joystick;
    }

    public String determineJoystick() {
        if (ball_x < paddle_x) {
            return "-1";
        } else if (ball_x > paddle_x) {
            return "1";
        } else {
            return "0";
        }
    }

    public void printScreen() {
        for (int y = 0; y < 25; y++) {
            for (int x = 0; x < 45; x++) {
                String key = "(" + x + "," + y + ")";
                if (tileMap.get(key) != null) {
                    int val = tileMap.get(key);
                    String p = "";
                    switch (val) {
                        case 0: { p = " "; break; }
                        case 1: { p = "W"; break; }
                        case 2: { p = "B"; break; }
                        case 3: { p = "P"; break; }
                        case 4: { p = "O"; break; }
                        default: { throw new RuntimeException("Invalid val: " + val); }
                    }
                    System.out.print(p);
                }
            }
            System.out.println();
        }
    }

    public int blocksCount() {
        int blockCount = 0;
        for (Integer k : this.tileMap.values()) {
            if (k == 2) {
                blockCount++;
            }
        }
        return blockCount;
    }
}
