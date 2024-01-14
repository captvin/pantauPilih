package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.DB;

public class hasil {
    private String by;
    private int id;
    private int idTps;
    private int suara1;
    private int suara2;
    private int suara3;
    private int golput;

    public hasil(String by, int suara1, int suara2, int suara3, int golput) {
        this.by = by;
        this.suara1 = suara1;
        this.suara2 = suara2;
        this.suara3 = suara3;
        this.golput = golput;
    }

    public String getBy(){
        return by;
    }

    public int suara1(){
        return suara1;
    }

    public int suara2(){
        return suara2;
    }

    public int suara3(){
        return suara3;
    }

    public int golput(){
        return golput;
    }

    public static void postHasil(int idTps, int suara1, int suara2, int suara3, int golput) {
        Connection connection = DB.connect();

        if (connection != null) {
            try {
                // Lakukan operasi SQL untuk memasukkan hasil suara ke database
                String query = "INSERT INTO hasilSuara (id_tps, paslon1, paslon2, paslon3, golput) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, idTps);
                statement.setInt(2, suara1);
                statement.setInt(3, suara2);
                statement.setInt(4, suara3);
                statement.setInt(5, golput);

                statement.executeUpdate();

                DB.close(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void patchHasil(int idTps, int suara1, int suara2, int suara3, int golput) {
        Connection connection = DB.connect();

        if (connection != null) {
            try {
                // Lakukan operasi SQL untuk memasukkan hasil suara ke database
                String query = "UPDATE hasilSuara SET paslon1 = ?, paslon2 = ?, paslon3 = ?, golput = ? WHERE id_tps = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, suara1);
                statement.setInt(2, suara2);
                statement.setInt(3, suara3);
                statement.setInt(4, golput);
                statement.setInt(5, idTps);

                statement.executeUpdate();

                DB.close(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean check(int idTps) {
        Connection connection = DB.connect();

        if (connection != null) {
            try {
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM hasilSuara WHERE id_tps = ?");
                statement.setInt(1, idTps);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    DB.close(connection);
                    return false;
                } else {
                    DB.close(connection);
                    return true;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static List<hasil> getAll(){
        List<hasil> hasilList = new ArrayList<>();
        Connection connection = DB.connect();

        if (connection != null){
            try{
                PreparedStatement statement = connection.prepareStatement("SELECT p.prov_name AS 'by', SUM(hs.paslon1) AS paslon1, SUM(hs.paslon2) AS paslon2, SUM(hs.paslon3) AS paslon3, SUM(hs.golput) AS golput, SUM(t.jumlahPemilih) AS total FROM hasilSuara hs INNER JOIN tps t ON hs.id_tps = t.tps_id RIGHT JOIN subdistricts s ON t.subdis_id = s.subdis_id RIGHT JOIN districts d ON s.dis_id = d.dis_id RIGHT JOIN cities c ON d.city_id = c.city_id RIGHT JOIN provinces p ON c.prov_id = p.prov_id GROUP BY p.prov_name ");
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()){
                    String by = resultSet.getString("by");
                    int suara1 = resultSet.getInt("paslon1");
                    int suara2 = resultSet.getInt("paslon2");
                    int suara3 = resultSet.getInt("paslon3");
                    int golput = resultSet.getInt("golput");

                    hasil hasil = new hasil(by, suara1, suara2, suara3, golput);
                    hasilList.add(hasil);
                }
                DB.close(connection);
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return hasilList;
    }

    public static List<hasil> getProv(int idProv){
        List<hasil> hasilList = new ArrayList<>();
        Connection connection = DB.connect();

        if (connection != null){
            try{
                PreparedStatement statement = connection.prepareStatement("SELECT c.city_name  AS 'by', SUM(hs.paslon1) AS paslon1, SUM(hs.paslon2) AS paslon2, SUM(hs.paslon3) AS paslon3, SUM(hs.golput) AS golput, SUM(t.jumlahPemilih) AS total FROM hasilSuara hs INNER JOIN tps t ON hs.id_tps = t.tps_id RIGHT JOIN subdistricts s ON t.subdis_id = s.subdis_id RIGHT JOIN districts d ON s.dis_id = d.dis_id RIGHT JOIN cities c ON d.city_id = c.city_id RIGHT JOIN provinces p ON c.prov_id = p.prov_id WHERE c.prov_id = "+ idProv +" GROUP BY c.city_name");
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()){
                    String by = resultSet.getString("by");
                    int suara1 = resultSet.getInt("paslon1");
                    int suara2 = resultSet.getInt("paslon2");
                    int suara3 = resultSet.getInt("paslon3");
                    int golput = resultSet.getInt("golput");

                    hasil hasil = new hasil(by, suara1, suara2, suara3, golput);
                    hasilList.add(hasil);
                }
                DB.close(connection);
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return hasilList;
    }

    public static List<hasil> getKota(int idCity){
        List<hasil> hasilList = new ArrayList<>();
        Connection connection = DB.connect();

        if (connection != null){
            try{
                PreparedStatement statement = connection.prepareStatement("SELECT d.dis_name  AS 'by', SUM(hs.paslon1) AS paslon1, SUM(hs.paslon2) AS paslon2, SUM(hs.paslon3) AS paslon3, SUM(hs.golput) AS golput, SUM(t.jumlahPemilih) AS total FROM hasilSuara hs INNER JOIN tps t ON hs.id_tps = t.tps_id RIGHT JOIN subdistricts s ON t.subdis_id = s.subdis_id RIGHT JOIN districts d ON s.dis_id = d.dis_id RIGHT JOIN cities c ON d.city_id = c.city_id RIGHT JOIN provinces p ON c.prov_id = p.prov_id WHERE d.city_id = "+ idCity +" GROUP BY d.dis_name");
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()){
                    String by = resultSet.getString("by");
                    int suara1 = resultSet.getInt("paslon1");
                    int suara2 = resultSet.getInt("paslon2");
                    int suara3 = resultSet.getInt("paslon3");
                    int golput = resultSet.getInt("golput");

                    hasil hasil = new hasil(by, suara1, suara2, suara3, golput);
                    hasilList.add(hasil);
                }
                DB.close(connection);
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return hasilList;
    }

    public static List<hasil> getKec(int idKec){
        List<hasil> hasilList = new ArrayList<>();
        Connection connection = DB.connect();

        if (connection != null){
            try{
                PreparedStatement statement = connection.prepareStatement("SELECT s.subdis_name  AS 'by', SUM(hs.paslon1) AS paslon1, SUM(hs.paslon2) AS paslon2, SUM(hs.paslon3) AS paslon3, SUM(hs.golput) AS golput, SUM(t.jumlahPemilih) AS total FROM hasilSuara hs INNER JOIN tps t ON hs.id_tps = t.tps_id RIGHT JOIN subdistricts s ON t.subdis_id = s.subdis_id RIGHT JOIN districts d ON s.dis_id = d.dis_id RIGHT JOIN cities c ON d.city_id = c.city_id RIGHT JOIN provinces p ON c.prov_id = p.prov_id WHERE s.dis_id = "+ idKec +" GROUP BY s.subdis_name");
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()){
                    String by = resultSet.getString("by");
                    int suara1 = resultSet.getInt("paslon1");
                    int suara2 = resultSet.getInt("paslon2");
                    int suara3 = resultSet.getInt("paslon3");
                    int golput = resultSet.getInt("golput");

                    hasil hasil = new hasil(by, suara1, suara2, suara3, golput);
                    hasilList.add(hasil);
                }
                DB.close(connection);
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return hasilList;
    }

    public static List<hasil> getDesa(int idDesa){
        List<hasil> hasilList = new ArrayList<>();
        Connection connection = DB.connect();

        if (connection != null){
            try{
                PreparedStatement statement = connection.prepareStatement("SELECT t.tps_name  AS 'by', SUM(hs.paslon1) AS paslon1, SUM(hs.paslon2) AS paslon2, SUM(hs.paslon3) AS paslon3, SUM(hs.golput) AS golput, SUM(t.jumlahPemilih) AS total FROM hasilSuara hs INNER JOIN tps t ON hs.id_tps = t.tps_id RIGHT JOIN subdistricts s ON t.subdis_id = s.subdis_id RIGHT JOIN districts d ON s.dis_id = d.dis_id RIGHT JOIN cities c ON d.city_id = c.city_id RIGHT JOIN provinces p ON c.prov_id = p.prov_id WHERE t.subdis_id = "+ idDesa +" GROUP BY t.tps_name");
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()){
                    String by = resultSet.getString("by");
                    int suara1 = resultSet.getInt("paslon1");
                    int suara2 = resultSet.getInt("paslon2");
                    int suara3 = resultSet.getInt("paslon3");
                    int golput = resultSet.getInt("golput");

                    hasil hasil = new hasil(by, suara1, suara2, suara3, golput);
                    hasilList.add(hasil);
                }
                DB.close(connection);
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return hasilList;
    }
}
