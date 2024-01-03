package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.DB;

public class kota {
    private int id;
    private String nama;
    private String namaProv;
    // private int jumlahKota;

    public kota(int id, String nama, String namaProv) {
        this.id = id;
        this.nama = nama;
        this.namaProv = namaProv;
        // this.jumlahKota = jumlahKota;
    }

    // Getter untuk ID Provinsi
    public int getId() {
        return id;
    }

    public String getProv(){
        return namaProv;
    }

    // Getter untuk nama Provinsi
    public String getNama() {
        return nama;
    }

    // Getter untuk jumlah kota dalam Provinsi
    // public int getJumlahKota() {
    //     return jumlahKota;
    // }

    // Method untuk mengambil semua data Provinsi dari database
    public static List<kota> getKotaInProvinsi(int id_prov) {
        List<kota> kotaList = new ArrayList<>();
        Connection connection = DB.connect();

        if (connection != null) {
            try {
                PreparedStatement statement = connection.prepareStatement("SELECT c.city_id, c.city_name, p.prov_name FROM cities c INNER JOIN provinces p ON p.prov_id = c.prov_id WHERE c.prov_id =" + id_prov);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int id = resultSet.getInt("city_id");
                    String nama = resultSet.getString("city_name");
                    String namaProv = resultSet.getString("prov_name");

                    kota kota = new kota(id, nama, namaProv);
                    kotaList.add(kota);
                }

                DB.close(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return kotaList;
    }

    public static String getKotaById(int id) {
        Connection connection = DB.connect();
        String result = null;
    
        if (connection != null) {
            try {
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM cities WHERE city_id = ?");
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
    
                if (resultSet.next()) {
                    String nama = resultSet.getString("city_name");
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
