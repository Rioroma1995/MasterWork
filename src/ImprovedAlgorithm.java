import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

class ImprovedAlgorithm {

    static Optional<Result> solve(List<Double>[][] a, int sizeX1, double[] y, double[] ck, double[] ckj, final int dimA, final int countOfProductionMethods) {
        List<List<List<Double>>> paths = workWithTree(a, dimA, countOfProductionMethods);
        return paths.parallelStream()
                .map(path -> {
                    double[][] matrixA = new double[dimA][dimA];
                    for (int col = 0; col < path.size(); ++col) {
                        for (int row = 0; row < path.get(col).size(); ++row) {
                            matrixA[row][col] = path.get(col).get(row);
                        }
                    }
                    return Utils.checkedConditions(matrixA, sizeX1, y) ? Utils.calculate(matrixA, sizeX1, y, ck, ckj) : null;
                })
                .filter(Objects::nonNull)
                .reduce((result, result2) -> result.getResult() < result2.getResult() ? result : result2);
    }

    private static List<List<List<Double>>> workWithTree(List<Double>[][] a, final int dimA, final int countOfProductionMethods) {
        Tree tree = new Tree();
        tree.addRoot(new Node(null));
        List<Node> nodes = new ArrayList<>();
        nodes.add(tree.getRoot());
        for (int col = 0; col < dimA; ++col) {
            int finalCol = col;
            nodes = nodes.stream().flatMap(nod -> Utils.addBlockOfNodes(nod, a, finalCol, countOfProductionMethods, dimA).stream()).collect(Collectors.toList());
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