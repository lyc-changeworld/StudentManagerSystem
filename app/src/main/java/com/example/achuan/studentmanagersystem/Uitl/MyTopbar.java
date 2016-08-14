package com.example.achuan.studentmanagersystem.Uitl;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.achuan.studentmanagersystem.R;

/**
 * Created by achuan on 16-2-16.
 * 功能：自定义标题模板（接口回调机制）
 */
public class MyTopbar extends RelativeLayout
{
    /*中间title的属性*/
    private TextView MytvTitle;//标题文本框
    private String MytitleText;//标题的文本内容
    private float MytitleTextSize;//文本的大小
    private int MytitleTextColor;//文本的颜色
    /*左右的按钮的属性*/
    private Button leftButton,rightButton;
    private int leftTextColor;//左按钮中的文本颜色
    private Drawable leftBackground;//左按钮的背景
    private String leftText;//左按钮的文本
    private int rightTextColor;//右按钮中的文本颜色
    private Drawable rightBackground;//右按钮的背景
    private String rightText;//右按钮的文本
    private LayoutParams leftParams,rightParams,titleParams;
    //添加自定义事件监听
    private topbarClickListener listener;
    /*定义Topbar触碰事件接口*/
    public interface topbarClickListener{
        public void LeftClick();
        public void RightClick();
    }
    /*创建触碰事件，引入接口*/
    public void setOnTopbarClickListener(topbarClickListener listener)
    {
        this.listener=listener;
    }
    /*自定义属性时调用*/
    public MyTopbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /*获得自定义的属性的集合组*/
        TypedArray ta=context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.MyTopbar, defStyleAttr,0);
        /******************通过集合组获得相关的属性**********/
        //中间文本
        MytitleText=ta.getString(R.styleable.MyTopbar_MytitleText);
        MytitleTextSize=ta.getDimension(R.styleable.MyTopbar_MytitleTextSize, 0);
        MytitleTextColor=ta.getColor(R.styleable.MyTopbar_MytitleTextColor, 0);
        //左边按钮
        leftTextColor=ta.getColor(R.styleable.MyTopbar_MyleftTextColor, 0);
        leftBackground=ta.getDrawable(R.styleable.MyTopbar_MyleftBackgroud);
        leftText=ta.getString(R.styleable.MyTopbar_MyleftText);
        //右边按钮
        rightTextColor=ta.getColor(R.styleable.MyTopbar_MyrightTextColor, 0);
        rightBackground=ta.getDrawable(R.styleable.MyTopbar_MyrightBackgroud);
        rightText=ta.getString(R.styleable.MyTopbar_MyrightText);
        ta.recycle();//回收集合组，避免浪费资源
        /****************实例化控件**************/
        MytvTitle=new TextView(context);
        leftButton=new Button(context);
        rightButton=new Button(context);
        /**********************为控件设置属性******************/
        //中间文本
        //System.out.println("标题的内容为：" + MytvTitle.getText());
        MytvTitle.setTextSize(MytitleTextSize);
        MytvTitle.setTextColor(MytitleTextColor);
        MytvTitle.setText(MytitleText);
        MytvTitle.setGravity(Gravity.CENTER);//设置文本居中
        //左边按钮
        leftButton.setText(leftText);
        leftButton.setTextColor(leftTextColor);
        leftButton.setBackground(leftBackground);
        //右边按钮
        rightButton.setText(rightText);
        rightButton.setTextColor(rightTextColor);
        rightButton.setBackground(rightBackground);
        //setBackgroundColor(0xFFF59563);//为ViewGroup设置背景颜色
        /***************设置布局属性及添加控件**********/
        //中间文本部分
        titleParams=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);//高度为全扩开会更好看写
        titleParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);
        addView(MytvTitle, titleParams);
        //左按钮
        leftParams=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        addView(leftButton, leftParams);
        //右按钮
        rightParams=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
        addView(rightButton, rightParams);
        /*设置按钮监听事件*/
        leftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.LeftClick();
            }
        });
        rightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.RightClick();
            }
        });
    }
    /*未使用自定义属性时调用该方法*/
    public MyTopbar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    /*基本的文本创建时调用该方法*/
    public MyTopbar(Context context)
    {
        this(context,null);
    }
    /*自定义方法，让其实现不同的布局*/
    /*左按钮是否隐藏的方法*/
    public void setLeftVisable(boolean flag)
    {
        if(flag)
        {
            leftButton.setVisibility(VISIBLE);
        }
        else {
            leftButton.setVisibility(GONE);
        }
    }
    /*右按钮是否隐藏的方法*/
    public void setRightVisable(boolean flag)
    {
        if(flag)
        {
            rightButton.setVisibility(VISIBLE);
        }
        else {
            rightButton.setVisibility(GONE);
        }
    }
}