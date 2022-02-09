package company;

import java.sql.*;
import java.util.Scanner;

public class StudentRepository {
    private final Connection connection;
    static final Scanner scanner = new Scanner(System.in);

    StudentRepository (Connection connection) {
        this.connection = connection;
        createTable();
    }

    public void insertStudents() {
        String insert = "INSERT INTO students (first_name, last_name) VALUES (?, ?)";
        Person student = new Student();
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
        try (Statement statement = this.connection.createStatement()) {
            DatabaseMetaData dbData = this.connection.getMetaData();
            ResultSet setTables = dbData.getTables(null, null, "students", null);
            if (setTables.next()) {
                System.out.println("Table students has already exists!");
            } else {
                System.out.println("\nCreating table students...\n");
                String createTable = "CREATE TABlE IF NOT EXISTS students (" +
                        "id int(6) PRIMARY KEY AUTO_INCREMENT," +
                        "first_name varchar(33) NOT NULL," +
                        "last_name varchar(33) NOT NULL)";
                statement.executeUpdate(createTable);
                System.out.println("\nTable students was created!\n");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void dropStudents() {
        String drop = "DROP TABLE IF EXISTS students";
        System.out.println("\nDeleting table students...\n");
        try (Statement statement = connection.createStatement()) {
            statement.execute(drop);
            System.out.println("\nDeleting was successful!\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void selectAllStudents() {
        System.out.println("\nTable students");
        System.out.println("--------------------------------------");
        try (Statement statement = connection.createStatement();
             ResultSet selectAll = statement.executeQuery("SELECT * FROM students")) {
            while (selectAll.next()) {
                System.out.println(selectAll.getString(1) + " " + selectAll.getString(2) + " " + selectAll.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getCount() {
        int res = 0;
        String count = "SELECT COUNT(*) FROM students";
        try (Statement statement = this.connection.createStatement()) {
            ResultSet countRes =  statement.executeQuery(count);
            while (countRes.next()) {
                res = countRes.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}
