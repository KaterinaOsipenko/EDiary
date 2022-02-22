package repositories;

import pojo.Mark;
import pojo.Student;
import pojo.Subject;
import pojo.Tables;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Scanner;

public class MarksRepository implements Tables {
    static final Scanner scanner = new Scanner(System.in);
    private final Connection connection;

    public MarksRepository(Connection connection) {
        this.connection = connection;
        createTable();
    }

    private void createTable() {
        try (Statement statement = this.connection.createStatement()) {
            DatabaseMetaData dbData = this.connection.getMetaData();
            ResultSet setTables = dbData.getTables(null, null, "marks", null);
            if (setTables.next()) {
                System.out.println("\nTable marks has already exists!\n");
                putToMap();
            } else {
                System.out.println("\nCreating table marks...\n");
                String createTable = "CREATE TABlE IF NOT EXISTS marks (" +
                        "mark_id int(6) PRIMARY KEY AUTO_INCREMENT," +
                        "student_subject_id int(6) NOT NULL, " +
                        "mark int(3) NOT NULL, " +
                        "date DATE NOT NULL, " +
                        "notice varchar(33)," +
                        "CONSTRAINT check_mark CHECK ((mark >= 0) AND (mark <= 100))) " ;
                statement.executeUpdate(createTable);
                System.out.println("\nTable marks was created!\n");
                alterAttendance();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void putToMap() {
        try (Statement statement = connection.createStatement();
             ResultSet selectAll = statement.executeQuery("SELECT student_subject_id, mark, date, notice FROM marks")) {
            while (selectAll.next()) {
                Student student = getStudent(selectAll.getInt(1));
                student.addMark(getSubject(selectAll.getInt(1)).getId(), selectAll.getInt(2),
                        selectAll.getDate(3), selectAll.getString(4));
                Subject subject = getSubject(selectAll.getInt(1));
                subject.addMark(getStudent(selectAll.getInt(1)).getId(), selectAll.getInt(2),
                        selectAll.getDate(3), selectAll.getString(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void alterAttendance() {
        String alterStudent_Subject = "ALTER TABLE marks ADD CONSTRAINT marks_fk FOREIGN KEY (student_subject_id) REFERENCES student_subject(id) ON UPDATE CASCADE ON DELETE CASCADE";
        try(Statement statement = this.connection.createStatement()) {
            statement.execute(alterStudent_Subject);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropTable() {
        String drop = "DROP TABLE IF EXISTS marks";
        System.out.println("\nDeleting table marks...\n");
        try (Statement statement = connection.createStatement()) {
            statement.execute(drop);
            StudentRepository.getStudents().forEach((integer, student) -> student.getMarks().clear());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertData() {
        String insert = "INSERT INTO marks (student_subject_id, mark, date, notice) VALUES (?, ?, ?, ?)";
        System.out.print("Please, enter the student_subject`s id : ");
        int studentSubjectId = scanner.nextInt();
        System.out.print("Please, enter mark: ");
        int value = scanner.nextInt();
        java.util.Date date = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        System.out.print("Enter the notice if you want: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try (PreparedStatement insertMark = this.connection.prepareStatement(insert)) {
            String notice = reader.readLine();
            insertMark.setInt(1, studentSubjectId);
            insertMark.setInt(2, value);
            insertMark.setDate(3, sqlDate);
            insertMark.setString(4, notice);
            insertMark.executeUpdate();
            getStudent(studentSubjectId).addMark(getSubject(studentSubjectId).getId(), value, sqlDate, notice);
            getSubject(studentSubjectId).addMark(getStudent(studentSubjectId).getId(), value, sqlDate, notice);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public Subject getSubject(int id) {
        int subjectId = 0;
        Subject subject = null;
        String getSubject = "SELECT subject_id FROM student_subject WHERE id = " + id;
        try(Statement statement = this.connection.createStatement()) {
            ResultSet subj = statement.executeQuery(getSubject);
            while (subj.next()) {
                subjectId = subj.getInt(1);
            }
            subject = SubjectRepository.getSubjects().get(subjectId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subject;
    }

    public Student getStudent(int id) {
        int studentId = 0;
        Student student = null;
        String getStudent = "SELECT student_id FROM student_subject WHERE id = " + id;
        try(Statement statement = this.connection.createStatement()) {
            ResultSet subj = statement.executeQuery(getStudent);
            while (subj.next()) {
                studentId = subj.getInt(1);
            }
            student = StudentRepository.getStudents().get(studentId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    @Override
    public void updateData() {
        System.out.print("Enter the mark`s id whose mark you want to update: ");
        int markId = scanner.nextInt();
        System.out.print("Enter the new value of mark: ");
        int mark = scanner.nextInt();
        int studentSubjectId = getStudents_SubjectId(markId);
        Mark oldVal = getMark(markId);
        Mark newVal = new Mark(mark, oldVal.getDate(), oldVal.getNotice());
        String update = "UPDATE marks SET mark = '" + mark +  "' WHERE mark_id = " + markId;
        try(PreparedStatement statement = this.connection.prepareStatement(update)) {
            statement.executeUpdate();
            getStudent(studentSubjectId).updateMark(getSubject(studentSubjectId).getId(), oldVal, newVal);
            getSubject(studentSubjectId).updateMark(getStudent(studentSubjectId).getId(), oldVal, newVal);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void selectAll() {
        System.out.println("\nTable marks");
        System.out.println("--------------------------------------");
        try (Statement statement = connection.createStatement();
             ResultSet selectAll = statement.executeQuery("SELECT marks.mark_id, students.id,  subjects.name, marks.mark, marks.date, marks.notice " +
                     "FROM marks, student_subject, students, subjects WHERE marks.student_subject_id = student_subject.id AND " +
                     "student_subject.student_id = students.id AND student_subject.subject_id = subjects.id")) {
            while (selectAll.next()) {
                System.out.println(selectAll.getString(1) + "   " + selectAll.getString(2) + "      " + selectAll.getString(3) +
                        "   " + selectAll.getString(4) + "  " + selectAll.getString(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRow() {
        System.out.print("Enter the student`s id who you want to delete from tables: ");
        int markId = scanner.nextInt();
        String delete = "DELETE FROM marks WHERE mark_id = " + markId;
        try(Statement statement = this.connection.createStatement()) {
            statement.execute(delete);
            int studentSubjectId = getStudents_SubjectId(markId);
            getStudent(studentSubjectId).deleteMark(getSubject(studentSubjectId).getId(), getMark(markId));
            getSubject(studentSubjectId).deleteMark(getStudent(studentSubjectId).getId(), getMark(markId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Mark getMark(int id) {
        Mark mark = new Mark();
        String query = "SELECT mark, date, notice FROM marks WHERE mark_id = " + id;
        try(Statement statement = this.connection.createStatement()) {
           ResultSet set = statement.executeQuery(query);
           while (set.next()) {
               mark.setValue(set.getInt(1));
               mark.setDate(set.getDate(2));
               mark.setNotice(set.getString(3));
           }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mark;
    }

    public int getStudents_SubjectId(int markId) {
        int id = 0;
        String query = "SELECT student_subject_id FROM marks WHERE mark_id = " + markId;
        try (Statement statement = this.connection.createStatement()) {
            ResultSet set = statement.executeQuery(query);
            while (set.next()) {
                id = set.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public void selectByName() {
        System.out.print("Enter the students id: ");
        int studentsId = scanner.nextInt();
        String selectName = "SELECT students.first_name, students.last_name, subjects.name, marks.mark, marks.date, marks.notice" +
                " FROM marks, student_subject, students, subjects WHERE " +
                "marks.student_subject_id = student_subject.id AND student_subject.student_id = students.id AND " +
                "student_subject.subject_id = subjects.id AND students.id = " + studentsId;
        try (Statement statement = this.connection.createStatement();
             ResultSet selectAll = statement.executeQuery(selectName)) {
            while (selectAll.next()) {
                System.out.println(selectAll.getString(1) + "    " + selectAll.getString(2) + "    " + selectAll.getString(3) + "   " +
                        selectAll.getString(4) + " " + selectAll.getString(5) + " " + selectAll.getString(6));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getAverageByGroup() {
        System.out.print("Enter the subject`s id: ");
        int subjectId = scanner.nextInt();
        String average = "SELECT AVG(mark) FROM marks, student_subject, students, subjects WHERE " +
                "marks.student_subject_id = student_subject.id AND student_subject.subject_id = subjects.id" +
                " AND subjects.id =" + subjectId;
        try(Statement statement = this.connection.createStatement()) {
            ResultSet avg = statement.executeQuery(average);
            while (avg.next()) {
                System.out.println(avg.getDouble(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
