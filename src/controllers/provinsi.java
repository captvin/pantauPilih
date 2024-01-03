package controllers;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.DB;

public class provinsi {
    private int id;
    private String nama;
    // private int jumlahKota;

    public provinsi(int id, String nama) {
        this.id = id;
        this.nama = nama;
        // this.jumlahKota = jumlahKota;
    }

    // Getter untuk ID Provinsi
    public int getId() {
        return id;
    }

    // Getter untuk nama Provinsi
    public String getNama() {
        return nama;
    }

    // Method untuk mengambil semua data Provinsi dari database
    public static List<provinsi> getAllProvinsi() {
        List<provinsi> provinsiList = new ArrayList<>();
        Connection connection = DB.connect();

        if (connection != null) {
            try {
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM provinces");
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int id = resultSet.getInt("prov_id");
                    String nama = resultSet.getString("prov_name");
                    // int jumlahKota = resultSet.getInt("jumlah_kota");

                    provinsi provinsi = new provinsi(id, nama);
                    provinsiList.add(provinsi);
                }

                DB.close(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return provinsiList;
    }

    //GET prov ById
    public static provinsi getProvinsiById(int id) {
        Connection connection = DB.connect();
        provinsi result = null;
    
        if (connection != null) {
            try {
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM provinces WHERE prov_id = ?");
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
    
                if (resultSet.next()) {
                    String nama = resultSet.getString("prov_name");
                    // int jumlahKota = resultSet.getInt("jumlah_kota");
    
                    result = new provinsi(id, nama);
                }
    
                DB.close(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}