package homework.ch11_13.p3;

import java.util.Objects;

public class Faculty extends Person {
    private String email;
    private int facultyId;
    private String title;

    public Faculty() {
    }

    public Faculty(String name, int age, int facultyId, String title, String email) {
        super(name, age);
        this.email = email;
        this.facultyId = facultyId;
        this.title = title;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public String getEmail() {
        return email;
    }

    public String getTitle() {
        return title;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Faculty clone() throws CloneNotSupportedException {
        Faculty clone = (Faculty) super.clone();
        if(this.title!=null)    clone.title =new String(this.title);
        else clone.title=null;
        clone.facultyId = this.facultyId;
        if(this.email!=null)    clone.email =new String(this.email);
        else clone.email=null;
        return clone;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Faculty) {
            return super.equals(obj) && Objects.equals(this.email,((Faculty) obj).email) && Objects.equals(this.title,((Faculty) obj).title) && this.facultyId == ((Faculty) obj).facultyId;
        }
        return false;
    }

    @Override
    public String toString() {
        return super.toString() + "facultyId: " + this.facultyId + ", title: " + this.title + ", email: " + this.email;
    }
}
