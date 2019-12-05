package com.mersic.day3.part2.failed.PB6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class PB_6 {


    public Graph buildGraph(BufferedReader br) throws Exception {
        Graph G = new Graph();

        String nextLine = null;
        int id = -1;
        while ((nextLine = br.readLine()) != null) {
            id++;
            String[] moves = nextLine.split(",");
            int x1 = 0;
            int y1 = 0;
            int x2 = 0;
            int y2 = 0;
            for (String m : moves) {
                char opcode = m.charAt(0);
                int d = Integer.parseInt(m.substring(1));
                switch (opcode) {
                    case 'R':
                        for (int i = 0; i<d; i++) {
                            x2++;
                            G.addEdge(x1, y1, x2, y2, id);
                            x1++;
                        }
                        break;
                    case 'L':
                        for (int i = 0; i<d; i++) {
                            x2--;
                            G.addEdge(x1, y1, x2, y2, id);
                            x1--;
                        }
                        break;
                    case 'U':
                        for (int i = 0; i<d; i++) {
                            y2++;
                            G.addEdge(x1, y1, x2, y2, id);
                            y1++;
                        }
                        break;
                    case 'D':
                        for (int i = 0; i<d; i++) {
                            y2--;
                            G.addEdge(x1, y1, x2, y2, id);
                            y1--;
                        }
                        break;
                    default:
                        throw new RuntimeException("opcode error: " + opcode);
                }
            }
        }
        return G;
    }

    public List<Node> intersectionList(Graph G) {
        Collection<Node> c = G.nodes.values();
        List<Node> intersectionList = new ArrayList<>();
        for (Node n : c) {
            if (n.wires.contains(0) && n.wires.contains(1)) {
                int d = Math.abs(n.x) + Math.abs(n.y);
                if (d > 0) {
                    intersectionList.add(n);
                }
            }
        }
        return intersectionList;
    }

/*
    public int distance(Node start, Node dest, int wire) {
        Node currentNode = start;

        if (currentNode == dest) {
            return start.dist;
        }
        List<Edge> nextEdges = null;
        while ((nextEdges = currentNode.getNextEdges(wire)).size() == 1) {
            Node nextNode = nextEdges.get(0).d;
            nextEdges.get(0).visited = true;
            if (nextNode.dist == -1 || nextNode.dist > 1+currentNode.dist)
                nextNode.dist = 1+currentNode.dist;
            currentNode = nextNode;
        }
        for (Edge e : nextEdges) {
            if (e.d.dist == -1 || e.d.dist > 1+currentNode.dist)
                e.d.dist = 1+currentNode.dist;
            if (!e.visited) {
                e.visited = true;
                distance(e.d, dest, wire);
            }
        }

        return dest.dist;
    }
*/

    public void distance(Graph G, int wire) {
        boolean didWork = false;
        System.out.println("nodes: " + G.nodes.values().size());
        int x = 0;

        List<Node> nodes = new ArrayList<>();
        for (Node n : G.nodes.values()) {
            nodes.add(n);
        }
        do {
            didWork = false;
            if (x % 1000 == 0) {
                System.out.println("x: " + x);
            }
            for (Node n : nodes) {
                if (n.dist[wire] == -1) {
                    continue;
                }
                for (Edge e : n.getNextEdges(wire)) {
                    if (n.dist[wire] == -1 && e.d.dist[wire] != -1) {
                        n.dist[wire] = e.d.dist[wire] + 1;
                        didWork = true;
                        x++;
                        continue;
                    }
                    if (n.dist[wire] != -1 && e.d.dist[wire] != -1 && n.dist[wire] > e.d.dist[wire]+1) {
                        n.dist[wire] = e.d.dist[wire] + 1;
                        didWork = true;
                        x++;
                        continue;
                    }
                    if (n.dist[wire] != -1 && e.d.dist[wire] == -1) {
                        e.d.dist[wire] = n.dist[wire]+1;
                        didWork = true;
                        x++;
                        continue;
                    }
                }
            }
        } while (didWork);

    }

 


    public static void main(String[] args) throws Exception {
        PB_6 P = new PB_6();
        FileReader fr = new FileReader(args[0]);
        BufferedReader br = new BufferedReader(fr);
        Graph G = P.buildGraph(br);
        List<Node> intersectionList = P.intersectionList(G);
        for (Node n : intersectionList) {
            System.out.println("intersect at: " + n);
        }
        int shortest_combined_path = Integer.MAX_VALUE;

        G.resetVisited();
        P.distance(G, 0);
        P.distance(G, 1);

        for (Node n : intersectionList) {
            int w1 = n.dist[0];
            int w2 = n.dist[1];
            System.out.println("w1: " + w1);
            System.out.println("w2: " + w2);
            if (w1+w2 < shortest_combined_path) {
                shortest_combined_path = w1+w2;
            }
        }

        System.out.println("shortest_combined_path: " + shortest_combined_path);
    }
}
