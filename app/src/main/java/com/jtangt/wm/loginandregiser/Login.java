package com.jtangt.wm.loginandregiser;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.jtangt.wm.R;
import com.jtangt.wm.bean.Message_Post;
import com.jtangt.wm.bean.User;
import com.jtangt.wm.utils.DBDefine;
import com.jtangt.wm.utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    ProgressDialog waitingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        init();
    }

    public void postData(String account,String password){
        showWaitingDialog();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String json = null;
                try {
                    HttpUtils httpUtils = new HttpUtils();
                    Message_Post message_post=new Message_Post();
                    message_post.setType("user_login");
                    Map<String,String> jsonp=new HashMap<>();
                    jsonp.put("account",account);
                    jsonp.put("password",password);

                    message_post.setMessage(JSON.toJSONString(jsonp));

                    json = httpUtils.getJsonContent(message_post);
                    //System.out.println(json);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Message msg = handler.obtainMessage();
                msg.obj = json;
                msg.what=1;
                handler.sendMessage(msg);
            }
        }).start();
    }
    Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    //隐藏加载
                    waitingDialog.dismiss();
                    if(msg.obj.equals("{}")){
                        System.out.println("登录失败");
                        showtsDialog();

                        return;
                    }
                    setuser_data((String)msg.obj);
                    finish();
                    break;
            }

        }
    };

    private void setuser_data(String json){
        User user=JSON.parseObject(json,User.class);

        DBDefine dbDefine=new DBDefine(this);
        dbDefine.open();

        long re=dbDefine.insert(user);

        //System.out.println("ads"+re);
        //TODO
        dbDefine.close();
    }


    private void showWaitingDialog() {
        waitingDialog=
                new ProgressDialog(Login.this,R.style.Theme_AppCompat_Light_Dialog_Alert);
        waitingDialog.setTitle("等待服务器返回");
        waitingDialog.setMessage("等待中...");
        waitingDialog.setIndeterminate(true);
        waitingDialog.setCancelable(false);
        waitingDialog.show();
    }
    private void showtsDialog(){
        new AlertDialog.Builder(Login.this,R.style.Theme_AppCompat_Light_Dialog_Alert)
                .setTitle("提示")
                .setMessage("账号或者密码错误")
                .create().show();
    }

    private void init(){

        ImageView imgre=findViewById(R.id.imgre);
        Button login=findViewById(R.id.login_log);
        Button reg=findViewById(R.id.login_reg);
        imgre.setOnClickListener(clickListener);
        login.setOnClickListener(clickListener);
        reg.setOnClickListener(clickListener);



    }
    View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.login_log:
                    EditText account=findViewById(R.id.login_account);
                    EditText password=findViewById(R.id.login_password);
                    postData(account.getText().toString(),password.getText().toString());

                    break;
                case R.id.login_reg:
                    startActivity(new Intent(Login.this,Register.class));
                    break;
                case R.id.imgre:
                    finish();
                    break;
            }
        }
    };
}