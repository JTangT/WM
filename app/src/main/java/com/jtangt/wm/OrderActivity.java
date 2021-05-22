package com.jtangt.wm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;

import com.alibaba.fastjson.JSON;
import com.jtangt.wm.bean.Message_Post;
import com.jtangt.wm.bean.User;
import com.jtangt.wm.utils.DBDefine;
import com.jtangt.wm.utils.HttpUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderActivity extends AppCompatActivity {
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dingdai);
        user=getuser_id();
        getListdate();
    }

    private void getListdate(){
        Message_Post message_post=new Message_Post();
        message_post.setType("get_allorder");
        Map<String,Object> re=new HashMap<>();
        re.put("user_id",user.getId());
        re.put("token",user.getToken());
        message_post.setMessage(JSON.toJSONString(re));
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
    private void setListdate(String json){
        List<String> data=JSON.parseArray(json,String.class);
        System.out.println(data.size());
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    setListdate((String) msg.obj);
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