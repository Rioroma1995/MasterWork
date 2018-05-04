import java.util.ArrayList;
import java.util.List;

class Node {
    private List<Double> value;
    private List<Node> nodes;

    Node(List<Double> value) {
        this.value = value;
    }

    List<Node> getNodes() {
        return nodes;
    }

    List<Double> getValue() {
        return value;
    }

    void addNode(Node node) {
        if (nodes == null) {
            nodes = new ArrayList<>();
        }
        nodes.add(node);
    }
}
