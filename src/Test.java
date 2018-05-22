import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class Test {
    private static int dimA = 10;
    private static int countOfProductionMethods = 3;
    private static double[] ck = {1.5, 1, 0.7, 2, 1.4};
    private static double[] ckj = {0.2, 0.4, 0.3, 0.2, 0.2};
    private static double[] y = {4, 5, 3, 4, 5, 0.4, 0.6, 0.7, 0.3, 0.6};
    private static int productTypes = dimA / 2;


    public static void main(String[] args) {
        /*double[][] a11 = {{0.1, 0.2}, {0.5, 0.6}};
        double[][] a12 = {{0.3, 0.4}, {0.7, 0.8}};
        double[][] a21 = {{0.11, 0.12}, {0.15, 0.16}};
        double[][] a22 = {{0.13, 0.14}, {0.17, 0.18}};*/
        List<Double>[][] a = Utils.generate(dimA, countOfProductionMethods);
        testAlgorithm(a);
        testImprovedAlgorithm(a);
    }


    private static void testAlgorithm(List<Double>[][] a) {
        long startTime = System.nanoTime();
        Optional<Result> result = Algorithm.solve(a, productTypes, y, ck, ckj, dimA, countOfProductionMethods);
        long endTime = System.nanoTime();
        System.out.println("Total Algorithm time: " + TimeUnit.NANOSECONDS.toMillis(endTime - startTime));
        printResult(result);
    }

    private static void testImprovedAlgorithm(List<Double>[][] a) {
        long startTime = System.nanoTime();
        Optional<Result> result = ImprovedAlgorithm.solve(a, productTypes, y, ck, ckj, dimA, countOfProductionMethods);
        long endTime = System.nanoTime();
        System.out.println("Total ImprovedAlgorithm time: " + TimeUnit.NANOSECONDS.toMillis(endTime - startTime));
        printResult(result);
    }

    private static void printResult(Optional<Result> result){
        result.ifPresent(res -> {
            System.out.println("finalX: ");
            Matrix.print(res.getVectorX());
            System.out.println("finalA: ");
            Matrix.print(res.getMatrixA());
            System.out.println("finalRes: " + res.getResult());
        });
    }
}
