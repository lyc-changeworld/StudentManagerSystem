package com.example.achuan.studentmanagersystem.MySQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by achuan on 16-4-29.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper
{
    private static String DB_NAME = "StudentManager.db";
    private static int DB_VERSION = 2;
    /*4-数据库创建表的操作*/
    public static final String CREATE_Teacher =
            "create table Teacher("
                    +"teacher_id integer primary key autoincrement,"
                    +"password text"//密码
                    +"name text,"
                    +"sdept text," //院系
                    +"mobile_number integer,"//手机号
                    +"qq_number integer)";//qq号
    public static final String CREATE_Student =
            "create table Student("
                    +"student_id integer primary key autoincrement,"
                    +"name text,"//姓名
                    +"grade text,"//班级
                    +"mobile_number integer,"//手机号
                    +"qq_number integer)";//qq号
    /*1-构造方法*/
    public MyDatabaseHelper(Context context) {
        super(
                context,
                DB_NAME,//数据库的名字
                null,//查询数据时返回一个自定义的Cursor,一般传入null
                DB_VERSION);//数据库的版本号,版本号变大时会执行升级数据库操作
    }
    /*2-创建数据库的方法*/
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_Teacher);//先创建一张教工信息表
        //数据库版本2时添加一张新的表时添加
        db.execSQL(CREATE_Student);//创建一个学生信息表
    }
    /*3-升级数据库---数据库的版本号变大时会执行升级数据库操作*/
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion)
        {
            case 1://如果你曾经是第1版本程序的老用户,将会接着创建新的表
                db.execSQL(CREATE_Student);
            /*case 2://后面版本的更新会将之前的更新操作全部执行
                db.execSQL("alter table Book add column category_id integer");//为Book表添加一个新列*/
            default:
            //如果你是第2版本的新用户，将不执行该升级操作,直接onCreate创建两张新的表
        }
    }
}
