package com.jtangt.wm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jtangt.wm.bean.Gwc;
import com.jtangt.wm.bean.Message_Post;
import com.jtangt.wm.bean.ShopBean;
import com.jtangt.wm.utils.HttpUtils;
import com.jtangt.wm.utils.Base64ToPicture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Order_comfireActivity extends AppCompatActivity {
    private  String id;
    private List<Gwc> gwcs;
    private ShopBean shopBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dingdan_quereng);
        Intent intent =getIntent();
        id=intent.getStringExtra("shop_id");
        gwcs= JSON.parseArray(intent.getStringExtra("gwcs"),Gwc.class);
        getData();
        set_listviewandAll();
        TextView confirm=(TextView)findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Order_comfireActivity.this,SettlementActivity.class);
                intent.putExtra("shop_id",id);
                intent.putExtra("gwcs",JSON.toJSONString(gwcs));
                startActivity(intent);
                finish();
            }
        });

    }
    private void set_listviewandAll(){
        ArrayList arrayList=new ArrayList();
        double sum=0;
        for (int i= 0;i<gwcs.size();i++) {
            HashMap<String,Object> map=new HashMap<>();
            map.put("good_name",gwcs.get(i).name);
            map.put("good_num","X"+gwcs.get(i).num);
            map.put("good_money","￥"+(gwcs.get(i).price*gwcs.get(i).num));
            //System.out.println(gwcs.get(i).toString());
            sum+=gwcs.get(i).price*gwcs.get(i).num;
            arrayList.add(map);
        }

        String from[]={"good_name","good_num","good_money"};
        int to[]={R.id.good_name,R.id.good_num,R.id.good_money};

        SimpleAdapter simpleAdapter=new SimpleAdapter(this,arrayList,R.layout.gwc_yihang,from,to);
        ListView lv_order_food = findViewById(R.id.lv_order_food);
        lv_order_food.setAdapter(simpleAdapter);
        TextView price=findViewById(R.id.price);
        price.setText("￥"+sum);
        ProgressBar progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);


        ScrollView sv = (ScrollView) findViewById(R.id.qr_scrollview);
        sv.smoothScrollTo(0, 0);


    }
    private void setshopinof(){
        ImageView siv_shop_icon=findViewById(R.id.siv_shop_icon);
        TextView tv_shop_name=findViewById(R.id.tv_shop_name);
        Base64ToPicture base64ToPicture=new Base64ToPicture();
        siv_shop_icon.setImageBitmap(base64ToPicture.sendImage(shopBean.getShopIconbase64()));
        tv_shop_name.setText(shopBean.getShopName());
    }
    Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    shopBean=JSON.parseObject((String) msg.obj,ShopBean.class);
                    setshopinof();
                    break;

            }

        }
    };

    private void getData(){
        Message_Post message_post=new Message_Post();
        message_post.setType("Shop_getbyid");
        message_post.setMessage("{\"shop_id\":"+id+"}");
        postData(message_post,1);
    }
    private void postData(Message_Post message_post,int what){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String json = null;
                try {
                    HttpUtils httpUtils = new HttpUtils();
                    json = httpUtils.getJsonContent(message_post);
                    //System.out.println(json);
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