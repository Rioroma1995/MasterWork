import java.util.*;

public class Algorithm {
    private static int dimA = 10;
    private static int countOfProductionMethods = 3;
    private static double[][] finalA;
    private static double finalRes = Double.MAX_VALUE;
    private static double[] finalX;

    public static void main(String[] args) {
        double[][] a11 = {{0.1, 0.2}, {0.5, 0.6}};
        double[][] a12 = {{0.3, 0.4}, {0.7, 0.8}};
        double[][] a21 = {{0.11, 0.12}, {0.15, 0.16}};
        double[][] a22 = {{0.13, 0.14}, {0.17, 0.18}};
        double[] ck = {1.5, 1, 0.7, 2, 1.4};
        double[] ckj = {0.2, 0.4, 0.3, 0.2, 0.2};
        double[] y = {4, 5, 3, 4, 5, 0.4, 0.6, 0.7, 0.3, 0.6};
        List<Double>[][] a = generate();
        int productTypes = 5;
        solve(a, productTypes, y, ck, ckj);
        System.out.print("finalX: ");
        Matrix.print(finalX);
        System.out.println("finalA: ");
        Matrix.print(finalA);
        System.out.print("finalRes: " + finalRes);
    }

    private static void solve(List<Double>[][] a, int sizeX1, double[] y, double[] ck, double[] ckj) {
        List<List<List<Double>>> paths = workWithTree(a);
        for (List<List<Double>> path : paths) {
            double[][] matrixA = new double[dimA][dimA];
            for (int col = 0; col < path.size(); ++col) {
                for (int row = 0; row < path.get(col).size(); ++row) {
                    matrixA[row][col] = path.get(col).get(row);
                }
            }
            if (checkedConditions(matrixA, sizeX1, y))
                calculate(matrixA, sizeX1, y, ck, ckj);
        }
    }

    private static List<List<List<Double>>> workWithTree(List<Double>[][] a) {
        Tree tree = new Tree();
        tree.addRoot(new Node(null));
        List<Node> nodes = new ArrayList<>();
        List<Node> tempNodes = new ArrayList<>();
        nodes.add(tree.getRoot());
        for (int col = 0; col < dimA; ++col) {
            for (Node nod : nodes) {
                tempNodes.addAll(addBlockOfNodes(nod, a, col));
            }
            nodes = new ArrayList<>(tempNodes);
            Collections.copy(nodes, tempNodes);
            tempNodes.clear();
        }
        return tree.printPaths(tree.getRoot());
    }

    private static List<Node> addBlockOfNodes(Node parentNode, List<Double>[][] a, int col) {
        List array = new ArrayList<Node>();
        for (int k = 0; k < countOfProductionMethods; ++k) {
            array.add(addInsideNodes(parentNode, a, col, k));
        }
        return array;
    }

    private static Node addInsideNodes(Node parentNode, List<Double>[][] a, int col, int insideCol) {
        List array = new ArrayList<Double>(dimA);
        for (int j = 0; j < dimA; ++j) {
            array.add(a[j][col].get(insideCol));
        }
        Node node = new Node(array);
        parentNode.addNode(node);
        return node;
    }

    static double[] solve(double[][] a11, double[][] a12, double[][] a21, double[][] a22, int sizeX1, double[] y, double[] ck, double[] ckj) {
        double[][] a = Matrix.buildA(a11, a12, a21, a22);
        if (checkedConditions(a, sizeX1, y)) {
            calculate(a, sizeX1, y, ck, ckj);
        }
        return null;
    }

    private static List<Double>[][] generate() {
        List<Double>[][] a = new ArrayList[dimA][dimA];
        for (int i = 0; i < dimA; ++i) {
            for (int j = 0; j < dimA; ++j) {
                a[i][j] = new ArrayList(countOfProductionMethods);
                for (int k = 0; k < countOfProductionMethods; ++k)
                    a[i][j].add(Math.random() / dimA);
            }
        }
        return a;
    }

    private static boolean checkedConditions(double[][] a, int sizeX1, double[] y) {
        return isPositiveSolutionsExist(a, sizeX1, y) && isProductionEnoughForConsumption(a, sizeX1, y, y) && isPollutionAllowed(a, sizeX1, y, y);
    }

