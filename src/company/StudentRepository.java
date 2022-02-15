package company;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class StudentRepository implements Tables {
    private final Connection connection;
    static final Scanner scanner = new Scanner(System.in);
    private ArrayList<Student> listStudents = new ArrayList<>();

    StudentRepository (Connection connection) {
        this.connection = connection;
        createTable();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropTable() {
        removeAdd();
        String drop = "DROP TABLE IF EXISTS students";
        System.out.println("\nDeleting table students...\n");
        try (Statement statement = connection.createStatement()) {
            statement.execute(drop);
            System.out.println("\nDeleting was successful!\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertData() {
        String insert = "INSERT INTO students (first_name, last_name) VALUES (?, ?)";
        System.out.print("Please, enter the student`s first name: ");
        String nameStudent = scanner.next();
        System.out.print("Please, enter the student`s last name: ");
        String lastNameStudent = scanner.next();
        Student student = new Student(nameStudent, lastNameStudent);
        listStudents.add(student);
        try (PreparedStatement insertStudent = this.connection.prepareStatement(insert)) {
            insertStudent.setString(1, student.getName());
            insertStudent.setString(2, student.getSurname());
            insertStudent.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateData() {
        System.out.print("Enter the student`s id whose data you want to update: ");
        int studentId = scanner.nextInt();
        System.out.print("Enter the new value of first name: ");
        String name = scanner.next();
        System.out.print("Enter the new value of last name: ");
        String surname = scanner.next();
        String update = "UPDATE students SET first_name = '" + name +  "' ,last_name = '" + surname + "' WHERE id = " + studentId;
        System.out.println(update);
        try(PreparedStatement statement = this.connection.prepareStatement(update)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void selectAll() {
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

    @Override
    public void deleteRow() {
        System.out.print("Enter the student`s id who you want to delete from tables: ");
        int studentId = scanner.nextInt();
        String delete = "DELETE FROM students WHERE id = " + studentId;
        try(PreparedStatement statement = this.connection.prepareStatement(delete)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void selectByName() {
        System.out.print("Enter the student`s id: ");
        int studentId = scanner.nextInt();
        String selectName = "SELECT first_name, last_name FROM students WHERE id = " + studentId;
        try(Statement statement = this.connection.createStatement();
            ResultSet set = statement.executeQuery(selectName)) {
            while (set.next()) {
                System.out.println(set.getString(1) + " " + set.getString(2));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeAdd() {
        String removeStudent = "ALTER TABLE student_subject DROP CONSTRAINT student_fk";
        String removeSubject = "ALTER TABLE student_subject DROP CONSTRAINT subject_fk";
        try(PreparedStatement statement = this.connection.prepareStatement(removeStudent);
            PreparedStatement statement1 = this.connection.prepareStatement(removeSubject)) {
            statement.executeUpdate();
            statement1.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int getCount() {
        int res = 0;
        String count = "SELECT COUNT(*) FROM students";
        try (Statement statement = this.connection.createStatement();
            ResultSet countRes =  statement.executeQuery(count)) {
            while (countRes.next()) {
                res = countRes.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}
