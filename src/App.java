import java.util.Scanner;

import services.pantau;
import services.suara;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n<<<----------PANTAUPILIH 2024---------->>>\n");
        System.out.println("PILIH MENU YANG TERSEDIA");
        System.out.println("1. Input Hasil Suara \n2. Pantau");
        // System.out.println("2. Opsi lain...");

        // Meminta input dari pengguna
        System.out.print("Pilih opsi: ");
        int opsi = scanner.nextInt();

        // Memproses input pengguna
        if (opsi == 1) {
            suara.main(); // Memanggil method untuk menampilkan data provinsi dari Suara.java
        } else if (opsi == 2) {
            pantau.main();
        } else {
            System.out.println("Opsi tidak valid.");
        }

        scanner.close();
    }
}