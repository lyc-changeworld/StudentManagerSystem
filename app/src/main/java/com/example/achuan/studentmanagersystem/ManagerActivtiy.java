package com.example.achuan.studentmanagersystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.achuan.studentmanagersystem.DataClass.Student;
import com.example.achuan.studentmanagersystem.MySQLite.MyDBManager;
import com.example.achuan.studentmanagersystem.Uitl.CircleImageView;
import com.example.achuan.studentmanagersystem.Uitl.MyAdapter;
import com.example.achuan.studentmanagersystem.Uitl.MyTopbar;
import com.example.achuan.studentmanagersystem.Uitl.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by achuan on 16-5-5.
 */
public class ManagerActivtiy extends AppCompatActivity
{
    //数据库操作对象
    private MyDBManager mDBManager;
    //标题对象
    private MyTopbar topbar1;
    private CircleImageView mCircleImageView;
    //列表对象
    private ListView mListView;
    private MyAdapter mAdapter;
    private List<Student> mStudents=new ArrayList<Student>();
    //总的滑动界面
    private SlidingMenu mLeftMenu;
    //新建数据按钮
    private ImageView mImageView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slidingmenu);
        initView();
        topbarSetClickListener();
        /*3-切换菜单的方法*/
        mCircleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLeftMenu.toggle();
            }
        });
        /*4-为新建图片添加事件监听*/
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*开始跳转到编辑界面*/
                Intent intent = new Intent();
                intent.setClass(ManagerActivtiy.this,EditorStudentActivity.class);
                startActivity(intent);//实现跳转
                ManagerActivtiy.this.finish();//结束当前activity
            }
        });
        /*5-为ListView中的item设置点击监听*/
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                Student student=mStudents.get(position);
                int student_id=student.getStudent_id();
                intent.putExtra("student_id", student_id);
                intent.setClass(ManagerActivtiy.this,ShowStudentInfo.class);
                startActivity(intent);//实现跳转
                ManagerActivtiy.this.finish();//结束当前activity
            }
        });
        /*6-为为ListView中的item设置长按监听*/
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                /*创建一个对话框*/
                new AlertDialog.Builder(ManagerActivtiy.this).setTitle("系统提示")//设置对话框标题
                        .setMessage("请确认是否删除该条信息")//设置显示的内容
                        .setPositiveButton("确定",//添加确认按钮
                                new DialogInterface.OnClickListener()
                        {
                            //添加确定按钮
                            public void onClick(DialogInterface dialog, int which)
                            {
                                //确定按钮的响应事件
                                //finish();
                                Student student=mStudents.get(position);
                                int student_id=student.getStudent_id();
                                deleteStudent(student_id);//从数据库中删除学生信息
                                setAdapter();//更新列表信息
                            }
                        }
                        ).setNegativeButton("取消",//添加取消按钮
                                new DialogInterface.OnClickListener()
                        {
                          public void onClick(DialogInterface dialog, int which)
                          {
                              //响应事件
                              //Log.i("alertdialog"," 请保存数据！");
                          }
                        }
                        ).show();//在按键响应事件中显示此对话框
                return true;
            }
        });
    }
    /*4-为listview添加数据显示适配器*/
    //执行添加、删除、更新方法后将重新调用该方法
    private void setAdapter()
    {
        mStudents.clear();//清空
        if(queryAllStudent()!=null)//数据库中含有数据
        {
            mStudents=queryAllStudent();
        }
        else {//数据库中没有任何数据
            Toast.makeText(ManagerActivtiy.this,//在该activity显示
                    "此处空空如也...",//显示的内容
                    Toast.LENGTH_SHORT).show();//显示的格式
        }
        mAdapter=new MyAdapter(this,mStudents);//实例化适配器
        mListView.setAdapter(mAdapter);
    }
    /*2-标题的点击事件*/
    private void topbarSetClickListener() {
        /***********通过引用不同的实例，可以为其添加不同监听的方法（满足模板的需求）**********/
        /*为自定义的接口中的方法名添加具体的方法*/
        topbar1.setOnTopbarClickListener(new MyTopbar.topbarClickListener() {
            public void LeftClick() {
                //Toast.makeText(registerActivity.this, "点击左按钮", Toast.LENGTH_SHORT).show();
            }
            public void RightClick() {
                //Toast.makeText(registerActivity.this,"点击右按钮",Toast.LENGTH_SHORT).show();
            }
        });
        //让按钮隐藏
        topbar1.setLeftVisable(false);
        //topbar1.setRightVisable(false);
    }
    /*1-初始化布局控件*/
    private void initView() {
        mDBManager=new MyDBManager(this);
        //标题框
        mCircleImageView= (CircleImageView) findViewById(R.id.id_iv_my_header);
        topbar1= (MyTopbar) findViewById(R.id.topbar_manager);//获得一个MyTopbar的实例引用
        mLeftMenu= (SlidingMenu) findViewById(R.id.id_menu);//加载资源
        mListView= (ListView) findViewById(R.id.id_lv_student);
        mImageView= (ImageView) findViewById(R.id.id_iv_add_info);
        setAdapter();//初始化显示列表
    }
    /*1查询所有的学生的信息*/
    protected List<Student> queryAllStudent()
    {
        if(mDBManager.queryAllStudent()==null)//数据库中无任何数据时
        {
            return null;
        }
        else
        {
            return mDBManager.queryAllStudent();
        }
    }
    /*2添加学生的信息到数据库*/
    protected void addStudent(Student student)
    {
        mDBManager.addStudent(student);//添加数据到数据库中
    }
    //3查询学生是否存在,子类可以使用该方法
    protected Student quaryStudent(int id)
    {
        //Teacher teacher=new Teacher(Integer.valueOf(mAccName.getText().toString()));
        Student student=mDBManager.queryStudent(id);
        //用户存在返回真
        return  student;
    }
    /*4-删除学生的信息*/
    protected void deleteStudent(int id)
    {
        mDBManager.deleteStudent(id);
    }
    /*5-更新学生的信息*/
    protected void updateStudent(Student student)
    {
        mDBManager.updateStudent(student);
    }

}
