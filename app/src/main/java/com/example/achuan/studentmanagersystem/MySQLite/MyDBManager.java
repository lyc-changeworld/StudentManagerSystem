package com.example.achuan.studentmanagersystem.MySQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.achuan.studentmanagersystem.DataClass.Student;
import com.example.achuan.studentmanagersystem.DataClass.Teacher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by achuan on 16-5-5.
 */
public class MyDBManager
{
    private MyDatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;

    public MyDBManager(Context context)
    {
        mDatabaseHelper=new MyDatabaseHelper(context);
        mDatabase=mDatabaseHelper.getWritableDatabase();//获得数据库操作对象
    }
    /******数据库的相关操作方法*******/
    /*1-查询教师用户*/
    public Teacher queryTeacher(int id)
    {
        //通过query()方法获得Cursor对象,查询到的所以数据都将从这个对象中取出
        /*Cursor cursor=mDatabase.query(
                "Teacher",//指定查询的表名 相当于：from table_name
                new String[]{"teacher_id"},//指定查询的列名          select column1,column2
                String.valueOf(id),//指定where的约束条件     where column=value
                null,//为where中的占位符提供具体的值
                null,//指定需要group by的列    group by column
                null,//对group by后的结果进一步约束 having column=value
                null//指定查询结果的排序方式     order by column1,column2
        );*/
        //SQL操作数据库
        Cursor cursor=mDatabase.rawQuery(
                "select * from Teacher where teacher_id=?",
                new String[]{id+""}
                ,null);
        if(cursor.moveToFirst())//如果游标中存在信息
        {
            Teacher teacher=new Teacher();
            //遍历Cursor对象,取出数据
            do {
                //遍历查询每一行的数据
                //通过cursor.getColumnIndex()方法获得某一列在表中对应的位置索引
                teacher.setTeacher_id(cursor.getInt(cursor.getColumnIndex("teacher_id")));
                teacher.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            }while (cursor.moveToNext());
            cursor.close();//关闭游标,释放空间
            return teacher;
        }
        else //如果查询无结果,返回空
        {
            cursor.close();
            return null;
        }
    }
    /*2-添加教师用户*/
    public void addTeacher(Teacher teacher)
    {
        ContentValues values=new ContentValues();//数据存储对象,用来存储相关信息
        //获得数据
        int teacher_id=teacher.getTeacher_id();
        String password=teacher.getPassword();
        //开始组装数据
        values.put("teacher_id",teacher_id);
        values.put("password",password);
        mDatabase.insert(           //插入数据
                "Teacher",//表名
                null,//未添加数据的列自动赋值NULL
                values);//数据对象
        values.clear();
    }
    /*3-查询得到学生表中所有学生的信息*/
    public List<Student> queryAllStudent()
    {
        //SQL操作数据库
        Cursor cursor=mDatabase.rawQuery(
                "select student_id,name,grade from Student",
        null);
        if(cursor.moveToFirst())//如果游标中存在信息
        {
            ArrayList<Student> students=new ArrayList<Student>();//学生信息存储数组
            do {
                //遍历查询每一行的数据
                Student student=new Student();
                student.setStudent_id(cursor.getInt(cursor.getColumnIndex("student_id")));
                student.setName(cursor.getString(cursor.getColumnIndex("name")));
                student.setGrade(cursor.getString(cursor.getColumnIndex("grade")));
                students.add(student);//添加单个学生的信息到动态数组中
            }while (cursor.moveToNext());
            cursor.close();//关闭游标,释放空间
            return students;
        }
        else {
            cursor.close();//关闭游标,释放空间
            return null;
        }
    }
    /*4-添加学生信息数据*/
    public void addStudent(Student student)
    {
        ContentValues values=new ContentValues();//数据存储对象,用来存储相关信息
        //获得数据
        int student_id=student.getStudent_id();
        String name=student.getName();
        String grade=student.getGrade();
        //开始组装数据
        values.put("student_id",student_id);
        values.put("name",name);
        values.put("grade",grade);
        mDatabase.insert(           //插入数据
                "Student",//表名
                null,//未添加数据的列自动赋值NULL
                values);//数据对象
        values.clear();
    }
    /*5-删除学生信息数据*/
    public void deleteStudent(int student_id)
    {
        //通过学号来删除该学生的信息
        mDatabase.execSQL(
                "delete from Student where student_id=?",
                new Object[]{student_id});
    }
    /*6-查询学生信息是否存在*/
    public Student queryStudent(int id)
    {
        //SQL操作数据库
        Cursor cursor=mDatabase.rawQuery(
                "select * from Student where student_id=?",
                new String[]{id+""}
                ,null);
        if(cursor.moveToFirst())//如果游标中存在信息
        {
            Student student=new Student();
            //遍历Cursor对象,取出数据
            do {
                //遍历查询每一行的数据
                //通过cursor.getColumnIndex()方法获得某一列在表中对应的位置索引
                student.setStudent_id(cursor.getInt(cursor.getColumnIndex("student_id")));
                student.setName(cursor.getString(cursor.getColumnIndex("name")));
                student.setGrade(cursor.getString(cursor.getColumnIndex("grade")));
            }while (cursor.moveToNext());
            cursor.close();//关闭游标,释放空间
            return student;
        }
        else //如果查询无结果,返回空
        {
            cursor.close();
            return null;
        }
    }
    /*7-更新学生信息*/
    public void updateStudent(Student student)
    {
        //获得传递过来的数据
        String name=student.getName();
        String grade=student.getGrade();
        int student_id=student.getStudent_id();
        //数据存储对象,用来存储相关信息
        ContentValues values=new ContentValues();
        values.put("name",name);//更新姓名信息
        values.put("grade",grade);//更新班级信息
        mDatabase.update(
                "Student",//表名
                values,//数据 相当于：set name ="",set grade=""
                "student_id=?",//相当于：where student_id =
                new String[]{student_id+""});//匹配的学号
        values.clear();
    }

}
