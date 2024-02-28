package unioeste.ia.models;

import java.util.Objects;

public class NodePair {
    public MyNode first;
    public MyNode second;

    public NodePair(MyNode first, MyNode second) {
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
        return (first.name.equals(nodePair.first.name) && second.name.equals(nodePair.second.name))
                || (second.name.equals(nodePair.first.name) && first.name.equals(nodePair.second.name));
    }

    @Override
    public int hashCode() {
        return Objects.hash(first.name) ^ Objects.hash(second.name);
    }
}
