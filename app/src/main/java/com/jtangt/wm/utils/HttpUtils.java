package com.jtangt.wm.utils;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.jtangt.wm.po.Message_Post;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtils {
    static String url_path="http://192.168.2.239:10003/info";

    public HttpUtils(){
    }


    public static String getJsonContent(Message_Post message_post) {
        try {
            //添加一个json格式数据
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            String json = com.alibaba.fastjson.JSON.toJSONString(message_post);
            //1 . 拿到OkHttpClient对象
            OkHttpClient client = new OkHttpClient();
            //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
            RequestBody requestBody = RequestBody.create(JSON, json);
            //3 . 构建Request,将FormBody作为Post方法的参数传入
            Request request = new Request.Builder()
                    .url(url_path)
                    .post(requestBody)
                    .build();

            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            return responseData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//    private static String changeInputStream(InputStream inputStream) {
//        String jsonString = "";
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        int len = 0;
//        byte[] data = new byte[1024];
//        try{
//            while ((len = inputStream.read(data)) != -1) {
//                outputStream.write(data, 0, len);
//            }
//            jsonString = new String(outputStream.toByteArray());
//            return jsonString;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
}
