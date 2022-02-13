package company;

public class Teacher extends Person {

    private int id;

    Teacher(String name, String surname) {
        super(name, surname);
    }

    public int getId() {
        return id;
    }


}
