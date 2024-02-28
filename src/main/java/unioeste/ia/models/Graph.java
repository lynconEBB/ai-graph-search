package unioeste.ia.models;

import java.util.HashMap;
import java.util.Map;

public class Graph {
    public Node origin;

    public Node destination;

    public Map<NodePair, Integer> heuristicMap;

    public Graph(Node origin, Node destination, Map<NodePair, Integer> heuristicMap) {
        this.origin = origin;
        this.destination = destination;
        this.heuristicMap = heuristicMap;
    }
}
