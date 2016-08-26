package com.example.achuan.studentmanagersystem.uitl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.achuan.studentmanagersystem.model.Student;
import com.example.achuan.studentmanagersystem.R;

import java.util.List;

/**
 * Created by achuan on 16-2-23.
 * 功能：自定义适配器
 */
/*第四步：自定义适配器类*/
public class MyAdapter extends BaseAdapter
{
    private List<Student> mList;//创建集合来保存传入的数据集
    private LayoutInflater mInflater;//创建布局装载对象来获取相关控件（类似于findViewById()）

    /*1.4创建构造方法*/
    public MyAdapter(Context context,List<Student> list) {
        mList=list;
        //通过获取context来初始化mInflater对象
        mInflater=LayoutInflater.from(context);
    }
    /**************下面四个方法是直接继承来的方法**********/
    /*2.0修改方法获得相关量*/
    //适配器中数据集中的个数
    public int getCount() {
        return mList.size();
    }
    //获取数据集中与指定索引对应的数据项
    public Object getItem(int position) {
        return mList.get(position);
    }
    //获取指定行对应的ID
    public long getItemId(int position) {
        return position;
    }
    //获取每一个Item的显示内容
    /***********2.1主要编写该方法实现布局控件的设置************/
    public View getView(final int position, View convertView, ViewGroup parent) {
        /*1.先加载布局控件*/
         /***3.文艺式（viewHolder方法）***time:23665297***/
        //long start=System.nanoTime();//开始的时间
        ViewHolder viewHolder;//先创建实例
        if(convertView==null)//如果缓冲池中没有东西,才创建新的view
        {
            viewHolder =new ViewHolder();
            //获得（父）布局，并且加载一个item时只会创建一次view
            convertView=mInflater.inflate(R.layout.student_item,null);
            //通过viewHolder的对象来获得（子）控件，并保存到viewHolder对象中
            viewHolder.name= (TextView) convertView.findViewById(R.id.id_tv_name);
            viewHolder.grade= (TextView) convertView.findViewById(R.id.id_tv_grade);
            viewHolder.student_id= (TextView) convertView.findViewById(R.id.id_tv_student_id);
            convertView.setTag(viewHolder);//将viewHolder对象与convertView进行关联
        }
        else {
            viewHolder= (ViewHolder) convertView.getTag();//获得关联的对象（包括缓冲的控件）
        }
        //再通过viewHolder中缓冲的控件添加先关数据
        Student student=mList.get(position);//从数据源集合中获得对象
        viewHolder.name.setText(student.getName());
        viewHolder.grade.setText(student.getGrade());
        viewHolder.student_id.setText(Integer.toString(student.getStudent_id()));
        /*//设置item长按事件
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                deleteData(position);
                return false;
            }
        });*/
        return convertView;
    }
    /*2.3编写内部类：ViewHolder类,避免重复的findViewById(),节约资源*/
    private static class ViewHolder
    {
        //对应item中的3个控件
        public TextView name;
        public TextView grade;
        public TextView student_id;
    }
    /********3.0item的添加和删除方法*******/
    public void deleteData(int pos)
    {
        mList.remove(pos);
        notifyDataSetChanged();
    }
}
