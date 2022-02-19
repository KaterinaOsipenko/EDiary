package company;

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

        try {
            Connection connection = DriverManager.getConnection(url2, user, password);
            StudentRepository tableStudent = new StudentRepository(connection);
            TeacherRepository tableTeacher = new TeacherRepository(connection);
            SubjectRepository tableSubject = new SubjectRepository(connection);
            Student_SubjectRepository tableStudentsSubjects = new Student_SubjectRepository(connection);
            AttendenceRepository tableAttendance = new AttendenceRepository(connection);
            MarksRepository tableMarks = new MarksRepository(connection);
            boolean exit = true;
            do {
                System.out.println("\nIf you want to select all rows from table press '1'\n" +
                        "If you want to insert data in the table press '2'\n" +
                        "If you want to delete row in the table press '3'\n" +
                        "If you want to update data in the table press '4'\n" +
                        "If you want to drop table press '5'\n" +
                        "If you want to get information by id press '6'\n" +
                        "If you want to get average mark by drop to subject press '7'\n" +
                        "For exit press '0'\n");
                System.out.print("Enter the key: ");
                int key = scanner.nextInt();
                switch (key) {
                    case 1:
                        boolean exit1 = true;
                        do {
                            System.out.print("\nChoose the table which you want to see:\n");
                            System.out.println("Students - press 1\nTeachers - press 2\nSubjects - press 3\nStudent_Subject - press 4\n" +
                                    "Attendance - press 5\nMarks - press 6\nFor exit - press 0");
                            int key1 = scanner.nextInt();
                            switch (key1) {
                                case 1:
                                    tableStudent.selectAll();
                                    break;
                                case 2:
                                    tableTeacher.selectAll();
                                    break;
                                case 3:
                                    tableSubject.selectAll();
                                    break;
                                case 4:
                                    tableStudentsSubjects.selectAll();
                                    break;
                                case 5:
                                    tableAttendance.selectAll();
                                    break;
                                case 6:
                                    tableMarks.selectAll();
                                    break;
                                case 0:
                                    exit1 = false;
                                    break;
                                default:
                                    System.out.println("The wrong instruction");
                                    break;

                            }
                        } while (exit1);
                        break;
                    case 2:
                        boolean exit2 = true;
                        do {
                            System.out.print("\nChoose the table which you want to see:\n");
                            System.out.println("Students - press 1\nTeachers - press 2\nSubjects - press 3\nStudent_Subject - press 4\n" +
                                    "Attendance - press 5\nMarks - press 6\nFor exit - press 0");
                            int key2 = scanner.nextInt();
                            switch (key2) {
                                case 1:
                                    tableStudent.insertData();
                                    break;
                                case 2:
                                    tableTeacher.insertData();
                                    break;
                                case 3:
                                    tableSubject.insertData();
                                    break;
                                case 4:
                                    tableStudentsSubjects.insertData();
                                    break;
                                case 5:
                                    tableAttendance.insertData();
                                    break;
                                case 6:
                                    tableMarks.insertData();
                                    break;
                                case 0:
                                    exit2 = false;
                                    break;
                                default:
                                    System.out.println("The wrong instruction");
                                    break;
                            }
                        } while (exit2);
                        break;
                    case 3:
                        boolean exit3 = true;
                        do {
                            System.out.print("\nChoose the table which you want to see:\n");
                            System.out.println("Students - press 1\nTeachers - press 2\nSubjects - press 3\nStudent_subject - press\n" +
                                    "Attendance - press 5\nMarks - press 6\nFor exit - press 0");
                            int key4 = scanner.nextInt();
                            switch (key4) {
                                case 1:
                                    tableStudent.deleteRow();
                                    break;
                                case 2:
                                    tableTeacher.deleteRow();
                                    break;
                                case 3:
                                    tableSubject.deleteRow();
                                    break;
                                case 4:
                                    tableStudentsSubjects.deleteRow();
                                    break;
                                case 5:
                                    tableAttendance.deleteRow();
                                    break;
                                case 6:
                                    tableMarks.deleteRow();
                                    break;
                                case 0:
                                    exit3 = false;
                                    break;
                                default:
                                    System.out.println("The wrong instruction");
                                    break;

                            }
                        } while (exit3);
                        break;
                    case 4:
                        boolean exit4 = true;
                        do {
                            System.out.print("\nChoose the table which you want to see:\n");
                            System.out.println("Students - press 1\nTeachers - press 2\nSubjects - press 3\nStudent_Subject - press 4\n" +
                                    "Attendance - press 5\nMarks - press 6\nFor exit - press 0");
                            int key5 = scanner.nextInt();
                            switch (key5) {
                                case 1:
                                    tableStudent.updateData();
                                    break;
                                case 2:
                                    tableTeacher.updateData();
                                    break;
                                case 3:
                                    tableSubject.updateData();
                                    break;
                                case 4:
                                    tableStudentsSubjects.updateData();
                                    break;
                                case 5:
                                    tableAttendance.updateData();
                                    break;
                                case 6:
                                    tableMarks.updateData();
                                    break;
                                case 0:
                                    exit4 = false;
                                    break;
                                default:
                                    System.out.println("The wrong instruction");
                                    break;

                            }
                        } while (exit4);
                        break;
                    case 5:
                        boolean exit5 = true;
                        do {
                            System.out.print("\nChoose the table which you want to see:\n");
                            System.out.println("Students - press 1\nTeachers - press 2\nSubjects - press 3\nStudent_Subject - press 4\n" +
                                    "Attendance - press 5\nMarks - press 6\nFor exit - press 0");
                            int key2 = scanner.nextInt();
                            switch (key2) {
                                case 1:
                                    tableStudent.dropTable();
                                    break;
                                case 2:
                                    tableTeacher.dropTable();
                                    break;
                                case 3:
                                    tableSubject.dropTable();
                                    break;
                                case 4:
                                    tableStudentsSubjects.dropTable();
                                    break;
                                case 5:
                                    tableAttendance.dropTable();
                                    break;
                                case 6:
                                    tableMarks.dropTable();
                                    break;
                                case 0:
                                    exit5 = false;
                                    break;
                                default:
                                    System.out.println("The wrong instruction");
                                    break;

                            }
                        } while (exit5);
                        break;
                    case 6:
                        boolean exit6 = true;
                        do {
                            System.out.print("\nChoose the table which you want to see:\n");
                            System.out.println("Students - press 1\nTeachers - press 2\nSubjects - press 3\nStudent_Subject - press 4\n" +
                                    "Attendance - press 5\nMarks - press 6\nFor exit - press 0");
                            int key2 = scanner.nextInt();
                            switch (key2) {
                                case 1:
                                    tableStudent.selectByName();
                                    break;
                                case 2:
                                    tableTeacher.selectByName();
                                    break;
                                case 3:
                                    tableSubject.selectByName();
                                    break;
                                case 4:
                                    tableStudentsSubjects.selectByName();
                                    break;
                                case 5:
                                    tableAttendance.selectByName();
                                    break;
                                case 6:
                                    tableMarks.selectByName();
                                    break;
                                case 0:
                                    exit6 = false;
                                    break;
                                default:
                                    System.out.println("The wrong instruction");
                                    break;

                            }
                        } while (exit6);
                        break;
                    case 7:
                        boolean exit7 = true;
                        do {
                            System.out.print("\nChoose the average point which you want to see:\n");
                            System.out.println("By all group - press 1\nBy student - press 2\nBy 1 subject to student - press 3\nFor exit - press 0");
                            int key2 = scanner.nextInt();
                            switch (key2) {
                                case 1:
                                    tableMarks.getAverageByGroup();
                                    break;
                                case 2:
                                    tableStudent.getAverageByGroup();
                                    break;
                                case 3:
                                    tableStudentsSubjects.getAverageByGroup();
                                    break;
                                case 0:
                                    exit7 = false;
                                    break;
                                default:
                                    System.out.println("The wrong instruction");
                                    break;

                            }
                        } while (exit7);
                        break;
                    case 8:
                        tableStudent.getAllMarks();
                        break;
                    case 0:
                        System.out.println("Exit. Good luck!");
                        exit = false;
                        break;
                    default:
                        System.out.println("The wrong instruction!");
                        break;
                }
            } while (exit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
