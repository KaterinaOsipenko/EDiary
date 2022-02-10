package company;

import java.sql.*;
import java.util.Scanner;

public class Student_SubjectRepository {
    static final Scanner scanner = new Scanner(System.in);
    private Connection connection;
    private int countStudents;
    private int countSubjects;

    Student_SubjectRepository (Connection connection, int countStudents, int countSubjects) {
        this.countStudents =  countStudents;
        this.countSubjects = countSubjects;
        this.connection = connection;
        createTable();
    }

    private void createTable() {
        try (Statement statement = this.connection.createStatement()) {
            DatabaseMetaData dbData = this.connection.getMetaData();
            ResultSet setTables = dbData.getTables(null, null, "student_subject", null);
            if (setTables.next()) {
                System.out.println("\nTable student_subject has already exists!\n");
            } else {
                System.out.println("\nCreating table student_subject...\n");
                String createTable = "CREATE TABlE IF NOT EXISTS student_subject (" +
                        "id int(6) PRIMARY KEY AUTO_INCREMENT," +
                        "student_id int(6)  NOT NULL," +
                        "subject_id int(6)  NOT NULL," +
                        "FOREIGN KEY (student_id) REFERENCES students(id), " +
                        "FOREIGN KEY (subject_id) REFERENCES subjects(id)" +
                        "ON UPDATE CASCADE ON DELETE CASCADE)";
                statement.executeUpdate(createTable);
                System.out.println("\nTable student_subject was created!\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void selectAllStudents_Subjects() {
        System.out.println("\nTable student_subject");
        System.out.println("--------------------------------------");
        try (Statement statement = this.connection.createStatement();
             ResultSet selectAll = statement.executeQuery("SELECT * FROM student_subject")) {
            while (selectAll.next()) {
                System.out.println(selectAll.getString(1) + " " + selectAll.getString(2) + " " + selectAll.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void selectAllDiary() {
        System.out.println("\nTable diary student_subject");
        System.out.println("--------------------------------------");
        try (Statement statement = this.connection.createStatement();
             ResultSet selectAll = statement.executeQuery("SELECT student_subject.id, students.id, students.first_name, " +
                     "students.last_name, subjects.id, subjects.name  FROM student_subject, " +
                     " students, subjects WHERE student_subject.student_id = students.id " +
                     "AND student_subject.subject_id = subjects.id;")) {
            while (selectAll.next()) {
                System.out.println(selectAll.getString(1) + " " + selectAll.getString(2) + " " + selectAll.getString(3) +
                        " " + selectAll.getString(4) + " " + selectAll.getString(5) + " " + selectAll.getString(6));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertSubject() {
        int i = (int)(Math.random() * (this.countStudents - 1)) + 1;
        int j = (int)(Math.random() * (this.countSubjects - 1)) + 1;
        String insert = "INSERT INTO student_subject (student_id, subject_id) VALUES (" +
                "(SELECT id FROM students WHERE id = " + i + ")" +
                ", (SELECT id FROM subjects WHERE id = " + j + "))";
        try (PreparedStatement insertTeacher = this.connection.prepareStatement(insert)) {
            insertTeacher.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropStudents_Subjects() {
        String drop = "DROP TABLE IF EXISTS student_subject";
        System.out.println("\nDeleting table student_subject...\n");
        try (Statement statement = connection.createStatement()) {
            statement.execute(drop);
            System.out.println("\nDeleting was successful!\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addStudentToSubject () {
        System.out.print("Enter the student id which you want to add: ");
        int studentId = scanner.nextInt();
        System.out.print("\nEnter the subject is which you want to add: ");
        int subjectId = scanner.nextInt();
        String add = "INSERT INTO student_subject (student_id, subject_id) VALUES ('" + studentId + "', '" + subjectId + "')";
        try (PreparedStatement insertTeacher = this.connection.prepareStatement(add)) {
            insertTeacher.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
