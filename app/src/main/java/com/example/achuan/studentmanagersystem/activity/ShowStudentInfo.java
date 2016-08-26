package com.example.achuan.studentmanagersystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.example.achuan.studentmanagersystem.DataClass.Student;
import com.example.achuan.studentmanagersystem.R;
import com.example.achuan.studentmanagersystem.Uitl.MyTopbar;


/**
 * Created by achuan on 16-5-11.
 * 功能：显示学生的详细信息
 */
public class ShowStudentInfo extends ManagerActivtiy
{
    //显示信息
    private TextView mtvName,mtvGrade,mtvStudent_ID;
    //标题框
    private MyTopbar topbar_show;
    //上个界面返回的id号
    int student_id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_student_info);
        initView();
        topbarSetClickListener();
        //获得从上个Activity传递过来的参数
        Intent intent=getIntent();
        student_id=intent.getIntExtra("student_id",10);//转换成10进制的数
        if(String.valueOf(student_id)!=null)//如果有数据传递过来,将其显示在相应的部位
        {
            Student student=quaryStudent(student_id);
            //将详细信息显示在界面框内
            mtvName.setText(student.getName().toString());
            mtvGrade.setText(student.getGrade().toString());
            mtvStudent_ID.setText(String.valueOf(student.getStudent_id()));
        }
    }
    /*2-标题的点击事件*/
    private void topbarSetClickListener() {
        /***********通过引用不同的实例，可以为其添加不同监听的方法（满足模板的需求）**********/
        /*为自定义的接口中的方法名添加具体的方法*/
        topbar_show.setOnTopbarClickListener(new MyTopbar.topbarClickListener() {
            public void LeftClick() {
                /*开始跳转到主管理界面*/
                Intent mIntent=new Intent(ShowStudentInfo.this,ManagerActivtiy.class);
                startActivity(mIntent);//实现跳转
                ShowStudentInfo.this.finish();//结束当前activity
            }
            public void RightClick() {
                /*开始跳转到编辑界面*/
                Student student=quaryStudent(student_id);//包装好当前的信息
                String name=student.getName();
                String grade=student.getGrade();
                int student_id=student.getStudent_id();
                //创建一个Intent
                Intent intent = new Intent();
                intent.setClass(ShowStudentInfo.this,EditorStudentActivity.class);
                //传递数据
                intent.putExtra("name",name);
                intent.putExtra("grade",grade);
                intent.putExtra("student_id",student_id);
                startActivity(intent);//实现跳转
                ShowStudentInfo.this.finish();//结束当前activity
            }
        });
        //让按钮隐藏
        //topbar_show.setLeftVisable(false);
        //topbar1.setRightVisable(false);
    }
    /*1-初始化控件布局*/
    private void initView() {
        //标题框
        topbar_show= (MyTopbar) findViewById(R.id.topbar_show);
        //显示文本
        mtvName= (TextView) findViewById(R.id.id_et_name);
        mtvGrade= (TextView) findViewById(R.id.id_et_grade);
        mtvStudent_ID= (TextView) findViewById(R.id.id_et_student_id);
    }
}
