import java.util.ArrayList;
import java.util.List;

public class Node {
    private List<Double> value;
    private List<Node> nodes;

    Node(List<Double> value) {
        this.value = value;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public List<Double> getValue() {
        return value;
    }

    public void addNode(Node node) {
        if (nodes == null)
            nodes = new ArrayList<>();
        nodes.add(node);
    }
}
