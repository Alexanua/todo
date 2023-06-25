package org.campusmolndal;

import java.time.LocalDate;

public class User {
    private int id;
    private String name;
    private int age;
    private LocalDate registerDate;
    String user = "Användarnamn";

    public User() {
    }

    public User(int id, String name, int age, LocalDate registerDate) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.registerDate = registerDate;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDate getRegisterDate() {
        return this.registerDate;
    }

    public void setRegisterDate(LocalDate registerDate) {
        this.registerDate = registerDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", registerDate=" + registerDate +
                '}';
    }
}
