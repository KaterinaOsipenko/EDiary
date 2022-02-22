package pojo;

public class Teacher extends Person {

    private int id;

    public Teacher(int id, String name, String surname) {
        super(name, surname);
        this.id = id;
    }

    public Teacher(String name, String surname) {
        super(name, surname);
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }
}
