package StudentMVC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentController {
    private static final String URL = "jdbc:mysql://bytexldb.com:5051/db_43zytdf8j";
    private static final String USER = "user_43zytdf8j";
    private static final String PASS = "p43zytdf8j";

    private Connection conn;

    public StudentController() throws SQLException, ClassNotFoundException {
        // Ensure driver is loaded
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(URL, USER, PASS);
    }

    public void close() {
        if (conn != null) {
            try { conn.close(); } catch (SQLException ignored) {}
        }
    }

    // Create
    public boolean addStudent(Student s) {
        String sql = "INSERT INTO students (name, department, marks) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getDepartment());
            ps.setInt(3, s.getMarks());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Read all
    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT student_id, name, department, marks FROM students";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Student s = new Student(
                    rs.getInt("student_id"),
                    rs.getString("name"),
                    rs.getString("department"),
                    rs.getInt("marks")
                );
                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Read single
    public Student getStudentById(int id) {
        String sql = "SELECT student_id, name, department, marks FROM students WHERE student_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Student(
                        rs.getInt("student_id"),
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getInt("marks")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update
    public boolean updateStudent(Student s) {
        String sql = "UPDATE students SET name = ?, department = ?, marks = ? WHERE student_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getDepartment());
            ps.setInt(3, s.getMarks());
            ps.setInt(4, s.getStudentId());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete
    public boolean deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE student_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
