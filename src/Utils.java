import java.util.ArrayList;
import java.util.List;

class Utils {

    static Result calculate(double[][] a, int sizeX1, double[] y, double[] ck, double[] ckj) {
        double[] x = y;
        double[] tempX;
        double bestRes = Utils.function(a, x, sizeX1, ck, ckj); //initial res when х=у
        double temp_res;
        final double eps = 0.03;
        do {
            tempX = Matrix.add(Matrix.multiply(a, x), y); //x=Ax+y
            temp_res = Utils.function(a, tempX, sizeX1, ck, ckj);
            if (temp_res >= 0 && temp_res < bestRes && Utils.productionEnoughForConsumption(a, sizeX1, tempX, y) && Utils.pollutionLevelAllowed(a, sizeX1, tempX, y)) {
                x = tempX;
                bestRes = temp_res;
            } else break;
        } while (Math.abs(temp_res - bestRes) > eps);
        return new Result(bestRes, a, x);
    }

    static List<Double>[][] generate(final int dimA, final int countOfProductionMethods) {
        List<Double>[][] a = new ArrayList[dimA][dimA];
        for (int i = 0; i < dimA; ++i) {
            for (int j = 0; j < dimA; ++j) {
                a[i][j] = new ArrayList<>(countOfProductionMethods);
                for (int k = 0; k < countOfProductionMethods; ++k) {
                    a[i][j].add(Math.random() / dimA);
                }
            }
        }
        return a;
    }

    static List<Node> addBlockOfNodes(Node parentNode, List<Double>[][] a, final int col, final int countOfProductionMethods, final int dimA) {
        List<Node> array = new ArrayList<>();
        for (int k = 0; k < countOfProductionMethods; ++k) {
            array.add(addInsideNodes(parentNode, a, col, k, dimA));
        }
        return array;
    }

    private static Node addInsideNodes(Node parentNode, List<Double>[][] a, int col, int insideCol, final int dimA) {
        List<Double> array = new ArrayList<>(dimA);
        for (int j = 0; j < dimA; ++j) {
            array.add(a[j][col].get(insideCol));
        }
        Node node = new Node(array);
        parentNode.addNode(node);
        return node;
    }

    static boolean checkedConditions(double[][] a, int sizeX1, double[] y) {
        return positiveSolutionsExist(a, sizeX1, y) && productionEnoughForConsumption(a, sizeX1, y, y) && pollutionLevelAllowed(a, sizeX1, y, y);
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

    private static boolean pollutionLevelAllowed(double[][] a, int sizeX1, double[] x, double[] y) {
        double[] res = new double[sizeX1];
        for (int i = sizeX1; i < a.length; ++i) {
            for (int j = 0; j < sizeX1; ++j) {
                res[i - sizeX1] += a[i][j] * x[j];
            }
        }
        double[] tempRes = new double[sizeX1];
        for (int i = sizeX1; i < a.length; ++i) {
            for (int j = sizeX1; j < a.length; ++j) {
                tempRes[i - sizeX1] += (1 - a[i][j]) * x[j];
            }
        }
        res = Matrix.subtract(tempRes, res);
        double res1 = 0;
        double res2 = 0;
        for (double re : res) {
            res1 += re * re;
        }
        res1 = Math.sqrt(res1);
        for (int i = sizeX1; i < y.length; i++) {
            res2 += y[i] * y[i];
        }
        res2 = Math.sqrt(res2);
        return !(res1 < res2);
    }

    private static boolean positiveSolutionsExist(double[][] a, int sizeX1, double[] y) {
        double[] res = new double[sizeX1];
        for (int i = sizeX1; i < a.length; i++) {
            for (int j = 0; j < sizeX1; j++) {
                res[i - sizeX1] += a[i][j] * y[j];
            }
        }
        double res1 = 0;
        double res2 = 0;
        for (double re : res) {
            res1 += re * re;
        }
        res1 = Math.sqrt(res1);

        for (int i = sizeX1; i < y.length; i++) {
            res2 += y[i] * y[i];
        }
        res2 = Math.sqrt(res2);
        return !(res1 < res2);
    }

    private static boolean productionEnoughForConsumption(double[][] a, int sizeX1, double[] x, double[] y) {
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
        for (double re : res) {
            res1 += re * re;
        }
        res1 = Math.sqrt(res1);

        for (int i = 0; i < sizeX1; i++) {
            res2 += y[i] * y[i];
        }
        res2 = Math.sqrt(res2);
        return !(res1 < res2);
    }
}
