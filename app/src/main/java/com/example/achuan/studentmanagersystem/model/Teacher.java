package com.example.achuan.studentmanagersystem.model;

/**
 * Created by achuan on 16-5-5.
 * 功能：教师主要信息类
 */
public class Teacher
{
    private int teacher_id;//教工号
    private String password;//密码
    //private String sdept;//院系


    public Teacher() {

    }

    public Teacher(int teacher_id, String password) {
        this.teacher_id = teacher_id;
        this.password = password;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
