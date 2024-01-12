package services;

import java.util.InputMismatchException;
import java.util.Scanner;

public class pantau {
    public static void main(){
        
    }

    public static int inputValidasi() {
        Scanner scanner = new Scanner(System.in);
        int value = 0;
        boolean validInput = false;

        while (!validInput) {
            // System.out.print("Masukkan angka: ");

            try {
                value = scanner.nextInt();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Input yang diperbolehkan hanya numeric");
                scanner.next(); // Hapus input yang bukan angka
            }
        }
        return value;
    }
}
