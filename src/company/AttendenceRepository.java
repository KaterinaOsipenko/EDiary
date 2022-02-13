package company;

import java.sql.*;
import java.util.Scanner;

public class AttendenceRepository {
    static final Scanner scanner = new Scanner(System.in);
    private Connection connection;

    AttendenceRepository(Connection connection) {
        this.connection = connection;
        createTable();
    }

    private void createTable() {
        try (Statement statement = this.connection.createStatement()) {
            DatabaseMetaData dbData = this.connection.getMetaData();
            ResultSet setTables = dbData.getTables(null, null, "attendance", null);
            if (setTables.next()) {
                System.out.println("\nTable attendance has already exists!\n");
            } else {
                System.out.println("\nCreating table attendance...\n");
                String createTable = "CREATE TABlE IF NOT EXISTS attendance (" +
                        "student_subject_id int(6)," +
                        "Monday varchar(3) DEFAULT '   ', " +
                        "Tuesday varchar(3) DEFAULT '   ', " +
                        "Wednesday varchar(3) DEFAULT '   ', " +
                        "Thursday varchar(3) DEFAULT '   ', " +
                        "Friday varchar(3) DEFAULT '   ' ) ";
                statement.executeUpdate(createTable);
                System.out.println("\nTable attendance was created!\n");
                alterAttendance();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void alterAttendance() {
        String alterStudent_Subject = "ALTER TABLE attendance ADD CONSTRAINT student_subject_fk FOREIGN KEY (student_subject_id) REFERENCES student_subject(id) ON UPDATE CASCADE";
        try(PreparedStatement statement = this.connection.prepareStatement(alterStudent_Subject)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStudent () {
        System.out.print("Enter the id student_subject that you want to update: ");
        int studentId = scanner.nextInt();
        System.out.print("Enter the day of week that you would like to change:  ");
        String day = scanner.next();
        System.out.print("Enter the new value of status: ");
        String status = scanner.next();
        String update = "UPDATE attendance SET " + day + " = '" + status +  "' WHERE student_subject_id = " + studentId;
        try(PreparedStatement statement = this.connection.prepareStatement(update)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void selectAll() {
        System.out.println("\nTable attendance");
        System.out.println("--------------------------------------");
        try (Statement statement = this.connection.createStatement();
             ResultSet selectAll = statement.executeQuery("SELECT * FROM attendance")) {
            System.out.println("   " + "Mon " + "Tue " + "Wed " + "Thur " + "Frd ");
            while (selectAll.next()) {
                System.out.println(" " + selectAll.getString(1) + " " + selectAll.getString(2) + " " + selectAll.getString(3) + " " +
                        selectAll.getString(4) + " " + selectAll.getString(5) + "  " + selectAll.getString(6));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAttendance() {
        System.out.print("Enter id of students_subjects that you want to delete from tables: ");
        int id = scanner.nextInt();
        String delete = "DELETE FROM attendance WHERE student_subject_id = " + id;
        try(PreparedStatement statement = this.connection.prepareStatement(delete)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert() {
        System.out.print("Enter the id of student_subject: ");
        int id = scanner.nextInt();
        System.out.print("Enter the day of week: ");
        String dayOfWeek = scanner.next();
        String insert = "INSERT INTO attendance (student_subject_id, " + dayOfWeek + ") VALUES (" + id + ", 'abs')";
        try (PreparedStatement insertTeacher = this.connection.prepareStatement(insert)) {
            insertTeacher.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void drop() {
        String drop = "DROP TABLE IF EXISTS attendance";
        System.out.println("\nDeleting table attendance...\n");
        try (Statement statement = connection.createStatement()) {
            statement.execute(drop);
            System.out.println("\nDeleting was attendance!\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//     String all = "SELECT students.id, subjects.name AS subName, attendance.monday, " +
//                "attendance.tuesday, attendance.wednesday, attendance.thursday, attendance.friday FROM student_subject," +
//                " students, subjects, attendance WHERE student_subject.student_id = students.id AND" +
//                " student_subject.subject_id = subjects.id AND attendance.student_subject_id = student_subject.id;";
//

}
