package com.mersic.day11;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

enum RobotWriteState {
    PAINT,
    MOVE
}

enum RobotDirection {
    UP,
    DOWN,
    LEFT,
    RIGHT;
}

public class IOHelperRobot implements IOHelper {

    boolean debug = true;

    private final RobotPosition robotPosition = new RobotPosition(0,0);
    private Map<String, String> stateSpace = new HashMap<>();
    private RobotWriteState robotWriteState = RobotWriteState.PAINT;
    private RobotDirection robotDirection = RobotDirection.UP;
    private Set<String> squaresPainted = new HashSet<String>();
    int paintCounter =  0;
    public IOHelperRobot() {
    }

    public IOHelperRobot(Map<String, String> stateSpace) {
        this.stateSpace = stateSpace;
    }


    @Override
    public void write(String s) {
        if (debug) System.out.println("robotWriteState: " + this.robotWriteState);
        switch (this.robotWriteState) {
            case PAINT: {
                if (!s.equals("0") && !s.equals("1")) {
                    throw new RuntimeException("Unknown paint color: " + s);
                }
                if (debug) System.out.println("painted: " + robotPosition + " color: " + s);
                squaresPainted.add(robotPosition.toString());
                stateSpace.put(robotPosition.toString(), s);
                paintCounter++;
                break;
            }
            case MOVE: {
                int direction = Integer.parseInt(s);
                if (debug) System.out.println("direction: " + direction + " old direction: " + this.robotDirection + " old position: " + this.robotPosition);
                if (direction == 0) { //left;
                    switch (this.robotDirection) {
                        case UP: this.robotDirection = RobotDirection.LEFT; break;
                        case LEFT: this.robotDirection = RobotDirection.DOWN; break;
                        case DOWN: this.robotDirection = RobotDirection.RIGHT; break;
                        case RIGHT: this.robotDirection = RobotDirection.UP; break;
                        default: throw new RuntimeException("Invalid state: " + this.robotDirection);
                    }
                } else if (direction == 1) {
                    switch (this.robotDirection) {
                        case UP: this.robotDirection = RobotDirection.RIGHT; break;
                        case RIGHT: this.robotDirection = RobotDirection.DOWN; break;
                        case DOWN: this.robotDirection = RobotDirection.LEFT; break;
                        case LEFT: this.robotDirection = RobotDirection.UP; break;
                        default: throw new RuntimeException("Invalid state: " + this.robotDirection);
                    }
                } else {
                    throw new RuntimeException("Unknown direction: " + direction);
                }
                switch (this.robotDirection) {
                    case UP: this.robotPosition.y++; break;
                    case DOWN: this.robotPosition.y--; break;
                    case LEFT: this.robotPosition.x--; break;
                    case RIGHT: this.robotPosition.x++; break;
                    default: throw new RuntimeException("Invalid state: " + this.robotDirection);
                }
                if (debug) System.out.println("new direction: " + this.robotDirection + " new position: " + this.robotPosition);
                break;
            }
            default: throw new RuntimeException(("Invalid robotwritestate: " + this.robotWriteState));
        }
        if (this.robotWriteState == RobotWriteState.PAINT) {
            this.robotWriteState = RobotWriteState.MOVE;
        } else if (this.robotWriteState == RobotWriteState.MOVE) {
            this.robotWriteState = RobotWriteState.PAINT;
        }
        if (debug) System.out.println("new robotWriteState: " + this.robotWriteState);
    }

    @Override
    public String read() {
        if (stateSpace.containsKey(robotPosition.toString())) {
            return stateSpace.get(robotPosition.toString());
        } else {
            return "0";
        }
    }

    public int getSquaresPainted() {
        System.out.println("painted: " + paintCounter);
        System.out.println("unique squares painted: " + this.squaresPainted.size());
        return this.squaresPainted.size();
    }

    public Map<String, String> getStateSpace() {
        return this.stateSpace;
    }
}
