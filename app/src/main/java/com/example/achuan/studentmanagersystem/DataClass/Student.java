package com.example.achuan.studentmanagersystem.DataClass;

/**
 * Created by achuan on 16-5-7.
 */
public class Student
{
    private int student_id;//学号
    private String name;//姓名
    private String grade;//班级
    /*1-构造方法*/
    public Student() {

    }
    public Student(int student_id, String name, String grade) {
        this.student_id = student_id;
        this.name = name;
        this.grade = grade;
    }
    /*2-get和set方法*/
    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
