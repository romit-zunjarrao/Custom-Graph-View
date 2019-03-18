package com.example.customviewassignment;

public class DataObject {
    private String date;
    private int studentCount;

    public DataObject(String date, int studentCount) {
        this.date = date;
        this.studentCount = studentCount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }

    @Override
    public String toString() {
        return "DataObject{" +
                "date='" + date + '\'' +
                ", studentCount=" + studentCount +
                '}';
    }
}
