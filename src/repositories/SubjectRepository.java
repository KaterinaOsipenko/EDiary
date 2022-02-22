package repositories;

import pojo.*;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SubjectRepository implements Tables {
    static final Scanner scanner = new Scanner(System.in);
    private final Connection connection;
    private static final HashMap<Integer, Subject> subjects = new HashMap<>();

   public SubjectRepository (Connection connection) {
        this.connection = connection;
        createTable();
    }

    private void createTable() {
        try (Statement statement = this.connection.createStatement()) {
            DatabaseMetaData dbData = this.connection.getMetaData();
            ResultSet setTables = dbData.getTables(null, null, "subjects", null);
            if (setTables.next()) {
                System.out.println("\nTable subjects has already exists!\n");
                putToMap();
            } else {
                System.out.println("\nCreating table subjects...\n");
                String createTable = "CREATE TABlE IF NOT EXISTS subjects (" +
                        "id int(6) PRIMARY KEY AUTO_INCREMENT," +
                        "name varchar(33) NOT NULL," +
                        "teacher int(6) NOT NULL)";
                statement.executeUpdate(createTable);
                System.out.println("\nTable subjects was created!\n");
                alterTeachers();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static HashMap<Integer, Subject> getSubjects() {
        return subjects;
    }

    public void alterTeachers() {
        String alert = "ALTER TABLE subjects ADD CONSTRAINT teacher_fk FOREIGN KEY (teacher) REFERENCES teachers(id) ON DELETE CASCADE";
        try(Statement statement = this.connection.createStatement()) {
            statement.execute(alert);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getCount() {
        int res = 0;
        String count = "SELECT COUNT(*) FROM subjects";
        try (Statement statement = this.connection.createStatement();
             ResultSet countRes =  statement.executeQuery(count)) {
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
        try (Statement statement = this.connection.createStatement();
             ResultSet joinSet = statement.executeQuery(join)) {
            System.out.println("\nJoin-table subjects with teachers:");
            System.out.println("--------------------------------------");
            while (joinSet.next()) {
                System.out.println(joinSet.getString(1) + " " + joinSet.getString(2) + " " + joinSet.getString(3) + " " + joinSet.getString(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropTable() {
        String drop = "DROP TABLE IF EXISTS subjects";
        System.out.println("\nDeleting table subjects...\n");
        try (Statement statement = connection.createStatement()) {
            statement.execute(drop);
            subjects.clear();
            System.out.println("\nDeleting was successful!\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertData() {
        String getId = "SELECT LAST_INSERT_ID()";
        System.out.print("Please, enter the teachers id: ");
        int teacherId  = scanner.nextInt();
        System.out.print("Please, enter the subject`s name: ");
        String nameSubject = scanner.next();
        String insert = "INSERT INTO subjects (name, teacher) VALUES (?, (SELECT id FROM teachers WHERE id = " + teacherId + "))";
        Subject subject = new Subject(nameSubject, teacherId);
     try (PreparedStatement insertTeacher = this.connection.prepareStatement(insert);
             Statement getIdStatement = this.connection.createStatement()) {
            insertTeacher.setString(1, subject.getName());
            insertTeacher.executeUpdate();
            ResultSet lastId = getIdStatement.executeQuery(getId);
            while (lastId.next()) {
                subject.setId(lastId.getInt(1));
            }
            subjects.put(subject.getId(), subject);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateData() {
        System.out.print("Enter the subject`s id whose name you want to change: ");
        int subjectId = scanner.nextInt();
        System.out.print("Enter the new value of the subject`s name: ");
        String subjectName = scanner.next();
        String update = "UPDATE subjects SET name = '" + subjectName + "' WHERE id = " + subjectId;
        try(Statement statement = this.connection.createStatement()) {
            statement.execute(update);
            Subject subject = subjects.get(subjectId);
            subject.setName(subjectName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void selectAll() {
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

    @Override
    public void deleteRow() {
        System.out.print("Enter subject`s id that you want to delete from tables: ");
        int subjectId = scanner.nextInt();
        String delete = "DELETE FROM subjects WHERE id = " + subjectId;
        try(Statement statement = this.connection.createStatement()) {
            statement.execute(delete);
            subjects.remove(subjectId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void selectByName() {
        System.out.print("Enter the subject`s id: ");
        int subjectId = scanner.nextInt();
        String selectName = "SELECT name FROM subjects WHERE id = " + subjectId;
        try(Statement statement = this.connection.createStatement();
            ResultSet set = statement.executeQuery(selectName)) {
            while (set.next()) {
                System.out.println(set.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void putToMap() {
        try (Statement statement = connection.createStatement();
             ResultSet selectAll = statement.executeQuery("SELECT * FROM subjects")) {
            while (selectAll.next()) {
                Subject subject = new Subject(selectAll.getInt(1), selectAll.getString(2), selectAll.getInt(3));
                subjects.put(subject.getId(), subject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getMarksBySubject() {
        System.out.print("Enter the id of subject: ");
        int idSubject = scanner.nextInt();
        Subject subject = subjects.get(idSubject);
        subject.printMarks();
    }

    public void printMap() {
        subjects.values().forEach(entry -> System.out.println(entry.getId() + " " + entry.getName() + " " + entry.getTeacher()));
    }
}
