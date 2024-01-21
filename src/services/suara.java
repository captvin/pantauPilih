package services;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import controllers.provinsi;
import controllers.tps;
import controllers.desa;
import controllers.hasil;
import controllers.kecamatan;
import controllers.kota;



public class suara {
    private static int selectReferensi(List<?> referensiList, String jenisReferensi) {
        List<Integer> idList = new ArrayList<>();
        
        // Menampilkan data referensi yang telah diambil dari database
        if (referensiList != null && !referensiList.isEmpty()) {
            System.out.println("Pilih " + jenisReferensi + ":");
            for (Object referensi : referensiList) {
                // Pastikan referensi memiliki metode getId() dan getNama()
                int id = (int) invokeMethod(referensi, "getId");
                String nama = (String) invokeMethod(referensi, "getNama");

                idList.add(id);
                System.out.println("(" + id + ") " + nama);
            }
            System.out.print("Masukkan nomor " + jenisReferensi + " yang anda pilih:");
        } else {
            System.out.println("Gagal mengambil data " + jenisReferensi + ".");
        }

        // input referensi yang dipilih
        int selectedReferensi = inputValidasi();

        if (!idList.contains(selectedReferensi)) {
            do {
                System.out.print("\nInput anda tidak valid. \nMasukkan kembali nomor " + jenisReferensi + " yang tersedia: ");
                selectedReferensi = inputValidasi();
            } while (!idList.contains(selectedReferensi));
        }

        return selectedReferensi;
    }

