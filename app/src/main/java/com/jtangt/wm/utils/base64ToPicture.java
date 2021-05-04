package com.jtangt.wm.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class base64ToPicture {
    //解码
    public Bitmap sendImage(String imgAddress) {

        byte[] input = Base64.decode(imgAddress, Base64.DEFAULT);

        Bitmap bitmap = BitmapFactory.decodeByteArray(input, 0, input.length);

        return bitmap;

    }

    //编码
    public String encodeImageToString(Bitmap bitmap) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        //读取图片到ByteArrayOutputStream

        bitmap.compress(Bitmap.CompressFormat.PNG, 40, outputStream); //参数如果为100那么就不压缩

        byte[] bytes = outputStream.toByteArray();

        String strImg = Base64.encodeToString(bytes, Base64.DEFAULT);

        return strImg;

    }

}
