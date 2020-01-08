package com.mersic.day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static char[][] readGraph(BufferedReader br) throws Exception {
        String line = null;
        List<String> lines = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
        char[][] G = new char[lines.size()][lines.get(0).length()];
        for (int i = 0; i < G.length; i++) {
            G[i] = lines.get(i).toCharArray();
        }
        return G;
    }

    public static void main(String args[]) throws Exception {
        FileReader fr = new FileReader(args[0]);
        BufferedReader br = new BufferedReader(fr);

        char[][] G = readGraph(br);
        Asteroid[] A = getAsteroids(G);

        int rows = G.length;
        int columns = G[0].length;
        int max_visible = Integer.MIN_VALUE;
        int best_x = -1;
        int best_y = -1;
        for (Asteroid p : A) {
            int current_visible = countVisibleFromPos(A, p, rows, columns);
            if (current_visible > max_visible) {
                print_grid(A, p, rows, columns);
                max_visible = current_visible;
                best_x = p.x;
                best_y = p.y;
                System.out.println("new max: " + max_visible + " at pos: (" + p.x + "," + p.y + ")");
                if (max_visible > 200) {
                    print_vaporized(A, p, 200);
                }
            }
        }

        System.out.println(max_visible + " visible from (" + best_x + ", " + best_y + ")");
    }

    private static void print_vaporized(Asteroid[] A, Asteroid p, int n) {

        for (Asteroid a : A) {
            if (a == p) {
                continue;
            }
            float ax = a.x;
            float ay = a.y;
            float px = p.x;
            float py = p.y;
            float angle = -1;

            ax = ax - px;
            ay = ay - py;
            if (ax > 0 && ay > 0) {
                a.angle = (float) Math.atan2(ay, ax);
                //a.Q = 1;
                a.Q = 2;
                continue;
            } else if (ax < 0 && ay > 0) {
                //a.Q = 2;
                a.Q = 3;
                a.angle = (float) Math.atan2(ay, ax);
                continue;
            } else if (ax < 0 && ay < 0) {
                //a.Q = 3;
                a.Q = 4;
                a.angle = (float) Math.atan2(ay, ax);
                continue;
            } else if (ax > 0 && ay < 0) {
                //a.Q = 4;
                a.Q = 1;
                a.angle = (float) Math.atan2(ay, ax);
                continue;
            }
            if (ax == 0 && ay > 0) {
                a.Q = 3;
                a.angle = (float) Math.PI/2.0f;
                continue;
            } else if (ax == 0 && ay < 0) {
                a.Q = 1;
                a.angle = (float) (-1.0f*(Math.PI/2.0f));
                continue;
            } else if (ay == 0 && ax > 0) {
                a.Q = 2;
                a.angle = 0;
                continue;
            } else if (ay == 0 && ax < 0) {
                a.Q = 4;
                a.angle = (float) -Math.PI;
                continue;
            }

            throw new RuntimeException("Shouldn't be here.");
        }

        Asteroid[] copy = copyAsteroids(A);

        Arrays.sort(copy);

        int count = 0;
        for (int i = 0; i < copy.length; i++) {
            if (copy[i].x == p.x && copy[i].y == p.y) {
                    continue;
            }
            if (copy[i].visible) {
                count++;
                int z = (copy[i].x*100) + copy[i].y;
                System.out.println("vaporized: " + count  + " with z: " + z + " " + copy[i]);
            }
        }

        System.out.println("now find the 200th...");

    }

    private static Asteroid[] getAsteroids(char[][] G) {
        List<Asteroid> asteroids = new ArrayList<Asteroid>();
        for (int i = 0; i < G.length; i++) {
            for (int j = 0; j < G[0].length; j++) {
                if (G[i][j] == '#') {
                    asteroids.add(new Asteroid(j, i));
                }
            }
        }
        return asteroids.toArray(new Asteroid[0]);
    }

    private static boolean does_b_block_a_fromP(Asteroid b, Asteroid a, Asteroid p) {

        if (p.x == a.x) {
            if (p.x != b.x) {
                return false;
            }
            //P
            //B
            //A
            if (b.y < a.y && p.y < b.y) {
                return true;
            }

            //A
            //B
            //P
            if (a.y < b.y && b.y < p.y) {
                return true;
            }
        }

        if (p.x == b.x) {
            return false;
        }

        float r1 = ((p.y - a.y) * 1.0f) / (p.x - a.x);
        float r2 = ((p.y - b.y) * 1.0f) / (p.x - b.x);

        float b1 = a.y - (r1 * a.x);
        float b2 = b.y - (r1 * b.x);

        if (Math.abs(r1 - r2) < 0.00001 && (Math.abs(b1 - b2) < 0.00001)) {
            //same line, does b block a?
            if (b.x < a.x && p.x < b.x) {
                //PBA
                return true;
            }
            if (b.x < p.x && a.x < b.x) {
                //ABP
                return true;
            }
        }

        return false;
    }

    private static int countVisibleFromPos(Asteroid[] A, Asteroid P, int rows, int columns) {

        int visible_count = 0;
        for (Asteroid a : A) {
            if (a == P) {
                continue;
            }
            boolean blocks = false;
            for (Asteroid b : A) {
                if (b == a || b == P) {
                    continue;
                }
                blocks = does_b_block_a_fromP(b, a, P);
                a.visible = !blocks;
                if (blocks) {
                    break;
                }
            }
            if (!blocks) {
                visible_count++;
            }
        }
        return visible_count;
    }

    private static void print_grid(Asteroid[] A, Asteroid p, int rows, int columns) {
        System.out.println("------------------");
        for (int i = 0; i < rows; i++) {
            next:
            for (int j = 0; j < columns; j++) {
                if (p.x == j && p.y == i) {
                    System.out.print("P");
                    continue;
                }
                for (Asteroid a : A) {
                    if (a.x == j && a.y == i) {
                        if (a.visible) {
                            System.out.print("V");
                        } else {
                            System.out.print(".");
                        }
                        continue next;
                    }
                }
                System.out.print(" ");
            }
            System.out.println();
        }

    }

    public static Asteroid[] copyAsteroids(Asteroid[] A) {
        Asteroid[] copy = new Asteroid[A.length];
        for (int i = 0; i < A.length; i++) {
            copy[i] = new Asteroid(A[i]);
        }

        return copy;
    }
}


class Asteroid implements Comparable<Asteroid> {
    int x;
    int y;
    float angle;
    int Q;
    boolean visible;

    Asteroid(Asteroid orig) {
        this.x = orig.x;
        this.y = orig.y;
        this.angle = orig.angle;
        this.Q = orig.Q;
        this.visible = orig.visible;
    }

    Asteroid(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "asteroid at: (" + this.x + "," + this.y + ") visible: " + this.visible + " Q: " + this.Q + " angle: " + this.angle;
    }

    @Override
    public int compareTo(Asteroid other) {
        if (this.Q != other.Q) {
            //counter-clock 1, 2, 3, 4
            if (this.Q < other.Q) {
                return -1;
            } else {
                return 1;
            }
        } else {
            if (this.angle < other.angle) {
                return -1;
            } else if (this.angle > other.angle){
                return 1;
            } else {
                return 0;
            }
        }
    }
}
