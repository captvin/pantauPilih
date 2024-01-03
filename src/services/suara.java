package services;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import controllers.provinsi;
import controllers.tps;
import controllers.desa;
import controllers.kecamatan;
import controllers.kota;

public class suara {
    public static void main() {
        // STEP PILIH PROVINSI
        List<provinsi> daftarProvinsi = provinsi.getAllProvinsi();
        List<Integer> idProv = new ArrayList<>();

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
        Scanner scanner = new Scanner(System.in);
        int prov = scanner.nextInt();

        if (!idProv.contains(prov)) {
            do {
                System.out.print("\nInput anda tidak valid. \nMasukkan kembali nomor provinsi yang tersedia: ");
                prov = scanner.nextInt();
            } while (!idProv.contains(prov));
        }

        // STEP PILIH KOTA
        List<kota> daftarKota = kota.getKotaInProvinsi(prov);
        List<Integer> idKota = new ArrayList<>();

        // Menampilkan data kota yang telah diambil dari database
        if (daftarKota != null) {
            System.out.println(
                    "\nBerikut ini daftar kota yang ada di provinsi " + provinsi.getProvinsiById(prov).getNama());
            for (kota kota : daftarKota) {
                idKota.add(kota.getId());
                System.out.println("(" + kota.getId() + ") " + kota.getNama());
            }
            System.out.print("Masukkan nomor kota yang anda pilih:");
        } else {
            System.out.println("Gagal mengambil data Provinsi.");
        }

        // input kota yang dipilih
        int selectedKota = scanner.nextInt();

        if (!idKota.contains(selectedKota)) {
            do {
                System.out.print("\nInput anda tidak valid. \nMasukkan kembali nomor kota yang tersedia: ");
                selectedKota = scanner.nextInt();
            } while (!idKota.contains(selectedKota));
        }

        // STEP PILIH KECAMATAN
        List<kecamatan> daftarKec = kecamatan.getKecInKota(selectedKota);
        List<Integer> idKec = new ArrayList<>();

        // Menampilkan data kecamatan yang telah diambil dari database
        if (daftarKec != null) {
            System.out.println(
                    "\nBerikut ini daftar kecamatan yang ada di kota " + kota.getKotaById(selectedKota));
            for (kecamatan kecamatan : daftarKec) {
                idKec.add(kecamatan.getId());
                System.out.println("(" + kecamatan.getId() + ") " + kecamatan.getNama());
            }
            System.out.print("Masukkan nomor kecamatan yang anda pilih:");
        } else {
            System.out.println("Gagal mengambil data Kecamatan.");
        }

        // input kecamatan yang dipilih
        int selectedKec = scanner.nextInt();

        if (!idKec.contains(selectedKec)) {
            do {
                System.out.print("\nInput anda tidak valid. \nMasukkan kembali nomor kecamatan yang tersedia: ");
                selectedKota = scanner.nextInt();
            } while (!idKec.contains(selectedKota));
        }

        // STEP PILIH DESA
        List<desa> daftarDesa = desa.getDesaInKec(selectedKec);
        List<Integer> idDesa = new ArrayList<>();

        // Menampilkan data desa yang telah diambil dari database
        if (daftarDesa != null) {
            System.out.println(
                    "\nBerikut ini daftar desa yang ada di kecamatan " + kecamatan.getKecById(selectedKec));
            for (desa desa : daftarDesa) {
                idDesa.add(desa.getId());
                System.out.println("(" + desa.getId() + ") " + desa.getNama());
            }
            System.out.print("Masukkan nomor Desa yang anda pilih:");
        } else {
            System.out.println("Gagal mengambil data Desa.");
        }

        // input desa yang dipilih
        int selectedDesa = scanner.nextInt();

        if (!idDesa.contains(selectedDesa)) {
            do {
                System.out.print("\nInput anda tidak valid. \nMasukkan kembali nomor desa yang tersedia: ");
                selectedDesa = scanner.nextInt();
            } while (!idDesa.contains(selectedDesa));
        }

        // STEP PILIH TPS
        List<tps> daftarTps = tps.getTpsInDesa(selectedDesa);
        List<Integer> idTps = new ArrayList<>();

        // Menampilkan data TPS yang telah diambil dari database
        if (daftarTps != null) {
            System.out.println(
                    "\nBerikut ini daftar TPS yang ada di desa " + desa.getDesaById(selectedDesa));
            for (tps tps : daftarTps) {
                idTps.add(tps.getId());
                System.out.println("(" + tps.getId() + ") " + tps.getNama());
            }
            System.out.print("Masukkan nomor TPS yang anda pilih:");
        } else {
            System.out.println("Gagal mengambil data TPS.");
        }

        // input TPS yang dipilih
        int selectedTps = scanner.nextInt();

        if (!idTps.contains(selectedTps)) {
            do {
                System.out.print("\nInput anda tidak valid. \nMasukkan kembali nomor TPS yang tersedia: ");
                selectedTps = scanner.nextInt();
            } while (!idTps.contains(selectedTps));
        }

        System.out.println("\nAnda memilih TPS " + tps.getTpsById(selectedTps).getNama()
                + " dengan jumlah pemilih sebanyak " + tps.getTpsById(selectedTps).getJumlah() + " orang");

        System.out.println("Input perolehan suara paslon 01:");
        int suara1 = inputValidasi();
        System.out.println("Input perolehan suara paslon 02:");
        int suara2 = inputValidasi();
        System.out.println("Input perolehan suara paslon 03:");
        int suara3 = inputValidasi();
        int golput;

        if (suara1 + suara2 + suara3 > tps.getTpsById(selectedTps).getJumlah()) {
            System.out.println("kebanyakan anjing");
        }
    }

    public static int inputValidasi() {
        Scanner scanner = new Scanner(System.in);
        int value = 0;
        boolean validInput = false;

        while(!validInput){
            System.out.print("Masukkan angka: ");

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