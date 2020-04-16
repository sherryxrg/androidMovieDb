package ca.bcit.labgraphql;

public class Student {

    String studentId;
    String studentName;
    String studentNum;
    String studentSet;

    public Student() {

    }

    public Student(String studentId, String studentName, String studentSet) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentSet = studentSet;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentSet() {
        return studentSet;
    }
}
