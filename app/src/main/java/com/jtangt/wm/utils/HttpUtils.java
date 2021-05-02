package com.jtangt.wm.utils;

import com.alibaba.fastjson.JSON;
import com.jtangt.wm.po.Message_Post;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtils {
    static String url_path="http://192.168.2.239:10003/info";

    public HttpUtils(){
    }


    public static String getJsonContent(Message_Post message_post) {
        try{
            URL url = new URL(url_path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("Post");
            connection.setDoInput(true);

            String body= JSON.toJSONString(message_post);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
            writer.write(body);
            writer.close();

            int code = connection.getResponseCode();
            if(code == 200){
                return changeInputStream(connection.getInputStream());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String changeInputStream(InputStream inputStream) {
        String jsonString = "";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int len = 0;
        byte[] data = new byte[1024];
        try{
            while ((len = inputStream.read(data)) != -1) {
                outputStream.write(data, 0, len);
            }
            jsonString = new String(outputStream.toByteArray());
            return jsonString;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
