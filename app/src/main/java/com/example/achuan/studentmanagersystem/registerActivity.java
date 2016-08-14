package com.example.achuan.studentmanagersystem;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.achuan.studentmanagersystem.Uitl.MyTopbar;

/**
 * Created by achuan on 16-5-6.
 */
public class registerActivity extends loginActivity
{
    private MyTopbar topbar;
    private CheckBox agreePass;
    //注册相关
    private EditText mRegisterName;
    private EditText mFirstPassword;
    private EditText mConfirmPassword;
    private Button mRegisterBtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_system);
        initView();
        topbarSetClickListener();
        RegisterclickListener();
        //复选框的点击监听事件
        agreePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(agreePass.isChecked())//如果选中,注册按钮变亮
                {
                    mRegisterBtn.setBackgroundResource(R.drawable.login_bt_bg);
                }else
                {
                    mRegisterBtn.setBackgroundResource(R.drawable.login_bt_bg_white);
                }
            }
        });
    }
    /*4-注册按钮点击事件监听*/
    private void RegisterclickListener() {
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterDeal();
            }
        });
    }
    /*3-注册处理方法*/
    private void RegisterDeal()
    {
        if(agreePass.isChecked())//同意协议后才可触发注册功能
        {
            if(mRegisterName.getText().toString().equals("")||
                    mFirstPassword.getText().toString().equals("")||
                    mConfirmPassword.getText().toString().equals(""))//有任何一个框为空时，显示提示消息
            {
                Toast.makeText(registerActivity.this,//在该activity显示
                        "请输入账号或密码...",//显示的内容
                        Toast.LENGTH_SHORT).show();//显示的格式
            }
            else {
                //先查询用户是否已经存在
                if(quaryTeacher(Integer.parseInt(mRegisterName.getText().toString().trim()))!=null)
                {
                    //存在,提示该用户已经存在
                    Toast.makeText(
                            registerActivity.this,//在该activity显示
                            "该用户已经注册...",//显示的内容
                            Toast.LENGTH_SHORT).show();//显示的格式
                }
                else//不存在
                {//接着判断两次输入的密码是否相同
                    if(mConfirmPassword.getText().toString().trim().equals(
                            mFirstPassword.getText().toString().trim()))
                    {
                        //开始执行添加数据操作
                        addTeacher(
                                Integer.parseInt(mRegisterName.getText().toString().trim()),//id
                                mConfirmPassword.getText().toString().trim());//password
                        Toast.makeText(
                                registerActivity.this,//在该activity显示
                                "注册成功,开始跳转...",//显示的内容
                                Toast.LENGTH_SHORT).show();//显示的格式
                        /*****延时后跳转到登录界面*****/
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                Intent mIntent=new Intent(registerActivity.this,loginActivity.class);
                                startActivity(mIntent);
                                registerActivity.this.finish();//跳转后清除内存
                            }
                        },1000);//延时1秒
                    }
                    else //不相同
                    {
                        Toast.makeText(
                                registerActivity.this,//在该activity显示
                                "两次密码不同,请重新输入！",//显示的内容
                                Toast.LENGTH_SHORT).show();//显示的格式
                    }
                }
            }
        }
        else {
            /*Toast.makeText(
                    registerActivity.this,//在该activity显示
                    "请先同意协议...",//显示的内容
                    Toast.LENGTH_SHORT).show();//显示的格式*/
        }
    }
    /*2-标题的点击事件*/
    private void topbarSetClickListener() {
        /***********通过引用不同的实例，可以为其添加不同监听的方法（满足模板的需求）**********/
        /*为自定义的接口中的方法名添加具体的方法*/
        topbar.setOnTopbarClickListener(new MyTopbar.topbarClickListener() {
            public void LeftClick() {
                Intent mIntent=new Intent(registerActivity.this,loginActivity.class);
                startActivity(mIntent);//实现跳转
                registerActivity.this.finish();//结束当前activity
                //Toast.makeText(registerActivity.this, "点击左按钮", Toast.LENGTH_SHORT).show();
            }
            public void RightClick() {
                Toast.makeText(registerActivity.this,"点击右按钮",Toast.LENGTH_SHORT).show();
            }
        });
        //让按钮隐藏
        //topbar.setLeftVisable(false);
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
    /*1-布局初始化*/
    private void initView()
    {
        agreePass= (CheckBox) findViewById(R.id.id_cb_agree);
        //标题框
        topbar= (MyTopbar) findViewById(R.id.topbar);//获得一个MyTopbar的实例引用
        //注册相关
        mRegisterName= (EditText) findViewById(R.id.id_et_registerName);
        mFirstPassword= (EditText) findViewById(R.id.id_et_firstPassword);
        mConfirmPassword= (EditText) findViewById(R.id.id_et_confirmPassword);
        mRegisterBtn= (Button) findViewById(R.id.id_bt_register);
    }

}
