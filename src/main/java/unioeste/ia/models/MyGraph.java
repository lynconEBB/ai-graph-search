package unioeste.ia.models;

import java.util.List;
import java.util.Map;

public class MyGraph {
    public MyNode origin;

    public MyNode destination;

    public List<Edge> edges;

    public Map<NodePair, Integer> heuristicMap;

    public MyGraph(MyNode origin, MyNode destination, Map<NodePair, Integer> heuristicMap, List<Edge> edges) {
        this.origin = origin;
        this.destination = destination;
        this.heuristicMap = heuristicMap;
        this.edges = edges;
    }
}
