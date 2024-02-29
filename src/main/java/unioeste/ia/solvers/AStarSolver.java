package unioeste.ia.solvers;

import unioeste.ia.models.Edge;
import unioeste.ia.models.MyGraph;
import unioeste.ia.models.MyNode;
import unioeste.ia.models.Solver;

import java.util.Comparator;
import java.util.PriorityQueue;

public class AStarSolver implements Solver {

    private boolean isSolved = false;
    private MyNode currentNode;
    private final MyGraph graph;
    PriorityQueue<MyNode> queue = new PriorityQueue<>(Comparator.comparingInt(n -> n.totalDistance));

    public AStarSolver(MyGraph graph) {
        this.graph = graph;

        // resetar grafo (visitar pra falso, min Dist pra infinito, e total dist para null)

        this.graph.origin.minDistanceFromStart = 0;
        this.queue.add(graph.origin);
    }

    @Override
    public void next() {
        currentNode = queue.poll();
        currentNode.visited = true;

        if (currentNode == graph.destination) {
            this.isSolved = true;
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
        this.isSolved = true;
    }

    @Override
    public MyNode getCurrentNode() {
        return currentNode;
    }

    @Override
    public boolean isSolved() {
        return isSolved;
    }
}
