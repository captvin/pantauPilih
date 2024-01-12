package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.DB;

public class hasil {
    private int id;
    private int idTps;
    private int suara1;
    private int suara2;
    private int suara3;
    private int golput;

    public hasil(int id, int idTps, int suara1, int suara2, int suara3, int golput) {
        this.id = id;
        this.idTps = idTps;
        this.suara1 = suara1;
        this.suara2 = suara2;
        this.suara3 = suara3;
        this.golput = golput;
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
}
