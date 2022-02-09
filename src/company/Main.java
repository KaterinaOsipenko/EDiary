package company;

import java.sql.*;

public class Main {

    static String url = "jdbc:mysql://localhost:3306/";
    static String url2 = "jdbc:mysql://localhost:3306/diary";
    static String user = "root";
    static String password = "K30052003o_";

    public static void main(String[] args) {
        DBManager db = new DBManager(url, user, password);
        db.getMetaData();

        try {
            Connection connection = DriverManager.getConnection(url2, user, password);
            StudentRepository tableStudent = new StudentRepository(connection);
            TeacherRepository tableTeacher = new TeacherRepository(connection);
            SubjectRepository tableSubject = new SubjectRepository(connection, tableTeacher.getCount());
            Student_SubjectRepository tableStudentsSubjects = new Student_SubjectRepository(connection, tableStudent.getCount(), tableSubject.getCount());
            tableSubject.selectAllSubjects();
            tableSubject.joinWithTeachers();
            tableStudentsSubjects.selectAllStudents_Subjects();
            tableStudentsSubjects.selectAllDiary();



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }




}
