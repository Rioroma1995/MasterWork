import java.util.ArrayList;

public class Node {
    private ArrayList<Double> value;
    private ArrayList<Node> nodes;

    public Node(ArrayList<Double> value) {
        this.value = value;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public ArrayList<Double> getValue() {
        return value;
    }

    public void addNode(Node node) {
        if (nodes == null)
            nodes = new ArrayList<>();
        nodes.add(node);
    }
}
