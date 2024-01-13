package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class DB {
    private static final String URL = "jdbc:mysql://db-mysql.csvdjkhcyyyp.ap-southeast-1.rds.amazonaws.com/pantaupilih";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "databaseadmin";

    public static Connection connect() {
        Connection connection = null;
        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Mencoba membuat koneksi ke database
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            if (connection != null) {
                // System.out.println("Koneksi berhasil.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Koneksi gagal: " + e.getMessage());
        }
        return connection;
    }

    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                // System.out.println("Koneksi ditutup.");
            } catch (SQLException e) {
                System.out.println("Gagal menutup koneksi: " + e.getMessage());
            }
        }
    }

    public static <T> List<T> getResults(ResultSet resultSet, Function<ResultSet, T> mapper) throws SQLException {
        List<T> resultList = new ArrayList<>();
        while (resultSet.next()) {
            T result = mapper.apply(resultSet);
            resultList.add(result);
        }
        return resultList;
    }

    public static <T> T getResult(ResultSet resultSet, Function<ResultSet, T> mapper) throws SQLException {
        T result = null;
        if (resultSet.next()) {
            result = mapper.apply(resultSet);
        }
        return result;
    }
}
