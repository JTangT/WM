package com.jtangt.wm.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jtangt.wm.po.LeftBean;
import com.jtangt.wm.po.RightBean;
import com.jtangt.wm.R;

import java.util.List;
import java.util.Random;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by Administrator on 2017.05.27.0027.
 */

public class RightAdapter extends BaseAdapter implements StickyListHeadersAdapter {
    private List<LeftBean> mLeft;
    private List<RightBean> mRight;
    private Context context;

    public RightAdapter(List<LeftBean> mLeft, List<RightBean> mRight, Context context) {
        this.mLeft = mLeft;
        this.mRight = mRight;
        this.context = context;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        TextView tv = new TextView(context);
        tv.setTextColor(Color.RED);
        tv.setTextSize(18);
        tv.setText(mRight.get(position).type);
        return tv;
    }

    @Override
    public long getHeaderId(int position) {
        return mRight.get(position).typeId;
    }

    @Override
    public int getCount() {
        return mRight.size();
    }

    @Override
    public RightBean getItem(int position) {
        return mRight.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView==null){
            convertView = View.inflate(context, R.layout.shop_detail_right,null);
            viewHolder = new ViewHolder();
            viewHolder.title1 = (TextView) convertView.findViewById(R.id.tv_right_title1);
            viewHolder.title2 = (TextView) convertView.findViewById(R.id.tv_right_title2);
            viewHolder.count = (TextView) convertView.findViewById(R.id.tv_right_count);
            viewHolder.image=(ImageView)convertView.findViewById(R.id.iv);


            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        RightBean rightBean = mRight.get(position);
        viewHolder.title1.setText(rightBean.biaoti);
        viewHolder.title2.setText(rightBean.biaoti);
        viewHolder.image.setImageBitmap();//TODO//设置图片，bean中添加String，在这里进行转换
        //使用Random获取随机数
        Random random = new Random();
        int i = random.nextInt(100);
        viewHolder.count.setText("月销量"+i+"份");
        return convertView;
    }

    static class ViewHolder{
        TextView title1;
        TextView title2;
        TextView count;
        ImageView image;
    }
}