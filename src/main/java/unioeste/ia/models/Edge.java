package unioeste.ia.models;

public class Edge {
    public MyNode src;
    public MyNode dest;
    public int distance;

    public Edge(MyNode src, MyNode destNode, int distance) {
        this.src = src;
        this.dest = destNode;
        this.distance = distance;
    }
}
