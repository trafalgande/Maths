import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        int size;
        ArrayList<ArrayList<Double>> matrix = new ArrayList<>();
        double eps = 0.0001;
        int a = 0;
        do {
            System.out.println("MENU: \n 1 - Input data from console \n 2 - Input data from file");
            a = scanner.nextInt();
        } while (a == 0);

        switch (a) {
            case 1:
                System.out.println("Input data: ");
                Scanner another_scanner = new Scanner(System.in);
                size = another_scanner.nextInt();
                eps = another_scanner.nextDouble();
                for (int i = 0; i < size; i++) {
                    matrix.add(new ArrayList<>());
                    for (int j = 0; j < size + 1; j++) {
                        matrix.get(i).add(another_scanner.nextDouble());
                    }
                }
                break;
            case 2:
                System.out.println("Input file name: ");
                Scanner another_one_scanner = new Scanner(System.in);
                String filename = another_one_scanner.nextLine();
                size = 5;
                matrix = Jacobi.matrixFromFile(size, filename);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + a);
        }
        matrix = Jacobi.matrixReducer(matrix, size);
        Jacobi.matrixSolver(matrix, size, eps);
        assert matrix != null;
        Jacobi.printSolution(matrix);

    }
}


