package unioeste.ia.models;

public class Edge {
    public Node dest;
    public int distance;

    public Edge(Node destNode, int distance) {
       this.dest = destNode;
       this.distance = distance;
    }
}
