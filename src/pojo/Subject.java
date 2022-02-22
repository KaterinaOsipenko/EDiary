package pojo;

public class Subject {
    private Integer id;
    private int teacherId;
    private String name;

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
}
