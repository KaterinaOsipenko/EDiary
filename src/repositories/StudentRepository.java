package repositories;

import pojo.Mark;
import pojo.Student;
import pojo.Tables;

import java.sql.*;
import java.util.*;

public class StudentRepository implements Tables {
    private final Connection connection;
    static final Scanner scanner = new Scanner(System.in);
    private static final HashMap<Integer, Student> students = new HashMap<>();


    public StudentRepository (Connection connection) {
        this.connection = connection;
        createTable();
    }

    public static HashMap<Integer, Student> getStudents () {
        return students;
    }

    private void createTable() {
        try (Statement statement = this.connection.createStatement()) {
            DatabaseMetaData dbData = this.connection.getMetaData();
            ResultSet setTables = dbData.getTables(null, null, "students", null);
            if (setTables.next()) {
                System.out.println("Table students has already exists!");
                putToMap();
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
            students.clear();
            System.out.println("\nDeleting was successful!\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertData() {
        String insert = "INSERT INTO students (first_name, last_name) VALUES (?, ?)";
        String getId = "SELECT LAST_INSERT_ID()";
        System.out.print("Please, enter the student`s first name: ");
        String nameStudent = scanner.next();
        System.out.print("Please, enter the student`s last name: ");
        String lastNameStudent = scanner.next();
        Student student = new Student(nameStudent, lastNameStudent);
        try (PreparedStatement insertStudent = this.connection.prepareStatement(insert);
             Statement getIdStatement = this.connection.createStatement()) {
            insertStudent.setString(1, student.getName());
            insertStudent.setString(2, student.getSurname());
            insertStudent.executeUpdate();
            ResultSet lastId = getIdStatement.executeQuery(getId);
            while (lastId.next()) {
                student.setId(lastId.getInt(1));
            }
            students.put(student.getId(), student);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateData() {
        System.out.print("Enter the student`s id whose data you want to update: ");
        Integer studentId = scanner.nextInt();
        System.out.print("Enter the new value of first name: ");
        String name = scanner.next();
        System.out.print("Enter the new value of last name: ");
        String surname = scanner.next();
        String update = "UPDATE students SET first_name = '" + name +  "' ,last_name = '" + surname + "' WHERE id = " + studentId;
        try(Statement statement = this.connection.createStatement()) {
            statement.execute(update);
            Student student = students.get(studentId);
            student.setName(name);
            student.setSurname(surname);
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
        try(Statement statement = this.connection.createStatement()) {
            statement.execute(delete);
            students.remove(studentId);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeAdd() {
        String removeStudent = "ALTER TABLE student_subject DROP CONSTRAINT student_fk";
        String removeSubject = "ALTER TABLE student_subject DROP CONSTRAINT subject_fk";
        try(Statement statement = this.connection.createStatement();
           Statement statement1 = this.connection.createStatement()) {
            statement.execute(removeStudent);
            statement1.execute(removeSubject);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getAverageByGroup() {
        System.out.print("Enter the subject`s id: ");
        int studentId = scanner.nextInt();
        String average = "SELECT AVG(mark) FROM marks, student_subject, students, subjects WHERE " +
                "marks.student_subject_id = student_subject.id AND student_subject.subject_id = subjects.id" +
                " AND student_subject.student_id = students.id AND students.id =" + studentId;
        try(Statement statement = this.connection.createStatement()) {
            ResultSet avg = statement.executeQuery(average);
            while (avg.next()) {
                System.out.println(avg.getDouble(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void putToMap() {
        try (Statement statement = connection.createStatement();
             ResultSet selectAll = statement.executeQuery("SELECT * FROM students")) {
            while (selectAll.next()) {
                Student student = new Student(selectAll.getInt(1), selectAll.getString(2), selectAll.getString(3));
                students.put(student.getId(), student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printMap() {
        students.values().forEach(entry -> System.out.println(entry.getId() + " " + entry.getName() + " " + entry.getSurname()));
    }

    public void getMarks() {
        System.out.println("Enter the id of students: ");
        int id = scanner.nextInt();
        Student student = students.get(id);
        for (Map.Entry<Integer, Mark> entry : student.getMarks().entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue().toString());
        }
        System.out.println(student.getMarks().size());
    }

    public void getAllMarks() {
        students.forEach((integer, student) -> student.printMarks());
    }
}
