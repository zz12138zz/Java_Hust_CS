package homework.ch11_13.p3;

import java.util.Objects;

public class Student extends Person {
    private String classNo;
    private String department;
    private int studentId;

    public Student() {
    }

    public Student(String name, int age, int studentId, String department, String classNo) {
        super(name, age);
        this.classNo = classNo;
        this.studentId = studentId;
        this.department =department;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getClassNo() {
        return classNo;
    }

    public String getDepartment() {
        return department;
    }

    public void setClassNo(String classNo) {
        this.classNo = classNo;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    @Override
    public Student clone() throws CloneNotSupportedException {
        Student clone = (Student) super.clone();
        clone.studentId = this.studentId;
        if(this.classNo!=null)  clone.classNo =new String(this.classNo);
        else clone.classNo=null;
        if(this.department!=null)   clone.department =new String(this.department);
        else clone.department=null;
        return clone;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Student) {
            return super.equals(obj) && Objects.equals(this.classNo,((Student) obj).classNo) && this.studentId == ((Student) obj).studentId && Objects.equals(this.department,((Student) obj).department);
        }
        return false;
    }

    @Override
    public String toString() {
        return super.toString() + "studentId: " + this.studentId + ", department: " + this.department + ", classNo: " + this.classNo;
    }
}
