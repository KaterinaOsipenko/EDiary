package company;

import java.sql.*;
import java.util.Scanner;

public class Student_SubjectRepository implements Tables{
    static final Scanner scanner = new Scanner(System.in);
    private final Connection connection;

    Student_SubjectRepository (Connection connection) {
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
                        "subject_id int(6)  NOT NULL)";
                statement.executeUpdate(createTable);
                System.out.println("\nTable student_subject was created!\n");
                alterAdd();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void alterAdd() {
        String addStudent = "ALTER TABLE student_subject ADD CONSTRAINT student_fk FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE ON UPDATE CASCADE";
        String addUpdate = "ALTER TABLE student_subject ADD CONSTRAINT subject_fk FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE ON UPDATE CASCADE";
        try(PreparedStatement statement = this.connection.prepareStatement(addStudent);
            PreparedStatement statement1 = this.connection.prepareStatement(addUpdate)) {
            statement.executeUpdate();
            statement1.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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

    private void removeConstraint() {
        String removeAttendance = "ALTER TABLE attendance DROP CONSTRAINT student_subject_fk";
        try(PreparedStatement statement = this.connection.prepareStatement(removeAttendance)) {
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void dropTable() {
        removeConstraint();
        String drop = "DROP TABLE IF EXISTS student_subject";
        System.out.println("\nDeleting table student_subject...\n");
        try (Statement statement = connection.createStatement()) {
            statement.execute(drop);
            System.out.println("\nDeleting was successful!\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertData() {
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

    @Override
    public void updateData() {
        System.out.print("Enter student_subjects id that you want to update: ");
        int id = scanner.nextInt();
        System.out.print("Enter the id of student that you want to update: ");
        int studentId = scanner.nextInt();
        System.out.print("Enter id of subject that you would like to change:  ");
        int subjectId = scanner.nextInt();
        String update = "UPDATE student_subject SET student_id = " +  studentId + "subject_id = " + subjectId + " WHERE student_subject.id = " + id;
        try(PreparedStatement statement = this.connection.prepareStatement(update)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void selectAll() {
        System.out.println("\nTable diary student_subject");
        System.out.println("--------------------------------------");
        try (Statement statement = this.connection.createStatement();
             ResultSet selectAll = statement.executeQuery("SELECT student_subject.id, students.id, students.first_name, " +
                     "students.last_name, subjects.id, subjects.name  FROM student_subject, " +
                     " students, subjects WHERE student_subject.student_id = students.id " +
                     "AND student_subject.subject_id = subjects.id")) {
            while (selectAll.next()) {
                System.out.println(selectAll.getString(1) + " " + selectAll.getString(2) + " " + selectAll.getString(3) +
                        " " + selectAll.getString(4) + " " + selectAll.getString(5) + " " + selectAll.getString(6));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRow() {
        System.out.print("Enter id of students_subjects that you want to delete from tables: ");
        int id = scanner.nextInt();
        String delete = "DELETE FROM student_subject WHERE student_subject.id = " + id;
        try(PreparedStatement statement = this.connection.prepareStatement(delete)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void selectByName() {
        System.out.print("Enter the student_subject`s id: ");
        int student_subjectId = scanner.nextInt();
        String selectName = "SELECT  students.first_name, students.last_name, subjects.name " +
                "FROM student_subject, students, subjects WHERE student_subject.student_id = students.id AND student_subject.subject_id = subjects.id AND student_subject.id = " + student_subjectId;
        try(Statement statement = this.connection.createStatement();
            ResultSet set = statement.executeQuery(selectName)) {
            while (set.next()) {
                System.out.println(set.getString(1) + " " + set.getString(2) + " " + set.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
