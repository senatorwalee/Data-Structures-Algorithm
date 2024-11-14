package assignment3;

public class Person {
    String name;
    String role;
    int hours;

    public Person() {
        this.name = "";
        this.role = "";
        this.hours = 0;
    }

    public Person(String name, String role, int hours) {
        this.name = name;
        this.role = role;
        this.hours = hours;
    }

    public String getName() {
        return name;
    }

    public int getHours() {
        return hours;
    }

    public String getRole() {
        return role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
