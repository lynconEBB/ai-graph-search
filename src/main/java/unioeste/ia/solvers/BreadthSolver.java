package unioeste.ia.solvers;

import unioeste.ia.models.Edge;
import unioeste.ia.models.MyGraph;
import unioeste.ia.models.MyNode;
import unioeste.ia.models.Solver;

import java.util.LinkedList;
import java.util.Queue;

public class BreadthSolver implements Solver {

    private boolean isSolved = false;
    private Queue<MyNode> queue;
    private final MyGraph graph;
    private MyNode current;

    public BreadthSolver(MyGraph graph) {
       this.graph = graph;
       this.queue = new LinkedList<>();

        this.graph.reset();
        this.queue.add(graph.origin);
    }
    @Override
    public void next() {

        current = queue.poll();
        current.visited = true;

        if (current == graph.destination) {
            this.isSolved = true;
            this.queue.clear();
        }

        for (Edge edge : current.edges) {
            if (edge.dest.visited) {
                continue;
            }
            if (queue.contains(edge.dest)) {
                edge.dest.previousNode = current;
                continue;
            }

            edge.dest.previousNode = current;
            queue.add(edge.dest);
        }
    }

    @Override
    public void solve() {
        while (!queue.isEmpty()) {
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
