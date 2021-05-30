package com.jtangt.wm.loginandregiser;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.fastjson.JSON;
import com.jtangt.wm.R;
import com.jtangt.wm.bean.Message_Post;
import com.jtangt.wm.bean.User;
import com.jtangt.wm.utils.HttpUtils;
import com.jtangt.wm.utils.Base64ToPicture;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    private ImageView reg_img;
    private EditText reg_username;
    private EditText reg_password;
    private EditText reg_cpassword;
    private EditText reg_phone;
    private Button reg_confirm;
    private RadioGroup reg_sex;
    private ProgressDialog waitingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        init();
    }
    private void init(){
        reg_img=findViewById(R.id.reg_img);
        reg_username=findViewById(R.id.reg_username);
        reg_password=findViewById(R.id.reg_password);
        reg_cpassword=findViewById(R.id.reg_cpassword);
        reg_phone=findViewById(R.id.reg_phone);
        reg_confirm=findViewById(R.id.reg_confirm);
        reg_sex=(RadioGroup)findViewById(R.id.reg_sex);
        ImageView regre=findViewById(R.id.imgre);

        regre.setOnClickListener(clickListener);
        reg_img.setOnClickListener(clickListener);
        reg_confirm.setOnClickListener(clickListener);





    }

    View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.reg_img:
                    Intent intent = new Intent(Intent.ACTION_PICK, null);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent, 2);
                    break;
                case R.id.reg_confirm:
                    User user=new User();
                    Base64ToPicture base64ToPicture=new Base64ToPicture();
                    reg_img.setDrawingCacheEnabled(true);
                    user.setPicturebase64(base64ToPicture.encodeImageToString(Bitmap.createBitmap(reg_img.getDrawingCache())));
                    reg_img.setDrawingCacheEnabled(false);
                    for(int i=0;i<reg_sex.getChildCount();i++){
                        RadioButton radioButton=(RadioButton)reg_sex.getChildAt(i);
                        if(radioButton.isChecked()){
                            user.setSex(radioButton.getText().toString().equals("男")?1:0);
                            break;
                        }
                    }
                    if(!reg_password.getText().toString().equals(reg_cpassword.getText().toString())){
                        showtsDialog("确认密码不一致");
                        return;
                    }
                    user.setPhone(reg_phone.getText().toString());
                    user.setPassword(reg_password.getText().toString());
                    user.setUsername(reg_username.getText().toString());

                    if(user.getUsername().equals("")||user.getPassword().equals("")||user.getSex()==0||user.getPhone().equals("")){
                        showtsDialog("格式错误");
                        return;
                    }

                    String pattern = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
                    Pattern p = Pattern.compile(pattern);
                    Matcher m = p.matcher(user.getPhone());
                    boolean isMatch = m.matches();
                    if(!isMatch){
                        showtsDialog("手机号码格式错误");
                        return;
                    }

                    register(user);



                    break;
                case R.id.imgre:
                    finish();
                    break;

            }
        }
    };
    private void showtsDialog(String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage(msg);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
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
//                    //隐藏加载
//                    ProgressBar progressBar=view.findViewById(R.id.progressbar);
//                    progressBar.setVisibility(View.GONE);
//
//                    initlist((String)msg.obj);
                    waitingDialog.dismiss();
                    Map<String,Object> map=JSON.parseObject((String)msg.obj);
                    showtsDialog(map.get("message").toString());
                    break;
            }

        }
    };

    public void register(User user){
        showWaitingDialog();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String json = null;
                try {
                    HttpUtils httpUtils = new HttpUtils();
                    Message_Post message_post=new Message_Post();
                    message_post.setType("user_register");
                    message_post.setMessage(JSON.toJSONString(user));
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
    private void showWaitingDialog() {
        waitingDialog=
                new ProgressDialog(Register.this,R.style.Theme_AppCompat_Light_Dialog_Alert);
        waitingDialog.setTitle("等待服务器返回");
        waitingDialog.setMessage("等待中...");
        waitingDialog.setIndeterminate(true);
        waitingDialog.setCancelable(false);
        waitingDialog.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                reg_img.setImageURI(uri);
            }
        }
    }

}