package jm.task.core.jdbc.model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private static Long id;

    @Column
    private static String name;

    @Column
    private static String lastName;

    @Column
    private static Byte age;

    public User() {

    }

    public User(String name, String lastName, Byte age) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

    public static Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public static String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public static String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public static Byte getAge() { return age; }

    public void setAge(Byte age) { this.age = age; }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id +
                ", name='" + name + '\'' +
                ", last name='" + lastName + '\'' +
                ", age=" + age +
                '}';
    }
}
