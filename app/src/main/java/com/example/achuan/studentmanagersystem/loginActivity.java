package com.example.achuan.studentmanagersystem;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.achuan.studentmanagersystem.DataClass.Teacher;
import com.example.achuan.studentmanagersystem.MySQLite.MyDBManager;
import com.example.achuan.studentmanagersystem.MySQLite.MyDatabaseHelper;
import com.example.achuan.studentmanagersystem.Uitl.MyTopbar;

public class loginActivity extends AppCompatActivity {
    private MyTopbar topbar;
    //数据库相关,给子类继承,可重复利用
    private MyDatabaseHelper mDatabaseHelper;
    private MyDBManager mDBManager;
    //登录相关
    private Button mLoginBtn;
    private EditText mAccName;
    private EditText mAccPassword;
    //注册用户相关
    private TextView mNewUser;
    private TextView mForgetPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_system);
        createDB();
        initView();
        clickListener();
    }
    /*5-标题的点击事件*/
    private void topbarSetClickListener() {
        /***********通过引用不同的实例，可以为其添加不同监听的方法（满足模板的需求）**********/
        /*为自定义的接口中的方法名添加具体的方法*/
        topbar.setOnTopbarClickListener(new MyTopbar.topbarClickListener() {
            public void LeftClick() {
                //Toast.makeText(registerActivity.this, "点击左按钮", Toast.LENGTH_SHORT).show();
            }
            public void RightClick() {
                //Toast.makeText(registerActivity.this,"点击右按钮",Toast.LENGTH_SHORT).show();
            }
        });
        //让按钮隐藏
        topbar.setLeftVisable(false);
        topbar.setRightVisable(false);
       /* //引入第二个实例，实现不同的监听方法
        MyTopbar topbar1= (MyTopbar) findViewById(R.id.topbar);
        topbar1.setOnTopbarClickListener(new MyTopbar.topbarClickListener() {
            public void LeftClick() {
                Toast.makeText(MainActivity.this, "点击左按钮1", Toast.LENGTH_SHORT).show();
            }
            public void RightClick() {
                Toast.makeText(MainActivity.this,"点击右按钮1",Toast.LENGTH_SHORT).show();
            }
        });*/
    }
    /*4-点击事件监听*/
    private void clickListener() {
        //标题的相关点击事件
        topbarSetClickListener();
        //为登陆界面添加点击事件
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginDeal();
            }
        });
        /*新用户点击事件*/
        mNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent=new Intent(loginActivity.this,registerActivity.class);
                startActivity(mIntent);//实现跳转
                loginActivity.this.finish();//结束当前activity
            }
        });
        /*忘记密码点击事件*/
        mForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(loginActivity.this,//在该activity显示
                        "再好好想想...",//显示的内容
                        Toast.LENGTH_SHORT).show();//显示的格式
            }
        });
    }
    /*3-登录处理方法*/
    private void LoginDeal() {
        if(mAccName.getText().toString().equals("")|| mAccPassword.
                getText().toString().equals(""))//有任何一个框为空时，显示提示消息
        {
            Toast.makeText(loginActivity.this,//在该activity显示
                    "请输入账号或密码...",//显示的内容
                    Toast.LENGTH_SHORT).show();//显示的格式
        }
        else {
            if(quaryTeacher(Integer.parseInt(mAccName.getText().toString().trim()))!=null)//如果查询到数据库中有该用户
            {
                //接着核对密码是否正确
                if(mAccPassword.getText().toString().trim().equals(//输入的密码
                        quaryTeacher(Integer.parseInt(mAccName.getText().toString().trim())).getPassword()))//数据库的密码
                {
                    Toast.makeText(
                            loginActivity.this,//在该activity显示
                            "登录成功,开始跳转...",//显示的内容
                            Toast.LENGTH_SHORT).show();//显示的格式
                    /*****延时后跳转到登录界面*****/
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            Intent mIntent=new Intent(loginActivity.this,ManagerActivtiy.class);
                            startActivity(mIntent);//实现跳转
                            loginActivity.this.finish();//结束当前activity
                        }
                    },1000);//延时1秒
                }
                else {
                    Toast.makeText(
                            loginActivity.this,//在该activity显示
                            "密码与账号不匹配...",//显示的内容
                            Toast.LENGTH_SHORT).show();//显示的格式
                }
            }
            else//该用户不存在
            {
                Toast.makeText(loginActivity.this,//在该activity显示
                        "该用户不存在,请注册！",//显示的内容
                        Toast.LENGTH_SHORT).show();//显示的格式
            }
        }
    }
    /*2-组件初始化*/
    private void initView() {
        //创建一个数据库管理对象
        mDBManager=new MyDBManager(this);
        //标题框
        topbar= (MyTopbar) findViewById(R.id.topbar);//获得一个MyTopbar的实例引用
        //登录相关
        mLoginBtn= (Button) findViewById(R.id.id_bt_login);
        mAccName= (EditText) findViewById(R.id.id_et_AccName);
        mAccPassword= (EditText) findViewById(R.id.id_et_AccPassword);
        mNewUser= (TextView) findViewById(R.id.id_tv_newUser);
        mForgetPassword= (TextView) findViewById(R.id.id_tv_forgetPassword);
    }
    /*1-创建数据库*/
    private void createDB() {
        /*新建一个数据库操作对象*/
        mDatabaseHelper=new MyDatabaseHelper(this);
        mDatabaseHelper.getWritableDatabase();//创建一个数据库
    }
    //查询用户是否存在,子类可以使用该方法
    protected Teacher quaryTeacher(int id)
    {
        //Teacher teacher=new Teacher(Integer.valueOf(mAccName.getText().toString()));
        Teacher teacher=mDBManager.queryTeacher(id);
        //用户存在返回真
        return  teacher;
    }
    //注册添加一个教工用户
    protected void addTeacher(int id,String password)
    {
        Teacher teacher=new Teacher(id,password);
        mDBManager.addTeacher(teacher);
    }
}
