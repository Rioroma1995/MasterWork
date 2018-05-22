public class Matrix {

    // return c = a + b
    static double[] add(double[] a, double[] b) {
        int m = a.length;
        double[] c = new double[m];
        for (int i = 0; i < m; i++) {
            c[i] = a[i] + b[i];
        }
        return c;
    }

    // return c = a - b
    static double[] subtract(double[] a, double[] b) {
        int m = a.length;
        double[] c = new double[m];
        for (int i = 0; i < m; i++) {
            c[i] = a[i] - b[i];
        }
        return c;
    }

    // return c = a * b
    public static double[][] multiply(double[][] a, double[][] b) {
        int m1 = a.length;
        int n1 = a[0].length;
        int m2 = b.length;
        int n2 = b[0].length;
        if (n1 != m2) throw new RuntimeException("Illegal matrix dimensions.");
        double[][] c = new double[m1][n2];
        for (int i = 0; i < m1; i++) {
            for (int j = 0; j < n2; j++) {
                for (int k = 0; k < n1; k++) {
                    c[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return c;
    }

    // matrix-vector multiplication (y = A * x)
    static double[] multiply(double[][] a, double[] x) {
        int m = a.length;
        int n = a[0].length;
        if (x.length != n) throw new RuntimeException("Illegal matrix dimensions.");
        double[] y = new double[m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                y[i] += a[i][j] * x[j];
            }
        }
        return y;
    }

    static void print(double[][] a) {
        int m = a.length;
        int n = a[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(a[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    static void print(double[] a) {
        for (double anA : a) {
            System.out.print(anA + "\t");
        }
        System.out.println();
    }

    static double[][] buildA(double[][] a11, double[][] a12, double[][] a21, double[][] a22) {
        int col = a11.length + a21.length;
        int row = a11[0].length + a12[0].length;
        double[][] c = new double[col][row];
        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                if (i < a11.length) {
                    if (j < a11[0].length) {
                        c[i][j] = a11[i][j];
                    } else {
                        c[i][j] = a12[i][j - a11[0].length];
                    }
                } else {
                    if (j < a11[0].length) {
                        c[i][j] = a21[i - a11.length][j];
                    } else {
                        c[i][j] = a22[i - a11.length][j - a11[0].length];
                    }
                }
            }
        }
        return c;
    }
}
