package company;

import java.sql.*;

import static company.StudentRepository.*;

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
            tableStudent.insertStudents();
            tableStudent.selectAllStudents();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }




}
