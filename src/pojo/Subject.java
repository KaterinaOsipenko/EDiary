package pojo;

import repositories.SubjectRepository;

import java.util.HashMap;
import java.util.Map;

public class Subject {
    private Integer id;
    private int teacherId;
    private String name;
    private HashMap<Integer, Mark> marksBySubject = new HashMap<>();

    Subject() {

    }

    public Subject(String name, int teacher) {
        this.name = name;
        this.teacherId = teacher;
    }

    public Subject(Integer id, String name, int teacher) {
        this.id = id;
        this.name = name;
        this.teacherId = teacher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTeacher() {
        return teacherId;
    }

    public void setTeacher(int teacher) {
        this.teacherId = teacher;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public HashMap<Integer, Mark> getMarksBySubject() {
        return marksBySubject;
    }

    public void printMarks() {
        for (Map.Entry<Integer, Mark> entry : this.getMarksBySubject().entrySet()) {
            System.out.println(this.getName() + " " + this.getTeacher() + " " + entry.getKey() + " " + entry.getValue().getValue() + " " + entry.getValue().getDate() + " " + entry.getValue().getNotice());
        }
    }

    public void addMark(int studentSubjectId, int value, java.sql.Date date, String notice) {
        Subject subject = SubjectRepository.getSubjects().get(this.getId());
        subject.getMarksBySubject().put(studentSubjectId, new Mark(value, date, notice));
    }

    public void updateMark(int studentSubjectId, Mark old, Mark newVal) {
        Subject subject = SubjectRepository.getSubjects().get(this.getId());
        subject.getMarksBySubject().replace(studentSubjectId, old, newVal);
    }

    public void deleteMark(int studentSubjectId, Mark mark ) {
        Subject subject = SubjectRepository.getSubjects().get(this.getId());
        subject.getMarksBySubject().remove(studentSubjectId, mark);
    }


}
