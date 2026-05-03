import javax.swing.*;
import java.sql.*;

public class ExamMenuApp {

    static Connection con;

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/exam_db",
                "root",
                "root"
            );

            showMenu();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void showMenu() {
        JFrame frame = new JFrame("Online Exam Evaluation System");
        frame.setSize(400, 400);
        frame.setLayout(null);

        JButton addBtn = new JButton("Add Student");
        JButton viewBtn = new JButton("View Results");
        JButton updateBtn = new JButton("Update Record");
        JButton deleteBtn = new JButton("Delete Record");

        addBtn.setBounds(100, 50, 200, 30);
        viewBtn.setBounds(100, 100, 200, 30);
        updateBtn.setBounds(100, 150, 200, 30);
        deleteBtn.setBounds(100, 200, 200, 30);

        frame.add(addBtn);
        frame.add(viewBtn);
        frame.add(updateBtn);
        frame.add(deleteBtn);

        frame.setVisible(true);

        addBtn.addActionListener(e -> addStudent());
        viewBtn.addActionListener(e -> viewResults());
        updateBtn.addActionListener(e -> updateStudent());
        deleteBtn.addActionListener(e -> deleteStudent());
    }

    static void addStudent() {
        String name = JOptionPane.showInputDialog("Student Name");
        String roll = JOptionPane.showInputDialog("Roll Number");
        String subject = JOptionPane.showInputDialog("Subject");
        String marks = JOptionPane.showInputDialog("Marks");
        String date = JOptionPane.showInputDialog("Exam Date");

        try {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO students(student_name,roll_number,subject,marks,exam_date) VALUES(?,?,?,?,?)"
            );

            ps.setString(1, name);
            ps.setString(2, roll);
            ps.setString(3, subject);
            ps.setString(4, marks);
            ps.setString(5, date);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Student Record Added!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void viewResults() {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM students");

            String data = "";

            while (rs.next()) {
                data += rs.getInt("id") + " | "
                      + rs.getString("student_name") + " | "
                      + rs.getString("roll_number") + " | "
                      + rs.getString("subject") + " | "
                      + rs.getString("marks") + " | "
                      + rs.getString("exam_date") + "\n";
            }

            JOptionPane.showMessageDialog(null, data);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void updateStudent() {
        String id = JOptionPane.showInputDialog("Enter ID");
        String name = JOptionPane.showInputDialog("Student Name");
        String roll = JOptionPane.showInputDialog("Roll Number");
        String subject = JOptionPane.showInputDialog("Subject");
        String marks = JOptionPane.showInputDialog("Marks");
        String date = JOptionPane.showInputDialog("Exam Date");

        try {
            PreparedStatement ps = con.prepareStatement(
                "UPDATE students SET student_name=?, roll_number=?, subject=?, marks=?, exam_date=? WHERE id=?"
            );

            ps.setString(1, name);
            ps.setString(2, roll);
            ps.setString(3, subject);
            ps.setString(4, marks);
            ps.setString(5, date);
            ps.setInt(6, Integer.parseInt(id));

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Record Updated!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void deleteStudent() {
        String id = JOptionPane.showInputDialog("Enter ID");

        try {
            PreparedStatement ps = con.prepareStatement(
                "DELETE FROM students WHERE id=?"
            );

            ps.setInt(1, Integer.parseInt(id));
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Record Deleted!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
