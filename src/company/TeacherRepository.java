package company;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TeacherRepository {
    static final Scanner scanner = new Scanner(System.in);
    private ArrayList<Teacher> listTeachers = new ArrayList<>();
    private Connection connection;

    TeacherRepository (Connection connection) {
        this.connection = connection;
        createTable();
    }

    public void insertTeachers() {
        String insert = "INSERT INTO teachers (first_name, last_name) VALUES (?, ?)";
        System.out.print("Please, enter the teacher`s first name: ");
        String nameTeacher = scanner.next();
        System.out.print("Please, enter the teacher`s last name: ");
        String lastNameTeacher = scanner.next();
        Teacher teacher = new Teacher(nameTeacher, lastNameTeacher);
        listTeachers.add(teacher);

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
        removeAdd();
        String drop = "DROP TABLE IF EXISTS teachers";
        System.out.println("\nDeleting table teachers...\n");
        try (Statement statement = connection.createStatement()) {
            statement.execute(drop);
            System.out.println("\nDeleting was successful!\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateTeacher () {
        System.out.print("Enter the teacher`s id whose data you want to update: ");
        int teacherId = scanner.nextInt();
        System.out.print("Enter the new value of first name: ");
        String name = scanner.next();
        System.out.print("Enter the new value of last name: ");
        String surname = scanner.next();
        String update = "UPDATE students SET first_name = '" + name +  "' last_name = '" + surname + "' WHERE id = " + teacherId;
        System.out.println(update);
        try(PreparedStatement statement = this.connection.prepareStatement(update)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  int getCount() {
        int res = 0;
        String count = "SELECT COUNT(*) FROM teachers";
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

    public void deleteTeacher () {
        System.out.print("Enter the teacher`s id who you want to delete from tables: ");
        int teacherId = scanner.nextInt();
        String delete = "DELETE FROM teachers WHERE id = " + teacherId;
        try(PreparedStatement statement = this.connection.prepareStatement(delete)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void joinWithSubjects () {
        String join = "SELECT teachers.id, teachers.first_name, teachers.last_name, subjects.name FROM subjects, teachers " +
                "WHERE subjects.id = teachers.id";
        try (Statement statement = this.connection.createStatement();
             ResultSet joinSet = statement.executeQuery(join)) {
            System.out.println("\nJoin-table teachers with subjects:");
            System.out.println("--------------------------------------");
            while (joinSet.next()) {
                System.out.println(joinSet.getString(1) + " " + joinSet.getString(2) + " " + joinSet.getString(3) + " " + joinSet.getString(4));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeAdd() {
        String addStudent = "ALTER TABLE subjects DROP CONSTRAINT teacher_fk";
        try(PreparedStatement statement = this.connection.prepareStatement(addStudent)) {
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void selectName() {
        System.out.print("Enter the teacher`s id: ");
        int teacherId = scanner.nextInt();
        String selectName = "SELECT first_name, last_name FROM teachers WHERE id = " + teacherId;
        try(Statement statement = this.connection.createStatement();
            ResultSet set = statement.executeQuery(selectName)) {
            while (set.next()) {
                System.out.println(set.getString(1) + " " + set.getString(2));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ArrayList<Teacher> getListTeachers() {
        return listTeachers;
    }
}
