package unioeste.ia.solvers;

import unioeste.ia.Logger;
import unioeste.ia.models.*;

import java.util.Comparator;
import java.util.PriorityQueue;

public class DijkstraSolver implements Solver {
    private Status status;
    PriorityQueue<MyNode> queue = new PriorityQueue<>(Comparator.comparingInt(n -> n.minDistanceFromStart));
    private int visits;
    private MyNode currentNode;
    private final MyGraph graph;

    public DijkstraSolver(MyGraph graph) {
        this.visits = 0;
        this.graph = graph;
        this.status = Status.SOLVING;
        graph.reset();

        this.graph.origin.minDistanceFromStart = 0;
        this.queue.add(graph.origin);
        Logger.addMessage("Setting solver to dijkstra!", Origin.DIJKSTRA_SOLVER, Severity.INFO);
    }

    @Override
    public void next() {
        if (queue.isEmpty()) {
            Logger.addMessage("Could not find the a path from origin node to final node!", Origin.DIJKSTRA_SOLVER, Severity.ERROR);
            status = Status.NOT_FOUND;
            return;
        }

        currentNode = queue.poll();
        currentNode.visited = true;
        visits++;

        if (currentNode == graph.destination) {
            this.status = Status.FOUND;
            Logger.addMessage("Shortest path to final node found with " + visits + " visits and distance " + graph.getFoundPathDistance(),Origin.DIJKSTRA_SOLVER, Severity.INFO);
            return;
        }

        for (Edge edge : currentNode.edges) {
            MyNode dest = edge.dest;

            if (dest.visited)
                continue;

            if (dest.minDistanceFromStart > currentNode.minDistanceFromStart + edge.distance) {
                dest.minDistanceFromStart = currentNode.minDistanceFromStart + edge.distance;
                dest.previousNode = currentNode;
            }
            if (!queue.contains(dest))
                queue.add(dest);
        }
    }

    public void solve() {
        while (!queue.isEmpty()) {
            next();
        }
        if (status != Status.FOUND) {
            Logger.addMessage("Could not find the a path from origin node to final node!", Origin.DIJKSTRA_SOLVER, Severity.ERROR);
            status = Status.NOT_FOUND;
        }
    }

    @Override
    public MyNode getCurrentNode() {
        return currentNode;
    }

    @Override
    public Status getStatus() {
        return status;
    }
}
