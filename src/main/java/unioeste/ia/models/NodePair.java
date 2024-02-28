package unioeste.ia.models;

import java.util.Objects;

public class NodePair {
    public Node first;
    public Node second;

    public NodePair(Node first, Node second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        NodePair nodePair = (NodePair) o;
        return (Objects.equals(first.name, nodePair.first.name) && Objects.equals(second.name, nodePair.second.name))
                || (Objects.equals(second.name, nodePair.first.name) && Objects.equals(first.name, nodePair.second.name));
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
