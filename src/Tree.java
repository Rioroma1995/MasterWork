import java.util.ArrayList;
import java.util.List;

class Tree {
    private Node root;
    private List<List<List<Double>>> finalPaths = new ArrayList<>();

    void addRoot(Node root) {
        this.root = root;
    }

    Node getRoot() {
        return root;
    }

    List<List<List<Double>>> printPaths(Node node) {
        List<List<Double>> path = new ArrayList<>(1000);
        printPathsRecursive(node, path, 0);
        return finalPaths;
    }

    private void printPathsRecursive(Node node, List<List<Double>> path, int pathLen) {
        if (node == null) {
            return;
        }
        if (node.getValue() != null) {
            path.add(pathLen, node.getValue());
            ++pathLen;
        }
        if (node.getNodes() == null || node.getNodes().isEmpty()) {
            printArray(path, pathLen);
        } else {
            for (Node nod : node.getNodes()) {
                printPathsRecursive(nod, path, pathLen);
            }
        }
    }

    private void printArray(List<List<Double>> path, int len) {
        List<List<Double>> tempPath = new ArrayList<>();
        for (int i = 0; i < len; ++i) {
            tempPath.add(path.get(i));
        }
        finalPaths.add(tempPath);
    }
}
 