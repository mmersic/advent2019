package com.mersic.day3.part2.failed.PB6X;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;



public class PB_6X {


    public static Node intersection(LineSeg l1, LineSeg l2) {
        if (l1.n1.x == l1.n2.x && l2.n1.y == l2.n2.y) {
            if (l2.n1.y <= l1.n1.y && l2.n1.y >= l1.n2.y) {
                if (l2.n1.x <= l1.n2.x && l2.n2.x >= l1.n2.x) {
                    int x = l1.n1.x;
                    int y = l2.n1.y;
                    if (x == 0 && y == 0) {
                        return null;
                    }
                    if (x == 0 && y == 0) {
                        return null;
                    }
                    if (x == l1.n1.x && y == l1.n1.y) {
                        return null;
                    }
                    if (x == l1.n2.x && y == l1.n2.y) {
                        return null;
                    }
                    if (x == l2.n1.x && y == l2.n1.y) {
                        return null;
                    }
                    if (x == l2.n2.x && y == l2.n2.y) {
                        return null;
                    }
                    Node n = new Node(l1.n1.x, l2.n1.y);
                    //System.out.println("l1: " + l1 + " l2: " + l2 + " intersect at: " + n);
                    return n;
                }
            }
        } else if (l1.n1.y == l1.n2.y && l2.n1.x == l2.n2.x) {
            if (l1.n1.y <= l2.n1.y && l1.n1.y >= l2.n2.y) {
                if (l1.n1.x <= l2.n2.x && l1.n2.x >= l2.n2.x) {
                    int x = l2.n1.x;
                    int y = l1.n1.y;
                    if (x == 0 && y == 0) {
                        return null;
                    }
                    if (x == l1.n1.x && y == l1.n1.y) {
                        return null;
                    }
                    if (x == l1.n2.x && y == l1.n2.y) {
                        return null;
                    }
                    if (x == l2.n1.x && y == l2.n1.y) {
                        return null;
                    }
                    if (x == l2.n2.x && y == l2.n2.y) {
                        return null;
                    }
                    Node n = new Node(l2.n1.x, l1.n1.y);
                    //System.out.println("l1: " + l1 + " l2: " + l2 + " intersect at: " + n);
                    return n;
                }
            }
        }

        return null;
    }

    public static List<Graph> readGraphs(BufferedReader br) throws Exception {
        List<Graph> graphs = new ArrayList<>();
        String nextLine = null;
        while ((nextLine = br.readLine()) != null) {
            Graph G = new Graph();
            String[] moves = nextLine.split(",");
            Node n1 = new Node(0, 0);
            int x2 = n1.x;
            int y2 = n1.y;
            for (String m : moves) {
                char opcode = m.charAt(0);
                int d = Integer.parseInt(m.substring(1));
                switch (opcode) {
                    case 'R' : x2 = n1.x + d; break;
                    case 'L' : x2 = n1.x - d; break;
                    case 'U' : y2 = n1.y + d; break;
                    case 'D' : y2 = n1.y - d; break;
                    default : throw new RuntimeException("opcode error: " + opcode);
                }
                Node n2 = new Node(x2, y2);
                G.addLineSeg(new LineSeg(n1, n2));
                n1 = n2;
            }
            graphs.add(G);
        }
        return graphs;
    }

    public static List<Node> getIntersections(Graph G1, Graph G2) {
        List<Node> intersections = new ArrayList<>();
        List<LineSeg> s = G1.getLineSegs();
        List<LineSeg> r = G2.getLineSegs();
        for (LineSeg l1 : s) {
            for (LineSeg l2 : r) {
                Node n = intersection(l1, l2);
                if (n != null) {
                    intersections.add(n);
                }
            }
        }
        return intersections;
    }

    public static void printClosestIntersection(List<Node> nodes) {
        int closest = 999999999;
        for (Node n : nodes) {
            int d = Math.abs(n.x) + Math.abs(n.y);
            if (d > 0 && d < closest) {
                closest = d;
            }
        }
        System.out.println("closest: " + closest);
    }

    public static void printNodes(String label, List<Node> nodes) {
        for (Node n : nodes) {
            System.out.println(label + n);
        }
    }

    public static int dist(Node n1, Node n2) {
        if (n1.x == n2.x) {
            return Math.abs(n1.y - n2.y);
        } else if (n1.y == n2.y) {
            return Math.abs(n1.x - n2.x);
        }
        throw new RuntimeException("Invalid dist compare for nodes: " + n1 + " and " + n2);
    }

