package com.jtangt.wm.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;

import com.jtangt.wm.R;

import java.util.List;
import java.util.Map;

public class MySimpleAdapter extends SimpleAdapter {
    private List<? extends Map<String, ?>> mydata;

    public MySimpleAdapter(Context context,
                           List<? extends Map<String, ?>> data, int resource,
                           String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.mydata=data;
        // TODO Auto-generated constructor stub
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        convertView = super.getView(position, convertView, parent);
        //System.out.println(mydata.get(position).get("order_stats").toString()+" "+position);
        Button again = (Button) convertView
                .findViewById(R.id.again);
        Button comment = (Button) convertView
                .findViewById(R.id.comment);
        if(!mydata.get(position).get("order_stats").toString().equals("已完成")){

            again.setVisibility(View.GONE);


            comment.setVisibility(View.GONE);
        }else{
            again.setVisibility(View.VISIBLE);


            comment.setVisibility(View.VISIBLE);
        }



        return convertView;
    }


}