package com.jtangt.wm.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.jtangt.wm.R;
import com.jtangt.wm.bean.Message_Post;
import com.jtangt.wm.bean.User;
import com.jtangt.wm.loginandregiser.Login;
import com.jtangt.wm.utils.DBDefine;
import com.jtangt.wm.utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;

public class DaiNaFragment extends Fragment {
    private View v;
    private User user;
    public DaiNaFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.tab_daina,container,false);
        user=getuser_id();
        init();
        return v;
    }
    private User getuser_id(){
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
    private void init(){
        Daina daina=new Daina();
        EditText bianhao=v.findViewById(R.id.daina_bianhao);
        EditText name=v.findViewById(R.id.daina_name);
        EditText phone=v.findViewById(R.id.daina_phone);
        EditText address=v.findViewById(R.id.daina_address);




        Button submit=v.findViewById(R.id.daina_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user==null){
                    Toast.makeText(getActivity(),"请先登录",Toast.LENGTH_LONG);
                    startActivity(new Intent(getActivity(), Login.class));
                    return;
                }
                daina.bianhao=bianhao.getText().toString();
                daina.name=name.getText().toString();
                daina.phone=phone.getText().toString();
                daina.address=phone.getText().toString();

                if(daina.bianhao.equals("")||daina.name.equals("")||daina.phone.equals("")||daina.address.equals("")){
                    Toast.makeText(getActivity(),"所有值不能为空",Toast.LENGTH_LONG);
                    return;
                }

                Map<String,Object> map=new HashMap<>();
                map.put("user_id", user.getId());
                map.put("dainainfo",JSON.toJSONString(daina));

                Message_Post message_post=new Message_Post();
                message_post.setType("daina");
                message_post.setMessage(JSON.toJSONString(map));
                getjson(message_post,1);

            }
        });

    }
    private void showtsDialog(String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("提示");
        builder.setMessage(msg);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    showtsDialog(msg.obj.toString());
                    break;
            }

        }
    };
    public void getjson(Message_Post message_post, int what){
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

    public class Daina{
        public String bianhao;
        public String name;
        public String phone;
        public String address;
        public Daina(){}

    }
}