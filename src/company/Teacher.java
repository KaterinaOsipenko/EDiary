package company;

public class Teacher extends Person {

    private int id;

    Teacher(int id, String name, String surname) {
        super(name, surname);
        this.id = id;
    }

    Teacher(String name, String surname) {
        super(name, surname);
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }
}
