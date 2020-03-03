import java.io.*;
import java.util.*;

public class Jacobi {
    static ArrayList<Double> normalisation_vector = new ArrayList<>();

    public static ArrayList<ArrayList<Double>> matrixReducer(ArrayList<ArrayList<Double>> matrix, int size) {
        boolean[][] boolMatrix = new boolean[size][size];
        for (int i = 0; i < size; i++) {
            double sum = 0;
            for (int j = 0; j < size; j++) {
                if (i != j)
                sum += Math.abs(matrix.get(i).get(j));
            }
            for (int j = 0; j < size; j++) {
                if (2 * Math.abs(matrix.get(i).get(j)) >= sum) {
                    boolMatrix[i][j] = true;
                }
            }
        }
        boolean found;
        ArrayList<ArrayList<Double>> tmp = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            found = false;
            for (int j = 0; j < size; j++) {
                if (boolMatrix[j][i]) {
                    found = true;
                    tmp.add(i, matrix.get(j));
                    break;
                }
            }
            if (!found) return null;
        }
        matrix = tmp;
        return matrix;
    }

    static int counter = 0;
    static ArrayList<Double> first_approach = new ArrayList<>();

    public static void matrixSolver(ArrayList<ArrayList<Double>> matrix, int size, double eps) {
        double normalisation;
        ArrayList<Double> current_approach = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            first_approach.add(matrix.get(i).get(size) / matrix.get(i).get(i));
        }
        do {
            for (int i = 0; i < size; i++) {
                double sum = 0;
                for (int j = 0; j < size; j++) {
                    if (j != i)
                        sum += matrix.get(i).get(j) * first_approach.get(j);
                }
                current_approach.add(i, (matrix.get(i).get(size) - sum) / matrix.get(i).get(i));
            }
            normalisation = normalisationCalculation(first_approach, current_approach, size);
            for (int i = 0; i < size; i++) {
                first_approach.set(i, current_approach.get(i));
            }
            counter++;
        } while (normalisation > eps);
    }

    static double normalisationCalculation(ArrayList<Double> x1, ArrayList<Double> x2, int n) {
        double sum = 0;
        for (int i = 0; i < n; i++) {
            sum = Math.abs(x1.get(i) - x2.get(i));
        }
        normalisation_vector.add(sum);
        return sum;
    }

    public static void printSolution(ArrayList<ArrayList<Double>> matrix) {
        System.out.println("Reduced matrix: ");
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.size() + 1; j++) {
                System.out.print(matrix.get(i).get(j) + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.err.println("Solution: " + first_approach);
        System.out.println("Iterations: " + counter);
        System.out.println("Normalisation vector: " + normalisation_vector.subList(normalisation_vector.size() - matrix.size(), normalisation_vector.size()) + "\n");
    }

    public static ArrayList<ArrayList<Double>> matrixFromFile(int size, String filename) throws IOException {
        ArrayList<ArrayList<Double>> matrix = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("src/" + filename + ".txt"));
        String tmp;
        while ((tmp = reader.readLine()) != null) {
            ArrayList<Double> temp_arr = new ArrayList<>();
            String[] line = tmp.trim().split(" ");
            for (String s : line) {
                double temp = Double.parseDouble(s);
                temp_arr.add(temp);
            }
            matrix.add(temp_arr);
        }
        return matrix;
    }
}

