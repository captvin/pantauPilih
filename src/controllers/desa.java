package controllers;

import java.sql.SQLException;
import java.util.List;

public class desa extends wilayah {

    private String namaKec;

    public desa(int id, String nama, String namaKec) {
        super(id, nama);
        this.namaKec = namaKec;
    }

    // Getter for nama Kota
    public String getKec() {
        return namaKec;
    }

    // Method to retrieve all data Provinsi from the database
    public static List<desa> getDesaInKec(int id_kec) throws SQLException {
        String sqlQuery = "SELECT s.subdis_id, s.subdis_name, d.dis_name FROM subdistricts s INNER JOIN districts d ON d.dis_id = s.dis_id WHERE d.dis_id = ?";
        return getWilayahList(sqlQuery, id_kec, rs -> {
            try {
                return new desa(rs.getInt("subdis_id"), rs.getString("subdis_name"), rs.getString("dis_name"));
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the exception appropriately, e.g., log it or throw a custom runtime exception
                throw new RuntimeException("Error while creating subdistrict object", e);
            }
        });
    }

    public static String getDesaById(int id) throws SQLException {
        String sqlQuery = "SELECT * FROM subdistricts WHERE subdis_id = ?";
        return getWilayahById(sqlQuery, id, rs -> {
            try {
                return rs.getString("subdis_name");
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the exception appropriately, e.g., log it or throw a custom runtime exception
                throw new RuntimeException("Error while retrieving subdis_name", e);
            }
        });
    }
    
}
