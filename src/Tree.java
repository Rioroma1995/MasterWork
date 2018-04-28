import java.util.ArrayList;

class Tree {
    private Node root;
    private ArrayList<ArrayList<ArrayList<Double>>> finalPaths = new ArrayList<>();

    public void addRoot(Node root) {
        this.root = root;
    }

    public Node getRoot() {
        return root;
    }

    ArrayList<ArrayList<ArrayList<Double>>> printPaths(Node node) {
        ArrayList<ArrayList<Double>> path = new ArrayList<>(1000);
        printPathsRecursive(node, path, 0);
        return finalPaths;
    }

    void printPathsRecursive(Node node, ArrayList<ArrayList<Double>> path, int pathLen) {
        if (node == null)
            return;
        if (node.getValue() != null) {
            path.add(pathLen, node.getValue());
            ++pathLen;
        }
        if (node.getNodes() == null || node.getNodes().isEmpty())
            printArray(path, pathLen);
        else {
            for (Node nod : node.getNodes()) {
                printPathsRecursive(nod, path, pathLen);
            }
        }
    }

    void printArray(ArrayList<ArrayList<Double>> path, int len) {
        ArrayList<ArrayList<Double>> tempPath = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            //System.out.print(path.get(i) + " ");
            tempPath.add(path.get(i));
        }
        finalPaths.add(tempPath);
        //System.out.println("");
    }
}
 