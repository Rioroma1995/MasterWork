import java.util.Arrays;
import java.util.Objects;

public class Result {

    private double result;
    private double[][] matrixA;
    private double[] vectorX;

    Result(double result, double[][] matrixA, double[] vectorX) {
        this.result = result;
        this.matrixA = matrixA;
        this.vectorX = vectorX;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result1 = (Result) o;
        return Double.compare(result1.result, result) == 0 &&
                Arrays.equals(matrixA, result1.matrixA) &&
                Arrays.equals(vectorX, result1.vectorX);
    }

    @Override
    public int hashCode() {

        int result1 = Objects.hash(result);
        result1 = 31 * result1 + Arrays.hashCode(matrixA);
        result1 = 31 * result1 + Arrays.hashCode(vectorX);
        return result1;
    }

    public double getResult() {
        return result;
    }

    public double[][] getMatrixA() {
        return matrixA;
    }

    public double[] getVectorX() {
        return vectorX;
    }
}
