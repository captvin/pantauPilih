package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.DB;

public class desa {
    private int id;
    private String nama;
    private String namaKec;

    public desa(int id, String nama, String namaKec) {
        this.id = id;
        this.nama = nama;
        this.namaKec = namaKec;
    }

    // Getter untuk ID Provinsi
    public int getId() {
        return id;
    }

     // Getter untuk nama Kota
    public String getKec(){
        return namaKec;
    }

    // Getter untuk nama Provinsi
    public String getNama() {
        return nama;
    }

    // Method untuk mengambil semua data Provinsi dari database
    public static List<desa> getDesaInKec(int id_kec) {
        List<desa> desaList = new ArrayList<>();
        Connection connection = DB.connect();

        if (connection != null) {
            try {
                PreparedStatement statement = connection.prepareStatement("SELECT s.subdis_id, s.subdis_name, d.dis_name FROM subdistricts s INNER JOIN districts d ON d.dis_id = s.dis_id WHERE d.dis_id =" + id_kec);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int id = resultSet.getInt("subdis_id");
                    String nama = resultSet.getString("subdis_name");
                    String namaKec = resultSet.getString("dis_name");

                    desa desa = new desa(id, nama, namaKec);
                    desaList.add(desa);
                }

                DB.close(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return desaList;
    }

    public static String getDesaById(int id) {
        Connection connection = DB.connect();
        String result = null;
    
        if (connection != null) {
            try {
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM subdistricts WHERE subdis_id = ?");
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
    
                if (resultSet.next()) {
                    String nama = resultSet.getString("subdis_name");
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
