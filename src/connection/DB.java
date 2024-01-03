package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
}
