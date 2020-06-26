package com.example.hw6;

import java.io.Serializable;

public class UserData implements Serializable {
    int image;
    String firstName;
    String lastName;
    String studentId;
    String dept;

    public UserData(int image, String firstName, String lastName, String studentId, String dept) {
        this.image = image;
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentId = studentId;
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "image=" + image +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", studentId='" + studentId + '\'' +
                ", dept='" + dept + '\'' +
                '}';
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }
}
