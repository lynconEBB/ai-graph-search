package unioeste.ia.models;

import java.util.ArrayList;
import java.util.List;

public class MyNode {
    String name;
    public List<Edge> edges;
    public boolean visited;
    public Integer minDistanceFromStart;
    public Integer totalDistance;
    public Integer distanceToEnd;
    public MyNode previousNode;

    public MyNode() {
        this.edges = new ArrayList<>();
        this.minDistanceFromStart = Integer.MAX_VALUE;
        this.totalDistance = null;
        this.distanceToEnd = null;
        this.visited = false;
    }

    public MyNode(String name) {
        this();
        this.name = name;
    }
    public MyNode(String name, int dist) {
        this();
        this.name = name;
        this.totalDistance = dist;
    }
}
