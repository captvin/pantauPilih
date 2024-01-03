package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.DB;

public class tps {
    private int id;
    private String nama;
    private String namaDesa;
    private int jumlahPemilih;

    public tps(int id, String nama, String namaDesa) {
        this.id = id;
        this.nama = nama;
        this.namaDesa = namaDesa;
    }

    public tps(String nama, int jumlahPemilih) {
        this.nama = nama;
        this.jumlahPemilih = jumlahPemilih;
    }

    // Getter untuk ID Provinsi
    public int getId() {
        return id;
    }

    // Getter untuk nama Kota
    public String getKec() {
        return namaDesa;
    }

    // Getter untuk jumlah peserta pemilihan
    public int getJumlah() {
        return jumlahPemilih;
    }

    // Getter untuk nama Provinsi
    public String getNama() {
        return nama;
    }

    // Method untuk mengambil semua data Provinsi dari database
    public static List<tps> getTpsInDesa(int id_desa) {
        List<tps> tpsList = new ArrayList<>();
        Connection connection = DB.connect();

        if (connection != null) {
            try {
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT t.tps_id, t.tps_name, s.subdis_name FROM tps t INNER JOIN subdistricts s ON t.subdis_id = s.subdis_id WHERE s.subdis_id = ?");
                statement.setInt(1, id_desa);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int id = resultSet.getInt("tps_id");
                    String nama = resultSet.getString("tps_name");
                    String namaDesa = resultSet.getString("subdis_name");

                    tps tps = new tps(id, nama, namaDesa);
                    tpsList.add(tps);
                }

                DB.close(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return tpsList;
    }

    public static tps getTpsById(int id) {
        Connection connection = DB.connect();
        String nama = null;
        int jumlahPemilih = 0;

        if (connection != null) {
            try {
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM tps WHERE tps_id = ?");
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    nama = resultSet.getString("tps_name");
                    jumlahPemilih = resultSet.getInt("jumlahPemilih");
                    // int jumlahKota = resultSet.getInt("jumlah_kota");

                }

                DB.close(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new tps(nama, jumlahPemilih);
    }
}
