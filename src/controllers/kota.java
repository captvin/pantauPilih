package controllers;

import java.sql.SQLException;
import java.util.List;

public class kota extends wilayah {

    private String namaProv;

    public kota(int id, String nama, String namaProv) {
        super(id, nama);
        this.namaProv = namaProv;
    }

    public String getProv() {
        return namaProv;
    }

    // Method to retrieve all data Provinsi from the database
    public static List<kota> getKotaInProvinsi(int id_prov) throws SQLException {
        String sqlQuery = "SELECT c.city_id, c.city_name, p.prov_name FROM cities c INNER JOIN provinces p ON p.prov_id = c.prov_id WHERE c.prov_id = ?";
        return getWilayahList(sqlQuery, id_prov, rs -> {
            try {
                return new kota(rs.getInt("city_id"), rs.getString("city_name"), rs.getString("prov_name"));
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the exception appropriately, e.g., log it or throw a custom runtime exception
                throw new RuntimeException("Error while creating city object", e);
            }
        });
    }

    public static String getKotaById(int id) throws SQLException {
        String sqlQuery = "SELECT * FROM cities WHERE city_id = ?";
        return getWilayahById(sqlQuery, id, rs -> {
            try {
                return rs.getString("city_name");
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the exception appropriately, e.g., log it or throw a custom runtime exception
                throw new RuntimeException("Error while retrieving city_name", e);
            }
        });
    }
}
