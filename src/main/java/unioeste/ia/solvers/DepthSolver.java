package unioeste.ia.solvers;

import unioeste.ia.Logger;
import unioeste.ia.models.*;

import java.util.Stack;

public class DepthSolver implements Solver {
    private int visits;
    private Status status;
    private Stack<MyNode> stack;
    private final MyGraph graph;
    private MyNode current;

    public DepthSolver(MyGraph graph) {
        this.visits = 0;
        this.graph = graph;
        this.stack = new Stack<>();
        this.status = Status.SOLVING;

        graph.reset();
        stack.add(graph.origin);

        Logger.addMessage("Setting solver to DFS", Origin.DFS_SOLVER, Severity.INFO);
    }

    @Override
    public void next() {
        if (stack.isEmpty()) {
            Logger.addMessage("Could not find the a path from origin node to final node!", Origin.DFS_SOLVER, Severity.ERROR);
            status = Status.NOT_FOUND;
            return;
        }

        current = stack.pop();
        current.visited = true;
        visits++;

        if (current == graph.destination) {
            this.status = Status.FOUND;
            this.stack.clear();
            Logger.addMessage("Shortest path to final node found with " + visits + " visits and distance " + graph.getFoundPathDistance(), Origin.DFS_SOLVER, Severity.INFO);
        }

        for (Edge edge : current.edges) {
            if (edge.dest.visited)
                continue;

            if (edge.dest.previousNode == null)
                edge.dest.previousNode = current;

            if (!stack.contains(edge.dest))
                stack.add(edge.dest);
        }

    }

    @Override
    public void solve() {
        while (!stack.isEmpty()) {
            next();
        }
        if (status != Status.FOUND) {
            Logger.addMessage("Could not find the a path from origin node to final node!", Origin.DFS_SOLVER, Severity.ERROR);
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
