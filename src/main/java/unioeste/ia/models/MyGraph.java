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

    public int getFoundPathDistance() {
        MyNode node = destination;
        int dist = 0;
        while (!node.equals(origin)) {
            if (node.previousNode == null)
                return 0;

            MyNode finalNode = node;
            Edge edge = node.edges.stream().filter(e -> e.dest.equals(finalNode.previousNode)).findFirst().get();

            dist += edge.distance;
            node = node.previousNode;
        }

        return dist;
    }
}
