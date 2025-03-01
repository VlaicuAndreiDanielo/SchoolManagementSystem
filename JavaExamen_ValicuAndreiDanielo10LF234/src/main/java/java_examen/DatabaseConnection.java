package java_examen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/studenti_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1q2w3e";
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexiunea la baza de date a fost închisă.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
