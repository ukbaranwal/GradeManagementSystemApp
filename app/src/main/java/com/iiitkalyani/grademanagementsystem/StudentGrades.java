package com.iiitkalyani.grademanagementsystem;

public class StudentGrades {
    private String subjectCode, grade;
    public StudentGrades(String subjectCode, String grade){
        this.subjectCode = subjectCode;
        this.grade = grade;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