    private static double[] calculate(double[][] a, int sizeX1, double[] y, double[] ck, double[] ckj) {
        double[] x = y;
        double[] tempX;
        //знайшли початковий результат при х=у
        double res = function(a, x, sizeX1, ck, ckj);
        //System.out.println("initial res: " + res);
        double old_res = Double.MAX_VALUE;
        double eps = 0.005;
        while (old_res - res > eps) {
            old_res = res;
            //System.out.println("");
            //x=Ax+y
            tempX = Matrix.add(Matrix.multiply(a, x), y);
            //System.out.print("x: ");
            //Matrix.print(tempX);
            res = function(a, tempX, sizeX1, ck, ckj);
            //System.out.println("res: " + res);
            if (res >= 0 && res < old_res && isProductionEnoughForConsumption(a, sizeX1, tempX, y) && isPollutionAllowed(a, sizeX1, tempX, y))
                x = tempX;
        }
        res = function(a, x, sizeX1, ck, ckj);
        if (res < finalRes) {
            finalRes = res;
            finalA = a;
            finalX = x;
        }
        return x;
    }

    private static double function(double[][] a, double[] x, int sizeX1, double[] ck, double[] ckj) {
        double res = 0;
        for (int i = 0; i < ck.length; ++i) {
            double tempRes = 0;
            for (int j = 0; j < sizeX1; ++j) {
                tempRes += a[sizeX1 + i][j] * x[j];
            }
            for (int j = sizeX1; j < x.length; ++j) {
                if (j - sizeX1 != i) {
                    tempRes += a[sizeX1 + i][j] * x[j];
                } else {
                    tempRes += (a[sizeX1 + i][j] - 1 + ckj[i] / ck[i]) * x[j];
                }
            }
            res += tempRes * ck[i];
        }
        return res;
    }

    private static boolean isPollutionAllowed(double[][] a, int sizeX1, double[] x, double[] y) {
        double[] res = new double[sizeX1];
        for (int i = sizeX1; i < a.length; ++i)
            for (int j = 0; j < sizeX1; ++j)
                res[i - sizeX1] += a[i][j] * x[j];
        double[] tempRes = new double[sizeX1];
        for (int i = sizeX1; i < a.length; ++i)
            for (int j = sizeX1; j < a.length; ++j)
                tempRes[i - sizeX1] += (1 - a[i][j]) * x[j];
        res = Matrix.subtract(tempRes, res);
        double res1 = 0;
        double res2 = 0;
        for (int i = 0; i < res.length; i++) {
            res1 += res[i] * res[i];
        }
        res1 = Math.sqrt(res1);
        for (int i = sizeX1; i < y.length; i++) {
            res2 += y[i] * y[i];
        }
        res2 = Math.sqrt(res2);
        if (res1 < res2) {
            return false;
        }
        return true;
    }

    private static boolean isPositiveSolutionsExist(double[][] a, int sizeX1, double[] y) {
        double[] res = new double[sizeX1];
        for (int i = sizeX1; i < a.length; i++) {
            for (int j = 0; j < sizeX1; j++) {
                res[i - sizeX1] += a[i][j] * y[j];
            }
        }
        double res1 = 0;
        double res2 = 0;
        for (int i = 0; i < res.length; i++) {
            res1 += res[i] * res[i];
        }
        res1 = Math.sqrt(res1);

        for (int i = sizeX1; i < y.length; i++)
            res2 += y[i] * y[i];
        res2 = Math.sqrt(res2);
        if (res1 < res2) {
            return false;
        }
        return true;
    }

    private static boolean isProductionEnoughForConsumption(double[][] a, int sizeX1, double[] x, double[] y) {
        double[] res = new double[sizeX1];
        for (int i = 0; i < sizeX1; i++) {
            for (int j = 0; j < sizeX1; j++) {
                res[i] += (1 - a[i][j]) * x[j];
            }
        }
        double[] tempRes = new double[sizeX1];
        for (int i = 0; i < sizeX1; i++) {
            for (int j = sizeX1; j < a.length; j++) {
                tempRes[i] += a[i][j] * x[j];
            }
        }
        res = Matrix.subtract(res, tempRes);
        double res1 = 0;
        double res2 = 0;
        for (int i = 0; i < res.length; i++) {
            res1 += res[i] * res[i];
        }
        res1 = Math.sqrt(res1);

        for (int i = 0; i < sizeX1; i++) {
            res2 += y[i] * y[i];
        }
        res2 = Math.sqrt(res2);
        if (res1 < res2) {
            return false;
        }
        return true;
    }
}
