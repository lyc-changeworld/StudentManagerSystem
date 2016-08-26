package com.example.achuan.studentmanagersystem.uitl;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.example.achuan.studentmanagersystem.R;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by achuan on 16-2-13.
 */
public class SlidingMenu extends HorizontalScrollView
{
    private LinearLayout mWapper;//主布局对象，用来获取子元素
    private ViewGroup mMenu;//左侧的菜单组,为mWapper中的第一个元素
    private ViewGroup mCotent;//右侧的内容组，为mWapper中的第二个元素
    private CircleImageView mCircleImageView;
    private int mScreenWidth;//总屏幕的宽度
    private int mMenuWidth;//菜单的宽度
    private int mMenuRightPadding=50;//菜单界面与屏幕右侧的距离，dp
    private boolean once=false;//判断onMeasure方法的调用情况，让其只调用一次
    private boolean isOpen=false;//判断菜单是否显示
    /*******当使用了自定义属性时，会调用此构造方法*******/
    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取我们定义的属性
        TypedArray a=context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.SlidingMenu,defStyleAttr,0);
        int n=a.getIndexCount();//获得自定义属性的数量
        for(int i=0;i<n;i++)
        {
            int attr=a.getIndex(i);
            switch (attr)
            {
                case R.styleable.SlidingMenu_rightPadding:
                    mMenuRightPadding=a.getDimensionPixelSize(attr,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,50,
                            context.getResources().getDisplayMetrics()));
                break;
                default:
                    break;
            }
        }
        a.recycle();//释放空间
        /*获取屏幕的宽度的方法*/
        DisplayMetrics outMetrics= new DisplayMetrics();//创建测量对象，可以获取尺寸属性
        WindowManager wm= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(outMetrics);//将属性传入对象
        mScreenWidth=outMetrics.widthPixels;//获得屏幕宽度值
    }
    /********未使用自定义属性时，调用*******/
    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);//调用3个参数的方法
    }
    /*****普通文本传递时会调用此方法******/
    public SlidingMenu(Context context) {
        this(context,null);//调用两个参数的方法
    }
    /***********设置子View的宽和高，设置自己的宽和高*******/
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(!once)//如果未调用过，则调用一次
        {
            mWapper=(LinearLayout)getChildAt(0);//为总结面中的第一个元素
            mMenu= (ViewGroup) mWapper.getChildAt(0);//为mWapper中的第一个元素
            mCotent= (ViewGroup) mWapper.getChildAt(1);//为mWapper中的第二个元素
            mCircleImageView= (CircleImageView) mCotent.getChildAt(1);//拿到切换图片,第二个元素
            //设置菜单的宽度，高度默认为全高，无需设置
            mMenuWidth=mMenu.getLayoutParams().width=mScreenWidth-mMenuRightPadding;
            //设置内容的宽度
            mCotent.getLayoutParams().width=mScreenWidth;
            once=true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    /****************通过设置偏移量，将Menu隐藏****************/
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(changed)
        {
            this.scrollTo(mMenuWidth,0);//初始化设置主界面向左滚动，使Menu隐藏
        }
    }
    /**************屏幕触碰事件监听***************/
    public boolean onTouchEvent(MotionEvent ev) {
        int action=ev.getAction();
        switch (action)
        {
            //监听手指离开屏幕事件
            case MotionEvent.ACTION_UP:
                /*隐藏在左边的宽度*/
                int scrollx=getScrollX();
                /*判断：隐藏在左边的宽度超过菜单宽度的一半时，不让菜单显示*/
                if(scrollx>=mMenuWidth/2)
                {
                   this.smoothScrollTo(mMenuWidth,0);//界面依旧隐藏菜单
                    isOpen=false;
                }
                else //让菜单显示
                {
                    this.smoothScrollTo(0,0);
                    isOpen=true;
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }
    /*打开菜单方法*/
    public void openMenu()
    {
        if(isOpen)return;
        this.smoothScrollTo(0,0);
        isOpen=true;
    }
    /*关闭菜单方法*/
    public void closeMenu()
    {
        if(!isOpen) return;
        smoothScrollTo(mMenuWidth,0);
        isOpen=false;
    }
    /*切换菜单*/
    public void toggle()
    {
        if(isOpen)
        {
            closeMenu();
        }
        else {
            openMenu();
        }
    }
    /*滑动发生时调用,使菜单部分发生额外的偏移*/
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        //l的值就等同于getScrollX()的值
        super.onScrollChanged(l, t, oldl, oldt);
        //添加梯度
        float scale=l*1.0f/mMenuWidth;//1~0
        /*1、内容区域1.0~0.7缩放的效果
           scale:1.0~0.0              0.7+0.3*scale
          2、菜单的偏移量需要修改
           0.7f*scale*mMenuWidth
          3、菜单的显示时有缩放，以及透明度变化
           缩放：0.7~1.0           1.0-scale*0.3
           透明度：0.6~1.0        1.0-scale*0.4*/
        float rightScale=0.7f+0.3f*scale;//内容框缩放比例
        float leftScale=1.0f-scale*0.3f;//菜单缩放比例
        float leftAlpha=1.0f-scale*0.4f;//透明度比例
        /*调用菜单动画，设置TraslationX*/
        ViewHelper.setTranslationX(mMenu, 0.7f*scale*mMenuWidth);
        //ViewHelper.setScaleX(mMenu, leftScale);
        //ViewHelper.setScaleY(mMenu, leftScale);
        ViewHelper.setAlpha(mMenu,leftAlpha);
        ViewHelper.setAlpha(mCircleImageView,scale);//为切换图片添加切换效果
        /*调用内容动画*/
        //设置缩放中心点为内容区域左侧的中点
        ViewHelper.setPivotX(mCotent,0);
        ViewHelper.setPivotY(mCotent,mCotent.getHeight()/2);
        //ViewHelper.setScaleX(mCotent,rightScale);
        //ViewHelper.setScaleY(mCotent,rightScale);
    }
}
