package services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import controllers.hasil;
import controllers.kota;
import controllers.provinsi;

public class pantau {
    public static void main() throws SQLException {
        List<hasil> hasilList;
        List<provinsi> daftarProvinsi;
        List<Integer> idProv;
        List<kota> daftarKota;
        List<Integer> idKota;

        System.out.println("<<<---PEMANTAUAN SUARA--->>> \n\nmenu:");
        System.out.print(
                "(1) Pantau keseluruhan \n(2) Pantau Provinsi \n(3) Pantau Kota \n(4) Pantau Kecamatan \n(5) Pantau Desa \n\nPilih salah satu menu:");
        switch (inputValidasi()) {
            case 1:
                hasilList = hasil.getAll();
                System.out.printf("%n%-15s %-15s %-15s %-15s %-15s %-15s%n", "PROVINSI", "PASLON 1", "PASLON 2",
                        "PASLON 3", "GOLPUT", "TOTAL");
                if (hasilList != null) {
                    int total1 = 0;
                    int total2 = 0;
                    int total3 = 0;
                    int totalGolput = 0;
                    int totalAll = 0;
                    for (hasil hasil : hasilList) {
                        total1 += hasil.suara1();
                        total2 += hasil.suara2();
                        total3 += hasil.suara3();
                        totalGolput += hasil.golput();
                        int total = hasil.suara1() + hasil.suara2() + hasil.suara3() + hasil.golput();
                        totalAll += total;
                        System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s%n", hasil.getBy(),
                                hasil.suara1() + "(" + persen(hasil.suara1(), total) + "%)",
                                hasil.suara2() + "(" + persen(hasil.suara2(), total) + "%)",
                                hasil.suara3() + "(" + persen(hasil.suara3(), total) + "%)",
                                hasil.golput() + "(" + persen(hasil.golput(), total) + "%)", total);
                    }
                    System.out.println("\n<<<---SUMMARY--->>>");
                    System.out.printf("%-15s %-15s %-15s %-15s %-15s%n", "PASLON 1", "PASLON 2", "PASLON 3", "GOLPUT",
                            "TOTAL");
                    System.out.printf("%-15s %-15s %-15s %-15s %-15s%n", total1 + "(" + persen(total1, totalAll) + "%)",
                            total2 + "(" + persen(total2, totalAll) + "%)",
                            total3 + "(" + persen(total3, totalAll) + "%)",
                            totalGolput + "(" + persen(totalGolput, totalAll) + "%)", totalAll);
                } else {
                    System.out.println("Gagal mengambil data.");
                    break;
                }
                break;

            case 2:
                daftarProvinsi = provinsi.getAllProvinsi();
                idProv = new ArrayList<>();

                // Menampilkan data Provinsi yang telah diambil dari database
                if (daftarProvinsi != null) {
                    System.out.println("Pilih Provinsi:");
                    for (provinsi provinsi : daftarProvinsi) {
                        idProv.add(provinsi.getId());
                        System.out.println("(" + provinsi.getId() + ") " + provinsi.getNama());
                    }
                    System.out.print("Masukkan nomor provinsi yang anda pilih:");
                } else {
                    System.out.println("Gagal mengambil data Provinsi.");
                }

                // input provinsi yang dipilih
                int prov = inputValidasi();

                if (!idProv.contains(prov)) {
                    do {
                        System.out.print("\nInput anda tidak valid. \nMasukkan kembali nomor provinsi yang tersedia: ");
                        prov = inputValidasi();
                    } while (!idProv.contains(prov));
                }

                hasilList = hasil.getProv(prov);
                System.out.printf("%n%-15s %-15s %-15s %-15s %-15s %-15s%n", "KOTA", "PASLON 1", "PASLON 2",
                        "PASLON 3", "GOLPUT", "TOTAL");
                if (hasilList != null) {
                    int total1 = 0;
                    int total2 = 0;
                    int total3 = 0;
                    int totalGolput = 0;
                    int totalAll = 0;
                    for (hasil hasil : hasilList) {
                        total1 += hasil.suara1();
                        total2 += hasil.suara2();
                        total3 += hasil.suara3();
                        totalGolput += hasil.golput();
                        int total = hasil.suara1() + hasil.suara2() + hasil.suara3() + hasil.golput();
                        totalAll += total;
                        System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s%n", hasil.getBy(),
                                hasil.suara1() + "(" + persen(hasil.suara1(), total) + "%)",
                                hasil.suara2() + "(" + persen(hasil.suara2(), total) + "%)",
                                hasil.suara3() + "(" + persen(hasil.suara3(), total) + "%)",
                                hasil.golput() + "(" + persen(hasil.golput(), total) + "%)", total);
                    }
                    System.out
                            .println("\n<<<---SUMMARY PROVINSI " + provinsi.getProvinsiById(prov).getNama() + "--->>>");
                    System.out.printf("%-15s %-15s %-15s %-15s %-15s%n", "PASLON 1", "PASLON 2", "PASLON 3", "GOLPUT",
                            "TOTAL");
                    System.out.printf("%-15s %-15s %-15s %-15s %-15s%n", total1 + "(" + persen(total1, totalAll) + "%)",
                            total2 + "(" + persen(total2, totalAll) + "%)",
                            total3 + "(" + persen(total3, totalAll) + "%)",
                            totalGolput + "(" + persen(totalGolput, totalAll) + "%)", totalAll);
                } else {
                    System.out.println("Gagal mengambil data.");
                    break;
                }
                break;

