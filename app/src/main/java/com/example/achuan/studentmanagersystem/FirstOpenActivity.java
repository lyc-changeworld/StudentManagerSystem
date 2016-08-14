package com.example.achuan.studentmanagersystem;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by achuan on 16-5-7.
 */
public class FirstOpenActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_open);
        /*****延时后跳转到登陆界面*****/
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent mIntent=new Intent(FirstOpenActivity.this,loginActivity.class);
                startActivity(mIntent);
                FirstOpenActivity.this.finish();//跳转后清除内存
            }
        },3000);//3秒

    }
}
