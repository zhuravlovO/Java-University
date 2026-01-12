package Lab1.src;

import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        try {
            int rows = 4;
            int cols = 4;
            final long a = 2;

            long[][] matrixB = generateMatrix(rows, cols);

            System.out.println("Matrix B:");
            printMatrix(matrixB);

            long[][] matrixC = multiplyByConstant(matrixB, a);
            System.out.println("Matrix C (a * B, where a=" + a + "):");
            printMatrix(matrixC);

            System.out.println("Calculating result (Min in even cols, Max in odd cols):");
            long result = calculateTask7(matrixC);
            System.out.println("Final result: " + result);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static long[][] generateMatrix(int rows, int cols) {
        Random rand = new Random();
        long[][] matrix = new long[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = rand.nextInt(20);
            }
        }
        return matrix;
    }

    private static void printMatrix(long[][] matrix) {
        for (long[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }

    private static long[][] multiplyByConstant(long[][] matrix, long constant) {
        long[][] resultMatrix = new long[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                resultMatrix[i][j] = matrix[i][j] * constant;
            }
        }
        return resultMatrix;
    }

    private static long calculateTask7(long[][] matrix) {
        long sum = 0;
        for (int j = 0; j < matrix[0].length; j++) {
            long extremeVal = matrix[0][j];
            for (int i = 1; i < matrix.length; i++) {
                if (j % 2 == 0) {
                    if (matrix[i][j] < extremeVal) {
                        extremeVal = matrix[i][j];
                    }
                } else {
                    if (matrix[i][j] > extremeVal) {
                        extremeVal = matrix[i][j];
                    }
                }
            }
            String type = (j % 2 == 0) ? "min" : "max";
            System.out.println("Column " + j + " (" + type + "): " + extremeVal);
            sum += extremeVal;
        }
        return sum;
    }
}