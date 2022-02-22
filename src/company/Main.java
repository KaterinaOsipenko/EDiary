package company;

import repositories.*;

import java.sql.*;
import java.util.Scanner;

public class Main {

    static String url = "jdbc:mysql://localhost:3306/";
    static String url2 = "jdbc:mysql://localhost:3306/diary";
    static String user = "root";
    static String password = "K30052003o_";
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        DBManager db = new DBManager(url, user, password);
        db.getMetaData();

        try (Connection connection = DriverManager.getConnection(url2, user, password)){
            StudentRepository tableStudent = new StudentRepository(connection);
            TeacherRepository tableTeacher = new TeacherRepository(connection);
            SubjectRepository tableSubject = new SubjectRepository(connection);
            Student_SubjectRepository tableStudentsSubjects = new Student_SubjectRepository(connection);
            AttendenceRepository tableAttendance = new AttendenceRepository(connection);
            MarksRepository tableMarks = new MarksRepository(connection);
            boolean exit = true;
            do {
                System.out.println("""

                        If you want to select all rows from table press '1'
                        If you want to insert data in the table press '2'
                        If you want to delete row in the table press '3'
                        If you want to update data in the table press '4'
                        If you want to drop table press '5'
                        If you want to get information by id press '6'
                        If you want to get average mark by drop to subject press '7'
                        For exit press '0'
                        """);
                System.out.print("Enter the key: ");
                int key = scanner.nextInt();
                switch (key) {
                    case 1 -> {
                        boolean exit1 = true;
                        do {
                            System.out.print("\nChoose the table which you want to see:\n");
                            System.out.println("""
                                    Students - press 1
                                    Teachers - press 2
                                    Subjects - press 3
                                    Student_Subject - press 4
                                    Attendance - press 5
                                    Marks - press 6
                                    For exit - press 0""");
                            int key1 = scanner.nextInt();
                            switch (key1) {
                                case 1 -> tableStudent.selectAll();
                                case 2 -> tableTeacher.selectAll();
                                case 3 -> tableSubject.selectAll();
                                case 4 -> tableStudentsSubjects.selectAll();
                                case 5 -> tableAttendance.selectAll();
                                case 6 -> tableMarks.selectAll();
                                case 0 -> exit1 = false;
                                default -> System.out.println("The wrong instruction");
                            }
                        } while (exit1);
                    }
                    case 2 -> {
                        boolean exit2 = true;
                        do {
                            System.out.print("\nChoose the table which you want to see:\n");
                            System.out.println("""
                                    Students - press 1
                                    Teachers - press 2
                                    Subjects - press 3
                                    Student_Subject - press 4
                                    Attendance - press 5
                                    Marks - press 6
                                    For exit - press 0""");
                            int key2 = scanner.nextInt();
                            switch (key2) {
                                case 1 -> tableStudent.insertData();
                                case 2 -> tableTeacher.insertData();
                                case 3 -> tableSubject.insertData();
                                case 4 -> tableStudentsSubjects.insertData();
                                case 5 -> tableAttendance.insertData();
                                case 6 -> tableMarks.insertData();
                                case 0 -> exit2 = false;
                                default -> System.out.println("The wrong instruction");
                            }
                        } while (exit2);
                    }
                    case 3 -> {
                        boolean exit3 = true;
                        do {
                            System.out.print("\nChoose the table which you want to see:\n");
                            System.out.println("""
                                    Students - press 1
                                    Teachers - press 2
                                    Subjects - press 3
                                    Student_subject - press
                                    Attendance - press 5
                                    Marks - press 6
                                    For exit - press 0""");
                            int key4 = scanner.nextInt();
                            switch (key4) {
                                case 1 -> tableStudent.deleteRow();
                                case 2 -> tableTeacher.deleteRow();
                                case 3 -> tableSubject.deleteRow();
                                case 4 -> tableStudentsSubjects.deleteRow();
                                case 5 -> tableAttendance.deleteRow();
                                case 6 -> tableMarks.deleteRow();
                                case 0 -> exit3 = false;
                                default -> System.out.println("The wrong instruction");
                            }
                        } while (exit3);
                    }
                    case 4 -> {
                        boolean exit4 = true;
                        do {
                            System.out.print("\nChoose the table which you want to see:\n");
                            System.out.println("""
                                    Students - press 1
                                    Teachers - press 2
                                    Subjects - press 3
                                    Student_Subject - press 4
                                    Attendance - press 5
                                    Marks - press 6
                                    For exit - press 0""");
                            int key5 = scanner.nextInt();
                            switch (key5) {
                                case 1 -> tableStudent.updateData();
                                case 2 -> tableTeacher.updateData();
                                case 3 -> tableSubject.updateData();
                                case 4 -> tableStudentsSubjects.updateData();
                                case 5 -> tableAttendance.updateData();
                                case 6 -> tableMarks.updateData();
                                case 0 -> exit4 = false;
                                default -> System.out.println("The wrong instruction");
                            }
                        } while (exit4);
                    }
                    case 5 -> {
                        boolean exit5 = true;
                        do {
                            System.out.print("\nChoose the table which you want to see:\n");
                            System.out.println("""
                                    Students - press 1
                                    Teachers - press 2
                                    Subjects - press 3
                                    Student_Subject - press 4
                                    Attendance - press 5
                                    Marks - press 6
                                    For exit - press 0""");
                            int key2 = scanner.nextInt();
                            switch (key2) {
                                case 1 -> tableStudent.dropTable();
                                case 2 -> tableTeacher.dropTable();
                                case 3 -> tableSubject.dropTable();
                                case 4 -> tableStudentsSubjects.dropTable();
                                case 5 -> tableAttendance.dropTable();
                                case 6 -> tableMarks.dropTable();
                                case 0 -> exit5 = false;
                                default -> System.out.println("The wrong instruction");
                            }
                        } while (exit5);
                    }
                    case 6 -> {
                        boolean exit6 = true;
                        do {
                            System.out.print("\nChoose the table which you want to see:\n");
                            System.out.println("""
                                    Students - press 1
                                    Teachers - press 2
                                    Subjects - press 3
                                    Student_Subject - press 4
                                    Attendance - press 5
                                    Marks - press 6
                                    For exit - press 0""");
                            int key2 = scanner.nextInt();
                            switch (key2) {
                                case 1 -> tableStudent.selectByName();
                                case 2 -> tableTeacher.selectByName();
                                case 3 -> tableSubject.selectByName();
                                case 4 -> tableStudentsSubjects.selectByName();
                                case 5 -> tableAttendance.selectByName();
                                case 6 -> tableMarks.selectByName();
                                case 0 -> exit6 = false;
                                default -> System.out.println("The wrong instruction");
                            }
                        } while (exit6);
                    }
                    case 7 -> {
                        boolean exit7 = true;
                        do {
                            System.out.print("\nChoose the average point which you want to see:\n");
                            System.out.println("""
                                    By all group - press 1
                                    By student - press 2
                                    By 1 subject to student - press 3
                                    For exit - press 0""");
                            int key2 = scanner.nextInt();
                            switch (key2) {
                                case 1 -> tableMarks.getAverageByGroup();
                                case 2 -> tableStudent.getAverageByStudent();
                                case 3 -> tableStudentsSubjects.getAverageByGroup();
                                case 0 -> exit7 = false;
                                default -> System.out.println("The wrong instruction");
                            }
                        } while (exit7);
                    }
                    case 8 -> {
                        boolean exit8 = true;
                        do {
                            System.out.print("\nChoose the average point which you want to see:\n");
                            System.out.println("""
                                    By student - press 1
                                    By subject - press 2
                                    For exit - press 0""");
                            int key2 = scanner.nextInt();
                            switch (key2) {
                                case 1 -> tableStudent.getMarksByStudent();
                                case 2 -> tableSubject.getMarksBySubject();
                                default -> exit8 = false;
                            }
                        } while (exit8);
                    }
                    case 0 -> {
                        System.out.println("Exit. Good luck!");
                        exit = false;
                    }
                    default -> System.out.println("The wrong instruction!");
                }
            } while (exit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

