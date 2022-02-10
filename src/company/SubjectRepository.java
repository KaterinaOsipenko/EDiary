package company;

import java.sql.*;
import java.util.Scanner;
import company.Main.*;

public class SubjectRepository {
    static final Scanner scanner = new Scanner(System.in);
    private Connection connection;
    int count;

    SubjectRepository (Connection connection, int count) {
        this.connection = connection;
        this.count = count;
        createTable();
    }

    private void createTable() {
        try (Statement statement = this.connection.createStatement()) {
            DatabaseMetaData dbData = this.connection.getMetaData();
            ResultSet setTables = dbData.getTables(null, null, "subjects", null);
            if (setTables.next()) {
                System.out.println("\nTable subjects has already exists!\n");
            } else {
                System.out.println("\nCreating table subjects...\n");
                String createTable = "CREATE TABlE IF NOT EXISTS subjects (" +
                        "id int(6) PRIMARY KEY AUTO_INCREMENT," +
                        "name varchar(33) NOT NULL," +
                        "teacher int(6) NOT NULL," +
                        "FOREIGN KEY (teacher) REFERENCES teachers(id) ON UPDATE CASCADE)";
                statement.executeUpdate(createTable);
                System.out.println("\nTable subjects was created!\n");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void alterTeachers() {
        String alert = "ALTER TABLE subjects ADD CONSTRAINT teacher_fk FOREIGN KEY (teacher) REFERENCES teachers(id) ON DELETE CASCADE";
        try(PreparedStatement statement = this.connection.prepareStatement(alert)) {
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void insertSubject() {
        int i = (int)(Math.random() * (this.count - 1)) + 1;
        String insert = "INSERT INTO subjects (name, teacher) VALUES (?, (SELECT id FROM teachers WHERE id = " + i + "))";
        Subject subject = new Subject();
        System.out.print("Please, enter the subject`s name: ");
        String nameSubject = scanner.next();
        subject.setName(nameSubject);
        try (PreparedStatement insertTeacher = this.connection.prepareStatement(insert)) {
            insertTeacher.setString(1, subject.getName());
            insertTeacher.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSubject () {
        System.out.print("Enter the subject`s id whose name you want to change: ");
        int subjectId = scanner.nextInt();
        System.out.print("Enter the new value of the subject`s name: ");
        String subjectName = scanner.next();
        String update = "UPDATE subjects SET name = '" + subjectName + "' WHERE id = " + subjectId;
        try(PreparedStatement statement = this.connection.prepareStatement(update)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSubject () {
        System.out.print("Enter subject`s id that you want to delete from tables: ");
        int subjectId = scanner.nextInt();
        String delete = "DELETE FROM subjects WHERE id = " + subjectId;
        try(PreparedStatement statement = this.connection.prepareStatement(delete)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void selectAllSubjects() {
        System.out.println("\nTable subjects");
        System.out.println("--------------------------------------");
        try (Statement statement = this.connection.createStatement();
             ResultSet selectAll = statement.executeQuery("SELECT * FROM subjects")) {
            while (selectAll.next()) {
                System.out.println(selectAll.getString(1) + " " + selectAll.getString(2) + " " + selectAll.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropSubjects() {
        String drop = "DROP TABLE IF EXISTS subjects";
        System.out.println("\nDeleting table subjects...\n");
        try (Statement statement = connection.createStatement()) {
            statement.execute(drop);
            System.out.println("\nDeleting was successful!\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getCount() {
        int res = 0;
        String count = "SELECT COUNT(*) FROM subjects";
        try (Statement statement = this.connection.createStatement()) {
            ResultSet countRes =  statement.executeQuery(count);
            while (countRes.next()) {
                res = countRes.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public void joinWithTeachers () {
        String join = "SELECT subjects.id,subjects.name, teachers.first_name, teachers.last_name FROM subjects, teachers " +
                "WHERE subjects.id = teachers.id";
        try (Statement statement = this.connection.createStatement()) {
            System.out.println("\nJoin-table subjects with teachers:");
            System.out.println("--------------------------------------");
            ResultSet joinSet = statement.executeQuery(join);
            while (joinSet.next()) {
                System.out.println(joinSet.getString(1) + " " + joinSet.getString(2) + " " + joinSet.getString(3) + " " + joinSet.getString(4));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
