package company;

public class Student extends Person {
    private int id;

    public Student(String name, String surname) {
        super(name, surname);
    }

    public int getId() {
        return id;
    }
}