    public static int distToNode(List<LineSeg> lineSegs, Node n) {
        for (LineSeg l : lineSegs) {
            if (l.n1.x == l.n2.x && l.n1.x == n.x && ((l.n1.y < n.y && l.n2.y > n.y) || (l.n2.y < n.y && l.n1.y > n.y))) {
                if (l.n1.dist > l.n2.dist) {
                    return l.n2.dist+(Math.abs(l.n2.y-n.y));
                } else {
                    return l.n1.dist+(Math.abs(l.n1.y-n.y));
                }
            } else if (l.n1.y == l.n2.y && l.n2.y == n.y && ((l.n1.x < n.x && l.n2.x > n.x) || (l.n2.x < n.x && l.n1.x > n.x))) {
                if (l.n1.dist > l.n2.dist) {
                    return l.n2.dist+(Math.abs(l.n2.x-n.x));
                } else {
                    return l.n1.dist+(Math.abs(l.n1.x-n.x));
                }
            }
        }
        throw new RuntimeException("Node not on path: " + n);
    }

    public static int shortestPath(Graph G, Node n) {
        boolean didWork = false;
        do {
            didWork = false;
            for (LineSeg l1 : G.lineSegs) {
                if (l1.n1.dist == -1 && l1.n2.dist != -1) {
                    l1.n1.dist = l1.n2.dist + dist(l1.n1, l1.n2);
                    didWork = true;
                } else if (l1.n1.dist != -1 && l1.n2.dist == -1) {
                    l1.n2.dist = l1.n1.dist + dist(l1.n1, l1.n2);
                    didWork = true;
                } else if (l1.n1.dist != -1 && l1.n2.dist != -1) {
                    int d = dist(l1.n1, l1.n2);
                    if (l1.n1.dist > l1.n2.dist) {
                        if (l1.n2.dist+d < l1.n1.dist) {
                            l1.n1.dist = d + l1.n2.dist;
                            didWork = true;
                        }
                    } else if (l1.n2.dist > l1.n1.dist) {
                        if (l1.n1.dist+d < l1.n2.dist) {
                            l1.n2.dist = d + l1.n1.dist;
                            didWork = true;
                        }
                    }
                    //check if shorter path.
                }
            }
        } while (didWork);

        return distToNode(G.lineSegs, n);
    }

    public static void printShortestCombinedPath(List<Graph> graphs, List<Node> intersections) {
        Graph G1 = graphs.get(0);
        Graph G2 = graphs.get(1);

        int min = Integer.MAX_VALUE;
        for (Node n : intersections) {
            G1.resetDist();
            G2.resetDist();
            int p1 = shortestPath(G1, n);
            int p2 = shortestPath(G2, n);
            if (p1+p2 < min) {
                min = p1+p2;
            }
        }
        System.out.println("min: " + min);
    }

    public static void main(String[] args) throws Exception {
        FileReader fr = new FileReader(args[0]);
        BufferedReader br = new BufferedReader(fr);

        List<Graph> graphs = readGraphs(br);

        List<Node>
                intersections = getIntersections(graphs.get(0), graphs.get(1));
        printNodes("G1 G2 intersections: ", intersections);
        printClosestIntersection(intersections);
//        printNodes("G0 G0 intersections: ", getIntersections(graphs.get(0), graphs.get(0)));
//
//        printNodes("G0 G0 intersections: ", getIntersections(graphs.get(0), graphs.get(0)));
//        printNodes("G1 G1 intersections: ", getIntersections(graphs.get(1), graphs.get(1)));


//        List<Node> i1 = new ArrayList<>();
//        i1.add(new Node(-556, 0));
//        int c1 = getIntersections(graphs.get(1), graphs.get(1)).size();
//        System.out.println("num i1: " + c1);
//        graphs.get(1).splitIntersections(i1);
//        int c2 = getIntersections(graphs.get(1), graphs.get(1)).size();
//        System.out.println("num i2: " + c2);
//        if (c1 <= c2) {
//            System.out.println("Error splitting node.");
//        }


//        List<Node> selfIntersections = getIntersections(graphs.get(1), graphs.get(1));
//        for (Node n : selfIntersections) {
//            List<Node> i1 = new ArrayList<>();
//            i1.add(n);
//            int c1 = getIntersections(graphs.get(1), graphs.get(1)).size();
//            System.out.println("num i1: " + c1);
//            graphs.get(1).splitIntersections(i1);
//            int c2 = getIntersections(graphs.get(1), graphs.get(1)).size();
//            System.out.println("num i2: " + c2);
//            if (c1 <= c2) {
//                System.out.println("Error splitting node: " + n);
//            }
//        }
        graphs.get(0).splitIntersections(getIntersections(graphs.get(0), graphs.get(0)));
        printNodes("G0 G0 intersections: ", getIntersections(graphs.get(0), graphs.get(0)));
        graphs.get(1).splitIntersections(getIntersections(graphs.get(1), graphs.get(1)));
        printNodes("G1 G1 intersections: ", getIntersections(graphs.get(1), graphs.get(1)));

        intersections = getIntersections(graphs.get(0), graphs.get(1));
        printNodes("G1 G2 intersections: ", intersections);
        printClosestIntersection(intersections);

        printShortestCombinedPath(graphs, intersections);
    }
}
