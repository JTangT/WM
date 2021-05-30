package com.jtangt.wm.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.jtangt.wm.MainActivity;
import com.jtangt.wm.Order_comfireActivity;
import com.jtangt.wm.R;
import com.jtangt.wm.Shop_detailActivity;
import com.jtangt.wm.adapter.MySimpleAdapter;
import com.jtangt.wm.bean.Message_Post;
import com.jtangt.wm.bean.ShopBean;
import com.jtangt.wm.bean.User;
import com.jtangt.wm.loginandregiser.Login;
import com.jtangt.wm.utils.Base64ToPicture;
import com.jtangt.wm.utils.DBDefine;
import com.jtangt.wm.utils.HttpUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderFragment extends Fragment {
    private View view;
    private User user;
       public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.tab_dingdai, container, false);
        user=getuser();
        TextView order_te=view.findViewById(R.id.order_te);
        if (user==null){

            order_te.setVisibility(View.VISIBLE);

            return view;
        }
        order_te.setHeight(0);
        Message_Post message_post=new Message_Post();
        message_post.setType("get_allorder");
        Map<String,String> map=new HashMap<>();
        map.put("user_id",user.getId()+"");
        map.put("token",user.getToken());
        message_post.setMessage(JSON.toJSONString(map));
        getdata(message_post,1);

        return view;
    }

    private User getuser(){
        User user;
        DBDefine dbDefine=new DBDefine(getActivity());
        dbDefine.open();
        User[] users=dbDefine.queryAllData();
        dbDefine.close();
        if(users!=null){
            user=users[0];
            return user;
        }
        return null;
    }

    private static class Orderlist{
        public String picture;
        public String name;
        public Date stattime;
        public int stats;
        public String show_ex;
        public double money;
        public int numofg;
        public Orderlist(){
        }

    }
    private void setListview(String json){
        ArrayList arrayList=new ArrayList();
        Base64ToPicture base64ToPicture=new Base64ToPicture();
        List<Orderlist> orderlists=JSON.parseArray(json,Orderlist.class);
        //System.out.println(orderlists.size());
        for (Orderlist o :orderlists) {
            HashMap<String,Object> map=new HashMap<>();
            map.put("shop_icon",base64ToPicture.sendImage(o.picture));
            map.put("shop_name",o.name);
            String strDateFormat = "yyyy/MM/dd-HH:mm:ss";
            SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
            map.put("order_time",sdf.format(o.stattime));
            if(o.stats==0){
                map.put("order_stats","已付款");
            }else if (o.stats==1){
                map.put("order_stats","已完成");
            }else if (o.stats==-1){
                map.put("order_stats","已取消");
            }

            if(o.numofg>1){
                map.put("order_example",o.show_ex+" 等 "+o.numofg+" 件");
            }else{
                map.put("order_example",o.show_ex+" "+o.numofg+" 件");
            }

            map.put("order_money","￥"+o.money);

            arrayList.add(map);
        }

        String from[]={"shop_icon","shop_name","order_time","order_stats","order_example","order_money"};

        int to[]={R.id.shop_icon,R.id.shop_name,R.id.order_time,R.id.order_stats,R.id.order_example,R.id.order_money};


        MySimpleAdapter simpleAdapter=new MySimpleAdapter(getActivity(),arrayList,R.layout.activity_orderlist_yihang,from,to);
        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            public boolean setViewValue(View view, Object data,
                                        String textRepresentation) {
                if (view instanceof ImageView && data instanceof Bitmap) {
                    ImageView image = (ImageView) view;
                    image.setImageBitmap((Bitmap) data);
                    return true;
                }
                return false;
            }
        });

        ListView listView=view.findViewById(R.id.ordlist_lv);

        ((ProgressBar)view.findViewById(R.id.order_prowait)).setVisibility(View.GONE);

        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


    }





    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    //System.out.println(22222);
                    //System.out.println(msg.obj);
                    setListview(msg.obj.toString());
                    break;
            }

        }
    };
    public void getdata(Message_Post message_post, int what){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String json = null;
                try {
                    HttpUtils httpUtils = new HttpUtils();
                    json = httpUtils.getJsonContent(message_post);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Message msg = handler.obtainMessage();
                msg.obj = json;
                msg.what=what;
                handler.sendMessage(msg);
            }
        }).start();
    }
}