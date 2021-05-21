package com.jtangt.wm.adapter;

import android.content.Context;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jtangt.wm.po.LeftBean;
import com.jtangt.wm.po.RightBean;
import com.jtangt.wm.R;
import com.jtangt.wm.utils.base64ToPicture;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class RightAdapter extends BaseAdapter implements StickyListHeadersAdapter {
    private List<LeftBean> mLeft;
    private List<RightBean> mRight;
    private Context context;
    private View.OnClickListener clickListener;
    private List<Gwc> gwcs;

    class Gwc{
        int id;
        int num;
    }
    public void addgwc(int id,int num){
        boolean f=true;
        for(Gwc gwc:gwcs){
            if(gwc.id==id){
                gwcs.remove(gwc);
                gwc.num=num;
                gwcs.add(gwc);
                f=false;
                break;
            }
        }
        if(f){
            Gwc gwc=new Gwc();
            gwc.num=num;
            gwc.id=id;
            gwcs.add(gwc);
        }
    }

    public RightAdapter(List<LeftBean> mLeft, List<RightBean> mRight, View.OnClickListener clickListener, Context context) {
        this.mLeft = mLeft;
        this.mRight = mRight;
        this.context = context;
        this.clickListener=clickListener;
        this.gwcs=new ArrayList<>();
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        TextView tv = new TextView(context);
        tv.setTextColor(Color.BLACK);
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
            viewHolder.dec_detail=(ImageView)convertView.findViewById(R.id.dec_detail);
            viewHolder.add_detail=(ImageView)convertView.findViewById(R.id.add_detail);
            viewHolder.num_detail=(TextView)convertView.findViewById(R.id.num_detail);
            viewHolder.price=(TextView)convertView.findViewById(R.id.tv_right_price);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        RightBean rightBean = mRight.get(position);
        viewHolder.title1.setText(rightBean.biaoti);
        viewHolder.title2.setText(rightBean.detail);
        viewHolder.image.setImageBitmap(new base64ToPicture().sendImage(rightBean.picture));
        viewHolder.count.setText("月销量"+rightBean.num+"份");

        viewHolder.dec_detail.setOnClickListener(clickListener);
        viewHolder.dec_detail.setTag(position);
        viewHolder.add_detail.setOnClickListener(clickListener);
        viewHolder.add_detail.setTag(position);
        viewHolder.num_detail.setText("0");
        for(Gwc gwc:gwcs){
            if(gwc.id==position){
                viewHolder.num_detail.setText(gwc.num+"");
            }
        }

        viewHolder.price.setText("￥ "+rightBean.price);

        return convertView;
    }




    static class ViewHolder{
        TextView title1;
        TextView title2;
        TextView count;
        ImageView image;
        ImageView dec_detail;
        ImageView add_detail;
        TextView num_detail;
        TextView price;
    }
}