package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.DB;

public class kecamatan {
    private int id;
    private String nama;
    private String namaKota;

    public kecamatan(int id, String nama, String namaKota) {
        this.id = id;
        this.nama = nama;
        this.namaKota = namaKota;
    }

    // Getter untuk ID Provinsi
    public int getId() {
        return id;
    }

     // Getter untuk nama Kota
    public String getKota(){
        return namaKota;
    }

    // Getter untuk nama Provinsi
    public String getNama() {
        return nama;
    }

    // Method untuk mengambil semua data Provinsi dari database
    public static List<kecamatan> getKecInKota(int id_kota) {
        List<kecamatan> kecList = new ArrayList<>();
        Connection connection = DB.connect();

        if (connection != null) {
            try {
                PreparedStatement statement = connection.prepareStatement("SELECT d.dis_id, d.dis_name, c.city_name FROM districts d INNER JOIN cities c ON c.city_id = d.city_id WHERE c.city_id =" + id_kota);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int id = resultSet.getInt("dis_id");
                    String nama = resultSet.getString("dis_name");
                    String namaKota = resultSet.getString("city_name");

                    kecamatan kecamatan = new kecamatan(id, nama, namaKota);
                    kecList.add(kecamatan);
                }

                DB.close(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return kecList;
    }

    public static String getKecById(int id) {
        Connection connection = DB.connect();
        String result = null;
    
        if (connection != null) {
            try {
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM districts WHERE dis_id = ?");
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
    
                if (resultSet.next()) {
                    String nama = resultSet.getString("dis_name");
                    // int jumlahKota = resultSet.getInt("jumlah_kota");
    
                    result = nama;
                }
    
                DB.close(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
