package company;

import java.sql.*;
import java.util.Scanner;

public class TeacherRepository {
    static final Scanner scanner = new Scanner(System.in);
    Connection connection;

    TeacherRepository (Connection connection) {
        this.connection = connection;
        createTable();
    }

    public void insertTeachers() {
        String insert = "INSERT INTO teachers (first_name, last_name) VALUES (?, ?)";
        Person teacher = new Teacher();
        System.out.print("Please, enter the teacher`s first name: ");
        String nameTeacher = scanner.next();
        teacher.setName(nameTeacher);
        System.out.print("Please, enter the teacher`s last name: ");
        String lastNameTeacher = scanner.next();
        teacher.setSurname(lastNameTeacher);

        try (PreparedStatement insertTeacher = this.connection.prepareStatement(insert)) {
            insertTeacher.setString(1, teacher.getName());
            insertTeacher.setString(2, teacher.getSurname());
            insertTeacher.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTable() {
        try (Statement statement = this.connection.createStatement()) {
            DatabaseMetaData dbData = this.connection.getMetaData();
            ResultSet setTables = dbData.getTables(null, null, "teachers", null);
            if (setTables.next()) {
                System.out.println("\nTable teachers has already exists!\n");
            } else {
                System.out.println("\nCreating table teachers...\n");
                String createTable = "CREATE TABlE IF NOT EXISTS teachers (" +
                        "id int(6) PRIMARY KEY AUTO_INCREMENT," +
                        "first_name varchar(33) NOT NULL," +
                        "last_name varchar(33) NOT NULL)";
                statement.executeUpdate(createTable);
                System.out.println("\nTable teachers was created!\n");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void selectAllTeachers() {
        System.out.println("\nTable teachers");
        System.out.println("--------------------------------------");
        try (Statement statement = this.connection.createStatement();
             ResultSet selectAll = statement.executeQuery("SELECT * FROM teachers")) {
            while (selectAll.next()) {
                System.out.println(selectAll.getString(1) + " " + selectAll.getString(2) + " " + selectAll.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropTeachers() {
        String drop = "DROP TABLE IF EXISTS teachers";
        System.out.println("\nDeleting table teachers...\n");
        try (Statement statement = connection.createStatement()) {
            statement.execute(drop);
            System.out.println("\nDeleting was successful!\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getCount() {
        int res = 0;
        String count = "SELECT COUNT(*) FROM teachers";
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

    public void joinWithSubjects () {
        String join = "SELECT teachers.id, teachers.first_name, teachers.last_name, subjects.name FROM subjects, teachers " +
                "WHERE subjects.id = teachers.id";
        try (Statement statement = this.connection.createStatement()) {
            System.out.println("\nJoin-table teachers with subjects:");
            System.out.println("--------------------------------------");
            ResultSet joinSet = statement.executeQuery(join);
            while (joinSet.next()) {
                System.out.println(joinSet.getString(1) + " " + joinSet.getString(2) + " " + joinSet.getString(3) + " " + joinSet.getString(4));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
