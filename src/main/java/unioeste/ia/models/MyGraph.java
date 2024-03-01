package unioeste.ia.models;

import java.util.List;

public class MyGraph {
    public MyNode origin;
    public MyNode destination;
    public List<Edge> edges;

    public void reset() {
        for (Edge edge: edges) {
            reset(edge.src);
            reset(edge.dest);
        }
    }

    private void reset(MyNode node) {
        node.previousNode = null;
        node.visited = false;
        node.minDistanceFromStart = Integer.MAX_VALUE;
        node.totalDistance = null;
    }
    public MyGraph(MyNode origin, MyNode destination, List<Edge> edges) {
        this.origin = origin;
        this.destination = destination;
        this.edges = edges;
    }
}
