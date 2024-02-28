package unioeste.ia.models;

import java.util.ArrayList;
import java.util.List;

public class MyNode {
    String name;
    public List<Edge> edges;

    public MyNode() {
        this.edges = new ArrayList<>();
    }

    public MyNode(String name) {
        this.name = name;
        this.edges = new ArrayList<>();
    }
}
