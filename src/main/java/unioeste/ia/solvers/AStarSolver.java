package unioeste.ia.solvers;

import unioeste.ia.Logger;
import unioeste.ia.models.*;

import java.util.Comparator;
import java.util.PriorityQueue;

public class AStarSolver implements Solver {
    private boolean isSolved = false;
    PriorityQueue<MyNode> queue = new PriorityQueue<>(Comparator.comparingInt(n -> n.totalDistance));
    private int visits;
    private MyNode currentNode;
    private final MyGraph graph;

    public AStarSolver(MyGraph graph) {
        this.visits = 0;
        this.graph = graph;
        graph.reset();

        this.graph.origin.minDistanceFromStart = 0;
        this.queue.add(graph.origin);
        Logger.addMessage("Setting solver to A*!",Origin.ASTAR_SOLVER, Severity.INFO);
    }

    @Override
    public void next() {
        currentNode = queue.poll();
        currentNode.visited = true;
        visits++;

        if (currentNode == graph.destination) {
            this.isSolved = true;
            Logger.addMessage("Shortest path to final node found with " + visits + " visits!",Origin.ASTAR_SOLVER, Severity.INFO);
            return;
        }

        for (Edge edge : currentNode.edges) {
            MyNode dest = edge.dest;

            if (dest.visited)
                continue;

            if (dest.minDistanceFromStart > currentNode.minDistanceFromStart + edge.distance) {
                dest.minDistanceFromStart = currentNode.minDistanceFromStart + edge.distance;
                dest.totalDistance = dest.minDistanceFromStart + dest.distanceToEnd;
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
    }

    @Override
    public MyNode getCurrentNode() {
        return currentNode;
    }

    @Override
    public boolean isSolved() {
        return isSolved;
    }

    public int getVisits() {
        return this.visits;
    }
}
