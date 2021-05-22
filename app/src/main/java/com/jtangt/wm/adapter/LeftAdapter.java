package com.jtangt.wm.adapter;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.jtangt.wm.R;
import com.jtangt.wm.bean.LeftBean;

import java.util.List;

public class LeftAdapter extends BaseAdapter {

    private List<LeftBean> mList;
    private int currentLeftItem = 0;
//创建一个构造方法

    public LeftAdapter(List<LeftBean> mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public LeftBean getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView==null){
            convertView = View.inflate(parent.getContext(), R.layout.shop_detail_left,null);

            //创建viewHolder对象
            viewHolder = new ViewHolder();
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            //让viewholder挂在convertview上面一起复用
            convertView.setTag(viewHolder);
        }else {
            //当convertView不为空时,吧viewholder取出来
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //获取对应条目的内容
        LeftBean leftBean = getItem(position);
        //把对应条目的内容设置在控件上
        viewHolder.tv_title.setText(leftBean.title);


        //给左侧条目设置颜色
        if (currentLeftItem ==position){
            viewHolder.tv_title.setTextColor(Color.RED);
        }else{
            viewHolder.tv_title.setTextColor(Color.GRAY);
        }
        //System.out.println(currentLeftItem+"---"+position);
        return convertView;
    }

    public void setCurrentSelect(int currentLeftItem){
        this.currentLeftItem = currentLeftItem;
    }

    //创建一个viewholder,用来复用对象
    class ViewHolder{
        TextView tv_title;
    }
}