    private static Object invokeMethod(Object object, String methodName) {
        try {
            Method method = object.getClass().getMethod(methodName);
            return method.invoke(object);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main() {

        List<provinsi> daftarProvinsi = provinsi.getAllProvinsi();
        int selectedProv = selectReferensi(daftarProvinsi, "Provinsi");


        List<kota> daftarKota = kota.getKotaInProvinsi(selectedProv);
        int selectedKota = selectReferensi(daftarKota, "Kota");

        List<kecamatan> daftarKec = kecamatan.getKecInKota(selectedKota);
        int selectedKec = selectReferensi(daftarKec, "Kecamatan");

        List<desa> daftarDesa = desa.getDesaInKec(selectedKec);
        int selectedDesa = selectReferensi(daftarDesa, "Desa");

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
        int selectedTps = inputValidasi();

        if (!idTps.contains(selectedTps)) {
            do {
                System.out.print("\nInput anda tidak valid. \nMasukkan kembali nomor TPS yang tersedia: ");
                selectedTps = inputValidasi();
            } while (!idTps.contains(selectedTps));
        }


        boolean edit = false;

        while (!hasil.check(selectedTps) && !edit ) {
            System.out.println("TPS ini telah menginputkan suara. Pilih salah satu opsi dibawah:");
            System.out.println("(1) Pilih TPS lain \n(2) Edit suara dari TPS ini");
            switch (inputValidasi()) {
                case 1 -> {
                    System.out.println(
                            "\nBerikut ini daftar TPS yang ada di desa " + desa.getDesaById(selectedDesa));
                    for (tps tps : daftarTps) {
                        idTps.add(tps.getId());
                        System.out.println("(" + tps.getId() + ") " + tps.getNama());
                    }
                    System.out.print("Masukkan nomor TPS yang anda pilih:");
                    selectedTps = inputValidasi();
                }
                case 2 -> {
                    edit = true;
                }
            }

        }

        if(edit){
            System.out.println("\nAnda memilih TPS " + tps.getTpsById(selectedTps).getNama()
                + " dengan jumlah pemilih sebanyak " + tps.getTpsById(selectedTps).getJumlah() + " orang");
        } else {
            System.out.println("\nAnda mengedit TPS " + tps.getTpsById(selectedTps).getNama()
                + " dengan jumlah pemilih sebanyak " + tps.getTpsById(selectedTps).getJumlah() + " orang");
        }
        

        System.out.println("\n<<<---INPUT DARI PETUGAS--->>>");
        System.out.println("Input perolehan suara paslon 01:");
        int suara1 = inputValidasi();
        System.out.println("Input perolehan suara paslon 02:");
        int suara2 = inputValidasi();
        System.out.println("Input perolehan suara paslon 03:");
        int suara3 = inputValidasi();
        int golput = 0;

        while (suara1 + suara2 + suara3 > tps.getTpsById(selectedTps).getJumlah()) {
            System.out.println(
                    "Input suara yang anda masukkan melebihi jumlah peserta pemilihan pada TPS tersebut \nSilahkan input kembali dengan benar");
            System.out.println("\nInput perolehan suara paslon 01:");
            suara1 = inputValidasi();
            System.out.println("Input perolehan suara paslon 02:");
            suara2 = inputValidasi();
            System.out.println("Input perolehan suara paslon 03:");
            suara3 = inputValidasi();
        }

        System.out.println("\n<<<---INPUT DARI SAKSI--->>>");
        System.out.println("Input suara oleh saksi paslon 01");
        int saksi1 = inputValidasi();
        System.out.println("Input suara oleh saksi paslon 02");
        int saksi2 = inputValidasi();
        System.out.println("Input suara oleh saksi paslon 03");
        int saksi3 = inputValidasi();

        while (saksi1 != suara1 || saksi2 != suara2 || saksi3 != suara3) {
            System.out.println("\nInput dari petugas dan saksi tidak sesuai, silahkan masukkan lagi");

            System.out.println("<<<---INPUT DARI PETUGAS--->>>");
            System.out.println("Input perolehan suara paslon 01:");
            suara1 = inputValidasi();
            System.out.println("Input perolehan suara paslon 02:");
            suara2 = inputValidasi();
            System.out.println("Input perolehan suara paslon 03:");
            suara3 = inputValidasi();

            while (suara1 + suara2 + suara3 > tps.getTpsById(selectedTps).getJumlah()) {
                System.out.println(
                        "Input suara yang anda masukkan melebihi jumlah peserta pemilihan pada TPS tersebut \nSilahkan input kembali dengan benar");
                System.out.println("\nInput perolehan suara paslon 01:");
                suara1 = inputValidasi();
                System.out.println("Input perolehan suara paslon 02:");
                suara2 = inputValidasi();
                System.out.println("Input perolehan suara paslon 03:");
                suara3 = inputValidasi();
            }

            System.out.println("\n<<<---INPUT DARI SAKSI--->>>");
            System.out.println("Input suara oleh saksi paslon 01");
            saksi1 = inputValidasi();
            System.out.println("Input suara oleh saksi paslon 02");
            saksi2 = inputValidasi();
            System.out.println("Input suara oleh saksi paslon 03");
            saksi3 = inputValidasi();
        }

        int jumlah = tps.getTpsById(selectedTps).getJumlah();

        if (suara1 + suara2 + suara3 < jumlah) {
            golput = tps.getTpsById(selectedTps).getJumlah() - (suara1 + suara2 + suara3);
        }

        if(edit){
            hasil.patchHasil(selectedTps, suara1, suara2, suara3, golput);
        }else{
            hasil.postHasil(selectedTps, suara1, suara2, suara3, golput);
        }

        hasil.postHasil(selectedTps, suara1, suara2, suara3, golput);

        System.out.println("\n<<<---SUMMARY--->>>");
        System.out.println("PASLON 1 : " + suara1 + "(" + Math.round((suara1 * 100.0 / jumlah) * 100.0) / 100.0 + "%)");
        System.out.println("PASLON 2 : " + suara2 + "(" + Math.round((suara2 * 100.0 / jumlah) * 100.0) / 100.0 + "%)");
        System.out.println("PASLON 3 : " + suara3 + "(" + Math.round((suara3 * 100.0 / jumlah) * 100.0) / 100.0 + "%)");
        System.out.println("GOLPUT : " + golput + "(" + Math.round((golput * 100.0 / jumlah) * 100.0) / 100.0 + "%)");
        System.out.println("\nTERIMA KASIH");

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
