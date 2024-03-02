package unioeste.ia.solvers;

import unioeste.ia.Logger;
import unioeste.ia.models.*;

import java.util.LinkedList;
import java.util.Queue;

public class BreadthSolver implements Solver {
    private Status status;
    private Queue<MyNode> queue;
    private final MyGraph graph;
    private MyNode current;
    private int visits;

    public BreadthSolver(MyGraph graph) {
        this.visits = 0;
        this.graph = graph;
        this.queue = new LinkedList<>();
        this.status = Status.SOLVING;

        this.graph.reset();
        this.queue.add(graph.origin);

        Logger.addMessage("Setting solver to BFS!", Origin.BFS_SOLVER, Severity.INFO);
    }

    @Override
    public void next() {
        if (queue.isEmpty()) {
            Logger.addMessage("Could not find the a path from origin node to final node!", Origin.BFS_SOLVER, Severity.ERROR);
            status = Status.NOT_FOUND;
            return;
        }

        current = queue.poll();
        current.visited = true;
        visits++;

        if (current == graph.destination) {
            this.status = Status.FOUND;
            Logger.addMessage("Shortest path to final node found with " + visits + " visits and distance " + graph.getFoundPathDistance(), Origin.BFS_SOLVER, Severity.INFO);
            this.queue.clear();
        }

        for (Edge edge : current.edges) {
            if (edge.dest.visited)
                continue;

            if (edge.dest.previousNode == null)
                edge.dest.previousNode = current;

            if (!queue.contains(edge.dest))
                queue.add(edge.dest);
        }
    }

    @Override
    public void solve() {
        while (!queue.isEmpty()) {
            next();
        }
        if (status != Status.FOUND) {
            Logger.addMessage("Could not find the a path from origin node to final node!", Origin.BFS_SOLVER, Severity.ERROR);
            status = Status.NOT_FOUND;
        }
    }

    @Override
    public MyNode getCurrentNode() {
        return current;
    }

    @Override
    public Status getStatus() {
        return status;
    }
}
