package com.example.achuan.studentmanagersystem;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.achuan.studentmanagersystem.DataClass.Student;
import com.example.achuan.studentmanagersystem.Uitl.MyTopbar;

/**
 * Created by achuan on 16-5-10.
 * 功能：对学生信息进行编辑并保存
 */
public class EditorStudentActivity extends ManagerActivtiy
{
    private boolean is_show_to_editor=false;
    //标题对象
    private MyTopbar topbar_add;
    //输入框
    private EditText mName,mGrade,mStudent_ID;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editor_student_info);
        initView();
        topbarSetClickListener();
        //获得启动该界面的Intent
        Intent intent=getIntent();
        //获得传递过来的数据
        String name=intent.getStringExtra("name");
        String grade=intent.getStringExtra("grade");
        int student_id=intent.getIntExtra("student_id",10);
        if(name!=null)
        {//说明是从详细信息界面跳转过来
            is_show_to_editor=true;
            Log.d("Achuan","从详细信息界面传递过来的");
            Log.d("Achuan","姓名："+name);
            Log.d("Achuan","班级："+grade);
            Log.d("Achuan","学号："+student_id);
            /*往edittext中添加信息*/
            mName.setText(name);
            mGrade.setText(grade);
            mStudent_ID.setText(String.valueOf(student_id));
            //设置光标的位置
            mName.setSelection(mName.getText().length());
            //设置不可编辑
            //mStudent_ID.setEnabled(false);
            mStudent_ID.setFocusable(false);
        }
        else {
            is_show_to_editor=false;
            Log.d("Achuan","从新建界面传递过来的");
        }
    }
    /*2-标题的点击事件*/
    private void topbarSetClickListener() {
        /***********通过引用不同的实例，可以为其添加不同监听的方法（满足模板的需求）**********/
        /*为自定义的接口中的方法名添加具体的方法*/
        topbar_add.setOnTopbarClickListener(new MyTopbar.topbarClickListener() {
            public void LeftClick() {
                    //点击返回按钮，回到管理界面
                    Intent mIntent=new Intent(EditorStudentActivity.this,ManagerActivtiy.class);
                    startActivity(mIntent);//实现跳转
                    EditorStudentActivity.this.finish();//结束当前activity
            }
            public void RightClick() {
                //点击保存按钮
                if(mName.getText().toString().equals("")||
                        mGrade.getText().toString().equals("")||
                        mStudent_ID.getText().toString().equals(""))//
                {
                    Toast.makeText(EditorStudentActivity.this,//在该activity显示
                            "还没填完啦...",//显示的内容
                            Toast.LENGTH_SHORT).show();//显示的格式
                }
                else {
                    //情况1：新建学生信息
                    if(is_show_to_editor==false)
                    {
                        //先查询学号是否存在
                        if(quaryStudent(Integer.parseInt(mStudent_ID.getText().toString().trim()))!=null)//存在
                        {
                            //存在,提示该用户已经存在
                            Toast.makeText(
                                    EditorStudentActivity.this,//在该activity显示
                                    "该学生信息已经存在...",//显示的内容
                                    Toast.LENGTH_SHORT).show();//显示的格式
                        }
                        else {//不存在
                            Student student=new Student();
                            student.setName(mName.getText().toString());
                            student.setGrade(mGrade.getText().toString());
                            student.setStudent_id(Integer.parseInt(mStudent_ID.getText().toString()));
                            addStudent(student);//添加数据到数据库中
                            Toast.makeText(
                                    EditorStudentActivity.this,//在该activity显示
                                    "保存成功",//显示的内容
                                    Toast.LENGTH_SHORT).show();//显示的格式
                            /*****延时后跳转到管理界面*****/
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    Intent mIntent=new Intent(EditorStudentActivity.this,ManagerActivtiy.class);
                                    startActivity(mIntent);
                                    EditorStudentActivity.this.finish();//跳转后清除内存
                                }
                            },1000);//延时1秒
                        }
                    }
                    //情况2：更新学生信息
                    else
                    {
                        Student student=new Student();
                        student.setName(mName.getText().toString());
                        student.setGrade(mGrade.getText().toString());
                        student.setStudent_id(Integer.parseInt(mStudent_ID.getText().toString()));
                        updateStudent(student);//更新学生信息
                        Toast.makeText(
                                EditorStudentActivity.this,//在该activity显示
                                "保存成功",//显示的内容
                                Toast.LENGTH_SHORT).show();//显示的格式
                        /*****延时后跳转到界面*****/
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                Intent mIntent=new Intent(EditorStudentActivity.this,ManagerActivtiy.class);
                                startActivity(mIntent);
                                EditorStudentActivity.this.finish();//跳转后清除内存
                            }
                        },1000);//延时1秒
                    }
                }
            }
        });
        //让按钮隐藏
        //topbar_add.setLeftVisable(false);
        //topbar_add.setRightVisable(false);
    }
    /*1-初始化控件布局*/
    private void initView() {
        //标题
        topbar_add= (MyTopbar) findViewById(R.id.topbar_add);
        mName= (EditText) findViewById(R.id.id_et_name);
        mGrade= (EditText) findViewById(R.id.id_et_grade);
        mStudent_ID= (EditText) findViewById(R.id.id_et_student_id);
    }

}
