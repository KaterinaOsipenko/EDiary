package pojo;

import repositories.StudentRepository;

import java.util.HashMap;
import java.util.Map;

public class Student extends Person {
    private Integer id;
    private final HashMap<Integer, Mark> marks = new HashMap<>();

    public Student(Integer id, String name, String surname) {
        super(name, surname);
        this.id = id;
    }

    public Student(String name, String surname) {
        super(name, surname);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public HashMap<Integer, Mark> getMarks() {
        return this.marks;
    }

    public void printMarks() {
        for (Map.Entry<Integer, Mark> entry : this.getMarks().entrySet()) {
            System.out.println(this.getName() + " " + this.getSurname() + " " + entry.getKey() + " " + entry.getValue().getValue() + " " + entry.getValue().getDate() + " " + entry.getValue().getNotice());
        }
    }

    public void addMark(int studentSubjectId, int value, java.sql.Date date, String notice) {
        Student student = StudentRepository.getStudents().get(this.getId());
        student.getMarks().put(studentSubjectId, new Mark(value, date, notice));
    }

    public void deleteMark(int studentSubjectId, Mark mark ) {
        Student student = StudentRepository.getStudents().get(this.getId());
        student.getMarks().remove(studentSubjectId, mark);
    }

    public void updateMark(int studentSubjectId, Mark old, Mark newVal) {
        Student student = StudentRepository.getStudents().get(this.getId());
        student.getMarks().replace(studentSubjectId, old, newVal);
    }
}
