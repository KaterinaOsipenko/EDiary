package company;

import java.sql.*;
import java.util.Scanner;

public class StudentRepository {
    static String url = "jdbc:mysql://localhost:3306/diary";
    static String user = "root";
    static String password = "K30052003o_";
    private Connection connection;
    static final Scanner scanner = new Scanner(System.in);

    StudentRepository (Connection connection) {
        this.connection = connection;
        createTable();
    }

    public void insertStudents() {
        String insert = "INSERT INTO students (first_name, last_name) VALUES (?, ?)";
        Student student = new Student();
        System.out.print("Please, enter the student`s first name: ");
        String nameStudent = scanner.next();
        student.setName(nameStudent);
        System.out.print("Please, enter the student`s last name: ");
        String lastNameStudent = scanner.next();
        student.setSurname(lastNameStudent);

        try (PreparedStatement insertStudent = this.connection.prepareStatement(insert)) {
            insertStudent.setString(1, student.getName());
            insertStudent.setString(2, student.getSurname());
            insertStudent.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTable() {
        System.out.println("Creating table students...");
        String createTable = "CREATE TABlE IF NOT EXISTS students (" +
                "id int(6) Primary Key AUTO_INCREMENT," +
                "first_name varchar(33) NOT NULL," +
                "last_name varchar(33) NOT NULL)";
        try (Statement statement = this.connection.createStatement()) {
            statement.executeUpdate(createTable);
            System.out.println("Table students was created!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropStudents() {
        String drop = "DROP TABLE IF EXISTS students";
        System.out.println("Deleting table students...");
        try (Statement statement = connection.createStatement()) {
            statement.execute(drop);
            System.out.println("Deleting was successful!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void selectAllStudents() {
        System.out.println("\nTable students");
        try (Statement statement = connection.createStatement();
             ResultSet selectAll = statement.executeQuery("SELECT * FROM students")) {
            while (selectAll.next()) {
                System.out.println(selectAll.getString(1) + " " + selectAll.getString(2) + " " + selectAll.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
