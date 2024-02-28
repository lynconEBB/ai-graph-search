package unioeste.ia.models;

import java.util.ArrayList;
import java.util.List;

public class Node {
    String name;
    public List<Edge> edges;

    public Node() {
        this.edges = new ArrayList<>();
    }

    public Node(String name) {
        this.name = name;
        this.edges = new ArrayList<>();
    }
}
