package com.jtangt.wm.mine;

import com.alibaba.fastjson.JSON;
import com.jtangt.wm.R;
import com.jtangt.wm.bean.Address;
import com.jtangt.wm.bean.Message_Post;
import com.jtangt.wm.bean.User;
import com.jtangt.wm.utils.DBDefine;
import com.jtangt.wm.utils.HttpUtils;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class My_addressActivity extends Activity {
    ImageView re;
    User user;
    ListView dizhi_lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wodedizi);
        init();
    }
    public void init(){
        re=findViewById(R.id.imgre);
        re.setOnClickListener(clickListener);
        dizhi_lv=findViewById(R.id.dizhi_lv);

        user=getuser();

        Message_Post message_post=new Message_Post();
        message_post.setType("address_getAll");
        Map<String,Object> map=new HashMap<>();
        map.put("user_id",user.getId());
        message_post.setMessage(JSON.toJSONString(map));
        postdata(message_post,1);

    }

    public void initlistview(String json){
        ArrayList arrayList=new ArrayList();
        List<Address> addresses=JSON.parseArray(json,Address.class);

        for (Address a :addresses) {
            HashMap<String,Object> map=new HashMap<>();
            map.put("address",a.getAddress());
            if(a.getStats()==1)
                map.put("stats",true);
            else
                map.put("stats",false);

            arrayList.add(map);
        }

        String from[]={"address","stats"};
        int to[]={R.id.dizhi_dz,R.id.dizhi_xz};

        SimpleAdapter simpleAdapter=new SimpleAdapter(this,arrayList,R.layout.address_yihang,from,to);
        ScrollView sv = (ScrollView) findViewById(R.id.dihzi_scr);
        sv.smoothScrollTo(0, 0);
        dizhi_lv.setAdapter(simpleAdapter);
//        dizhi_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                System.out.println(11111);
//
//
//            }
//        });
    }

    View.OnClickListener clickListener =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.imgre:
                    finish();
                    break;
            }
        }
    };
    View.OnClickListener click_check=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private User getuser(){
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

    private void postdata(Message_Post message_post,int what){
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

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    //System.out.println((String)msg.obj);
                    initlistview((String)msg.obj);
                    break;
                case -1:
                    ListView lstres = (ListView)findViewById(R.id.dizhi_lv);// 结果列表
                    for (int i = 0; i < lstres.getChildCount(); i++) {
                        LinearLayout ll = (LinearLayout)lstres.getChildAt(i);// 获得子级
                        CheckBox chkone = (CheckBox) ll.findViewById(R.id.dizhi_xz);// 从子级中获得控件
                        chkone.setChecked(false);
                    }
                    CheckBox chkone=((View)msg.obj).findViewById(R.id.dizhi_xz);
                    chkone.setChecked(true);
                    break;
            }

        }
    };

}