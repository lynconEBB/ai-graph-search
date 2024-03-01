package unioeste.ia.solvers;

import unioeste.ia.models.Edge;
import unioeste.ia.models.MyGraph;
import unioeste.ia.models.MyNode;
import unioeste.ia.models.Solver;

import java.util.Stack;

public class DepthSolver implements Solver {
    private boolean isSolved = false;
    private Stack<MyNode> stack;
    private final MyGraph graph;
    private MyNode current;

    public DepthSolver(MyGraph graph) {
       this.graph = graph;
       this.stack = new Stack<>();

       graph.reset();
       stack.add(graph.origin);
    }

    @Override
    public void next() {
        current = stack.pop();
        current.visited = true;

        if (current == graph.destination) {
            this.isSolved = true;
            this.stack.clear();
        }

        for (Edge edge : current.edges) {
            if (edge.dest.visited) {
                continue;
            }
            if (stack.contains(edge.dest)) {
                edge.dest.previousNode = current;
                continue;
            }

            edge.dest.previousNode = current;
            stack.add(edge.dest);
        }

    }

    @Override
    public void solve() {
        while (!stack.isEmpty()) {
            next();
        }
    }

    @Override
    public MyNode getCurrentNode() {
        return current;
    }

    @Override
    public boolean isSolved() {
        return isSolved;
    }
}
