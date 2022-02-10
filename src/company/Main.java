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
            SubjectRepository tableSubject = new SubjectRepository(connection, tableTeacher.getCount());
            Student_SubjectRepository tableStudentsSubjects = new Student_SubjectRepository(connection, tableStudent.getCount(), tableSubject.getCount());

            boolean exit = true;
            do {
                System.out.println("\nIf you want to select all rows from table press '1'\n" +
                        "If you want to insert data in the table press '2'\n" +
                        "If you want to delete row in the table press '3'\n" +
                        "If you want to update data in the table press '4'\n" +
                        "If you want to drop table press '5'\n" +
                        "If you want to add subject to student press '6'\n" +
                        "For exit press '0'\n");
                System.out.print("Enter the key: ");
                int key = scanner.nextInt();
                switch (key) {
                    case 1:
                        boolean exit2 = true;
                        do {
                            System.out.print("\nChoose the table which you want to see:\n");
                            System.out.println("Students - press 1\nTeachers - press 2\nSubjects - press 3\nStudent_Subject - press 4\nFor exit - press 0");
                            int key2 = scanner.nextInt();
                            switch (key2) {
                                case 1:
                                    tableStudent.selectAllStudents();
                                    break;
                                case 2:
                                    tableTeacher.selectAllTeachers();
                                    break;
                                case 3:
                                    tableSubject.selectAllSubjects();
                                    break;
                                case 4:
                                    tableStudentsSubjects.selectAllDiary();
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
                    case 2:
                        boolean exit3 = true;
                        do {
                            System.out.print("\nChoose the table which you want to see:\n");
                            System.out.println("Students - press 1\nTeachers - press 2\nSubjects - press 3\nStudent_Subject - press 4\nFor exit - press 0");
                            int key2 = scanner.nextInt();
                            switch (key2) {
                                case 1:
                                    tableStudent.insertStudents();
                                    break;
                                case 2:
                                    tableTeacher.insertTeachers();
                                    break;
                                case 3:
                                    tableSubject.insertSubject();
                                    break;
                                case 4:
                                    tableStudentsSubjects.insertSubject();
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
                    case 3:
                        boolean exit4 = true;
                        do {
                            System.out.print("\nChoose the table which you want to see:\n");
                            System.out.println("Students - press 1\nTeachers - press 2\nSubjects - press 3\nFor exit - press 0");
                            int key2 = scanner.nextInt();
                            switch (key2) {
                                case 1:
                                    tableStudent.deleteStudent();
                                    break;
                                case 2:
                                    tableTeacher.deleteTeacher();
                                    break;
                                case 3:
                                    tableSubject.deleteSubject();
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
                    case 4:
                        boolean exit5 = true;
                        do {
                            System.out.print("\nChoose the table which you want to see:\n");
                            System.out.println("Students - press 1\nTeachers - press 2\nSubjects - press 3\nFor exit - press 0");
                            int key2 = scanner.nextInt();
                            switch (key2) {
                                case 1:
                                    tableStudent.updateStudent();
                                    break;
                                case 2:
                                    tableTeacher.updateTeacher();
                                    break;
                                case 3:
                                    tableSubject.updateSubject();
                                    break;
                                case 0:
                                    System.out.println("Exit. Good luck!");
                                    exit5 = false;
                                    break;
                                default:
                                    System.out.println("The wrong instruction");
                                    break;

                            }
                        } while (exit5);
                        break;
                    case 5:
                        boolean exit6 = true;
                        do {
                            System.out.print("\nChoose the table which you want to see:\n");
                            System.out.println("Students - press 1\nTeachers - press 2\nSubjects - press 3\nStudent_Subject - press 4\nFor exit - press 0");
                            int key2 = scanner.nextInt();
                            switch (key2) {
                                case 1:
                                    tableStudent.dropStudents();
                                    break;
                                case 2:
                                    tableTeacher.dropTeachers();
                                    break;
                                case 3:
                                    tableSubject.dropSubjects();
                                    break;
                                case 4:
                                    tableStudentsSubjects.dropStudents_Subjects();
                                    break;
                                case 0:
                                    System.out.println("Exit. Good luck)");
                                    exit6 = false;
                                    break;
                                default:
                                    System.out.println("The wrong instruction");
                                    break;

                            }
                        } while (exit6);
                        break;
                    case 6:
                        tableStudentsSubjects.addStudentToSubject();
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
