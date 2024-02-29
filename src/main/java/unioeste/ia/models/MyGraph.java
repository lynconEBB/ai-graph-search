package unioeste.ia.models;

import java.util.List;

public class MyGraph {
    public MyNode origin;
    public MyNode destination;
    public List<Edge> edges;

    public MyGraph(MyNode origin, MyNode destination, List<Edge> edges) {
        this.origin = origin;
        this.destination = destination;
        this.edges = edges;
    }
}
