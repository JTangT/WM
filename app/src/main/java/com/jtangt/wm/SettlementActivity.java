package com.jtangt.wm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.jtangt.wm.bean.Gwc;
import com.jtangt.wm.bean.Message_Post;
import com.jtangt.wm.bean.User;
import com.jtangt.wm.utils.DBDefine;
import com.jtangt.wm.utils.HttpUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettlementActivity extends AppCompatActivity {
    private String shop_id;
    private List<Gwc> gwcs;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.settlement);

        Intent intent =getIntent();
        shop_id=intent.getStringExtra("shop_id");
        gwcs= JSON.parseArray(intent.getStringExtra("gwcs"), Gwc.class);
        user=getuser_id();


        Button finish_ok=findViewById(R.id.finish);
        finish_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finshorder();
                finish();
            }
        });
    }
    private void finshorder(){
        Map<String,Object> map=new HashMap<>();
        map.put("user_id", user.getId());
        map.put("shop_id",shop_id);
        map.put("goods",JSON.toJSONString(gwcs));
        Message_Post message_post=new Message_Post();
        message_post.setType("save_order");
        message_post.setMessage(JSON.toJSONString(map));
        getdata(message_post,1);
    }

    private User getuser_id(){
        User user;
        DBDefine dbDefine=new DBDefine(this);
        dbDefine.open();
        User[] users=dbDefine.queryAllData();
        dbDefine.close();
        if(users!=null){
            user=users[0];
            return user;
        }
        return null;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
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