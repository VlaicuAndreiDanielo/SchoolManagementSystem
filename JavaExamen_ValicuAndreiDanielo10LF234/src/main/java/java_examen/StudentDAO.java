package java_examen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class StudentDAO {
    public static List<Student> getStudentiByGrupa(String grupaNume) {
        List<Student> studenti = new ArrayList<>();
        String query = "SELECT s.id, s.nume, AVG(n.valoare) as media " +
                "FROM student s " +
                "JOIN grupa g ON s.grupa_id = g.id " +
                "JOIN (SELECT DISTINCT ON (student_id, materie_id) * FROM nota ORDER BY student_id, materie_id, data_nota DESC) n " +
                "ON s.id = n.student_id " +
                "WHERE g.nume = ? " +
                "GROUP BY s.id, s.nume";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, grupaNume);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int studentId = rs.getInt("id");
                String studentNume = rs.getString("nume");
                double media = rs.getDouble("media");
                boolean restanta = checkRestanta(studentId);
                studenti.add(new Student(studentId, studentNume, media, restanta));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studenti;
    }

    public static Student getStudentByName(String nume) {
        String query = "SELECT s.id, s.nume, AVG(n.valoare) as media " +
                "FROM student s " +
                "JOIN (SELECT DISTINCT ON (student_id, materie_id) * FROM nota ORDER BY student_id, materie_id, data_nota DESC) n " +
                "ON s.id = n.student_id " +
                "WHERE s.nume = ? " +
                "GROUP BY s.id, s.nume";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nume);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int studentId = rs.getInt("id");
                double media = rs.getDouble("media");
                boolean restanta = checkRestanta(studentId);
                return new Student(studentId, nume, media, restanta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static boolean checkRestanta(int studentId) {
        String query = "SELECT MIN(n.valoare) as minNota FROM (" +
                "SELECT DISTINCT ON (student_id, materie_id) * FROM nota ORDER BY student_id, materie_id, data_nota DESC) n " +
                "WHERE n.student_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("minNota") < 5;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
