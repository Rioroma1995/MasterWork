import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class Algorithm {

    static Optional<Result> solve(List<Double>[][] a, int sizeX1, double[] y, double[] ck, double[] ckj, final int dimA, final int countOfProductionMethods) {
        Optional<Result> result = Optional.of(new Result(Integer.MAX_VALUE, null, y));
        List<List<List<Double>>> paths = workWithTree(a, dimA, countOfProductionMethods);
        for (List<List<Double>> path : paths) {
            double[][] matrixA = new double[dimA][dimA];
            for (int col = 0; col < path.size(); ++col) {
                for (int row = 0; row < path.get(col).size(); ++row) {
                    matrixA[row][col] = path.get(col).get(row);
                }
            }
            if (Utils.checkedConditions(matrixA, sizeX1, y)) {
                Optional<Result> res = Optional.of(Utils.calculate(matrixA, sizeX1, y, ck, ckj));
                result = result.get().getResult() < res.get().getResult() ? result : res;
            }
        }
        return result;
    }

    private static List<List<List<Double>>> workWithTree(List<Double>[][] a, final int dimA, final int countOfProductionMethods) {
        Tree tree = new Tree();
        tree.addRoot(new Node(null));
        List<Node> nodes = new ArrayList<>();
        List<Node> tempNodes = new ArrayList<>();
        nodes.add(tree.getRoot());
        for (int col = 0; col < dimA; ++col) {
            for (Node nod : nodes) {
                tempNodes.addAll(Utils.addBlockOfNodes(nod, a, col, countOfProductionMethods, dimA));
            }
            nodes = new ArrayList<>(tempNodes);
            Collections.copy(nodes, tempNodes);
            tempNodes.clear();
        }
        return tree.printPaths(tree.getRoot());
    }

    static double[] solve(double[][] a11, double[][] a12, double[][] a21, double[][] a22, int sizeX1, double[] y, double[] ck, double[] ckj) {
        double[][] a = Matrix.buildA(a11, a12, a21, a22);
        if (Utils.checkedConditions(a, sizeX1, y)) {
            Utils.calculate(a, sizeX1, y, ck, ckj);
        }
        return null;
    }
}