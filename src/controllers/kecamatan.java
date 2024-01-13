package controllers;

import java.sql.SQLException;
import java.util.List;

public class kecamatan extends wilayah {

    private String namaKota;

    public kecamatan(int id, String nama, String namaKota) {
        super(id, nama);
        this.namaKota = namaKota;
    }

    // Getter for nama Kota
    public String getKota() {
        return namaKota;
    }

    // Method to retrieve all data Provinsi from the database
    public static List<kecamatan> getKecInKota(int id_kota) throws SQLException {
        String sqlQuery = "SELECT d.dis_id, d.dis_name, c.city_name FROM districts d INNER JOIN cities c ON c.city_id = d.city_id WHERE c.city_id = ?";
        return getWilayahList(sqlQuery, id_kota, rs -> {
            try {
                return new kecamatan(rs.getInt("dis_id"), rs.getString("dis_name"), rs.getString("city_name"));
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the exception appropriately, e.g., log it or throw a custom runtime exception
                throw new RuntimeException("Error while creating district object", e);
            }
        });
    }

    public static String getKecById(int id) throws SQLException {
        String sqlQuery = "SELECT * FROM districts WHERE dis_id = ?";
        return getWilayahById(sqlQuery, id, rs -> {
            try {
                return rs.getString("dis_name");
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the exception appropriately, e.g., log it or throw a custom runtime exception
                throw new RuntimeException("Error while retrieving dis_name", e);
            }
        });
    }
}
