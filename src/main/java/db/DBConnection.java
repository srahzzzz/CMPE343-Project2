package db;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Handles MySQL database connection using JDBC.
 */
public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/contactdb?useUnicode=true&characterEncoding=UTF-8";

    private static final String USER = "myuser";
    private static final String PASSWORD = "1234";

    private static Connection connection;

    /**
     * Returns a connection to the MySQL database.
     *
     * @return active JDBC connection or null if connection fails
     */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (Exception e) {
            System.out.println("DB Connection Error: " + e.getMessage());
        }
        return connection;
    }
}
