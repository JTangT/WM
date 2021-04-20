package com.jtangt.wm;

import android.util.Log;

import com.jtangt.wm.po.ShopBean;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void jsontest(){
        String json="[{\"1\":1},{\"1\":2}]";
        List<ShopBean> shopInfos = null;
        JSONArray j=null;
        try {
            j=new JSONArray(json);
        }catch (Exception e){
            e.printStackTrace();
        }
        j.length();

        try {
            JSONObject t= (JSONObject) j.get(0);
            Log.e( "getShopInfos: ", t.toString());

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}