            case 3:
                daftarProvinsi = provinsi.getAllProvinsi();
                idProv = new ArrayList<>();

                // Menampilkan data Provinsi yang telah diambil dari database
                if (daftarProvinsi != null) {
                    System.out.println("Pilih Provinsi:");
                    for (provinsi provinsi : daftarProvinsi) {
                        idProv.add(provinsi.getId());
                        System.out.println("(" + provinsi.getId() + ") " + provinsi.getNama());
                    }
                    System.out.print("Masukkan nomor provinsi yang anda pilih:");
                } else {
                    System.out.println("Gagal mengambil data Provinsi.");
                }

                // input provinsi yang dipilih
                prov = inputValidasi();

                if (!idProv.contains(prov)) {
                    do {
                        System.out.print("\nInput anda tidak valid. \nMasukkan kembali nomor provinsi yang tersedia: ");
                        prov = inputValidasi();
                    } while (!idProv.contains(prov));
                }

                daftarKota = kota.getKotaInProvinsi(prov);
                idKota = new ArrayList<>();

                // Menampilkan data kota yang telah diambil dari database
                if (daftarKota != null) {
                    System.out.println(
                            "\nBerikut ini daftar kota yang ada di provinsi "
                                    + provinsi.getProvinsiById(prov).getNama());
                    for (kota kota : daftarKota) {
                        idKota.add(kota.getId());
                        System.out.println("(" + kota.getId() + ") " + kota.getNama());
                    }
                    System.out.print("Masukkan nomor kota yang anda pilih:");
                } else {
                    System.out.println("Gagal mengambil data Provinsi.");
                }

                // input kota yang dipilih
                int selectedKota = inputValidasi();

                if (!idKota.contains(selectedKota)) {
                    do {
                        System.out.print("\nInput anda tidak valid. \nMasukkan kembali nomor kota yang tersedia: ");
                        selectedKota = inputValidasi();
                    } while (!idKota.contains(selectedKota));
                }

                hasilList = hasil.getKota(selectedKota);
                System.out.printf("%n%-20s %-15s %-15s %-15s %-15s %-15s%n", "KECAMATAN", "PASLON 1", "PASLON 2",
                        "PASLON 3", "GOLPUT", "TOTAL");
                if (hasilList != null) {
                    int total1 = 0;
                    int total2 = 0;
                    int total3 = 0;
                    int totalGolput = 0;
                    int totalAll = 0;
                    for (hasil hasil : hasilList) {
                        total1 += hasil.suara1();
                        total2 += hasil.suara2();
                        total3 += hasil.suara3();
                        totalGolput += hasil.golput();
                        int total = hasil.suara1() + hasil.suara2() + hasil.suara3() + hasil.golput();
                        totalAll += total;
                        System.out.printf("%-20s %-15s %-15s %-15s %-15s %-15s%n", hasil.getBy(),
                                hasil.suara1() + "(" + persen(hasil.suara1(), total) + "%)",
                                hasil.suara2() + "(" + persen(hasil.suara2(), total) + "%)",
                                hasil.suara3() + "(" + persen(hasil.suara3(), total) + "%)",
                                hasil.golput() + "(" + persen(hasil.golput(), total) + "%)", total);
                    }
                    System.out
                            .println("\n<<<---SUMMARY KOTA " + kota.getKotaById(selectedKota) + "--->>>");
                    System.out.printf("%-15s %-15s %-15s %-15s %-15s%n", "PASLON 1", "PASLON 2", "PASLON 3", "GOLPUT",
                            "TOTAL");
                    System.out.printf("%-15s %-15s %-15s %-15s %-15s%n", total1 + "(" + persen(total1, totalAll) + "%)",
                            total2 + "(" + persen(total2, totalAll) + "%)",
                            total3 + "(" + persen(total3, totalAll) + "%)",
                            totalGolput + "(" + persen(totalGolput, totalAll) + "%)", totalAll);
                } else {
                    System.out.println("Gagal mengambil data.");
                    break;
                }                
                break;
            default:
                break;
        }
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

    private static double persen(int value, int jumlah) {
        double hasil = Math.round((value * 100.0 / jumlah) * 100.0) / 100.0;

        return hasil;
    }
}