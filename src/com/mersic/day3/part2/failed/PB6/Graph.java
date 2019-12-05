package com.mersic.day3.part2.failed.PB6;

import java.util.*;

public class Graph {

    Node startNode;
    Map<String, Node> nodes = new HashMap<>();
    List<Edge> edges = new ArrayList<>();

    public Graph() {
        startNode = new Node(0, 0);
        startNode.addWire(0);
        startNode.addWire(1);
        nodes.put("0,0",startNode);
    }

    public void addEdge(int x1, int y1, int x2, int y2, int wire) {
        Node n1 = nodes.get(x1+","+y1);
        n1.addWire(wire);
        Node n2 = nodes.get(x2+","+y2);
        if (n2 == null) {
            n2 = new Node(x2, y2);
            nodes.put(x2 + "," + y2, n2);
        }
        n2.addWire(wire);
        Edge e = new Edge(n1, n2);
        n1.addNextEdge(e, wire);
        edges.add(e);
    }

    public void resetVisited() {
        for (Edge e : edges) {
            e.visited = false;
        }
        for (Node n : nodes.values()) {
            n.dist[0] = -1;
            n.dist[1] = -1;
        }
        startNode.dist[0] = 0;
        startNode.dist[1] = 0;
    }

    public void traverse(int wire) {
        Node currentNode = startNode;
        nextloop: while(true) {
            System.out.print(currentNode + "->");
            List<Edge> edgeList = currentNode.getNextEdges(wire);
            if (edgeList.size() == 0) {
                break;
            }
            for (Edge e : edgeList) {
                if (!e.visited) {
                    currentNode = e.d;
                    continue nextloop;
                }
            }
            System.out.println("done");
            break;
        }
    }
}
