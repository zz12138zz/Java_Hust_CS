package homework.ch11_13.p3;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Course implements Cloneable {
    private String courseName;
    private List<Person> students = new ArrayList<>();
    private Person teacher;

    public Course(String courseName, Person teacher) {
        this.courseName = courseName;
        this.teacher = teacher;
    }

    public void register(Person s) {
        if (s instanceof Student) {
            for (Person a : students) {
                if (s.equals(a)) return;
            }
            students.add(s);
        }
    }

    public String getCourseName() {
        return courseName;
    }

    public List<Person> getStudents() {
        return students;
    }

    public Person getTeacher() {
        return teacher;
    }

    public void unregister(Person s) {
        students.remove(s);
    }

    public int getNumberOfStudent() {
        return students.size();
    }

    @Override
    public Course clone() throws CloneNotSupportedException {
        Course clone = (Course) super.clone();
        clone.students = new ArrayList<>();
        for (Person s : this.students) {
            clone.students.add(s.clone());
        }
        if(this.courseName!=null)   clone.courseName = new String(this.courseName);
        else clone.courseName=null;
        clone.teacher = this.teacher.clone();
        return clone;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Course Name: ").append(this.courseName).append("\n").append("Teacher Info: ").append(this.teacher.toString()).append("\nStudent List:");
        for (Person stu : this.students) {
            s.append("\n\t").append(stu.toString());
        }
        s.append("\nTotally: ").append(this.students.size());
        if (this.students.size() == 1) {
            s.append(" student.");
        } else s.append(" students");
        return s.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Course) {
            return this.students.size() == ((Course) obj).students.size() && this.students.containsAll(((Course) obj).students) && Objects.equals(this.courseName,((Course) obj).courseName) && Objects.equals(this.teacher,((Course) obj).teacher);
            //return this.students.size() == ((Course) obj).students.size() && this.students.containsAll(((Course) obj).students) && this.courseName.equals(((Course) obj).courseName) && this.teacher.equals(((Course) obj).teacher);
        }
        return false;
    }
}
