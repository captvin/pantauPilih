package controllers;

import connection.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class wilayah {
    protected int id;
    protected String nama;

    public wilayah(int id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    // Getter for ID
    public int getId() {
        return id;
    }

    // Getter for nama
    public String getNama() {
        return nama;
    }

    // Utility method to retrieve all data from the database based on the given SQL query
    protected static <T extends wilayah> List<T>
        getWilayahList(String sqlQuery, int idParam, Function<ResultSet, T> mapper) throws SQLException {
        List<T> wilayahList = new ArrayList<>();
        Connection connection = DB.connect();

        if (connection != null) {
            try {
                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                statement.setInt(1, idParam);

                ResultSet resultSet = statement.executeQuery();

                wilayahList = DB.getResults(resultSet, mapper::apply);

                DB.close(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return wilayahList;
    }

    protected static String getWilayahById(String sqlQuery, int idParam, Function<ResultSet, String> mapper) throws SQLException {
        Connection connection = DB.connect();
        String result = null;
    
        if (connection != null) {
            try {
                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                statement.setInt(1, idParam);
    
                ResultSet resultSet = statement.executeQuery();

                result = DB.getResult(resultSet, mapper::apply);

                DB.close(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}