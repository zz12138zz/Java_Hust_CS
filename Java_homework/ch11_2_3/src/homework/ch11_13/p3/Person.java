package homework.ch11_13.p3;

import java.util.Objects;

public class Person implements Cloneable {
    private int age;
    private String name;

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public int getAge() {
        return this.age;
    }

    public String getName() {
        return this.name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Person clone() throws CloneNotSupportedException {
        Person clone = (Person) super.clone();
        clone.age = this.age;
        clone.name = new String(this.name);
        return clone;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Person) {
            return Objects.equals(this.name,((Person) obj).name) && this.age == ((Person) obj).age;
        }
        return false;
    }

    @Override
    public String toString() {
        return "name: " + this.name + ", age: " + this.age + ", ";
    }
}
